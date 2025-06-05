package com.fixmystreet.fixmystreet.controllers;

import com.fixmystreet.fixmystreet.dtos.users.CreateUserDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserResponseDto;
import com.fixmystreet.fixmystreet.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "/newUser")
    public ResponseEntity<UserResponseDto> userResponseDtoResponseEntity( @RequestBody CreateUserDTO dto){
        return ResponseEntity.ok().body(userService.createUser(dto));
    }
}
