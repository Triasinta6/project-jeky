package com.jeky.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeky.backend.model.Layanan;
import com.jeky.backend.repository.LayananRepository;

@Controller
@RequestMapping("/admin/layanan")
public class AdminLayananController {

    private final LayananRepository layananRepository;

    public AdminLayananController(LayananRepository layananRepository) {
        this.layananRepository = layananRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("layananList", layananRepository.findAll());
        model.addAttribute("layanan", new Layanan());
        return "admin/layanan";
    }

    @PostMapping
    public String create(@ModelAttribute Layanan layanan) {
        if (layanan.getAktif() == null) {
            layanan.setAktif(false);
        }

        layananRepository.save(layanan);
        return "redirect:/admin/layanan";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Layanan layanan = layananRepository.findById(id).orElseThrow(() -> new RuntimeException("Layanan not found"));

        model.addAttribute("layanan", layanan);
        return "admin/edit-layanan";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute Layanan updatedLayanan) {
        Layanan layanan = layananRepository.findById(id).orElseThrow(() -> new RuntimeException("Layanan not found"));

        layanan.setNama(updatedLayanan.getNama());
        layanan.setDeskripsi(updatedLayanan.getDeskripsi());
        layanan.setHargaDasar(updatedLayanan.getHargaDasar());

        if (updatedLayanan.getAktif() == null) {
            layanan.setAktif(false);
        } else {
            layanan.setAktif(updatedLayanan.getAktif());
        }
        
        layananRepository.save(layanan);
        return "redirect:/admin/layanan";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        layananRepository.deleteById(id);
        return "redirect:/admin/layanan";
    }
}