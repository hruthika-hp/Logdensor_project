package com.logdensor.report.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class JsonReportWriter {

    public void write(Object data, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // ✅ Fix for LocalDateTime

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
            System.out.println("✅ JSON written to " + filePath);
        } catch (IOException e) {
            System.out.println("❌ Failed to write JSON: " + e.getMessage());
        }
    }
}
