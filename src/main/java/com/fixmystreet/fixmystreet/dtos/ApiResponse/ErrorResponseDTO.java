package com.fixmystreet.fixmystreet.dtos.ApiResponse;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    private int statusCode;
    private String error;
    private List<String> details;
    private Instant timestamp;
}

