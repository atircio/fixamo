package com.fixmystreet.fixmystreet.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummaryDTO {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String profileImage;
}
