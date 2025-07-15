package com.logdensor.metrics;

import com.logdensor.model.*;
import com.logdensor.processor.LogProcessor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MetricsService {
    public MetricsReport computeMetrics() {
        List<LogEntry> logs = LogProcessor.parsedLogs;
        MetricsReport report = new MetricsReport();

        Map<String, Integer> levelCount = new HashMap<>();
        Map<String, Integer> errorFreq = new HashMap<>();
        Map<String, LocalDateTime> loginMap = new HashMap<>();
        List<LoginSession> sessions = new ArrayList<>();
        Map<String, Integer> hourMap = new HashMap<>();
        List<Anomaly> anomalies = new ArrayList<>();

        for (LogEntry log : logs) {
            // a. Count by log level
            levelCount.put(log.getLevel(), levelCount.getOrDefault(log.getLevel(), 0) + 1);

            // b. Peak hours
            String hour = log.getTimestamp().format(DateTimeFormatter.ofPattern("HH:00"));
            hourMap.put(hour, hourMap.getOrDefault(hour, 0) + 1);

            // c. Frequent errors
            if ("ERROR".equalsIgnoreCase(log.getLevel())) {
                errorFreq.put(log.getMessage(), errorFreq.getOrDefault(log.getMessage(), 0) + 1);
            }

            // d. Login/logout session
            if (log.getMessage().toLowerCase().contains("login")) {
                String userId = extractUserId(log.getMetadata());
                loginMap.put(userId, log.getTimestamp());
            } else if (log.getMessage().toLowerCase().contains("logout")) {
                String userId = extractUserId(log.getMetadata());
                if (loginMap.containsKey(userId)) {
                    LocalDateTime login = loginMap.remove(userId);
                    sessions.add(new LoginSession(userId, login, log.getTimestamp()));
                }
            }
        }

        // e. Anomaly detection: spike in ERRORs within 5 minutes
        logs.sort(Comparator.comparing(LogEntry::getTimestamp));
        for (int i = 0; i < logs.size(); i++) {
            if (!"ERROR".equalsIgnoreCase(logs.get(i).getLevel())) continue;

            int count = 1;
            LocalDateTime start = logs.get(i).getTimestamp();

            for (int j = i + 1; j < logs.size(); j++) {
                if (!"ERROR".equalsIgnoreCase(logs.get(j).getLevel())) continue;

                long minutes = java.time.Duration.between(start, logs.get(j).getTimestamp()).toMinutes();
                if (minutes <= 5) {
                    count++;
                } else {
                    break;
                }
            }

            if (count >= 5) {
                anomalies.add(new Anomaly(start, "Spike in ERRORs", "HIGH"));
            }
        }

        // Final report fields
        report.setLogLevelCounts(levelCount);

        List<MetricsReport.FrequentError> errorList = errorFreq.entrySet()
                .stream()
                .map(e -> new MetricsReport.FrequentError(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        report.setFrequentErrorMessages(errorList);

        List<PeakHour> peakHours = hourMap.entrySet()
                .stream()
                .map(e -> new PeakHour(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        report.setPeakHours(peakHours);

        report.setLoginLogoutDurations(sessions);
        report.setAnomalies(anomalies);

        return report;
    }

    private String extractUserId(String metadata) {
        if (metadata != null && metadata.contains("userId=")) {
            return metadata.substring(metadata.indexOf("userId=") + 7).trim();
        }
        return "unknown";
    }
}
