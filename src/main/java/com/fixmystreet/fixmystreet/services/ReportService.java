package com.fixmystreet.fixmystreet.services;

import com.fixmystreet.fixmystreet.dtos.reports.CreateReportDTO;
import com.fixmystreet.fixmystreet.dtos.reports.ReportResponseDTO;
import com.fixmystreet.fixmystreet.mappers.ReportMapper;
import com.fixmystreet.fixmystreet.model.Category;
import com.fixmystreet.fixmystreet.model.Keyword;
import com.fixmystreet.fixmystreet.model.Location;
import com.fixmystreet.fixmystreet.model.Report;
import com.fixmystreet.fixmystreet.repository.CategoryRepository;
import com.fixmystreet.fixmystreet.repository.LocationRepository;
import com.fixmystreet.fixmystreet.repository.ReportRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
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

    public ReportService(ReportRepository reportRepository, ReportMapper reportMapper, UserService userService, CategoryRepository categoryRepository, LocationRepository locationRepository, AIProcessorService aiProcessorService) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
        this.aiProcessorService = aiProcessorService;
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

    public ReportResponseDTO updateReport(Long id, CreateReportDTO dto) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));

        report.setTitle(dto.title());
        report.setDescription(dto.description());

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
