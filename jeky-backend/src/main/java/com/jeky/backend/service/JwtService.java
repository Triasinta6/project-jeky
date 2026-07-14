package com.jeky.backend.service;

import com.jeky.backend.model.AdminUser;
import com.jeky.backend.model.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final String secretKey;
    private final long expirationTime;

    public JwtService(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration-ms}") long expirationTime) {
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
    }

    public String generateToken(AdminUser adminUser) {
        return Jwts.builder()
                .subject(adminUser.getUsername())
                .claim("id", adminUser.getId())
                .claim("username", adminUser.getUsername())
                .claim("role", adminUser.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateCustomerToken(Customer customer) {
        return Jwts.builder()
                .subject(customer.getEmail() != null ? customer.getEmail() : customer.getNoHp())
                .claim("id", customer.getId())
                .claim("name", customer.getName())
                .claim("email", customer.getEmail())
                .claim("noHp", customer.getNoHp())
                .claim("role", "CUSTOMER")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, AdminUser adminUser) {
        String username = extractUsername(token);
        return username.equals(adminUser.getUsername()) && !isTokenExpired(token);
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

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
