package com.jeky.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeky.backend.model.Order;

public interface OrderJekyRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(String status);
    long countByStatus(String status);
}