package com.logdensor.processor;

import com.logdensor.model.LogEntry;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LogProcessorTest {

    @Test
    public void testProcessFile() throws IOException {
        File tempLog = File.createTempFile("test-log", ".log");
        try (PrintWriter pw = new PrintWriter(tempLog)) {
            pw.println("[2025-07-15 10:00:00] ERROR: Something went wrong");
        }

        LogProcessor processor = new LogProcessor();
        processor.processLogs(tempLog.getParent());

        List<LogEntry> results = LogProcessor.parsedLogs;
        assertFalse(results.isEmpty());
    }
}
