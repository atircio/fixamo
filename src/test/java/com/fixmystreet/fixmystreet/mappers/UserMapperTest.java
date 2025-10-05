//package com.fixmystreet.fixmystreet.mappers;
//
//
//
//import com.fixmystreet.fixmystreet.dtos.auth.SignupRequestDTO;
//import com.fixmystreet.fixmystreet.dtos.users.UserProfileDTO;
//import com.fixmystreet.fixmystreet.dtos.users.UserWithReportsDTO;
//import com.fixmystreet.fixmystreet.model.*;
//import com.fixmystreet.fixmystreet.model.enums.Role;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mapstruct.factory.Mappers;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class UserMapperTest {
//
//    private UserMapper userMapper;
//
//    @BeforeEach
//    void setUp(){
//        userMapper = Mappers.getMapper(UserMapper.class);
//    }
//
//
//
//    @Test
//    void mapCreateUserDtoToUser(){
//
//        SignupRequestDTO dto = new SignupRequestDTO(
//             "Atircio Matias",
//             "atm",
//             "atmatas12@gmail.com",
//             "password123",
//             "profile.png"
//        );
//
//        User user = userMapper.mapCreateUserDtoToUser(dto);
//
//        assertEquals(dto.name(), user.getName());
//        assertEquals(dto.email(), user.getEmail());
//        assertEquals(dto.password(), user.getPassword());
//        assertEquals(dto.profileImage(), user.getProfileImage());
//        assertEquals(Role.CITIZEN, user.getRole());
//
//    }
//
//    @Test
//    void shouldMapUserToUserResponseDto(){
//        User user = new User();
//        user.setName("Atircio Matias");
//        user.setEmail("atircio12@gmail.com");
//        user.setProfileImage("profile.png");
//
//
//        UserProfileDTO dto = userMapper.mapUserToUserResponseDto(user);
//
//        assertEquals(user.getName(), dto.name());
//        assertEquals(user.getEmail(), dto.email());
//        assertEquals(user.getProfileImage(), dto.profileImage());
//    }
//
//    @Test
//    void shouldMapUserToUserWithReportsDTO(){
//        User user = new User();
//        user.setName("Atircio Matias");
//        user.setEmail("atircio12@gmail.com");
//        user.setProfileImage("profile.png");
//
//        Report report = new Report();
//        report.setTitle("Hole on the street");
//        report.setDescription("A big hole has appeared yesterday on the main street ");
//        report.setRewrittenMessage("Yesterday on the main street, people have seen a hole");
//        report.setSeverity("High");
//        report.setCategory(new Category("Street"));
//        List<ReportImage> reportImages = new ArrayList<>();
//        reportImages.add(new ReportImage("imag1", report));
//        reportImages.add(new ReportImage("imag2", report));
//        report.setReportImages(reportImages);
//        List<Keyword> keywords = new ArrayList<>();
//        keywords.add(new Keyword("Hole", report));
//        keywords.add(new Keyword("Street", report));
//        report.setKeywords(keywords);
//        report.setLocation(new Location(12.345, 8.0, "Main Street", report));
//        report.setCreatedAt(LocalDateTime.now());
//
//        user.setReportList(List.of(report));
//
//        UserWithReportsDTO dto = userMapper.mapUserToUserWithReportsDTO(user);
//
//        assertNotNull(dto);
//        assertEquals(user.getName(), dto.name());
//        assertEquals(user.getEmail(), dto.email());
//        assertEquals(user.getProfileImage(), dto.profileImage());
//        assertEquals(1, dto.reportList().size());
//
//    }
//}
