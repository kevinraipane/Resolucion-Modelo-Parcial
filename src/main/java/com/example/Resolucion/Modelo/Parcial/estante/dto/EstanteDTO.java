package com.example.Resolucion.Modelo.Parcial.estante.dto;

public record EstanteDTO(
        Integer id,
        String codigoAlmacen,
        Double capacidadMaxKg,
        Integer riesgoLimite,
        Integer riesgoActual
) {}
