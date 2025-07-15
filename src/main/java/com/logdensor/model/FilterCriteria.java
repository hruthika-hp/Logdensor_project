package com.logdensor.model;

import java.time.LocalDateTime;

public class FilterCriteria {
    private String logLevel;
    private String customTag;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public String getLogLevel() { return logLevel; }
    public String getCustomTag() { return customTag; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }

    public void setLogLevel(String logLevel) { this.logLevel = logLevel; }
    public void setCustomTag(String customTag) { this.customTag = customTag; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
}
