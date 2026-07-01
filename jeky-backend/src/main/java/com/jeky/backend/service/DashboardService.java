package com.jeky.backend.service;

import com.jeky.backend.dto.DashboardStatsResponse;
import com.jeky.backend.enums.OrderStatus;
import com.jeky.backend.repository.LayananRepository;
import com.jeky.backend.repository.OrderJekyRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final OrderJekyRepository orderJekyRepository;
    private final LayananRepository layananRepository;

    public DashboardService(OrderJekyRepository orderJekyRepository, LayananRepository layananRepository) {
        this.orderJekyRepository = orderJekyRepository;
        this.layananRepository = layananRepository;
    }

    public DashboardStatsResponse getStats() {
        return new DashboardStatsResponse(
                layananRepository.count(),
                orderJekyRepository.count(),
                orderJekyRepository.countByStatus(OrderStatus.WAITING),
                orderJekyRepository.countByStatus(OrderStatus.ACCEPTED),
                orderJekyRepository.countByStatus(OrderStatus.ON_PROGRESS),
                orderJekyRepository.countByStatus(OrderStatus.COMPLETED),
                orderJekyRepository.countByStatus(OrderStatus.CANCELLED)
        );
    }
}
