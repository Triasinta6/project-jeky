package com.jeky.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeky.backend.model.Order;
import com.jeky.backend.repository.OrderJekyRepository;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderJekyRepository orderJekyRepository;

    public AdminOrderController(OrderJekyRepository orderJekyRepository) {
        this.orderJekyRepository = orderJekyRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("orderList", orderJekyRepository.findAll());
        return "admin/orders";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        Order order = orderJekyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order tidak ditemukan"));

        order.setStatus(status);
        orderJekyRepository.save(order);

        return "redirect:/admin/orders";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        orderJekyRepository.deleteById(id);
        return "redirect:/admin/orders";
    }
}