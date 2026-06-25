package com.jeky.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "data_customer")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String noHp;

    private String address;

    private Boolean aktif = true ;

    public Customer() {
    }

    public Customer(String name, String email, String noHp, String address, Boolean aktif) {
        this.name = name;
        this.email = email;
        this.noHp = noHp;
        this.address = address;
        this.aktif = aktif;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getAktif() {
        return aktif;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAktif(Boolean aktif) {
        this.aktif = aktif;
    }
}