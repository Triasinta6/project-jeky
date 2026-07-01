package com.jeky.backend.repository;

import com.jeky.backend.enums.OrderStatus;
import com.jeky.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJekyRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);

    long countByStatus(OrderStatus status);
}
