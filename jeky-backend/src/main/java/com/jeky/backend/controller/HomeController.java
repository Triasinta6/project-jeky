package com.jeky.backend.controller;

import com.jeky.backend.dto.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<MessageResponse> home() {
        return ResponseEntity.ok(new MessageResponse("Jeky Backend is running"));
    }

    @GetMapping("/api/health")
    public ResponseEntity<MessageResponse> health() {
        return ResponseEntity.ok(new MessageResponse("OK"));
    }
}
