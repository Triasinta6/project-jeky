package com.jeky.backend.dto;

import com.jeky.backend.model.AdminUser;

public class AdminUserResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private Boolean isActive;

    public AdminUserResponse(AdminUser adminUser) {
        this.id = adminUser.getId();
        this.name = adminUser.getName();
        this.email = adminUser.getEmail();
        this.role = adminUser.getRole().name();
        this.isActive = adminUser.getIsActive();
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

    public String getRole() {
        return role;
    }

    public Boolean getIsActive() {
        return isActive;
    }
}
