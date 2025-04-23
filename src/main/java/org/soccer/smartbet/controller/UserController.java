package org.soccer.smartbet.controller;

import org.soccer.smartbet.dto.ApiResponse;
import org.soccer.smartbet.dto.JwtResponse;
import org.soccer.smartbet.dto.LoginRequest;
import org.soccer.smartbet.dto.SignupRequest;
import org.soccer.smartbet.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<JwtResponse>> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(jwtResponse));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> registerUser(
            @Valid @RequestBody SignupRequest signUpRequest) {
        authService.registerUser(signUpRequest);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully!"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfile>> getCurrentUser() {
        UserProfile profile = authService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.success(profile));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfile>> updateProfile(
            @Valid @RequestBody UserProfile profile) {
        UserProfile updatedProfile = authService.updateProfile(profile);
        return ResponseEntity.ok(ApiResponse.success(updatedProfile));
    }
}