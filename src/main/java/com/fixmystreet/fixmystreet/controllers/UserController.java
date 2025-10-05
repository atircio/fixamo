package com.fixmystreet.fixmystreet.controllers;

import com.fixmystreet.fixmystreet.dtos.ApiResponse.ApiResponseDTO;
import com.fixmystreet.fixmystreet.dtos.auth.AuthResponseDTO;
import com.fixmystreet.fixmystreet.dtos.auth.LoginRequestDTO;
import com.fixmystreet.fixmystreet.dtos.auth.RefreshTokenRequestDTO;
import com.fixmystreet.fixmystreet.dtos.auth.SignupRequestDTO;
import com.fixmystreet.fixmystreet.dtos.users.UpdateUserDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserSummaryDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserWithReportsDTO;
import com.fixmystreet.fixmystreet.services.UserService;
import com.fixmystreet.fixmystreet.services.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthServiceImpl authService;

    public UserController(UserService userService, AuthServiceImpl authService) {
        this.userService = userService;
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO> register(@Valid @RequestBody SignupRequestDTO request) {
        AuthResponseDTO response = authService.register(request);
        return ResponseEntity.ok(ApiResponseDTO.success("User registered successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(ApiResponseDTO.success("Login successful", response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponseDTO> refresh(@Valid @RequestBody RefreshTokenRequestDTO request) {
        AuthResponseDTO response = authService.refreshToken(request);
        return ResponseEntity.ok(ApiResponseDTO.success("Token refreshed successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSummaryDTO> getUserById(@PathVariable Long id) {
        UserSummaryDTO user = userService.findUserProfileDtoById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email")
    public ResponseEntity<UserSummaryDTO> getUserByEmail(@RequestParam String email) {
        UserSummaryDTO user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/reports")
    public ResponseEntity<UserWithReportsDTO> getUserWithReports(@PathVariable Long id) {
        UserWithReportsDTO user = userService.findUserByIdWithAllReports(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/with-reports")
    public ResponseEntity<List<UserWithReportsDTO>> getAllUsersWithReports() {
        List<UserWithReportsDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping
    public ResponseEntity<List<UserSummaryDTO>> getAllUsers() {
        List<UserSummaryDTO> users = userService.getAllUserWithAllReports();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSummaryDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO dto) {
        UserSummaryDTO updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
