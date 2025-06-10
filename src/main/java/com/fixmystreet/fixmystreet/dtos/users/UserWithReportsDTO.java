package com.fixmystreet.fixmystreet.dtos.users;

import com.fixmystreet.fixmystreet.dtos.reports.ReportResponseDTO;


import java.util.List;

public record UserWithReportsDTO(
        String name,
        String email,
        String profileImage,
        List<ReportResponseDTO> reportList
) {
}
