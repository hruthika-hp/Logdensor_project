package com.logdensor.report.writer;

import com.logdensor.model.MetricsReport;

import java.io.FileWriter;
import java.util.Map;

public class CsvReportWriter {
    public void write(MetricsReport report, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Log Level,Count\n");
            for (Map.Entry<String, Integer> entry : report.getLogLevelCounts().entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
            System.out.println("✅ CSV report written to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
