package com.jeky.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeky.backend.repository.LayananRepository;
import com.jeky.backend.repository.OrderJekyRepository;

@RestController
public class DashboardApiController {

    private final OrderJekyRepository orderJekyRepository;
    private final LayananRepository layananRepository;

    public DashboardApiController(OrderJekyRepository orderJekyRepository, LayananRepository layananRepository) {
        this.orderJekyRepository = orderJekyRepository;
        this.layananRepository = layananRepository;
    }

    @GetMapping("/api/dashboard/stats")
    public Map<String, Long> getDashboardStats() {
        Map<String, Long> stats = new HashMap<>();

        stats.put("totalLayanan", layananRepository.count());
        stats.put("totalOrder", orderJekyRepository.count());
        stats.put("waitingOrder", orderJekyRepository.countByStatus("WAITING"));
        stats.put("acceptedOrder", orderJekyRepository.countByStatus("ACCEPTED"));
        stats.put("onProgressOrder", orderJekyRepository.countByStatus("ON_PROGRESS"));
        stats.put("completedOrder", orderJekyRepository.countByStatus("COMPLETED"));
        stats.put("cancelledOrder", orderJekyRepository.countByStatus("CANCELLED"));

        return stats;
    }
}