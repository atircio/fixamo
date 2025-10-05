package com.fixmystreet.fixmystreet.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.Size;

@Data
public class PasswordResetRequestDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String newPassword;

    private String resetToken; // optional if using email verification links
}

