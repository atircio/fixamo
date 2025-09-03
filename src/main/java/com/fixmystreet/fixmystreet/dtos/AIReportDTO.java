package com.fixmystreet.fixmystreet.dtos;

import java.util.List;

public record AIReportDTO(
        String title,
        String rewrittenMessage,
        String severity,
        String category,
        List<String> keywords
) {}
