package com.fixmystreet.fixmystreet.dtos.reports;

import com.fixmystreet.fixmystreet.model.Category;
import com.fixmystreet.fixmystreet.model.Keyword;
import com.fixmystreet.fixmystreet.model.Location;
import com.fixmystreet.fixmystreet.model.ReportImage;

import java.time.LocalDateTime;
import java.util.List;

public record ReportResponseDTO(
        String title,
        String description,
        String rewrittenMessage,
        String Severity,
        Category category,
        List<ReportImage> reportImages,
        List<Keyword> keywords,
        Location location,
        LocalDateTime createdAt

) {
}
