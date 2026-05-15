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
