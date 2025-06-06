package com.fixmystreet.fixmystreet.mappers;

import com.fixmystreet.fixmystreet.dtos.reports.CreateReportDTO;
import com.fixmystreet.fixmystreet.dtos.reports.ReportResponseDTO;
import com.fixmystreet.fixmystreet.model.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    @Mappings({
            @Mapping(target = "rewrittenMessage", ignore = true),
            @Mapping(target = "user", expression = "java")
    })
    Report mapCreateReportDtoToReport(CreateReportDTO dto);

    ReportResponseDTO mapReportToReportResponseDto(Report report);
}
