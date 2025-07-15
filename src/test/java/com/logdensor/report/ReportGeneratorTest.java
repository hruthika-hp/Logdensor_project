package com.logdensor.report;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class ReportGeneratorTest {

    @Test
    public void testGenerateReports() {
        ReportGenerator generator = new ReportGenerator();
        generator.generateReports();

        assertTrue(new File("reports/summary.json").exists());
        assertTrue(new File("reports/summary.csv").exists());
        assertTrue(new File("reports/summary.xlsx").exists());
    }
}
