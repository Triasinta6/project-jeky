package com.jeky.backend.dto;

import com.jeky.backend.model.AdminUser;

public class AdminUserResponse {
    private final Long id;
    private final String username;
    private final String email;
    private final String role;
    private final Boolean isActive;

    public AdminUserResponse(AdminUser adminUser) {
        this.id = adminUser.getId();
        this.username = adminUser.getUsername();
        this.email = adminUser.getEmail();
        this.role = adminUser.getRole().name();
        this.isActive = adminUser.getIsActive();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
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
