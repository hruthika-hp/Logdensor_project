package com.logdensor.model;

import java.util.List;
import java.util.Map;

public class MetricsReport {

    private Map<String, Long> levelCounts;
    private List<PeakHour> peakHours;
    private List<String> frequentErrors;
    private List<LoginSession> loginSessions;
    private List<Anomaly> anomalies;

    public Map<String, Long> getLevelCounts() {
        return levelCounts;
    }

    public void setLevelCounts(Map<String, Long> levelCounts) {
        this.levelCounts = levelCounts;
    }

    public List<PeakHour> getPeakHours() {
        return peakHours;
    }

    public void setPeakHours(List<PeakHour> peakHours) {
        this.peakHours = peakHours;
    }

    public List<String> getFrequentErrors() {
        return frequentErrors;
    }

    public void setFrequentErrors(List<String> frequentErrors) {
        this.frequentErrors = frequentErrors;
    }

    public List<LoginSession> getLoginSessions() {
        return loginSessions;
    }

    public void setLoginSessions(List<LoginSession> loginSessions) {
        this.loginSessions = loginSessions;
    }

    public List<Anomaly> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomaly> anomalies) {
        this.anomalies = anomalies;
    }

    @Override
    public String toString() {
        return "MetricsReport{" +
                "levelCounts=" + levelCounts +
                ", peakHours=" + peakHours +
                ", frequentErrors=" + frequentErrors +
                ", loginSessions=" + loginSessions +
                ", anomalies=" + anomalies +
                '}';
    }
}
