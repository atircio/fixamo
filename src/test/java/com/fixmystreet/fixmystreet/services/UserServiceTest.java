package com.fixmystreet.fixmystreet.services;

import com.fixmystreet.fixmystreet.dtos.users.SignupRequestDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserProfileDTO;
import com.fixmystreet.fixmystreet.mappers.UserMapper;
import com.fixmystreet.fixmystreet.model.User;
import com.fixmystreet.fixmystreet.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;


    @Test
    void shouldCreateAndSaveUserSuccessfully(){
        //Given
        SignupRequestDTO dto = new SignupRequestDTO(
            "Atircio Matias",
                "att",
                "atmatias12@gmail.com",
                "pass123",
                "image.png"
        );

        User user = new User();
        user.setName("Atircio Matias");
        user.setEmail("atircio12@gmail.com");
        user.setPassword("pass123");
        user.setProfileImage("profile.png");

        User savedUser = new User();
        user.setId(1L);
        user.setName("Atircio Matias");
        user.setEmail("atircio12@gmail.com");
        user.setPassword("pass123");
        user.setProfileImage("profile.png");

        UserProfileDTO expectedDto = new UserProfileDTO(
                "Atircio Matias",
                "atmatias12@gmail.com",
                "image.png"
        );

        //Mock
        when(userMapper.mapCreateUserDtoToUser(dto)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPass123");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.mapUserToUserResponseDto(any(User.class))).thenReturn(expectedDto);


        //When
        UserProfileDTO response = userService.createUser(dto);

        //Then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(dto.name(), response.name());
        Assertions.assertEquals(dto.email(), response.email());
        Assertions.assertEquals(dto.profileImage(), response.profileImage());
        verify(userMapper, times(1)).mapCreateUserDtoToUser(dto);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).mapUserToUserResponseDto(savedUser);
    }
}
