package com.jeky.backend.controller;

import com.jeky.backend.dto.LoginRequest;
import com.jeky.backend.dto.LoginResponse;
import com.jeky.backend.model.AdminUser;
import com.jeky.backend.repository.AdminUserRepository;
import com.jeky.backend.service.Jwt;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.Map;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final Jwt jwt;

    public AuthController(
            AdminUserRepository adminUserRepository,
            PasswordEncoder passwordEncoder,
            Jwt jwt) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<AdminUser> optionalAdmin = adminUserRepository.findByUsername(request.getUsername());

        if (optionalAdmin.isEmpty()) {
            return ResponseEntity.badRequest().body("Username tidak ditemukan");
        }

        AdminUser admin = optionalAdmin.get();

        if (!Boolean.TRUE.equals(admin.getIsActive())) {
            return ResponseEntity.badRequest().body("Akun admin tidak aktif");
        }

        boolean passwordMatch = passwordEncoder.matches(request.getPassword(), admin.getPassword());

        if (!passwordMatch) {
            return ResponseEntity.badRequest().body("Password salah");
        }

        String token = jwt.generateToken(admin);

        LoginResponse response = new LoginResponse(
                "Login berhasil",
                token,
                admin.getId(),
                admin.getUsername(),
                admin.getEmail(),
                admin.getRole().name());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Optional<AdminUser> optionalAdmin = adminUserRepository.findByUsername(authentication.getName());

        if (optionalAdmin.isEmpty()) {
            return ResponseEntity.status(401).body("User tidak ditemukan");
        }

        AdminUser admin = optionalAdmin.get();

        return ResponseEntity.ok(Map.of(
                "id", admin.getId(),
                "name", admin.getUsername(),
                "email", admin.getEmail(),
                "role", admin.getRole().name()));
    }
}