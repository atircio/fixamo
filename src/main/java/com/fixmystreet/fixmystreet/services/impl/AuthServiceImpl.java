package com.fixmystreet.fixmystreet.services.impl;

import com.fixmystreet.fixmystreet.config.security.JwtService;
import com.fixmystreet.fixmystreet.dtos.auth.AuthResponseDTO;
import com.fixmystreet.fixmystreet.dtos.auth.LoginRequestDTO;
import com.fixmystreet.fixmystreet.dtos.auth.RefreshTokenRequestDTO;
import com.fixmystreet.fixmystreet.dtos.auth.SignupRequestDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserSummaryDTO;
import com.fixmystreet.fixmystreet.exceptions.BadRequestException;
import com.fixmystreet.fixmystreet.exceptions.ResourceNotFoundException;
import com.fixmystreet.fixmystreet.model.User;
import com.fixmystreet.fixmystreet.model.enums.Role;
import com.fixmystreet.fixmystreet.model.enums.Status;
import com.fixmystreet.fixmystreet.repository.UserRepository;
import com.fixmystreet.fixmystreet.services.AuthService;
import com.fixmystreet.fixmystreet.services.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationServiceImpl emailVerificationService;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailVerificationServiceImpl emailVerificationService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailVerificationService = emailVerificationService;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponseDTO register(SignupRequestDTO request) {


        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Email is already in use");
        }


        if (userRepository.existsByUsername(request.username())) {
            throw new BadRequestException("Username is already taken");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setName(request.name());
        user.setStatus(Status.PENDING_VERIFICATION);
        user.setRole(Role.CITIZEN);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setProfileImage(request.profileImage());

        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
        String refreshToken = jwtService.generateToken(user.getUsername(), user.getRole().name());

        emailVerificationService.sendVerificationEmail(user, "http://localhost:8080");

        return new AuthResponseDTO(token, refreshToken, "Bearer",
                new UserSummaryDTO(user.getId(), user.getUsername(), user.getEmail(), user.getName(), user.getProfileImage()));
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByUsername(request.getUsernameOrEmail())
                .or(() -> userRepository.findByEmail(request.getUsernameOrEmail()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
        String refreshToken = jwtService.generateToken(user.getUsername(), user.getRole().name()); // same method, different expiry if you prefer


        return new AuthResponseDTO(token, refreshToken, "Bearer",
                new UserSummaryDTO(user.getId(), user.getUsername(), user.getEmail(), user.getName(), user.getProfileImage()));
    }

    @Override
    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO request) {
        String username = jwtService.extractUsername(request.getRefreshToken());
        String role = jwtService.extractUserRole(request.getRefreshToken());
        if (!jwtService.isTokenValid(request.getRefreshToken(), username)) {
            throw new BadRequestException("Invalid or expired refresh token");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String newAccessToken = jwtService.generateToken(username, role);
        String newRefreshToken = jwtService.generateToken(username, role);

        return new AuthResponseDTO(newAccessToken, newRefreshToken, "Bearer",
                new UserSummaryDTO(user.getId(), user.getUsername(), user.getEmail(), user.getName(), user.getProfileImage()));
    }
}
