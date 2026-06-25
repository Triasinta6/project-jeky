package com.jeky.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "data_layanan")
public class Layanan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String nama;

    private String deskripsi;

    private Integer hargaDasar;

    private Boolean aktif = true;

    public Layanan() {
    }

    public Layanan(String nama, String deskripsi, Integer hargaDasar, Boolean aktif) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.hargaDasar = hargaDasar;
        this.aktif = aktif;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setHargaDasar(Integer hargaDasar) {
        this.hargaDasar = hargaDasar;
    }

    public void setAktif(Boolean aktif) {
        this.aktif = aktif;
    }
}
