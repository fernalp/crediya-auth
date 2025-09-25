package com.crediya.autenticacion.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponseDTO(
        @JsonProperty("timestamp")
        LocalDateTime timestamp,
        
        @JsonProperty("code")
        String code,
        
        @JsonProperty("message")
        String message
) {
    
    public static ErrorResponseDTO of(String code, String message) {
        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .code(code)
                .message(message)
                .build();
    }
    
}