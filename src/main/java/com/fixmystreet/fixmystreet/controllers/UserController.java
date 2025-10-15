package com.fixmystreet.fixmystreet.controllers;

import com.fixmystreet.fixmystreet.dtos.ApiResponse.ApiResponseDTO;
import com.fixmystreet.fixmystreet.dtos.users.UpdateUserDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserSummaryDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserWithReportsDTO;
import com.fixmystreet.fixmystreet.services.UserService;
import com.fixmystreet.fixmystreet.config.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);
        UserSummaryDTO user = userService.findByUsername(username);
        return ResponseEntity.ok(ApiResponseDTO.success("Current user fetched successfully", user));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','AUTHORITY')")
    public ResponseEntity<ApiResponseDTO> getUserById(@PathVariable Long id) {
        UserSummaryDTO user = userService.findUserProfileDtoById(id);
        return ResponseEntity.ok(ApiResponseDTO.success("User fetched successfully", user));
    }

    @GetMapping("/by-email")
    @PreAuthorize("hasAnyRole('ADMIN','AUTHORITY')")
    public ResponseEntity<ApiResponseDTO> getUserByEmail(@RequestParam String email) {
        UserSummaryDTO user = userService.findByEmail(email);
        return ResponseEntity.ok(ApiResponseDTO.success("User fetched successfully", user));
    }

    @GetMapping("/{id}/reports")
    @PreAuthorize("hasAnyRole('ADMIN','AUTHORITY')")
    public ResponseEntity<ApiResponseDTO> getUserWithReports(@PathVariable Long id) {
        UserWithReportsDTO user = userService.findUserByIdWithAllReports(id);
        return ResponseEntity.ok(ApiResponseDTO.success("User with reports fetched successfully", user));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO> getAllUsers() {
        List<UserSummaryDTO> users = userService.getAllUserWithAllReports();
        return ResponseEntity.ok(ApiResponseDTO.success("All users fetched successfully", users));
    }

    @GetMapping("/with-reports")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO> getAllUsersWithReports() {
        List<UserWithReportsDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponseDTO.success("All users with reports fetched successfully", users));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserDTO dto,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtService.extractUsername(token);

        // Optional: check that the authenticated user owns the account or is admin
        UserSummaryDTO updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(ApiResponseDTO.success("User updated successfully", updatedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO> deleteUser(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtService.extractUsername(token);

        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponseDTO.success("User deleted successfully"));
    }
}
