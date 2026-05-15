package com.example.Resolucion.Modelo.Parcial.lote.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record LoteUpdateDTO(
        @NotBlank String nroLote,
        @NotNull LocalDate fechaVencimiento,
        @NotNull @PositiveOrZero Double cantidadKg,
        @NotNull Integer idEstante
) {}
