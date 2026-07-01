package com.jeky.backend.controller;

import com.jeky.backend.dto.AdminUserResponse;
import com.jeky.backend.dto.CreateAdminUserRequest;
import com.jeky.backend.service.AdminUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin-users")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class AdminUserController {
    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping
    public ResponseEntity<List<AdminUserResponse>> getAllAdminUsers() {
        return ResponseEntity.ok(adminUserService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<AdminUserResponse> createAdminUser(@Valid @RequestBody CreateAdminUserRequest request) {
        return ResponseEntity.ok(adminUserService.createUser(request));
    }
}
