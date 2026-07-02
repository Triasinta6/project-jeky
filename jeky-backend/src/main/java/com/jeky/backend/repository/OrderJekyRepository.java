package com.jeky.backend.repository;

import com.jeky.backend.enums.OrderStatus;
import com.jeky.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderJekyRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);

    long countByStatus(OrderStatus status);

    long countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
            LocalDateTime startDate,
            LocalDateTime endDate);

    long countByStatusAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
            OrderStatus status,
            LocalDateTime startDate,
            LocalDateTime endDate);
}
