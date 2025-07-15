package com.logdensor.filter;

import com.logdensor.model.LogEntry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FilterService {

    public List<LogEntry> applyFilters(List<LogEntry> logs, FilterCriteria criteria) {
        List<LogEntry> result = new ArrayList<>();

        for (LogEntry log : logs) {
            boolean matches = true;

            if (criteria.getLevel() != null && !criteria.getLevel().equalsIgnoreCase(log.getLevel())) {
                matches = false;
            }

            if (criteria.getTag() != null && !log.getMessage().toLowerCase().contains(criteria.getTag().toLowerCase())) {
                matches = false;
            }

            if (criteria.getTimeRange() != null) {
                LocalDateTime start = LocalDateTime.parse(criteria.getTimeRange().getStart());
                LocalDateTime end = LocalDateTime.parse(criteria.getTimeRange().getEnd());

                if (log.getTimestamp().isBefore(start) || log.getTimestamp().isAfter(end)) {
                    matches = false;
                }
            }

            if (matches) {
                result.add(log);
            }
        }

        return result;
    }
}

