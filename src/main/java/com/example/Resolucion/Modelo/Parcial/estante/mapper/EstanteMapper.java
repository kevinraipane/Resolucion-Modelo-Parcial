package com.example.Resolucion.Modelo.Parcial.estante.mapper;

import com.example.Resolucion.Modelo.Parcial.estante.dto.EstanteCreateDTO;
import com.example.Resolucion.Modelo.Parcial.estante.dto.EstanteDTO;
import com.example.Resolucion.Modelo.Parcial.estante.dto.EstanteUpdateDTO;
import com.example.Resolucion.Modelo.Parcial.estante.model.Estante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EstanteMapper {
    // Con ignore = true le decimos al pana mapStruct: que este campo existe en el target (DTO) pero que no lo mapee,
    // que nosotros como somos re capos nos vamos a encargar (**No le compila)
    @Mapping(target = "riesgoActual", ignore = true)
    EstanteDTO toDTO(Estante estante);
    Estante toEntity(EstanteCreateDTO dto);
    Estante toEntity(EstanteUpdateDTO dto);
}
