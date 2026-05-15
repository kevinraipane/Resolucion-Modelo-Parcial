package com.example.Resolucion.Modelo.Parcial.reactivo.mapper;

import com.example.Resolucion.Modelo.Parcial.reactivo.dto.ReactivoCreateDTO;
import com.example.Resolucion.Modelo.Parcial.reactivo.dto.ReactivoDTO;
import com.example.Resolucion.Modelo.Parcial.reactivo.dto.ReactivoUpdateDTO;
import com.example.Resolucion.Modelo.Parcial.reactivo.model.Reactivo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReactivoMapper {
    ReactivoDTO toDTO(Reactivo reactivo);
    Reactivo toEntity(ReactivoCreateDTO dto);
    Reactivo toEntity(ReactivoUpdateDTO dto);
}
