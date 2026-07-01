package com.jeky.backend.service;

import com.jeky.backend.dto.AdminUserResponse;
import com.jeky.backend.dto.LoginRequest;
import com.jeky.backend.dto.LoginResponse;
import com.jeky.backend.exception.BadRequestException;
import com.jeky.backend.exception.ResourceNotFoundException;
import com.jeky.backend.exception.UnauthorizedException;
import com.jeky.backend.model.AdminUser;
import com.jeky.backend.repository.AdminUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            AdminUserRepository adminUserRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        AdminUser admin = adminUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Username tidak ditemukan"));

        if (!Boolean.TRUE.equals(admin.getIsActive())) {
            throw new BadRequestException("Akun admin tidak aktif");
        }

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new BadRequestException("Password salah");
        }

        String token = jwtService.generateToken(admin);

        return new LoginResponse(
                "Login berhasil",
                token,
                admin.getId(),
                admin.getUsername(),
                admin.getEmail(),
                admin.getRole().name()
        );
    }

    public AdminUserResponse getCurrentUser(Authentication authentication) {
        if (authentication == null) {
            throw new UnauthorizedException("Unauthorized");
        }

        AdminUser admin = adminUserRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));

        return new AdminUserResponse(admin);
    }
}
