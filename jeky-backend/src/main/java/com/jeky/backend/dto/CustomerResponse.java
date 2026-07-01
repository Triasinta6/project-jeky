package com.jeky.backend.dto;

import com.jeky.backend.model.Customer;

public class CustomerResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String noHp;
    private final String address;
    private final Boolean aktif;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.noHp = customer.getNoHp();
        this.address = customer.getAddress();
        this.aktif = customer.getAktif();
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
}
