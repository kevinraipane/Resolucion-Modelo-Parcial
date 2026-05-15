package com.example.Resolucion.Modelo.Parcial.estante.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estantes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Estante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo_almacen", nullable = false)
    private String codigoAlmacen;

    @Column(name = "capacidad_max_kg", nullable = false)
    private Double capacidadMaxKg;

    @Column(name = "riesgo_limite", nullable = false)
    private Integer riesgoLimite;
}
