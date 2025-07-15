package com.logdensor.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class LogEntryTest {

    @Test
    public void testLogEntryCreation() {
        LocalDateTime now = LocalDateTime.now();
        LogEntry entry = new LogEntry(now, "INFO", "User login", "userId=101");

        assertEquals("INFO", entry.getLevel());
        assertEquals("User login", entry.getMessage());
        assertEquals("userId=101", entry.getMetadata());
        assertEquals(now, entry.getTimestamp());
    }
}
