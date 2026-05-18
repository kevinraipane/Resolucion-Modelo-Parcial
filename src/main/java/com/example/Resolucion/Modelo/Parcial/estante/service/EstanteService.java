package com.example.Resolucion.Modelo.Parcial.estante.service;

import com.example.Resolucion.Modelo.Parcial.estante.dto.EstanteCreateDTO;
import com.example.Resolucion.Modelo.Parcial.estante.dto.EstanteDTO;
import com.example.Resolucion.Modelo.Parcial.estante.dto.EstanteUpdateDTO;
import com.example.Resolucion.Modelo.Parcial.estante.exception.EstanteNotFoundException;
import com.example.Resolucion.Modelo.Parcial.estante.mapper.EstanteMapper;
import com.example.Resolucion.Modelo.Parcial.estante.model.Estante;
import com.example.Resolucion.Modelo.Parcial.estante.repository.EstanteRepository;
import com.example.Resolucion.Modelo.Parcial.lote.model.Lote;
import com.example.Resolucion.Modelo.Parcial.lote.service.LoteService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class EstanteService {

    private final EstanteRepository estanteRepository;
    private final EstanteMapper estanteMapper;
    private final LoteService loteService;

    // Uso de @Lazy para evitar dependencia circular
    public EstanteService(EstanteRepository estanteRepository,
                          EstanteMapper estanteMapper,
                          @Lazy LoteService loteService) {
        this.estanteRepository = estanteRepository;
        this.estanteMapper = estanteMapper;
        this.loteService = loteService;
    }

    @Transactional
    public EstanteDTO crear(EstanteCreateDTO dto) {
        return toDTOWithCalculatedRisk(estanteRepository.save(estanteMapper.toEntity(dto)));
    }

    @Transactional
    public EstanteDTO actualizar(Integer id, EstanteUpdateDTO dto) {
        Estante estante = getEstanteById(id);
        estante.setCodigoAlmacen(dto.codigoAlmacen());
        estante.setCapacidadMaxKg(dto.capacidadMaxKg());
        estante.setRiesgoLimite(dto.riesgoLimite());
        return toDTOWithCalculatedRisk(estanteRepository.save(estante));
    }

    @Transactional
    public void eliminar(Integer id) {
        estanteRepository.delete(getEstanteById(id));
    }

    @Transactional(readOnly = true)
    public List<EstanteDTO> obtenerTodosOrdenadosPorRiesgo() {
        return estanteRepository.findAll().stream()
                .map(this::toDTOWithCalculatedRisk)
                .sorted(Comparator.comparingInt(EstanteDTO::riesgoActual))
                .toList();
    }

    public Estante getEstanteById(Integer id) {
        return estanteRepository.findById(id)
                .orElseThrow(() -> new EstanteNotFoundException("Estante no encontrado con ID: " + id));
    }

    public int calcularRiesgoActual(Integer estanteId) {
        List<Lote> lotes = loteService.getLotesByEstanteId(estanteId);
        LocalDate hoy = LocalDate.now();
        return lotes.stream()
                .filter(lote -> lote.getCantidadKg() > 0
                        && !lote.getFechaVencimiento().isBefore(hoy))  // solo lotes activos
                .mapToInt(lote -> (int) (lote.getCantidadKg() * lote.getReactivo().getNivelPeligro()))
                .sum();
    }

    private EstanteDTO toDTOWithCalculatedRisk(Estante estante) {
        int riesgo = calcularRiesgoActual(estante.getId());
        return new EstanteDTO(
                estante.getId(),
                estante.getCodigoAlmacen(),
                estante.getCapacidadMaxKg(),
                estante.getRiesgoLimite(),
                riesgo); // ← campo calculado que el mapper va a ignorar
    }
}