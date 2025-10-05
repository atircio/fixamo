package com.fixmystreet.fixmystreet.dtos.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseDTO {
    private boolean success;
    private String message;
    private Instant timestamp;

    // Optional: can carry extra data
    private Object data;

    public static ApiResponseDTO success(String message, Object data) {
        return ApiResponseDTO.builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(Instant.now())
                .build();
    }

    public static ApiResponseDTO error(String message) {
        return ApiResponseDTO.builder()
                .success(false)
                .message(message)
                .timestamp(Instant.now())
                .build();
    }
}
