package com.fixmystreet.fixmystreet.dtos.auth;

import com.fixmystreet.fixmystreet.dtos.users.UserSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String tokenType ;
    private UserSummaryDTO user;
}
