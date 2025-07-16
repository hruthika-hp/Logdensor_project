package com.logdensor.report;

public enum ReportFormat {
    JSON, CSV, EXCEL;

    public static ReportFormat fromString(String value) {
        return switch (value.toLowerCase()) {
            case "json" -> JSON;
            case "csv" -> CSV;
            case "excel" -> EXCEL;
            default -> throw new IllegalArgumentException("Unsupported report format: " + value);
        };
    }
}
