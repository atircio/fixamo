package com.fixmystreet.fixmystreet.mappers;

import com.fixmystreet.fixmystreet.dtos.auth.SignupRequestDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserSummaryDTO;
import com.fixmystreet.fixmystreet.dtos.users.UserWithReportsDTO;
import com.fixmystreet.fixmystreet.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings( {
            @Mapping(target = "role", expression = "java(com.fixmystreet.fixmystreet.model.enums.Role.CITIZEN)"),
            @Mapping(target = "reportList", ignore = true),
            @Mapping(target ="id", ignore = true),
            @Mapping(target ="status", expression = "java(com.fixmystreet.fixmystreet.model.enums.Status.PENDING_VERIFICATION)"),
            @Mapping(target ="createdAt", ignore = true),
            @Mapping(target ="updatedAt", ignore = true)
            } )
    User mapCreateUserDtoToUser(SignupRequestDTO dto);


    UserSummaryDTO mapUserToUserSummaryDTO(User user);

    @Mapping(target = "reportList", source = "reportList")
    UserWithReportsDTO mapUserToUserWithReportsDTO(User user);

}
