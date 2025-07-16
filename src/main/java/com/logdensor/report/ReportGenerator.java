package com.logdensor.report;

import com.logdensor.metrics.MetricsService;
import com.logdensor.model.MetricsReport;
import com.logdensor.processor.LogProcessor;
import com.logdensor.report.writer.CsvReportWriter;
import com.logdensor.report.writer.ExcelReportWriter;
import com.logdensor.report.writer.JsonReportWriter;

public class ReportGenerator {

    private final JsonReportWriter jsonWriter;
    private final CsvReportWriter csvWriter;
    private final ExcelReportWriter excelWriter;

    public ReportGenerator(JsonReportWriter jsonWriter, CsvReportWriter csvWriter, ExcelReportWriter excelWriter) {
        this.jsonWriter = jsonWriter;
        this.csvWriter = csvWriter;
        this.excelWriter = excelWriter;
    }

    public void generateSummary(ReportFormat format) {
        MetricsService metricsService = new MetricsService();
        MetricsReport report = metricsService.computeMetrics(LogProcessor.parsedLogs);

        switch (format) {
            case JSON -> jsonWriter.write(report, "reports/summary.json");
            case CSV -> csvWriter.write(report, "reports/summary.csv");
            case EXCEL -> excelWriter.write(report, "reports/summary.xlsx");
        }
    }

    // ✅ Used in ReportGeneratorTest to avoid file creation
    public void generateReports() {
        MetricsService metricsService = new MetricsService();
        MetricsReport report = metricsService.computeMetrics(LogProcessor.parsedLogs);

        jsonWriter.write(report, "reports/summary.json");
        csvWriter.write(report, "reports/summary.csv");
        excelWriter.write(report, "reports/summary.xlsx");
    }
}
