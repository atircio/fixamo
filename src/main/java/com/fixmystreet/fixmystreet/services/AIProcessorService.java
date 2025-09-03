package com.fixmystreet.fixmystreet.services;

import com.fixmystreet.fixmystreet.dtos.AIReportDTO;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AIProcessorService {

    private final RestTemplate restTemplate;
    Dotenv dotenv = Dotenv.load();
    private final String apiKey = dotenv.get("OPENROUTER_API_KEY");
    public AIProcessorService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public AIReportDTO processReport(String rawReport, String location) {
        String url = "https://openrouter.ai/api/v1/chat/completions";

        String prompt = String.join("\n",
                "Rewrite the following user-submitted report into a clear, formal, and professional format. Include:",
                "1. Title",
                "2. Category (Roads, Sanitation, Water, Electricity, Noise, Animal Control, Other)",
                "3. Location",
                "4. Severity (Low, Medium, High)",
                "5. Keywords (3–5)",
                "6. Formatted Report",
                "7. Language: English",
                "",
                "Raw report: " + rawReport,
                "Location: " + location
        );

        var request = Map.of(
                "model", "deepseek/deepseek-chat-v3.1:free",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);

        String content = (String) ((Map)((Map)((List)response.get("choices")).get(0)).get("message")).get("content");

        String title = extractField(content, "Title");
        String category = extractField(content, "Category");
        String severity = extractField(content, "Severity");
        String keywordsStr = extractField(content, "Keywords");
        List<String> keywords = keywordsStr != null ? Arrays.asList(keywordsStr.split("\\s*,\\s*")) : List.of();
        String formattedReport = extractField(content, "Formatted Report");



        return new AIReportDTO(title, formattedReport, severity, category, keywords);
    }

    private String extractField(String text, String fieldName) {
        Pattern pattern = Pattern.compile(fieldName + "\\s*:\\s*(.+?)(\\n|$)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }

}
