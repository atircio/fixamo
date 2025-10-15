package com.fixmystreet.fixmystreet.services;

import com.fixmystreet.fixmystreet.dtos.auth.AuthResponseDTO;
import com.fixmystreet.fixmystreet.dtos.auth.LoginRequestDTO;
import com.fixmystreet.fixmystreet.dtos.auth.RefreshTokenRequestDTO;
import com.fixmystreet.fixmystreet.dtos.auth.SignupRequestDTO;

public interface AuthService {

    AuthResponseDTO register(SignupRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);
    void logout(String authHeader);
    AuthResponseDTO refreshToken(RefreshTokenRequestDTO request);


}
