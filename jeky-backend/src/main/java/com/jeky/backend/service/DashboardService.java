package com.jeky.backend.service;

import com.jeky.backend.dto.DashboardStatsResponse;
import com.jeky.backend.enums.OrderStatus;
import com.jeky.backend.repository.LayananRepository;
import com.jeky.backend.repository.OrderJekyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DashboardService {
    private final OrderJekyRepository orderJekyRepository;
    private final LayananRepository layananRepository;

    public DashboardService(OrderJekyRepository orderJekyRepository, LayananRepository layananRepository) {
        this.orderJekyRepository = orderJekyRepository;
        this.layananRepository = layananRepository;

    }

    public DashboardStatsResponse getStats(String startDate, String endDate) {
        long totalLayanan = layananRepository.count();

        boolean filterTanggalAktif = startDate != null && !startDate.isBlank()
                && endDate != null && !endDate.isBlank();

        if (filterTanggalAktif) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            LocalDateTime startDateTime = start.atStartOfDay();
            LocalDateTime endDateTime = end.plusDays(1).atStartOfDay();

            long totalOrder = orderJekyRepository
                    .countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(startDateTime, endDateTime);

            long waitingOrder = orderJekyRepository
                    .countByStatusAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                            OrderStatus.WAITING,
                            startDateTime,
                            endDateTime);

            long acceptedOrder = orderJekyRepository
                    .countByStatusAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                            OrderStatus.ACCEPTED,
                            startDateTime,
                            endDateTime);

            long onProgressOrder = orderJekyRepository
                    .countByStatusAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                            OrderStatus.ON_PROGRESS,
                            startDateTime,
                            endDateTime);

            long completedOrder = orderJekyRepository
                    .countByStatusAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                            OrderStatus.COMPLETED,
                            startDateTime,
                            endDateTime);

            long cancelledOrder = orderJekyRepository
                    .countByStatusAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                            OrderStatus.CANCELLED,
                            startDateTime,
                            endDateTime);

            return new DashboardStatsResponse(
                    totalLayanan,
                    totalOrder,
                    waitingOrder,
                    acceptedOrder,
                    onProgressOrder,
                    completedOrder,
                    cancelledOrder);
        }

        return new DashboardStatsResponse(
                totalLayanan,
                orderJekyRepository.count(),
                orderJekyRepository.countByStatus(OrderStatus.WAITING),
                orderJekyRepository.countByStatus(OrderStatus.ACCEPTED),
                orderJekyRepository.countByStatus(OrderStatus.ON_PROGRESS),
                orderJekyRepository.countByStatus(OrderStatus.COMPLETED),
                orderJekyRepository.countByStatus(OrderStatus.CANCELLED));
    }
}
