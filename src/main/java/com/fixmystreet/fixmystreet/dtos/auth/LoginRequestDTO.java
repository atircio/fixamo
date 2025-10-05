package com.fixmystreet.fixmystreet.dtos.auth;


import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {

    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
}

