package com.fixmystreet.fixmystreet.services;

import com.fixmystreet.fixmystreet.dtos.users.CreateUserDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserResponseDto;
import com.fixmystreet.fixmystreet.dtos.users.UpdateUserDTO;
import com.fixmystreet.fixmystreet.mappers.UserMapper;
import com.fixmystreet.fixmystreet.model.User;
import com.fixmystreet.fixmystreet.model.enums.Role;
import com.fixmystreet.fixmystreet.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto createUser(CreateUserDTO dto) {
        User user = userMapper.mapCreateUserDtoToUser(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.REPORTER);
        userRepository.save(user);
        return userMapper.mapUserToUserResponseDto(user);
    }

    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->  new RuntimeException("User not found with email: " + email));

        return userMapper.mapUserToUserResponseDto(user);
    }

    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.mapUserToUserResponseDto(user);
    }

    public UserResponseDto findUserByIdWithAllReports(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.mapUserToUserResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapUserToUserResponseDto)
                .collect(Collectors.toList());
    }
    public List<UserResponseDto> getAllUserWithAllReports() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapUserToUserResponseDto)
                .collect(Collectors.toList());

    }


        public UserResponseDto updateUser(Long id, UpdateUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setName(dto.name());
        user.setEmail(dto.email());

        if (dto.password() != null && !dto.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }

        userRepository.save(user);
        return userMapper.mapUserToUserResponseDto(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }
}
