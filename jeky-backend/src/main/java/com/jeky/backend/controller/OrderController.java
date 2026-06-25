package com.jeky.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeky.backend.dto.CreateOrderRequest;
import com.jeky.backend.model.Layanan;
import com.jeky.backend.model.Order;
import com.jeky.backend.repository.LayananRepository;
import com.jeky.backend.repository.OrderJekyRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderJekyRepository orderJekyRepository;
    private final LayananRepository layananRepository;

    public OrderController(OrderJekyRepository orderJekyRepository, LayananRepository layananRepository) {
        this.orderJekyRepository = orderJekyRepository;
        this.layananRepository = layananRepository;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderJekyRepository.findAll();
    }

    @GetMapping("/waiting")
    public List<Order> getWaitingOrders() {
        return orderJekyRepository.findByStatus("WAITING");
    }

    @PostMapping
    public Order createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Layanan layanan = layananRepository.findById(request.getLayananId())
                .orElseThrow(() -> new RuntimeException("Layanan tidak ditemukan"));

        Order order = new Order();
        order.setLayanan(layanan);
        order.setCustomerName(request.getCustomerName());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setPickupAddress(request.getPickupAddress());
        order.setDestinationAddress(request.getDestinationAddress());
        order.setNote(request.getNote());
        order.setStatus("WAITING");

        return orderJekyRepository.save(order);
    }

    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestParam String status) {
        Order order = orderJekyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order tidak ditemukan"));

        order.setStatus(status);

        return orderJekyRepository.save(order);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderJekyRepository.deleteById(id);
        return "Order berhasil dihapus";
    }
}