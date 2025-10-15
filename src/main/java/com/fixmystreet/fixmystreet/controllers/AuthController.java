package com.fixmystreet.fixmystreet.controllers;

import com.fixmystreet.fixmystreet.dtos.ApiResponse.ApiResponseDTO;
import com.fixmystreet.fixmystreet.dtos.auth.*;
import com.fixmystreet.fixmystreet.services.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO> register(@Valid @RequestBody SignupRequestDTO request) {
        AuthResponseDTO response = authService.register(request);
        return ResponseEntity.ok(ApiResponseDTO.success("User registered successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(ApiResponseDTO.success("Login successful", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDTO> logout(@RequestHeader("Authorization") String authHeader) {
        authService.logout(authHeader);
        return ResponseEntity.ok(ApiResponseDTO.success("Logged out successfully", null));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponseDTO> refresh(@Valid @RequestBody RefreshTokenRequestDTO request) {
        AuthResponseDTO response = authService.refreshToken(request);
        return ResponseEntity.ok(ApiResponseDTO.success("Token refreshed successfully", response));
    }
}
