package com.example.Resolucion.Modelo.Parcial.reactivo.repository;


import com.example.Resolucion.Modelo.Parcial.reactivo.model.Reactivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReactivoRepository extends JpaRepository<Reactivo, Integer>, JpaSpecificationExecutor<Reactivo> {}
