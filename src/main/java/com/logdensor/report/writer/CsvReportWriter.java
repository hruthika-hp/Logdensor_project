package com.logdensor.report.writer;

import com.logdensor.model.MetricsReport;

import java.io.FileWriter;
import java.io.PrintWriter;

public class CsvReportWriter {

    public void write(MetricsReport report, String filePath) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            out.println("Metric,Value");

            // Log level counts
            report.getLevelCounts().forEach((level, count) ->
                out.println("Log Level - " + level + "," + count)
            );

            // Peak hours
            report.getPeakHours().forEach(ph ->
                out.println("Peak Hour - " + ph.getHour() + "," + ph.getCount())
            );

            // Frequent Errors
            int i = 1;
            for (String err : report.getFrequentErrors()) {
                out.println("Frequent Error " + (i++) + "," + err);
            }

            // Login Sessions
            i = 1;
            for (var session : report.getLoginSessions()) {
                out.println("Login Session " + (i++) + ",User: " + session.getUserId()
                        + " From: " + session.getLoginTime()
                        + " To: " + session.getLogoutTime());
            }

            // Anomalies
            i = 1;
            for (var anomaly : report.getAnomalies()) {
                out.println("Anomaly " + (i++) + "," + anomaly.getTimestamp() + " " + anomaly.getMessage());
            }

            System.out.println("✅ CSV report written to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
