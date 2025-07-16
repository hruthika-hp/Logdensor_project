package com.logdensor.metrics;

import com.logdensor.model.Anomaly;
import com.logdensor.model.LogEntry;
import com.logdensor.model.LoginSession;
import com.logdensor.model.MetricsReport;
import com.logdensor.model.PeakHour;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MetricsService {

    public MetricsReport computeMetrics(List<LogEntry> logs) {
        MetricsReport report = new MetricsReport();

        report.setLevelCounts(computeLogLevelCounts(logs));
        report.setPeakHours(findPeakHours(logs));
        report.setFrequentErrors(findFrequentErrors(logs));
        report.setLoginSessions(calculateLoginDurations(logs));
        report.setAnomalies(detectAnomalies(logs));

        return report;
    }

    private Map<String, Long> computeLogLevelCounts(List<LogEntry> logs) {
        return logs.stream()
                .collect(Collectors.groupingBy(LogEntry::getLevel, Collectors.counting()));
    }

    private List<PeakHour> findPeakHours(List<LogEntry> logs) {
        Map<String, Long> hourlyCount = logs.stream()
                .filter(l -> l.getTimestamp() != null)
                .collect(Collectors.groupingBy(
                        l -> l.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH")),
                        Collectors.counting()
                ));

        return hourlyCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(e -> new PeakHour(e.getKey(), e.getValue()))
                .toList();
    }

    private List<String> findFrequentErrors(List<LogEntry> logs) {
        Map<String, Long> messageFreq = logs.stream()
                .filter(l -> "ERROR".equalsIgnoreCase(l.getLevel()))
                .collect(Collectors.groupingBy(LogEntry::getMessage, Collectors.counting()));

        return messageFreq.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();
    }

    private List<LoginSession> calculateLoginDurations(List<LogEntry> logs) {
        List<LoginSession> sessions = new ArrayList<>();
        Map<String, LocalDateTime> loginTimes = new HashMap<>();

        for (LogEntry log : logs) {
            String level = log.getLevel();
            String message = log.getMessage().toLowerCase();
            LocalDateTime timestamp = log.getTimestamp();

            if (message.contains("login")) {
                loginTimes.put(level, timestamp);
            } else if (message.contains("logout") && loginTimes.containsKey(level)) {
                LocalDateTime loginTime = loginTimes.remove(level);
                sessions.add(new LoginSession(level, loginTime, timestamp));
            }
        }

        return sessions;
    }

    private List<Anomaly> detectAnomalies(List<LogEntry> logs) {
        List<Anomaly> anomalies = new ArrayList<>();
        Map<LocalDateTime, Long> errorCounts = new TreeMap<>();

        for (LogEntry log : logs) {
            if ("ERROR".equalsIgnoreCase(log.getLevel())) {
                LocalDateTime minute = log.getTimestamp().withSecond(0).withNano(0);
                errorCounts.put(minute, errorCounts.getOrDefault(minute, 0L) + 1);
            }
        }

        for (Map.Entry<LocalDateTime, Long> entry : errorCounts.entrySet()) {
            if (entry.getValue() >= 5) {
                anomalies.add(new Anomaly(entry.getKey(), "ERROR", "High ERROR volume: " + entry.getValue()));
            }
        }

        return anomalies;
    }
}
