package com.fixmystreet.fixmystreet.config.security;

import com.fixmystreet.fixmystreet.dtos.auth.AuthResponseDTO;
import com.fixmystreet.fixmystreet.dtos.auth.LoginRequestDTO;
import com.fixmystreet.fixmystreet.dtos.auth.SignupRequestDTO;
import com.fixmystreet.fixmystreet.model.enums.AuthProvider;
import com.fixmystreet.fixmystreet.repository.TokenRepository;
import com.fixmystreet.fixmystreet.repository.UserRepository;
import com.fixmystreet.fixmystreet.services.impl.AuthServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    private final AuthServiceImpl authService;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        AuthResponseDTO user = userRepository.findByEmail(oAuth2User.getAttribute("email"))
                .map(existingUSer -> authService.login(new LoginRequestDTO(
                        existingUSer.getUsername(), existingUSer.getPassword(), AuthProvider.GOOGLE)))
                .orElseGet(() -> {
                    String randomPassowrd = UUID.randomUUID().toString();

                    return authService.register(new SignupRequestDTO(
                            oAuth2User.getAttribute("name"),
                            oAuth2User.getAttribute("name"),
                            oAuth2User.getAttribute("email"),
                            randomPassowrd,
                            oAuth2User.getAttribute("picture")
                    ));
                });


        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + user.getAccessToken() + "\"}");
    }
}
