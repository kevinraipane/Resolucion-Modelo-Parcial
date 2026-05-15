package com.example.Resolucion.Modelo.Parcial.lote.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ConsumoLoteDTO(
        @NotNull @Positive Double cantidadAExtraer
) {}
