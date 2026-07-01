package com.jeky.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class LayananRequest {
    @NotBlank(message = "Nama layanan wajib diisi")
    private String nama;

    private String deskripsi;

    @Min(value = 0, message = "Harga dasar tidak boleh negatif")
    private Integer hargaDasar;

    private Boolean aktif = true;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Integer getHargaDasar() {
        return hargaDasar;
    }

    public void setHargaDasar(Integer hargaDasar) {
        this.hargaDasar = hargaDasar;
    }

    public Boolean getAktif() {
        return aktif;
    }

    public void setAktif(Boolean aktif) {
        this.aktif = aktif;
    }
}
