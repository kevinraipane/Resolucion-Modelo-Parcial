package com.example.Resolucion.Modelo.Parcial.reactivo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reactivos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Reactivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "nivel_peligro", nullable = false)
    private Integer nivelPeligro;

    @Column(name = "es_precursor_quimico", nullable = false)
    private Boolean esPrecursorQuimico;

    @Column(nullable = false)
    private Boolean activo;
}
