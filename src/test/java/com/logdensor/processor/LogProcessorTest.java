package com.logdensor.processor;

import com.logdensor.model.LogEntry;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LogProcessorTest {

    @Test
    public void testProcessLogs() throws Exception {
        String testFilePath = "logs/test_sample.log";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFilePath))) {
            writer.write("[2025-07-11 10:01:15] INFO: Test log - userId=1\n");
        }

        LogProcessor processor = new LogProcessor();
        processor.processLogs("logs");

        List<LogEntry> logs = LogProcessor.parsedLogs;
        assertTrue(logs.size() >= 1);
    }
}
