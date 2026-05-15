package com.example.Resolucion.Modelo.Parcial.estante.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record EstanteCreateDTO(
        @NotBlank String codigoAlmacen,
        @NotNull @Positive Double capacidadMaxKg,
        @NotNull @PositiveOrZero Integer riesgoLimite
) {}
