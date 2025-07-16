package com.logdensor;

import com.logdensor.filter.FilterService;
import com.logdensor.metrics.MetricsService;
import com.logdensor.model.LogEntry;
import com.logdensor.processor.LogProcessor;
import com.logdensor.report.ReportFormat;
import com.logdensor.report.ReportGenerator;
import com.logdensor.report.writer.CsvReportWriter;
import com.logdensor.report.writer.ExcelReportWriter;
import com.logdensor.report.writer.JsonReportWriter;
import com.logdensor.util.TimeUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LogProcessor processor = new LogProcessor();

        System.out.println("\n📂 Loading log files from /logs...");
        processor.processLogs("logs");
        System.out.println("✅ Logs loaded: " + LogProcessor.parsedLogs.size() + " entries\n");

        while (true) {
            System.out.println("=== Select an Option ===");
            System.out.println("1. Generate Summary Report");
            System.out.println("2. Filter by Log Level");
            System.out.println("3. Filter by Custom Message Tag");
            System.out.println("4. Filter by Time Range");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter format (json/csv/excel): ");
                    String formatInput = sc.nextLine().trim().toLowerCase();
                    ReportFormat format = ReportFormat.fromString(formatInput);
                    if (format == null) {
                        System.out.println("❌ Invalid format.");
                        break;
                    }

                    ReportGenerator generator = new ReportGenerator(
                        new JsonReportWriter(),
                        new CsvReportWriter(),
                        new ExcelReportWriter()
                    );
                    generator.generateSummary(format);
                    break;

                case "2":
                    handleLogLevelFilter(sc);
                    break;

                case "3":
                    handleCustomTagFilter(sc);
                    break;

                case "4":
                    handleTimeRangeFilter(sc);
                    break;

                case "0":
                    System.out.println("👋 Exiting. Thank you!");
                    return;

                default:
                    System.out.println("❌ Invalid choice. Try again.\n");
            }
        }
    }

    private static void handleLogLevelFilter(Scanner sc) {
        Set<String> levels = LogProcessor.parsedLogs.stream()
                .map(LogEntry::getLevel)
                .collect(Collectors.toSet());

        System.out.print("Type starting letter: ");
        String prefix = sc.nextLine().trim().toLowerCase();

        List<String> matched = levels.stream()
                .filter(level -> level.toLowerCase().startsWith(prefix))
                .sorted()
                .toList();

        if (matched.isEmpty()) {
            System.out.println("❌ No matching levels found.");
            return;
        }

        System.out.println("Matching Levels:");
        for (int i = 0; i < matched.size(); i++) {
            System.out.println((i + 1) + ". " + matched.get(i));
        }

        System.out.print("Select one: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;
        if (index < 0 || index >= matched.size()) {
            System.out.println("❌ Invalid selection.");
            return;
        }

        new FilterService().filterByLevel(matched.get(index));
    }

    private static void handleCustomTagFilter(Scanner sc) {
        Set<String> tags = LogProcessor.parsedLogs.stream()
                .map(log -> log.getMessage().toLowerCase())
                .collect(Collectors.toSet());

        System.out.print("Type starting letter: ");
        String prefix = sc.nextLine().trim().toLowerCase();

        List<String> filteredTags = tags.stream()
                .filter(tag -> tag.startsWith(prefix))
                .limit(20)
                .sorted()
                .toList();

        if (filteredTags.isEmpty()) {
            System.out.println("❌ No matching tags found.");
            return;
        }

        System.out.println("Matching Tags:");
        for (int i = 0; i < filteredTags.size(); i++) {
            System.out.println((i + 1) + ". " + filteredTags.get(i));
        }

        System.out.print("Select one: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;
        if (index < 0 || index >= filteredTags.size()) {
            System.out.println("❌ Invalid selection.");
            return;
        }

        new FilterService().filterByTag(filteredTags.get(index));
    }

    private static void handleTimeRangeFilter(Scanner sc) {
        try {
            System.out.print("Enter start datetime (yyyy-MM-dd HH:mm:ss): ");
            LocalDateTime start = TimeUtils.parse(sc.nextLine().trim());

            System.out.print("Enter end datetime (yyyy-MM-dd HH:mm:ss): ");
            LocalDateTime end = TimeUtils.parse(sc.nextLine().trim());

            System.out.print("Also split by level? (yes/no): ");
            boolean splitByLevel = sc.nextLine().trim().equalsIgnoreCase("yes");

            new FilterService().filterByTimeRange(start, end, splitByLevel);
        } catch (Exception e) {
            System.out.println("❌ Invalid date format.");
        }
    }
}
