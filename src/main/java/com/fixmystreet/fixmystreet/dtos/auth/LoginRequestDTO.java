package com.fixmystreet.fixmystreet.dtos.auth;


import com.fixmystreet.fixmystreet.model.enums.AuthProvider;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    AuthProvider authProvider = AuthProvider.LOCAL;



}

