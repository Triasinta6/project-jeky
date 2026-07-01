package com.jeky.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CustomerRequest {
    @NotBlank(message = "Nama customer wajib diisi")
    private String name;

    @Email(message = "Format email tidak valid")
    @NotBlank(message = "Email wajib diisi")
    private String email;

    @NotBlank(message = "Nomor HP wajib diisi")
    private String noHp;

    private String address;
    private Boolean aktif = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getAktif() {
        return aktif;
    }

    public void setAktif(Boolean aktif) {
        this.aktif = aktif;
    }
}
