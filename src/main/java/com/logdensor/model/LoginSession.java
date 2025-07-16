package com.logdensor.model;

import java.time.LocalDateTime;

public class LoginSession {
    private String userId;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;

    public LoginSession(String userId, LocalDateTime loginTime, LocalDateTime logoutTime) {
        this.userId = userId;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public LocalDateTime getLogoutTime() {
        return logoutTime;
    }
}
