package com.fixmystreet.fixmystreet.dtos.auth;


import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequestDTO {

    @NotBlank
    private String refreshToken;

    @NotBlank
    private String username;
}