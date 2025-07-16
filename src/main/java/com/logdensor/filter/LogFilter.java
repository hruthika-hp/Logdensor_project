package com.logdensor.filter;

import com.logdensor.model.LogEntry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class LogFilter {

    public List<LogEntry> applyFilters(List<LogEntry> logs, FilterCriteria criteria) {
        return logs.stream()
                .filter(log -> criteria.getLogLevel() == null || log.getLevel().equalsIgnoreCase(criteria.getLogLevel()))
                .filter(log -> criteria.getTag() == null || log.getMessage().toLowerCase().contains(criteria.getTag().toLowerCase()))
                .filter(log -> {
                    if (criteria.getStartTime() == null && criteria.getEndTime() == null) return true;
                    LocalDateTime timestamp = log.getTimestamp();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime start = criteria.getStartTime() != null ? LocalDateTime.parse(criteria.getStartTime(), formatter) : null;
                    LocalDateTime end = criteria.getEndTime() != null ? LocalDateTime.parse(criteria.getEndTime(), formatter) : null;
                    return (start == null || !timestamp.isBefore(start)) && (end == null || !timestamp.isAfter(end));
                })
                .collect(Collectors.toList());
    }
}
