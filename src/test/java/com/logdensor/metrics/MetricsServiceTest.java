package com.logdensor.metrics;

import com.logdensor.model.LogEntry;
import com.logdensor.model.MetricsReport;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MetricsServiceTest {

    @Test
    public void testComputeMetrics() {
        MetricsService service = new MetricsService();
        List<LogEntry> logs = List.of(
                new LogEntry(LocalDateTime.now(), "INFO", "User login"),
                new LogEntry(LocalDateTime.now(), "ERROR", "Database error"),
                new LogEntry(LocalDateTime.now(), "ERROR", "Database error"),
                new LogEntry(LocalDateTime.now(), "INFO", "User logout"),
                new LogEntry(LocalDateTime.now(), "WARN", "Slow response")
        );

        MetricsReport report = service.computeMetrics(logs);

        assertNotNull(report.getLevelCounts());
        assertNotNull(report.getPeakHours());
        assertNotNull(report.getFrequentErrors());
        assertNotNull(report.getLoginSessions());
        assertNotNull(report.getAnomalies());
    }
}
