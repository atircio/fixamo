package com.fixmystreet.fixmystreet.mappers;

import com.fixmystreet.fixmystreet.dtos.locations.LocationDTO;
import com.fixmystreet.fixmystreet.dtos.reportImage.ReportImageDTO;
import com.fixmystreet.fixmystreet.dtos.reports.CreateReportDTO;
import com.fixmystreet.fixmystreet.dtos.reports.ReportResponseDTO;
import com.fixmystreet.fixmystreet.model.*;
import com.fixmystreet.fixmystreet.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {ReportMapperImpl.class, ReportImageMapperImpl.class})
class ReportMapperTest {


    @Autowired
    ReportMapper reportMapper;



     UserService userService;

    @BeforeEach
    void setUp() {

        userService = mock(UserService.class);
    }




    @Test
    void shouldMapCreateReportDtoToReport() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Mockito.when(userService.findUserById(userId)).thenReturn(user);

        CreateReportDTO dto = new CreateReportDTO(
                "Title",
                "Description",
                userId,
                List.of(new ReportImageDTO("img.png")),
                new LocationDTO(10.0, 20.0, "Main Street")
        );

        // Act
        Report report = reportMapper.mapCreateReportDtoToReport(dto, userService);

        // Assert
        assertNotNull(report);
        assertEquals("Title", report.getTitle());
        assertEquals("Description", report.getDescription());
        assertEquals(1L, report.getUser().getId());
        assertEquals(1, report.getReportImages().size());
        assertEquals("Main Street", report.getLocation().getAddress());

    }

    @Test
    void shouldMapReportToReportResponseDto(){
        //Given
        User user = new User();
        user.setName("Atircio Matias");
        user.setEmail("atircio12@gmail.com");
        user.setProfileImage("profile.png");

        Report report = new Report();
        report.setTitle("Hole on the street");
        report.setDescription("A big hole has appeared yesterday on the main street ");
        report.setRewrittenMessage("Yesterday on the main street, people have seen a hole");
        report.setSeverity("High");
        report.setCategory(new Category("Street"));
        List<ReportImage> reportImages = new ArrayList<>();
        reportImages.add(new ReportImage("imag1", report));
        reportImages.add(new ReportImage("imag2", report));
        report.setReportImages(reportImages);
        List<Keyword> keywords = new ArrayList<>();
        keywords.add(new Keyword("Hole", report));
        keywords.add(new Keyword("Street", report));
        report.setKeywords(keywords);
        report.setLocation(new Location(12.345, 8.0, "Main Street", report));
        report.setCreatedAt(LocalDateTime.now());

        //When
        ReportResponseDTO dto = reportMapper.mapReportToReportResponseDto(report);

        assertNotNull(dto);
        assertEquals("Hole on the street", dto.title());
        assertEquals("Yesterday on the main street, people have seen a hole", dto.rewrittenMessage());
        assertEquals("Street", dto.category().name());
        assertEquals(2, report.getReportImages().size());
        assertEquals(2, report.getKeywords().size());
        assertEquals("Main Street", dto.location().address());
        assertEquals(report.getCreatedAt(), dto.createdAt());
    }
}
