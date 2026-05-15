package com.example.Resolucion.Modelo.Parcial.estante.mapper;

import com.example.Resolucion.Modelo.Parcial.estante.dto.EstanteCreateDTO;
import com.example.Resolucion.Modelo.Parcial.estante.dto.EstanteDTO;
import com.example.Resolucion.Modelo.Parcial.estante.dto.EstanteUpdateDTO;
import com.example.Resolucion.Modelo.Parcial.estante.model.Estante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EstanteMapper {
    @Mapping(target = "riesgoActual", ignore = true)
    EstanteDTO toDTO(Estante estante);
    Estante toEntity(EstanteCreateDTO dto);
    Estante toEntity(EstanteUpdateDTO dto);
}
