package com.jeky.backend.service;

import com.jeky.backend.dto.MobileAuthResponse;
import com.jeky.backend.dto.MobileLoginRequest;
import com.jeky.backend.dto.MobileRegisterRequest;
import com.jeky.backend.model.Customer;
import com.jeky.backend.service.JwtService;

import io.jsonwebtoken.Jwt;

import com.jeky.backend.repository.CustomerRepository;
import com.jeky.backend.repository.PasswordResetOtpRepository;
import com.jeky.backend.model.PasswordResetOtp;
import com.jeky.backend.dto.ForgotPasswordRequest;
import com.jeky.backend.dto.ResetPasswordRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
@Service
public class MobileAuthService {

    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final PasswordResetOtpRepository otpRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public MobileAuthService(CustomerRepository customerRepository, JwtService jwtService, 
                             PasswordResetOtpRepository otpRepository, EmailService emailService) {
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
        this.otpRepository = otpRepository;
        this.emailService = emailService;
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

    public MobileAuthResponse forgotPassword(ForgotPasswordRequest request) {
        String email = request.getEmail() == null ? "" : request.getEmail().trim();
        if (email.isBlank()) {
            return failed("Email wajib diisi");
        }

        Customer customer = customerRepository.findByEmailOrNoHp(email, email).orElse(null);
        if (customer == null || !email.equals(customer.getEmail())) {
            return failed("Email tidak terdaftar");
        }
        if (customer.getAktif() != null && !customer.getAktif()) {
            return failed("Akun tidak aktif");
        }

        // Generate 6 digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));
        
        // Hash OTP and save
        PasswordResetOtp resetOtp = new PasswordResetOtp();
        resetOtp.setCustomer(customer);
        resetOtp.setOtpHash(passwordEncoder.encode(otp));
        resetOtp.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        resetOtp.setUsed(false);
        otpRepository.save(resetOtp);

        // Send Email
        try {
            emailService.sendOtpEmail(email, otp);
        } catch (Exception e) {
            return failed("Gagal mengirim email OTP: " + e.getMessage());
        }

        return new MobileAuthResponse(true, "OTP berhasil dikirim ke email", null, null, null, null, null);
    }

    public MobileAuthResponse resetPassword(ResetPasswordRequest request) {
        String email = request.getEmail() == null ? "" : request.getEmail().trim();
        if (email.isBlank()) {
            return failed("Email wajib diisi");
        }
        if (request.getOtp() == null || request.getOtp().isBlank()) {
            return failed("OTP wajib diisi");
        }
        if (request.getNewPassword() == null || request.getNewPassword().length() < 8) {
            return failed("Password baru minimal 8 karakter");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return failed("Konfirmasi password tidak cocok");
        }

        Customer customer = customerRepository.findByEmailOrNoHp(email, email).orElse(null);
        if (customer == null || !email.equals(customer.getEmail())) {
            return failed("Email tidak terdaftar");
        }

        Optional<PasswordResetOtp> otpOpt = otpRepository.findTopByCustomerOrderByExpiryDateDesc(customer);
        if (otpOpt.isEmpty()) {
            return failed("Tidak ada permintaan OTP");
        }

        PasswordResetOtp resetOtp = otpOpt.get();
        if (resetOtp.isUsed()) {
            return failed("OTP sudah digunakan");
        }
        if (LocalDateTime.now().isAfter(resetOtp.getExpiryDate())) {
            return failed("OTP sudah kadaluarsa");
        }
        if (!passwordEncoder.matches(request.getOtp(), resetOtp.getOtpHash())) {
            return failed("OTP tidak valid");
        }

        // Valid OTP, proceed to update password
        customer.setPassword(passwordEncoder.encode(request.getNewPassword()));
        customerRepository.save(customer);

        resetOtp.setUsed(true);
        otpRepository.save(resetOtp);

        return new MobileAuthResponse(true, "Password berhasil diubah", null, null, null, null, null);
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