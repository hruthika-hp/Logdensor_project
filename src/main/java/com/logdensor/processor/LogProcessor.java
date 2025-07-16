package com.logdensor.processor;

import com.logdensor.model.LogEntry;
import com.logdensor.parser.LogParser;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogProcessor {
    public static final List<LogEntry> parsedLogs = new CopyOnWriteArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(6);
    private final LogParser parser = new LogParser();

    public void processLogs(String folderPath) {
        try {
            Files.list(Paths.get(folderPath))
                .filter(p -> p.toString().endsWith(".log"))
                .forEach(file -> executor.submit(() -> processFile(file.toFile())));

            executor.shutdown();
            while (!executor.isTerminated()) {
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogEntry entry = parser.parseLine(line);
                if (entry != null) parsedLogs.add(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
