package com.logdensor.parser;

import com.logdensor.model.LogEntry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogParserTest {

    @Test
    public void testValidLogParsing() {
        LogParser parser = new LogParser();
        String line = "[2025-07-15 10:01:00] INFO: User logged in";

        LogEntry entry = parser.parse(line);
        assertNotNull(entry);
        assertEquals("INFO", entry.getLevel());
        assertEquals("User logged in", entry.getMessage());
    }

    @Test
    public void testInvalidLogLine() {
        LogParser parser = new LogParser();
        assertNull(parser.parse("Invalid log line"));
    }
}
