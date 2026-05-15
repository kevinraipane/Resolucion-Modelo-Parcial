package com.example.Resolucion.Modelo.Parcial.reactivo.dto;

public record ReactivoDTO(
        Integer id,
        String nombre,
        Integer nivelPeligro,
        Boolean esPrecursorQuimico,
        Boolean activo
) {}
