package com.logdensor.model;

public class PeakHour {
    private String hour;
    private int eventCount;

    public PeakHour(String hour, int eventCount) {
        this.hour = hour;
        this.eventCount = eventCount;
    }

    public String getHour() { return hour; }
    public int getEventCount() { return eventCount; }

    public void setHour(String hour) { this.hour = hour; }
    public void setEventCount(int eventCount) { this.eventCount = eventCount; }
}
