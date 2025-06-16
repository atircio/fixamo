package com.fixmystreet.fixmystreet.dtos.reports;


import com.fixmystreet.fixmystreet.dtos.locations.LocationDTO;
import com.fixmystreet.fixmystreet.dtos.reportImage.ReportImageDTO;

import java.util.List;

public record updateReportDTO(
        String title,
        String description,
        List<ReportImageDTO> reportImages,
        LocationDTO location
) {
}
