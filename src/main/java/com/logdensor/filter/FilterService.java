package com.logdensor.filter;

import com.logdensor.model.LogEntry;
import com.logdensor.parser.LogParser;
import com.logdensor.report.writer.JsonReportWriter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class FilterService {
    private final LogParser parser = new LogParser();

    public void filterByLevel(String level) {
        List<LogEntry> logs = parser.parseAllLogs("logs");
        List<LogEntry> filtered = logs.stream()
                .filter(log -> log.getLevel().equalsIgnoreCase(level))
                .toList();
        new JsonReportWriter().write(filtered, "reports/filtered-" + level.toLowerCase() + "-only.json");
    }

    public void filterByTag(String tag) {
        List<LogEntry> logs = parser.parseAllLogs("logs");
        List<LogEntry> filtered = logs.stream()
                .filter(log -> log.getMessage().toLowerCase().contains(tag.toLowerCase()))
                .toList();
        new JsonReportWriter().write(filtered, "reports/filtered-tag-" + tag.toLowerCase().replace(" ", "-") + ".json");
    }

    public void filterByTimeRange(LocalDateTime start, LocalDateTime end, boolean splitByLevel) {
        List<LogEntry> logs = parser.parseAllLogs("logs");

        List<LogEntry> filtered = logs.stream()
                .filter(log -> {
                    LocalDateTime ts = log.getTimestamp();
                    return ts != null && !ts.isBefore(start) && !ts.isAfter(end);
                })
                .toList();

        new JsonReportWriter().write(filtered, "reports/filtered-time-range.json");

        if (splitByLevel) {
            Map<String, List<LogEntry>> grouped = filtered.stream()
                    .collect(Collectors.groupingBy(LogEntry::getLevel));
            for (Map.Entry<String, List<LogEntry>> entry : grouped.entrySet()) {
                String level = entry.getKey().toLowerCase();
                new JsonReportWriter().write(entry.getValue(), "reports/filtered-time-range-" + level + ".json");
            }
        }
    }
}
