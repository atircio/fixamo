package com.fixmystreet.fixmystreet.services;

import com.fixmystreet.fixmystreet.dtos.users.UpdateUserDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserSummaryDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserWithReportsDTO;
import com.fixmystreet.fixmystreet.exceptions.ResourceNotFoundException;
import com.fixmystreet.fixmystreet.mappers.UserMapper;
import com.fixmystreet.fixmystreet.model.User;
import com.fixmystreet.fixmystreet.repository.UserRepository;
import com.fixmystreet.fixmystreet.services.impl.AuthServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthServiceImpl authService;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder,
                       AuthServiceImpl authService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }


    public UserSummaryDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->  new RuntimeException("User not found with email: " + email));
        return userMapper.mapUserToUserSummaryDTO(user);
    }

    public UserSummaryDTO findUserProfileDtoById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.mapUserToUserSummaryDTO(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public UserWithReportsDTO findUserByIdWithAllReports(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.mapUserToUserWithReportsDTO(user);
    }

    public List<UserWithReportsDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapUserToUserWithReportsDTO)
                .collect(Collectors.toList());
    }
    public List<UserSummaryDTO> getAllUserWithAllReports() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapUserToUserSummaryDTO)
                .collect(Collectors.toList());

    }


        public UserSummaryDTO updateUser(Long id, UpdateUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if(dto.name() != null && !dto.name().isBlank()){
            user.setName(dto.name());
        }
        if(dto.email() != null && !dto.email().isBlank()){
            user.setEmail(dto.email());
        }
        if(dto.profileImage() != null && !dto.profileImage().isBlank()){
                user.setEmail(dto.profileImage());
        }

        if (dto.password() != null && !dto.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }

        userRepository.save(user);
        return userMapper.mapUserToUserSummaryDTO(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }


    public UserSummaryDTO findByUsername(String username) {
        return userMapper.mapUserToUserSummaryDTO(userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User not found with username: " + username)));
    }
}
