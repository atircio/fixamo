package com.fixmystreet.fixmystreet.services;

import com.fixmystreet.fixmystreet.dtos.AIReportDTO;
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
        String raw = dto.description();
        String locationText = dto.location() != null ? dto.location().toString() : "";
        AIReportDTO aiResult = aiProcessorService.processReport(raw, locationText);

        Report report = reportMapper.mapCreateReportDtoToReport(dto, userService);
        if (report == null) {
            throw new IllegalStateException("Mapper returned null report");
        }

        report.setTitle(aiResult.title());
        report.setRewrittenMessage(aiResult.rewrittenMessage());
        report.setSeverity(aiResult.severity());
        report.setCategory(categoryRepository.findByName(aiResult.category()).orElseGet(
                () -> {
                    return categoryRepository.save(new Category(aiResult.category()));
                }
        ));
        report.setKeywords(aiResult.keywords().stream().map(k -> new Keyword(k, report)).toList());

        // 4. Save
        Report savedReport = reportRepository.save(report);

        // 5. Return response
        return reportMapper.mapReportToReportResponseDto(savedReport);
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
