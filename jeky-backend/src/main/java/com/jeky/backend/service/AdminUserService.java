package com.jeky.backend.service;

import com.jeky.backend.dto.AdminUserResponse;
import com.jeky.backend.dto.CreateAdminUserRequest;
import com.jeky.backend.enums.Role;
import com.jeky.backend.exception.BadRequestException;
import com.jeky.backend.model.AdminUser;
import com.jeky.backend.repository.AdminUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserService {
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserService(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AdminUserResponse> getAllUsers() {
        return adminUserRepository.findAll()
                .stream()
                .map(AdminUserResponse::new)
                .toList();
    }

    public AdminUserResponse createUser(CreateAdminUserRequest request) {
        if (adminUserRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BadRequestException("Username admin sudah digunakan");
        }

        AdminUser adminUser = new AdminUser();
        adminUser.setUsername(request.getUsername());
        adminUser.setEmail(request.getEmail());
        adminUser.setPassword(passwordEncoder.encode(request.getPassword()));
        adminUser.setRole(parseRole(request.getRole()));
        adminUser.setIsActive(true);

        return new AdminUserResponse(adminUserRepository.save(adminUser));
    }

    private Role parseRole(String role) {
        try {
            return Role.valueOf(role);
        } catch (Exception exception) {
            throw new BadRequestException("Role tidak valid. Gunakan SUPER_ADMIN, ADMIN, OPERATOR, atau FINANCE");
        }
    }
}
