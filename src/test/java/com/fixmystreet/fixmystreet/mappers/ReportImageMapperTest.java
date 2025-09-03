package com.fixmystreet.fixmystreet.mappers;

import com.fixmystreet.fixmystreet.dtos.reportImage.ReportImageDTO;
import com.fixmystreet.fixmystreet.model.ReportImage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReportImageMapperTest {

    @Autowired
    ReportImageMapper reportImageMapper;



    @Test
    void shouldMapReportImageToReportImageDTO(){
        ReportImageDTO dto = new ReportImageDTO("image1.png");

        ReportImage reportImage = reportImageMapper.mapReportImageDtoToReportImage(dto);

        Assertions.assertNotNull(reportImage);
        Assertions.assertNull(reportImage.getReport());
        Assertions.assertEquals("image1.png", reportImage.getImageUrl());
    }

    @Test
    void shouldMapReportImageDtoListToReportImageList(){
        List<ReportImageDTO> reportImageList = List.of(
                new ReportImageDTO("image1.png"),
                new ReportImageDTO("image2.png"));

        List<ReportImage> response = reportImageMapper.mapReportImageDtoListToReportImageList(reportImageList);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, reportImageList.size());
    }

}
