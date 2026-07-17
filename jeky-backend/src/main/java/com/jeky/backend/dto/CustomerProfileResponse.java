package com.jeky.backend.dto;

import com.jeky.backend.model.Customer;

public class CustomerProfileResponse {

    private boolean success;
    private String message;
    private Long customerId;
    private String name;
    private String email;
    private String noHp;
    private String address;

    public CustomerProfileResponse() {
    }

    public CustomerProfileResponse(boolean success, String message, Customer customer) {
        this.success = success;
        this.message = message;

        if (customer != null) {
            this.customerId = customer.getId();
            this.name = customer.getName();
            this.email = customer.getEmail();
            this.noHp = customer.getNoHp();
            this.address = customer.getAddress();
        }
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

    public String getAddress() {
        return address;
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

    public void setAddress(String address) {
        this.address = address;
    }
}