package com.fixmystreet.fixmystreet.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequestDTO(

        @NotBlank(message = "Name is required")
        @Size(min = 6, max = 100)
        String name,

        @NotBlank
        @Size(min = 6, max = 50)
        String username,

        @Email(message = "Email is not valid")
        String email,

        @NotBlank(message = "Password is required")
        String password,
        String profileImage
) {
}
