package com.fixmystreet.fixmystreet.dtos.reports;

import com.fixmystreet.fixmystreet.dtos.categories.CategoryDTO;
import com.fixmystreet.fixmystreet.dtos.keywords.KeywordDTO;
import com.fixmystreet.fixmystreet.dtos.locations.LocationDTO;
import com.fixmystreet.fixmystreet.dtos.reportImage.ReportImageDTO;
import java.time.LocalDateTime;
import java.util.List;

public record ReportResponseDTO(
        String title,
        String description,
        String rewrittenMessage,
        String severity,
        CategoryDTO category,
        List<ReportImageDTO> reportImages,
        List<KeywordDTO> keywords,
        LocationDTO location,
        LocalDateTime createdAt

) {
}
