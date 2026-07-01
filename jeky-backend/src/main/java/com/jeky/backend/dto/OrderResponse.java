package com.jeky.backend.dto;

import com.jeky.backend.model.Order;
import java.time.LocalDateTime;

public class OrderResponse {
    private final Long id;
    private final String customerName;
    private final String phoneNumber;
    private final String pickupAddress;
    private final String destinationAddress;
    private final String note;
    private final String status;
    private final LocalDateTime createdAt;
    private final LayananResponse layanan;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.customerName = order.getCustomerName();
        this.phoneNumber = order.getPhoneNumber();
        this.pickupAddress = order.getPickupAddress();
        this.destinationAddress = order.getDestinationAddress();
        this.note = order.getNote();
        this.status = order.getStatus().name();
        this.createdAt = order.getCreatedAt();
        this.layanan = new LayananResponse(order.getLayanan());
    }

    public Long getId() {
        return id;
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

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LayananResponse getLayanan() {
        return layanan;
    }
}
