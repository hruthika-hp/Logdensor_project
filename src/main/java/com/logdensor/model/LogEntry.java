package com.logdensor.model;

import java.time.LocalDateTime;

public class LogEntry {
    private LocalDateTime timestamp;
    private String level;
    private String message;
    private String metadata;

    public LogEntry(LocalDateTime timestamp, String level, String message, String metadata) {
        this.timestamp = timestamp;
        this.level = level;
        this.message = message;
        this.metadata = metadata;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public String getLevel() { return level; }
    public String getMessage() { return message; }
    public String getMetadata() { return metadata; }

    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setLevel(String level) { this.level = level; }
    public void setMessage(String message) { this.message = message; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
}
