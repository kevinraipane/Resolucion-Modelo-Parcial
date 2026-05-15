package com.example.Resolucion.Modelo.Parcial.lote.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record LoteCreateDTO(
        @NotBlank String nroLote,
        @NotNull LocalDate fechaRecepcion,
        @NotNull LocalDate fechaVencimiento,
        @NotNull @Positive Double cantidadKg,
        @NotNull Integer idReactivo,
        @NotNull Integer idEstante
) {}
