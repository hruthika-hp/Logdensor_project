package com.logdensor.model;

import java.util.List;
import java.util.Map;

public class MetricsReport {
    private Map<String, Integer> logLevelCounts;
    private List<FrequentError> frequentErrorMessages;
    private List<Anomaly> anomalies;
    private List<PeakHour> peakHours;
    private List<LoginSession> loginLogoutDurations;

    public Map<String, Integer> getLogLevelCounts() { return logLevelCounts; }
    public List<FrequentError> getFrequentErrorMessages() { return frequentErrorMessages; }
    public List<Anomaly> getAnomalies() { return anomalies; }
    public List<PeakHour> getPeakHours() { return peakHours; }
    public List<LoginSession> getLoginLogoutDurations() { return loginLogoutDurations; }

    public void setLogLevelCounts(Map<String, Integer> logLevelCounts) {
        this.logLevelCounts = logLevelCounts;
    }

    public void setFrequentErrorMessages(List<FrequentError> frequentErrorMessages) {
        this.frequentErrorMessages = frequentErrorMessages;
    }

    public void setAnomalies(List<Anomaly> anomalies) {
        this.anomalies = anomalies;
    }

    public void setPeakHours(List<PeakHour> peakHours) {
        this.peakHours = peakHours;
    }

    public void setLoginLogoutDurations(List<LoginSession> loginLogoutDurations) {
        this.loginLogoutDurations = loginLogoutDurations;
    }

    public static class FrequentError {
        private String message;
        private int count;

        public FrequentError(String message, int count) {
            this.message = message;
            this.count = count;
        }

        public String getMessage() { return message; }
        public int getCount() { return count; }

        public void setMessage(String message) { this.message = message; }
        public void setCount(int count) { this.count = count; }
    }
}
