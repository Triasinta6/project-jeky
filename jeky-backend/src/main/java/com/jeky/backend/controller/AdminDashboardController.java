package com.jeky.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jeky.backend.repository.LayananRepository;
import com.jeky.backend.repository.OrderJekyRepository;

@Controller
public class AdminDashboardController {
    private final OrderJekyRepository orderJekyRepository;
    private final LayananRepository layananRepository;

    public AdminDashboardController(OrderJekyRepository orderJekyRepository, LayananRepository layananRepository) {
        this.orderJekyRepository = orderJekyRepository;
        this.layananRepository = layananRepository;
    }

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        model.addAttribute("totalLayanan", layananRepository.count());
        model.addAttribute("totalOrder", orderJekyRepository.count());
        model.addAttribute("waitingOrder", orderJekyRepository.countByStatus("WAITING"));
        model.addAttribute("acceptedOrder", orderJekyRepository.countByStatus("ACCEPTED"));
        model.addAttribute("onProgressOrder", orderJekyRepository.countByStatus("ON_PROGRESS"));
        model.addAttribute("completedOrder", orderJekyRepository.countByStatus("COMPLETED"));
        model.addAttribute("cancelledOrder", orderJekyRepository.countByStatus("CANCELLED"));

        return "admin/dashboard";
    }
}
