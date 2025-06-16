package com.fixmystreet.fixmystreet.mappers;

import com.fixmystreet.fixmystreet.dtos.locations.LocationDTO;
import com.fixmystreet.fixmystreet.dtos.reportImage.ReportImageDTO;
import com.fixmystreet.fixmystreet.dtos.reports.CreateReportDTO;
import com.fixmystreet.fixmystreet.model.Report;
import com.fixmystreet.fixmystreet.model.User;
import com.fixmystreet.fixmystreet.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
