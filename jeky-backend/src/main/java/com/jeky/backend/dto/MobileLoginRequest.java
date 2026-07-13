package com.jeky.backend.dto;

public class MobileLoginRequest {
    private String emailOrPhone;
    private String password;

    public String getEmailOrPhone() {
        return emailOrPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setEmailOrPhone(String emailOrPhone) {
        this.emailOrPhone = emailOrPhone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}