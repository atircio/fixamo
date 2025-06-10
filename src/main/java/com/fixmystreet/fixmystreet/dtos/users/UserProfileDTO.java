package com.fixmystreet.fixmystreet.dtos.users;


public record UserProfileDTO(

        String name,

        //@Email(message = "Email is not valid")
        String email,

        String profileImage
) {
}
