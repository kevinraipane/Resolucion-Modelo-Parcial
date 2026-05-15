package com.example.Resolucion.Modelo.Parcial.lote.repository;

import com.example.Resolucion.Modelo.Parcial.lote.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Integer> {
    List<Lote> findByEstanteId(Integer estanteId);
    boolean existsByReactivoIdAndFechaVencimientoGreaterThanEqualAndCantidadKgGreaterThan(Integer idReactivo, LocalDate fecha, Double cantidadKg);
}
