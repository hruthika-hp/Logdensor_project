package com.logdensor.processor;

import com.logdensor.model.LogEntry;
import com.logdensor.parser.LogParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LogProcessor {

    private final LogParser parser = new LogParser();
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    // Shared list accessible across threads
    public static final List<LogEntry> parsedLogs = new CopyOnWriteArrayList<>();

    public void processLogs(String folderPath) {
        try {
            Files.list(Paths.get(folderPath))
                .filter(file -> file.toString().endsWith(".log")) // FIXED HERE
                .forEach(file -> executor.submit(() -> processFile(file.toFile())));

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.HOURS); // Wait for completion

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                LogEntry entry = parser.parse(line);
                if (entry != null) {
                    parsedLogs.add(entry);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
