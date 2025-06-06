package com.fixmystreet.fixmystreet.dtos.users;

import com.fixmystreet.fixmystreet.model.Report;

import java.util.List;

public record UserResponseWithAllReportsDTO(
        String name,
        String email,
        String profileImage,
        List<Report> reportList
) {
}
