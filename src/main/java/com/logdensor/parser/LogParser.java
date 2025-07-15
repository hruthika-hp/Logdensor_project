package com.logdensor.parser;

import com.logdensor.model.LogEntry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogParser {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LogEntry parse(String line) {
        try {
            // Example line:
            // [2025-07-11 10:01:15] INFO: User login success - userId=1234
            int timestampStart = line.indexOf('[') + 1;
            int timestampEnd = line.indexOf(']');
            String timestampStr = line.substring(timestampStart, timestampEnd);
            LocalDateTime timestamp = LocalDateTime.parse(timestampStr, formatter);

            int levelEnd = line.indexOf(':', timestampEnd + 2);
            String level = line.substring(timestampEnd + 2, levelEnd).trim();

            String message = line.substring(levelEnd + 1).trim();

            String metadata = "";
            if (message.contains("userId=")) {
                metadata = message.substring(message.indexOf("userId="));
            }

            return new LogEntry(timestamp, level, message, metadata);
        } catch (Exception e) {
            return null; // skip malformed lines
        }
    }
}
