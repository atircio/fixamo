package com.fixmystreet.fixmystreet.dtos.reports;

import com.fixmystreet.fixmystreet.dtos.ReportImage.ReportImageDTO;
import com.fixmystreet.fixmystreet.dtos.categories.CategoryDTO;
import com.fixmystreet.fixmystreet.dtos.keywords.KeywordDTO;
import com.fixmystreet.fixmystreet.dtos.locations.LocationDTO;
import com.fixmystreet.fixmystreet.model.Category;
import com.fixmystreet.fixmystreet.model.Keyword;
import com.fixmystreet.fixmystreet.model.Location;
import com.fixmystreet.fixmystreet.model.ReportImage;

import java.time.LocalDateTime;
import java.util.List;

public record CreateReportDTO(
        String title,
        String description,
        String Severity,
        CategoryDTO category,
        Long userId,
        List<ReportImageDTO> reportImages,
        List<KeywordDTO> keywords,
        LocationDTO location,
        LocalDateTime createdAt
) {
}
