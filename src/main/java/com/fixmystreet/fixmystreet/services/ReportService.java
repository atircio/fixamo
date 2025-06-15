package com.fixmystreet.fixmystreet.services;

import com.fixmystreet.fixmystreet.dtos.reportImage.ReportImageDTO;
import com.fixmystreet.fixmystreet.dtos.reports.CreateReportDTO;
import com.fixmystreet.fixmystreet.dtos.reports.ReportResponseDTO;
import com.fixmystreet.fixmystreet.dtos.reports.updateReportDTO;
import com.fixmystreet.fixmystreet.mappers.LocationMapper;
import com.fixmystreet.fixmystreet.mappers.ReportImageMapper;
import com.fixmystreet.fixmystreet.mappers.ReportMapper;
import com.fixmystreet.fixmystreet.model.*;
import com.fixmystreet.fixmystreet.repository.CategoryRepository;
import com.fixmystreet.fixmystreet.repository.LocationRepository;
import com.fixmystreet.fixmystreet.repository.ReportRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final AIProcessorService aiProcessorService;
    private final ReportImageMapper reportImageMapper;
    private final LocationMapper locationMapper;

    public ReportService(ReportRepository reportRepository, ReportMapper reportMapper, UserService userService, CategoryRepository categoryRepository, LocationRepository locationRepository, AIProcessorService aiProcessorService, ReportImageMapper reportImageMapper, LocationMapper locationMapper) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
        this.aiProcessorService = aiProcessorService;
        this.reportImageMapper = reportImageMapper;
        this.locationMapper = locationMapper;
    }

    public ReportResponseDTO createReport(CreateReportDTO dto) {
        Report report = reportMapper.mapCreateReportDtoToReport(dto, userService);

        //Mocking an external service to set a category name
        String categoryFromAI = aiProcessorService.CategorizeReport(dto.title());
        Category category = categoryRepository.findByName(categoryFromAI).orElse(categoryRepository.save(new Category(categoryFromAI)));

        Location location = locationRepository.save(new Location(dto.location().latitude(), dto.location().longitude(), dto.location().address(), report));

        List<String> keywordsFromAI = aiProcessorService.setKeywords(dto.description());
        List<Keyword> keywords = keywordsFromAI.stream().map(
                word -> {
                    Keyword keyword = new Keyword();
                    keyword.setWord(word);
                    keyword.setReport(report);
                    return keyword;
                }).toList();

        report.setLocation(location);
        report.setSeverity("high");
        report.setCategory(category);
        report.setRewrittenMessage(aiProcessorService.rewriteDescription(dto.title() ,dto.description()));
        report.setKeywords(keywords);


        report.getReportImages().forEach(image -> image.setReport(report));
        reportRepository.save(report);
        return reportMapper.mapReportToReportResponseDto(report);
    }

    public List<ReportResponseDTO> getAllReports() {
        return reportRepository.findAll()
                .stream()
                .map(reportMapper::mapReportToReportResponseDto)
                .collect(Collectors.toList());
    }

    public ReportResponseDTO getReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
        return reportMapper.mapReportToReportResponseDto(report);
    }

    public ReportResponseDTO updateReport(Long id, updateReportDTO dto) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));

        if(dto.title() != null && !dto.title().isBlank()){
            report.setTitle(dto.title());
        }
        if(dto.description() != null && !dto.description().isBlank()){
            report.setDescription(dto.description());
        }
        if(dto.reportImages() != null){
            List<ReportImage> reportImagesList = new ArrayList<>();
            for (ReportImageDTO i : dto.reportImages()) {
                reportImagesList.add(new ReportImage(i.imageUrl(), report));
            }
            report.getReportImages().clear();
            report.setReportImages(reportImagesList);
        }
        if(dto.location() != null ){
            if (dto.location().address() != null){
                report.getLocation().setAddress(dto.location().address());
            }
            if (dto.location().longitude() != null){
                report.getLocation().setLongitude(dto.location().longitude());
            }
            if (dto.location().latitude() != null){
                report.getLocation().setLatitude(dto.location().latitude());
            }
        }

        reportRepository.save(report);
        return reportMapper.mapReportToReportResponseDto(report);
    }

    public void deleteReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
        reportRepository.delete(report);
    }

    public List<ReportResponseDTO> getReportsByUserId(Long userId) {
        return reportRepository.findAll()
                .stream()
                .filter(r -> r.getUser().getId().equals(userId))
                .map(reportMapper::mapReportToReportResponseDto)
                .collect(Collectors.toList());
    }
}
