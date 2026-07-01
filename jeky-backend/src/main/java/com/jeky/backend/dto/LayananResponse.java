package com.jeky.backend.dto;

import com.jeky.backend.model.Layanan;

public class LayananResponse {
    private final Long id;
    private final String nama;
    private final String deskripsi;
    private final Integer hargaDasar;
    private final Boolean aktif;

    public LayananResponse(Layanan layanan) {
        this.id = layanan.getId();
        this.nama = layanan.getNama();
        this.deskripsi = layanan.getDeskripsi();
        this.hargaDasar = layanan.getHargaDasar();
        this.aktif = layanan.getAktif();
    }

    public Long getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public Integer getHargaDasar() {
        return hargaDasar;
    }

    public Boolean getAktif() {
        return aktif;
    }
}
