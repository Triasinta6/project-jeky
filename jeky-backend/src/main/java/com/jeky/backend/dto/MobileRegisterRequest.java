package com.jeky.backend.dto;

public class MobileRegisterRequest {
    private String name;
    private String emailOrPhone;
    private String password;
    private String confirmPassword;

    public String getName() {
        return name;
    }

    public String getEmailOrPhone() {
        return emailOrPhone;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmailOrPhone(String emailOrPhone) {
        this.emailOrPhone = emailOrPhone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}