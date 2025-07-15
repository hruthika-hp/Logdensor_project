package com.logdensor.report.writer;

import com.logdensor.model.MetricsReport;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

public class ExcelReportWriter {
    public void write(MetricsReport report, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Summary");

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Log Level");
            header.createCell(1).setCellValue("Count");

            // Data
            int rowNum = 1;
            for (var entry : report.getLogLevelCounts().entrySet()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            try (FileOutputStream out = new FileOutputStream(filePath)) {
                workbook.write(out);
            }
            System.out.println("✅ Excel report written to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
