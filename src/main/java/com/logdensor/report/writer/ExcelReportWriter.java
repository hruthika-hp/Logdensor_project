package com.logdensor.report.writer;

import com.logdensor.model.MetricsReport;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

public class ExcelReportWriter {
    public void write(MetricsReport report, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Summary");
            int row = 0;

            // Style for better appearance (optional)
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);

            Row row1 = sheet.createRow(row++);
            row1.createCell(0).setCellValue("Log Level Counts");
            row1.createCell(1).setCellValue(report.getLevelCounts().toString());

            Row row2 = sheet.createRow(row++);
            row2.createCell(0).setCellValue("Peak Hours");
            row2.createCell(1).setCellValue(report.getPeakHours().toString());

            Row row3 = sheet.createRow(row++);
            row3.createCell(0).setCellValue("Frequent Errors");
            row3.createCell(1).setCellValue(report.getFrequentErrors().toString());

            Row row4 = sheet.createRow(row++);
            row4.createCell(0).setCellValue("Login Sessions");
            row4.createCell(1).setCellValue(report.getLoginSessions().toString());

            Row row5 = sheet.createRow(row);
            row5.createCell(0).setCellValue("Anomalies");
            row5.createCell(1).setCellValue(report.getAnomalies().toString());

            // Auto-size columns
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            try (FileOutputStream out = new FileOutputStream(filePath)) {
                workbook.write(out);
            }

            System.out.println("✅ Excel report written to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
