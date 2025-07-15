package com.logdensor.model;

import java.time.LocalDateTime;

public class LoginSession {
    private String userId;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private long durationSeconds;

    public LoginSession(String userId, LocalDateTime loginTime, LocalDateTime logoutTime) {
        this.userId = userId;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.durationSeconds = java.time.Duration.between(loginTime, logoutTime).getSeconds();
    }

    public String getUserId() { return userId; }
    public LocalDateTime getLoginTime() { return loginTime; }
    public LocalDateTime getLogoutTime() { return logoutTime; }
    public long getDurationSeconds() { return durationSeconds; }
}
