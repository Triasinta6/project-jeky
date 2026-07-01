package com.jeky.backend.controller;

import com.jeky.backend.dto.AdminUserResponse;
import com.jeky.backend.dto.CreateAdminUserRequest;
import com.jeky.backend.enums.Role;
import com.jeky.backend.model.AdminUser;
import com.jeky.backend.repository.AdminUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin-users")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminUserController {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserController(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getAllAdminUsers() {
        List<AdminUserResponse> users = adminUserRepository.findAll()
                .stream()
                .map(AdminUserResponse::new)
                .toList();

        return ResponseEntity.ok(users);
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> createAdminUser(@RequestBody CreateAdminUserRequest request) {
        if (adminUserRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Username admin sudah digunakan"));
        }

        Role role;

        try {
            role = Role.valueOf(request.getRole());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Role tidak valid. Gunakan SUPER_ADMIN, ADMIN, OPERATOR, atau FINANCE"));
        }

        AdminUser adminUser = new AdminUser();
        adminUser.setUsername(request.getUsername());
        adminUser.setEmail(request.getEmail());
        adminUser.setPassword(passwordEncoder.encode(request.getPassword()));
        adminUser.setRole(role);
        adminUser.setIsActive(true);

        AdminUser savedUser = adminUserRepository.save(adminUser);

        return ResponseEntity.ok(new AdminUserResponse(savedUser));
    }
}