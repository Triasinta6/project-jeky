package com.jeky.backend.controller;

import com.jeky.backend.dto.MobileAuthResponse;
import com.jeky.backend.dto.MobileLoginRequest;
import com.jeky.backend.dto.MobileRegisterRequest;
import com.jeky.backend.service.MobileAuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mobile/auth")
@CrossOrigin(origins = "*")
public class MobileAuthController {

    private final MobileAuthService mobileAuthService;

    public MobileAuthController(MobileAuthService mobileAuthService) {
        this.mobileAuthService = mobileAuthService;
    }

    @PostMapping("/register")
    public MobileAuthResponse register(@RequestBody MobileRegisterRequest request) {
        return mobileAuthService.register(request);
    }

    @PostMapping("/login")
    public MobileAuthResponse login(@RequestBody MobileLoginRequest request) {
        return mobileAuthService.login(request);
    }
}