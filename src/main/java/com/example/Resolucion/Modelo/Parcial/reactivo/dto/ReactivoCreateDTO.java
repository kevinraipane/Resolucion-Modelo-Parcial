package com.example.Resolucion.Modelo.Parcial.reactivo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReactivoCreateDTO(
        @NotBlank String nombre,
        @NotNull @Positive Integer nivelPeligro,
        @NotNull Boolean esPrecursorQuimico
        // Aqui no se setea el "activo" ya que MapStruct no puede saber este default de negocio,+
        // Entonces lo hacemos en el service
        // reactivo.setActivo(true);
) {}
