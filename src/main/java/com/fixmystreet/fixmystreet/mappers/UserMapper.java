package com.fixmystreet.fixmystreet.mappers;

import com.fixmystreet.fixmystreet.dtos.users.CreateUserDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserResponseDto;
import com.fixmystreet.fixmystreet.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings( {
                @Mapping(target = "role", expression = "java(com.fixmystreet.fixmystreet.model.enums.Role.REPORTER)"),
                @Mapping(target = "reportList", ignore = true)
            } )
    User mapCreateUserDtoToUser(CreateUserDTO dto);


    UserResponseDto mapUserToUserResponseDto(User user);

}
