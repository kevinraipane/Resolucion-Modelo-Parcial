package com.example.Resolucion.Modelo.Parcial.reactivo.service;


import com.example.Resolucion.Modelo.Parcial.lote.service.LoteService;
import com.example.Resolucion.Modelo.Parcial.reactivo.dto.ReactivoCreateDTO;
import com.example.Resolucion.Modelo.Parcial.reactivo.dto.ReactivoDTO;
import com.example.Resolucion.Modelo.Parcial.reactivo.dto.ReactivoUpdateDTO;
import com.example.Resolucion.Modelo.Parcial.reactivo.exception.ReactivoConLotesActivosException;
import com.example.Resolucion.Modelo.Parcial.reactivo.exception.ReactivoNotFoundException;
import com.example.Resolucion.Modelo.Parcial.reactivo.mapper.ReactivoMapper;
import com.example.Resolucion.Modelo.Parcial.reactivo.model.Reactivo;
import com.example.Resolucion.Modelo.Parcial.reactivo.repository.ReactivoRepository;
import com.example.Resolucion.Modelo.Parcial.reactivo.specification.ReactivoSpecification;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReactivoService {

    private final ReactivoRepository reactivoRepository;
    private final ReactivoMapper reactivoMapper;
    private final LoteService loteService;

    // Uso de @Lazy explícito en el constructor para evitar dependencia circular con LoteService
    public ReactivoService(ReactivoRepository reactivoRepository,
                           ReactivoMapper reactivoMapper,
                           @Lazy LoteService loteService) {
        this.reactivoRepository = reactivoRepository;
        this.reactivoMapper = reactivoMapper;
        this.loteService = loteService;
    }

    @Transactional
    public ReactivoDTO crear(ReactivoCreateDTO dto) {
        Reactivo reactivo = reactivoMapper.toEntity(dto);
        reactivo.setActivo(true);
        return reactivoMapper.toDTO(reactivoRepository.save(reactivo));
    }

    @Transactional
    public ReactivoDTO actualizar(Integer id, ReactivoUpdateDTO dto) {
        Reactivo reactivo = getReactivoById(id);
        reactivo.setNombre(dto.nombre());
        reactivo.setNivelPeligro(dto.nivelPeligro());
        reactivo.setEsPrecursorQuimico(dto.esPrecursorQuimico());
        reactivo.setActivo(dto.activo());
        return reactivoMapper.toDTO(reactivoRepository.save(reactivo));
    }

    @Transactional
    public void eliminar(Integer id) {
        Reactivo reactivo = getReactivoById(id);

        if (loteService.tieneLotesActivos(id)) {
            throw new ReactivoConLotesActivosException("El reactivo con ID " + id + " no puede eliminarse porque posee lotes activos.");
        }

        reactivoRepository.delete(reactivo);
    }

    @Transactional(readOnly = true)
    public List<ReactivoDTO> filtrarReactivos(String nombre, Integer nivelPeligro, Boolean esPrecursor) {
        Specification<Reactivo> spec = Specification.allOf(
                ReactivoSpecification.nombreContains(nombre),
                ReactivoSpecification.nivelPeligroEquals(nivelPeligro),
                ReactivoSpecification.esPrecursorEquals(esPrecursor)
        );

        return reactivoRepository.findAll(spec).stream()
                .map(reactivoMapper::toDTO)
                .toList();
    }

    public Reactivo getReactivoById(Integer id) {
        return reactivoRepository.findById(id)
                .orElseThrow(() -> new ReactivoNotFoundException("No se encontró el reactivo con ID: " + id));
    }
}
