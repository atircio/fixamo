package com.fixmystreet.fixmystreet.controllers;

import com.fixmystreet.fixmystreet.dtos.ApiResponse.ApiResponseDTO;
import com.fixmystreet.fixmystreet.services.impl.EmailVerificationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vi/auth")
public class EmailVerificationTokenController {

    private final EmailVerificationServiceImpl emailVerificationService;

    public EmailVerificationTokenController(EmailVerificationServiceImpl emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @GetMapping("verify-email")
    public ResponseEntity<ApiResponseDTO> verifyEmail(@RequestParam("token") String token){
        emailVerificationService.verifyToken(token);
        return ResponseEntity.ok(ApiResponseDTO.success("Email verified successfully", null));
    }
}
