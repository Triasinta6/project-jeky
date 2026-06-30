package com.jeky.backend.controller;

import com.jeky.backend.model.Layanan;
import com.jeky.backend.repository.LayananRepository;
import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/layanan")
public class LayananController {

    private final LayananRepository layananRepository;

    public LayananController(LayananRepository layananRepository) {
        this.layananRepository = layananRepository;
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'OPERATOR')")
    @GetMapping
    public List<Layanan> getAllLayanan() {
        return layananRepository.findAll();
    }

    @GetMapping("/aktif")
    public List<Layanan> getLayananAktif() {
        return layananRepository.findByAktifTrue();
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping
    public Layanan createLayanan(@Valid @RequestBody Layanan layanan) {
        return layananRepository.save(layanan);
    }

    @GetMapping("/{id}")
    public Layanan getLayananById(@PathVariable Long id) {
        return layananRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Layanan tidak ditemukan"));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping("/{id}")
    public Layanan updateLayanan(@PathVariable Long id, @Valid @RequestBody Layanan request) {
        Layanan layanan = layananRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Layanan tidak ditemukan"));

        layanan.setNama(request.getNama());
        layanan.setDeskripsi(request.getDeskripsi());
        layanan.setHargaDasar(request.getHargaDasar());
        layanan.setAktif(request.getAktif());

        return layananRepository.save(layanan);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteLayanan(@PathVariable Long id) {
        layananRepository.deleteById(id);
        return "Layanan berhasil dihapus";
    }
}