package com.example.Resolucion.Modelo.Parcial.lote.dto;

import java.time.LocalDate;

public record LoteDTO(
        Integer id,
        String nroLote,
        LocalDate fechaRecepcion,
        LocalDate fechaVencimiento,
        Double cantidadKg,
        Integer idReactivo,
        Integer idEstante
) {}
