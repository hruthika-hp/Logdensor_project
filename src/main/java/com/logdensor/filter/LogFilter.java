package com.logdensor.filter;

import com.logdensor.model.FilterCriteria;
import com.logdensor.model.LogEntry;

import java.util.List;
import java.util.stream.Collectors;

public class LogFilter {
    public List<LogEntry> applyFilters(List<LogEntry> logs, FilterCriteria criteria) {
        return logs.stream()
            .filter(log -> {
                boolean levelMatch = criteria.getLogLevel() == null || log.getLevel().equalsIgnoreCase(criteria.getLogLevel());
                boolean tagMatch = criteria.getCustomTag() == null || log.getMessage().toLowerCase().contains(criteria.getCustomTag().toLowerCase());
                boolean timeMatch = (criteria.getStartTime() == null || !log.getTimestamp().isBefore(criteria.getStartTime())) &&
                                    (criteria.getEndTime() == null || !log.getTimestamp().isAfter(criteria.getEndTime()));
                return levelMatch && tagMatch && timeMatch;
            })
            .collect(Collectors.toList());
    }
}
