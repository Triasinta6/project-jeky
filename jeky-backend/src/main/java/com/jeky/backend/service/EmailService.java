package com.jeky.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Kode OTP Reset Password - Jeky");
        message.setText("Halo,\n\n"
                + "Anda telah meminta untuk mereset kata sandi akun Jeky Anda.\n"
                + "Berikut adalah kode OTP Anda: " + otp + "\n\n"
                + "Kode OTP ini hanya berlaku selama 10 menit. Jangan berikan kode ini kepada siapapun.\n\n"
                + "Terima kasih,\n"
                + "Tim Jeky");

        mailSender.send(message);
    }
}
