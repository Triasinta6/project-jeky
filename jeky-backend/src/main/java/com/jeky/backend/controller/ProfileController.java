package com.jeky.backend.controller;

import com.jeky.backend.dto.CustomerProfileResponse;
import com.jeky.backend.dto.ProfileRequest;
import com.jeky.backend.service.ProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mobile/profile")
@CrossOrigin(origins = "*")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{customerId}")
    public CustomerProfileResponse getProfile(@PathVariable Long customerId) {
        return profileService.getProfile(customerId);
    }

    @PutMapping("/{customerId}")
    public CustomerProfileResponse updateProfile(
            @PathVariable Long customerId,
            @RequestBody ProfileRequest request) {
        return profileService.updateProfile(customerId, request);
    }
}