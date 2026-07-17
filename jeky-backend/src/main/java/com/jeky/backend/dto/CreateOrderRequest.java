package com.jeky.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateOrderRequest {

    @NotNull
    private Long layananId;

    @NotNull
    private Long customerId;

    @NotBlank
    private String customerName;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String pickupAddress;

    @NotBlank
    private String destinationAddress;

    private String note;

    public Long getLayananId() {
        return layananId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public String getNote() {
        return note;
    }

    public void setLayananId(Long layananId) {
        this.layananId = layananId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void setNote(String note) {
        this.note = note;
    }
}