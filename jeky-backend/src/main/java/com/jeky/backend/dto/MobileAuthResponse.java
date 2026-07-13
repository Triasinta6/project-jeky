package com.jeky.backend.dto;

public class MobileAuthResponse {
    private boolean success;
    private String message;
    private Long customerId;
    private String name;
    private String email;
    private String noHp;

    public MobileAuthResponse() {
    }

    public MobileAuthResponse(boolean success, String message, Long customerId, String name, String email,
            String noHp) {
        this.success = success;
        this.message = message;
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.noHp = noHp;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
}