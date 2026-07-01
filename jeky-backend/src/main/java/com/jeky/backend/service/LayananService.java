package com.jeky.backend.service;

import com.jeky.backend.dto.LayananRequest;
import com.jeky.backend.dto.LayananResponse;
import com.jeky.backend.exception.ResourceNotFoundException;
import com.jeky.backend.model.Layanan;
import com.jeky.backend.repository.LayananRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LayananService {
    private final LayananRepository layananRepository;

    public LayananService(LayananRepository layananRepository) {
        this.layananRepository = layananRepository;
    }

    public List<LayananResponse> getAllLayanan() {
        return layananRepository.findAll()
                .stream()
                .map(LayananResponse::new)
                .toList();
    }

    public List<LayananResponse> getActiveLayanan() {
        return layananRepository.findByAktifTrue()
                .stream()
                .map(LayananResponse::new)
                .toList();
    }

    public LayananResponse getById(Long id) {
        return new LayananResponse(findEntityById(id));
    }

    public LayananResponse create(LayananRequest request) {
        Layanan layanan = new Layanan();
        applyRequest(layanan, request);
        return new LayananResponse(layananRepository.save(layanan));
    }

    public LayananResponse update(Long id, LayananRequest request) {
        Layanan layanan = findEntityById(id);
        applyRequest(layanan, request);
        return new LayananResponse(layananRepository.save(layanan));
    }

    public void delete(Long id) {
        if (!layananRepository.existsById(id)) {
            throw new ResourceNotFoundException("Layanan tidak ditemukan");
        }
        layananRepository.deleteById(id);
    }

    public Layanan findEntityById(Long id) {
        return layananRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Layanan tidak ditemukan"));
    }

    private void applyRequest(Layanan layanan, LayananRequest request) {
        layanan.setNama(request.getNama());
        layanan.setDeskripsi(request.getDeskripsi());
        layanan.setHargaDasar(request.getHargaDasar() == null ? 0 : request.getHargaDasar());
        layanan.setAktif(request.getAktif() == null ? true : request.getAktif());
    }
}
