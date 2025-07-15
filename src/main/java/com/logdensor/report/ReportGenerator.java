package com.logdensor.report;

import com.logdensor.filter.FilterCriteria;
import com.logdensor.filter.FilterService;
import com.logdensor.metrics.MetricsService;
import com.logdensor.model.LogEntry;
import com.logdensor.model.MetricsReport;
import com.logdensor.processor.LogProcessor;
import com.logdensor.report.writer.CsvReportWriter;
import com.logdensor.report.writer.ExcelReportWriter;
import com.logdensor.report.writer.JsonReportWriter;

import java.util.List;

public class ReportGenerator {

    public void generateReports() {
        // Step 1: Summary Report
        MetricsService service = new MetricsService();
        MetricsReport report = service.computeMetrics();

        new JsonReportWriter().write(report, "reports/summary.json");
        new CsvReportWriter().write(report, "reports/summary.csv");
        new ExcelReportWriter().write(report, "reports/summary.xlsx");

        // Step 2: Filtered Reports
        List<LogEntry> logs = LogProcessor.parsedLogs;
        FilterService filterService = new FilterService();

        // 2a. Filtered by log level: ERROR
        FilterCriteria errorFilter = new FilterCriteria();
        errorFilter.setLevel("ERROR");
        List<LogEntry> errorLogs = filterService.applyFilters(logs, errorFilter);
        new JsonReportWriter().writeList(errorLogs, "reports/filtered-error-only.json");

        // 2b. Filtered by log level: WARN
        FilterCriteria warnFilter = new FilterCriteria();
        warnFilter.setLevel("WARN");
        List<LogEntry> warnLogs = filterService.applyFilters(logs, warnFilter);
        new JsonReportWriter().writeList(warnLogs, "reports/filtered-warn-only.json");

        // 2c. Filtered by tag: job created
        FilterCriteria jobTag = new FilterCriteria();
        jobTag.setTag("job created");
        List<LogEntry> jobLogs = filterService.applyFilters(logs, jobTag);
        new JsonReportWriter().writeList(jobLogs, "reports/filtered-tag-job-created.json");

        // 2d. Filtered by tag: login success
        FilterCriteria loginTag = new FilterCriteria();
        loginTag.setTag("login success");
        List<LogEntry> loginLogs = filterService.applyFilters(logs, loginTag);
        new JsonReportWriter().writeList(loginLogs, "reports/filtered-tag-login-success.json");

        // 2e. Filtered by time range (e.g. 10:00–11:00 on 2025-07-11)
        FilterCriteria timeRange = new FilterCriteria();
        timeRange.setTimeRange(new FilterCriteria.TimeRange("2025-07-11T10:00:00", "2025-07-11T11:00:00"));
        List<LogEntry> timeLogs = filterService.applyFilters(logs, timeRange);
        new JsonReportWriter().writeList(timeLogs, "reports/filtered-time-range.json");
    }
}
