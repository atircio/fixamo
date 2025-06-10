package com.fixmystreet.fixmystreet.dtos.reports;

import com.fixmystreet.fixmystreet.dtos.reportImage.ReportImageDTO;
import com.fixmystreet.fixmystreet.dtos.locations.LocationDTO;
import java.util.List;

public record CreateReportDTO(
        String title,
        String description,
        Long userId,
        List<ReportImageDTO> reportImages,
        LocationDTO location
) {
}
