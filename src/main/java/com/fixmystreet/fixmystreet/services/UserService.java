package com.fixmystreet.fixmystreet.services;

import com.fixmystreet.fixmystreet.dtos.users.CreateUserDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserResponseDto;
import com.fixmystreet.fixmystreet.mappers.UserMapper;
import com.fixmystreet.fixmystreet.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Transactional
    public UserResponseDto createUser(CreateUserDTO dto){
        return userMapper.mapUserToUserResponseDto(
                userRepository.save(
                        userMapper.mapCreateUserDtoToUser(dto)
                )
        );
    }

}
