package com.jeky.backend.service;

import com.jeky.backend.dto.CreateOrderRequest;
import com.jeky.backend.dto.OrderResponse;
import com.jeky.backend.dto.UpdateOrderStatusRequest;
import com.jeky.backend.enums.OrderStatus;
import com.jeky.backend.exception.BadRequestException;
import com.jeky.backend.exception.ResourceNotFoundException;
import com.jeky.backend.model.Layanan;
import com.jeky.backend.model.Order;
import com.jeky.backend.repository.OrderJekyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderJekyRepository orderJekyRepository;
    private final LayananService layananService;

    public OrderService(OrderJekyRepository orderJekyRepository, LayananService layananService) {
        this.orderJekyRepository = orderJekyRepository;
        this.layananService = layananService;
    }

    public List<OrderResponse> getAllOrders() {
        return orderJekyRepository.findAll()
                .stream()
                .map(OrderResponse::new)
                .toList();
    }

    public List<OrderResponse> getWaitingOrders() {
        return orderJekyRepository.findByStatus(OrderStatus.WAITING)
                .stream()
                .map(OrderResponse::new)
                .toList();
    }

    public List<OrderResponse> getOrdersByCustomerId(Long customerId) {
        return orderJekyRepository.findByCustomerIdOrderByCreatedAtDesc(customerId)
                .stream()
                .map(OrderResponse::new)
                .toList();
    }

    public OrderResponse create(CreateOrderRequest request) {
        Layanan layanan = layananService.findEntityById(request.getLayananId());

        Order order = new Order();
        order.setLayanan(layanan);
        order.setCustomerId(request.getCustomerId());
        order.setCustomerName(request.getCustomerName());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setPickupAddress(request.getPickupAddress());
        order.setDestinationAddress(request.getDestinationAddress());
        order.setNote(request.getNote());
        order.setStatus(OrderStatus.WAITING);

        return new OrderResponse(orderJekyRepository.save(order));
    }

    public OrderResponse updateStatus(Long id, UpdateOrderStatusRequest request) {
        Order order = findEntityById(id);
        order.setStatus(parseStatus(request.getStatus()));
        return new OrderResponse(orderJekyRepository.save(order));
    }

    public void delete(Long id) {
        if (!orderJekyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order tidak ditemukan");
        }
        orderJekyRepository.deleteById(id);
    }

    private Order findEntityById(Long id) {
        return orderJekyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order tidak ditemukan"));
    }

    private OrderStatus parseStatus(String status) {
        try {
            return OrderStatus.valueOf(status);
        } catch (Exception exception) {
            throw new BadRequestException("Status order tidak valid");
        }
    }
}
