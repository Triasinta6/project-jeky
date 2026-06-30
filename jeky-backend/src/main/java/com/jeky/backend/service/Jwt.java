package com.jeky.backend.service;

import com.jeky.backend.model.AdminUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class Jwt {

    private static final String SECRET_KEY = "jeky-admin-secret-key-minimal-32-character-long";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 jam

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(AdminUser adminUser) {
        return Jwts.builder()
                .subject(adminUser.getEmail())
                .claim("id", adminUser.getId())
                .claim("name", adminUser.getName())
                .claim("role", adminUser.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public boolean isTokenValid(String token, AdminUser adminUser) {
        String email = extractEmail(token);
        return email.equals(adminUser.getEmail()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}