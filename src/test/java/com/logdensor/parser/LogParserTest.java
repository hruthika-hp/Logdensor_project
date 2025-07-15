package com.logdensor.parser;

import com.logdensor.model.LogEntry;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class LogParserTest {

    @Test
    public void testParseValidLogLine() {
        LogParser parser = new LogParser();
        String line = "[2025-07-11 10:01:15] INFO: User login success - userId=1234";

        LogEntry entry = parser.parse(line);

        assertNotNull(entry);
        assertEquals("INFO", entry.getLevel());
        assertTrue(entry.getMessage().contains("User login success"));
        assertEquals("userId=1234", entry.getMetadata());
    }

    @Test
    public void testParseInvalidLogLine() {
        LogParser parser = new LogParser();
        String line = "Malformed log entry";

        LogEntry entry = parser.parse(line);

        assertNull(entry);
    }
}
