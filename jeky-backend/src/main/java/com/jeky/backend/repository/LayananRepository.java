package com.jeky.backend.repository;

import com.jeky.backend.model.Layanan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LayananRepository extends JpaRepository<Layanan, Long> {

    List<Layanan> findByAktifTrue();
}