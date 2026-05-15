package com.example.Resolucion.Modelo.Parcial.reactivo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReactivoCreateDTO(
        @NotBlank String nombre,
        @NotNull @Positive Integer nivelPeligro,
        @NotNull Boolean esPrecursorQuimico
) {}
