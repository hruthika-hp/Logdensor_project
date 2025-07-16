package com.logdensor.parser;

import com.logdensor.model.LogEntry;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LogParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<LogEntry> parseAllLogs(String folderPath) {
        List<LogEntry> allLogs = new ArrayList<>();
        try {
            Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".log"))
                    .forEach(p -> allLogs.addAll(parseLogFile(p.toFile())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allLogs;
    }

    private List<LogEntry> parseLogFile(File file) {
        List<LogEntry> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogEntry entry = parseLine(line);
                if (entry != null) {
                    logs.add(entry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logs;
    }

    public LogEntry parseLine(String line) {
        try {
            if (!line.startsWith("[")) return null;

            int endIndex = line.indexOf("]");
            String timestampStr = line.substring(1, endIndex);
            LocalDateTime timestamp = LocalDateTime.parse(timestampStr, FORMATTER);

            String remainder = line.substring(endIndex + 2);
            String[] parts = remainder.split(":", 2);
            if (parts.length < 2) return null;

            String level = parts[0].trim();
            String message = parts[1].trim();

            return new LogEntry(timestamp, level, message);
        } catch (Exception e) {
            return null;
        }
    }

    // ✅ Add this method so that your test code works:
    public LogEntry parse(String line) {
        return parseLine(line);
    }
}
