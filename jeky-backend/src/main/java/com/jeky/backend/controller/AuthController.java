package com.jeky.backend.controller;

import com.jeky.backend.dto.LoginRequest;
import com.jeky.backend.dto.LoginResponse;
import com.jeky.backend.model.AdminUser;
import com.jeky.backend.repository.AdminUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<AdminUser> optionalAdmin = adminUserRepository.findByEmail(request.getEmail());

        if (optionalAdmin.isEmpty()) {
            return ResponseEntity.badRequest().body("Email tidak ditemukan");
        }

        AdminUser admin = optionalAdmin.get();

        if (!Boolean.TRUE.equals(admin.getIsActive())) {
            return ResponseEntity.badRequest().body("Akun admin tidak aktif");
        }

        boolean passwordMatch = passwordEncoder.matches(request.getPassword(), admin.getPassword());

        if (!passwordMatch) {
            return ResponseEntity.badRequest().body("Password salah");
        }

        LoginResponse response = new LoginResponse(
                "Login berhasil",
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getRole().name());

        return ResponseEntity.ok(response);
    }
}