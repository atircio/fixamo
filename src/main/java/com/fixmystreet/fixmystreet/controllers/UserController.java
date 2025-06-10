package com.fixmystreet.fixmystreet.controllers;

import com.fixmystreet.fixmystreet.dtos.users.CreateUserDTO;
import com.fixmystreet.fixmystreet.dtos.users.UpdateUserDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserProfileDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserWithReportsDTO;
import com.fixmystreet.fixmystreet.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserProfileDTO> createUser(@RequestBody CreateUserDTO dto) {
        UserProfileDTO user = userService.createUser(dto);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getUserById(@PathVariable Long id) {
        UserProfileDTO user = userService.findUserProfileDtoById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email")
    public ResponseEntity<UserProfileDTO> getUserByEmail(@RequestParam String email) {
        UserProfileDTO user = userService.findByEmail(email);
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
    public ResponseEntity<List<UserProfileDTO>> getAllUsers() {
        List<UserProfileDTO> users = userService.getAllUserWithAllReports();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO dto) {
        UserProfileDTO updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
