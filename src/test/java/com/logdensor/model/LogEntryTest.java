package com.logdensor.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class LogEntryTest {

    @Test
    public void testLogEntryCreation() {
        LocalDateTime now = LocalDateTime.now();
        LogEntry entry = new LogEntry(now, "INFO", "Test message");

        assertEquals("INFO", entry.getLevel());
        assertEquals("Test message", entry.getMessage());
        assertEquals(now, entry.getTimestamp());
    }
}
