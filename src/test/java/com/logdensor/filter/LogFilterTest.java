package com.logdensor.filter;

import com.logdensor.model.LogEntry;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LogFilterTest {

    @Test
    public void testFilterByLevel() {
        LogFilter filter = new LogFilter();
        List<LogEntry> logs = Arrays.asList(
                new LogEntry(LocalDateTime.now(), "INFO", "Some info"),
                new LogEntry(LocalDateTime.now(), "ERROR", "Some error")
        );

        FilterCriteria criteria = new FilterCriteria();
        criteria.setLogLevel("ERROR");

        List<LogEntry> result = filter.applyFilters(logs, criteria);

        assertEquals(1, result.size());
        assertEquals("ERROR", result.get(0).getLevel());
    }
}
