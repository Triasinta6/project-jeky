package com.jeky.backend.controller;

import com.jeky.backend.dto.CreateOrderRequest;
import com.jeky.backend.dto.MessageResponse;
import com.jeky.backend.dto.OrderResponse;
import com.jeky.backend.dto.UpdateOrderStatusRequest;
import com.jeky.backend.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/waiting")
    public ResponseEntity<List<OrderResponse>> getWaitingOrders() {
        return ResponseEntity.ok(orderService.getWaitingOrders());
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.create(request));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam(required = false) String status,
            @RequestBody(required = false) UpdateOrderStatusRequest request) {
        UpdateOrderStatusRequest updateRequest = request == null ? new UpdateOrderStatusRequest() : request;
        if (updateRequest.getStatus() == null) {
            updateRequest.setStatus(status);
        }

        return ResponseEntity.ok(orderService.updateStatus(id, updateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Order berhasil dihapus"));
    }
}
