package com.fixmystreet.fixmystreet.dtos.users;


import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {

    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
}

