package com.jeky.backend.controller;

import com.jeky.backend.dto.LayananRequest;
import com.jeky.backend.dto.LayananResponse;
import com.jeky.backend.dto.MessageResponse;
import com.jeky.backend.service.LayananService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/layanan")
public class LayananController {
    private final LayananService layananService;

    public LayananController(LayananService layananService) {
        this.layananService = layananService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'OPERATOR')")
    public ResponseEntity<List<LayananResponse>> getAllLayanan() {
        return ResponseEntity.ok(layananService.getAllLayanan());
    }

    @GetMapping("/aktif")
    public ResponseEntity<List<LayananResponse>> getLayananAktif() {
        return ResponseEntity.ok(layananService.getActiveLayanan());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LayananResponse> getLayananById(@PathVariable Long id) {
        return ResponseEntity.ok(layananService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<LayananResponse> createLayanan(@Valid @RequestBody LayananRequest request) {
        return ResponseEntity.ok(layananService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<LayananResponse> updateLayanan(
            @PathVariable Long id,
            @Valid @RequestBody LayananRequest request
    ) {
        return ResponseEntity.ok(layananService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MessageResponse> deleteLayanan(@PathVariable Long id) {
        layananService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Layanan berhasil dihapus"));
    }
}
