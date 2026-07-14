package com.jeky.backend.service;

import com.jeky.backend.dto.MobileAuthResponse;
import com.jeky.backend.dto.MobileLoginRequest;
import com.jeky.backend.dto.MobileRegisterRequest;
import com.jeky.backend.model.Customer;
import com.jeky.backend.service.JwtService;

import io.jsonwebtoken.Jwt;

import com.jeky.backend.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MobileAuthService {

    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public MobileAuthService(CustomerRepository customerRepository, JwtService jwtService) {
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
    }

    public MobileAuthResponse register(MobileRegisterRequest request) {
        String name = request.getName() == null ? "" : request.getName().trim();
        String emailOrPhone = request.getEmailOrPhone() == null ? "" : request.getEmailOrPhone().trim();
        String password = request.getPassword() == null ? "" : request.getPassword();
        String confirmPassword = request.getConfirmPassword() == null ? "" : request.getConfirmPassword();

        if (name.isBlank()) {
            return failed("Nama lengkap wajib diisi");
        }

        if (emailOrPhone.isBlank()) {
            return failed("Nomor HP atau Email wajib diisi");
        }

        if (password.isBlank()) {
            return failed("Kata sandi wajib diisi");
        }

        if (password.length() < 6) {
            return failed("Kata sandi minimal 6 karakter");
        }

        if (!password.equals(confirmPassword)) {
            return failed("Konfirmasi kata sandi tidak sama");
        }

        boolean isEmail = emailOrPhone.contains("@");

        if (isEmail && customerRepository.existsByEmail(emailOrPhone)) {
            return failed("Email sudah terdaftar");
        }

        if (!isEmail && customerRepository.existsByNoHp(emailOrPhone)) {
            return failed("Nomor HP sudah terdaftar");
        }

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(isEmail ? emailOrPhone : null);
        customer.setNoHp(isEmail ? null : emailOrPhone);
        customer.setAddress("");
        customer.setPassword(passwordEncoder.encode(password));
        customer.setAktif(true);

        Customer savedCustomer = customerRepository.save(customer);

        return new MobileAuthResponse(
                true,
                "Registrasi berhasil",
                null,
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.getEmail(),
                savedCustomer.getNoHp());
    }

    public MobileAuthResponse login(MobileLoginRequest request) {
        String emailOrPhone = request.getEmailOrPhone() == null ? "" : request.getEmailOrPhone().trim();
        String password = request.getPassword() == null ? "" : request.getPassword();

        if (emailOrPhone.isBlank()) {
            return failed("Nomor HP atau Email wajib diisi");
        }

        if (password.isBlank()) {
            return failed("Kata sandi wajib diisi");
        }

        Customer customer = customerRepository.findByEmailOrNoHp(emailOrPhone, emailOrPhone)
                .orElse(null);

        if (customer == null) {
            return failed("Akun tidak ditemukan");
        }

        if (customer.getAktif() != null && !customer.getAktif()) {
            return failed("Akun tidak aktif");
        }

        if (!passwordEncoder.matches(password, customer.getPassword())) {
            return failed("Kata sandi salah");
        }

        String token = jwtService.generateCustomerToken(customer);

        return new MobileAuthResponse(
                true,
                "Login berhasil",
                token,
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getNoHp());
    }

    private MobileAuthResponse failed(String message) {
        return new MobileAuthResponse(
                false,
                message,
                null,
                null,
                null,
                null,
                null);
    }
}