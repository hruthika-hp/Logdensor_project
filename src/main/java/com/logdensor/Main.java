package com.logdensor;

import com.logdensor.processor.LogProcessor;
import com.logdensor.report.ReportGenerator;

public class Main {
    public static void main(String[] args) {
        String logDirectory = "logs"; // directory with .log files

        LogProcessor processor = new LogProcessor();
        processor.processLogs(logDirectory); // parses logs using thread pool

        ReportGenerator generator = new ReportGenerator();
        generator.generateReports(); // creates summary and filtered reports
    }
}
