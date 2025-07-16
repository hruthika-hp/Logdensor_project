package com.logdensor.report;

import com.logdensor.model.LogEntry;
import com.logdensor.model.MetricsReport;
import com.logdensor.processor.LogProcessor;
import com.logdensor.report.writer.CsvReportWriter;
import com.logdensor.report.writer.ExcelReportWriter;
import com.logdensor.report.writer.JsonReportWriter;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

public class ReportGeneratorTest {

    @Test
    public void testGenerateReports_withMockWriters() {
        // ✅ Add dummy logs
        LogProcessor.parsedLogs.clear();
        LogProcessor.parsedLogs.addAll(List.of(
                new LogEntry(LocalDateTime.now(), "INFO", "User login"),
                new LogEntry(LocalDateTime.now(), "ERROR", "Database down")
        ));

        // ✅ Create mocks
        JsonReportWriter jsonWriter = mock(JsonReportWriter.class);
        CsvReportWriter csvWriter = mock(CsvReportWriter.class);
        ExcelReportWriter excelWriter = mock(ExcelReportWriter.class);

        // ✅ Use mocks in generator
        ReportGenerator generator = new ReportGenerator(jsonWriter, csvWriter, excelWriter);
        generator.generateReports();

        // ✅ Verify that methods were called (but no file is created!)
        verify(jsonWriter).write(any(MetricsReport.class), eq("reports/summary.json"));
        verify(csvWriter).write(any(MetricsReport.class), eq("reports/summary.csv"));
        verify(excelWriter).write(any(MetricsReport.class), eq("reports/summary.xlsx"));
    }
}
