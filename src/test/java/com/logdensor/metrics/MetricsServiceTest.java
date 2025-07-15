package com.logdensor.metrics;

import com.logdensor.model.MetricsReport;
import com.logdensor.processor.LogProcessor;
import com.logdensor.model.LogEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MetricsServiceTest {

    @BeforeEach
    public void setup() {
        LogProcessor.parsedLogs.clear();
        LogProcessor.parsedLogs.add(new LogEntry(LocalDateTime.now(), "INFO", "User login success", "userId=101"));
        LogProcessor.parsedLogs.add(new LogEntry(LocalDateTime.now(), "ERROR", "DB connection timeout", ""));
    }

    @Test
    public void testComputeMetrics() {
        MetricsService service = new MetricsService();
        MetricsReport report = service.computeMetrics();

        assertNotNull(report);
        assertEquals(2, report.getLogLevelCounts().values().stream().mapToInt(Integer::intValue).sum());
    }
}
