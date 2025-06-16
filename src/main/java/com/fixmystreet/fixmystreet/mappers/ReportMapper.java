package com.fixmystreet.fixmystreet.mappers;

import com.fixmystreet.fixmystreet.dtos.reports.CreateReportDTO;
import com.fixmystreet.fixmystreet.dtos.reports.ReportResponseDTO;
import com.fixmystreet.fixmystreet.model.Report;
import com.fixmystreet.fixmystreet.services.UserService;
import org.mapstruct.*;

@Mapper(componentModel = "spring",  uses = {ReportImageMapper.class} , injectionStrategy = InjectionStrategy.FIELD)
public interface ReportMapper {

    @Mappings({
            @Mapping(target = "rewrittenMessage", ignore = true),
            @Mapping(target = "user", expression = "java(userService.findUserById(dto.userId()))"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "severity", ignore = true),
            @Mapping(target = "category", ignore = true),
            @Mapping(target = "keywords", ignore = true),
            @Mapping(target = "reportImages", source = "dto.reportImages")
    })
    Report mapCreateReportDtoToReport(CreateReportDTO dto, @Context UserService userService);

    ReportResponseDTO mapReportToReportResponseDto(Report report);
}
