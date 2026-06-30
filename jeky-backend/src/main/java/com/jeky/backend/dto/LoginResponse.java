package com.jeky.backend.dto;

public class LoginResponse {

    private String message;
    private Long id;
    private String name;
    private String email;
    private String role;

    public LoginResponse(String message, Long id, String name, String email, String role) {
        this.message = message;
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getMessage() {
        return message;
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
}