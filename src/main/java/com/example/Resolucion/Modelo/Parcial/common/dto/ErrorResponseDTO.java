package com.example.Resolucion.Modelo.Parcial.common.dto;
import java.time.LocalDateTime;

public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {}