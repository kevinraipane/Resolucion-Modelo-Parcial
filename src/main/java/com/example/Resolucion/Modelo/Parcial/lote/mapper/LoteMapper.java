package com.example.Resolucion.Modelo.Parcial.lote.mapper;

import com.example.Resolucion.Modelo.Parcial.lote.dto.LoteDTO;
import com.example.Resolucion.Modelo.Parcial.lote.model.Lote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoteMapper {
    @Mapping(source = "reactivo.id", target = "idReactivo")
    @Mapping(source = "estante.id", target = "idEstante")
    LoteDTO toDTO(Lote lote);
}

/*
 * lote.reactivo  → objeto Reactivo (con id, nombre, nivelPeligro...)
 * lote.estante   → objeto Estante  (con id, codigo, capacidad...)
 *
 * pero LoteDTO (la response) tiene
 * Integer idReactivo
   Integer idEstante


 * idReactivo  → Integer
 * idEstante   → Integer
 *
 * entonces mapStruct le dice
 *
 * source = "reactivo.id" → navega al objeto reactivo y toma su campo id
    target = "idReactivo" → ponelo en el campo idReactivo del DTO
    *
    * si no ponemos @Mapping mapStruct te deja esos campos como null, ya que no tienen el mismo nombre
 */