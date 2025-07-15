package com.logdensor.report.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.logdensor.model.MetricsReport;

import java.io.File;
import java.util.List;

public class JsonReportWriter {

    public void write(MetricsReport report, String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), report);
            System.out.println("✅ JSON report written to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Add this method to write a list of logs
    public void writeList(List<?> logs, String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), logs);
            System.out.println("✅ Filtered report written to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
