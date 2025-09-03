package com.fixmystreet.fixmystreet.mappers;

import com.fixmystreet.fixmystreet.dtos.reportImage.ReportImageDTO;
import com.fixmystreet.fixmystreet.model.ReportImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportImageMapper {

    @Mapping(target = "imageUrl", source = "imageUrl")
    ReportImageDTO mapReportImageToReportImageDTO(ReportImage reportImage);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "report", ignore = true)
    })
    ReportImage mapReportImageDtoToReportImage(ReportImageDTO reportImageDTO);

    List<ReportImage> mapReportImageDtoListToReportImageList(List<ReportImageDTO> dtoList);




}
