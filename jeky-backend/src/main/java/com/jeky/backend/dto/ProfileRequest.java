package com.jeky.backend.dto;

public class ProfileRequest {
    private String name;
    private String noHp;
    private String address;

    public String getName() {
        return name;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
