package com.example.Resolucion.Modelo.Parcial.lote.model;

import com.example.Resolucion.Modelo.Parcial.estante.model.Estante;
import com.example.Resolucion.Modelo.Parcial.reactivo.model.Reactivo;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "lotes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Lote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nro_lote", nullable = false)
    private String nroLote;

    @Column(name = "fecha_recepcion", nullable = false)
    private LocalDate fechaRecepcion;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "cantidad_kg", nullable = false)
    private Double cantidadKg;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reactivo")
    private Reactivo reactivo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_estante")
    private Estante estante;
}