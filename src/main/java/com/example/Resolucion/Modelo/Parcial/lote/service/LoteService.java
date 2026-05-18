package com.example.Resolucion.Modelo.Parcial.lote.service;

import com.example.Resolucion.Modelo.Parcial.estante.exception.RiesgoMaximoExcedidoException;
import com.example.Resolucion.Modelo.Parcial.estante.exception.SectorInvalidoParaPrecursorException;
import com.example.Resolucion.Modelo.Parcial.estante.model.Estante;
import com.example.Resolucion.Modelo.Parcial.estante.service.EstanteService;
import com.example.Resolucion.Modelo.Parcial.lote.dto.ConsumoLoteDTO;
import com.example.Resolucion.Modelo.Parcial.lote.dto.LoteCreateDTO;
import com.example.Resolucion.Modelo.Parcial.lote.dto.LoteDTO;
import com.example.Resolucion.Modelo.Parcial.lote.dto.LoteUpdateDTO;
import com.example.Resolucion.Modelo.Parcial.lote.exception.CantidadInvalidaException;
import com.example.Resolucion.Modelo.Parcial.lote.exception.FechaRecepcionInvalidaException;
import com.example.Resolucion.Modelo.Parcial.lote.exception.FechaVencimientoInvalidaException;
import com.example.Resolucion.Modelo.Parcial.lote.exception.LoteNotFoundException;
import com.example.Resolucion.Modelo.Parcial.lote.mapper.LoteMapper;
import com.example.Resolucion.Modelo.Parcial.lote.model.Lote;
import com.example.Resolucion.Modelo.Parcial.lote.repository.LoteRepository;
import com.example.Resolucion.Modelo.Parcial.reactivo.exception.ReactivoInactivoException;
import com.example.Resolucion.Modelo.Parcial.reactivo.model.Reactivo;
import com.example.Resolucion.Modelo.Parcial.reactivo.service.ReactivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoteService {

    private final LoteRepository loteRepository;
    private final ReactivoService reactivoService;
    private final EstanteService estanteService;
    private final LoteMapper loteMapper;

    @Transactional
    public LoteDTO crear(LoteCreateDTO dto) {
        Reactivo reactivo = reactivoService.getReactivoById(dto.idReactivo());
        Estante estante = estanteService.getEstanteById(dto.idEstante());

        if (Boolean.FALSE.equals(reactivo.getActivo())) {
            throw new ReactivoInactivoException(
                    "No se puede crear un lote para el reactivo con ID " + reactivo.getId() +
                            " porque está inactivo.");
        }

        if (dto.fechaRecepcion().isBefore(LocalDate.now())) {
            throw new FechaRecepcionInvalidaException("La fecha de recepción no puede ser anterior a la fecha actual.");
        }
        if (dto.fechaVencimiento().isBefore(LocalDate.now().plusMonths(6))) {
            throw new FechaVencimientoInvalidaException("La fecha de vencimiento debe ser al menos 6 meses desde hoy.");
        }

        if (Boolean.TRUE.equals(reactivo.getEsPrecursorQuimico()) && !"SEC-01".equals(estante.getCodigoAlmacen())) {
            throw new SectorInvalidoParaPrecursorException("Los precursores químicos solo pueden ir en el almacén SEC-01");
        }

        int riesgoAportado = (int) (dto.cantidadKg() * reactivo.getNivelPeligro());
        int riesgoActual = estanteService.calcularRiesgoActual(estante.getId());

        if ((riesgoActual + riesgoAportado) > estante.getRiesgoLimite()) {
            throw new RiesgoMaximoExcedidoException("Operación rechazada: Supera el riesgo límite del estante " + estante.getId());
        }

        Lote lote = Lote.builder()
                .nroLote(dto.nroLote())
                .fechaRecepcion(dto.fechaRecepcion())
                .fechaVencimiento(dto.fechaVencimiento())
                .cantidadKg(dto.cantidadKg())
                .reactivo(reactivo)
                .estante(estante)
                .build();

        return loteMapper.toDTO(loteRepository.save(lote));
    }

    @Transactional
    public LoteDTO actualizar(Integer id, LoteUpdateDTO dto) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new LoteNotFoundException("Lote no encontrado con ID: " + id));

        Estante estante = estanteService.getEstanteById(dto.idEstante());

        // Validar vencimiento
        if (dto.fechaVencimiento().isBefore(LocalDate.now().plusMonths(6))) {
            throw new FechaVencimientoInvalidaException(
                    "La fecha de vencimiento debe ser al menos 6 meses desde hoy.");
        }

        // Calcular el riesgo del estante destino, excluyendo la contribución actual de este lote
        int riesgoSinEsteLote = estanteService.calcularRiesgoActual(estante.getId())
                - (int) (lote.getCantidadKg() * lote.getReactivo().getNivelPeligro()); // casteo a int
        int riesgoNuevo = (int) (dto.cantidadKg() * lote.getReactivo().getNivelPeligro());

        if ((riesgoSinEsteLote + riesgoNuevo) > estante.getRiesgoLimite()) {
            throw new RiesgoMaximoExcedidoException(
                    "Operación rechazada: la actualización supera el riesgo límite del estante " + estante.getId());
        }

        lote.setNroLote(dto.nroLote());
        lote.setFechaVencimiento(dto.fechaVencimiento());
        lote.setCantidadKg(dto.cantidadKg());
        lote.setEstante(estante);

        return loteMapper.toDTO(loteRepository.save(lote));
    }

    @Transactional
    public LoteDTO consumir(Integer id, ConsumoLoteDTO dto) {
        Lote lote = loteRepository.findById(id).orElseThrow(() -> new LoteNotFoundException("Lote no encontrado con ID: " + id));

        if (dto.cantidadAExtraer() > lote.getCantidadKg()) {
            throw new CantidadInvalidaException("La cantidad solicitada supera el inventario disponible del lote.");
        }

        lote.setCantidadKg(lote.getCantidadKg() - dto.cantidadAExtraer());

        return loteMapper.toDTO(loteRepository.save(lote));
    }

    @Transactional(readOnly = true)
    public List<LoteDTO> listarTodos() {
        return loteRepository.findAll().stream().map(loteMapper::toDTO).toList();
    }

    public boolean tieneLotesActivos(Integer reactivoId) {
        return loteRepository.existsByReactivoIdAndFechaVencimientoGreaterThanEqualAndCantidadKgGreaterThan(
                reactivoId, LocalDate.now(), 0.0);
    }

    public List<Lote> getLotesByEstanteId(Integer estanteId) {
        return loteRepository.findByEstanteId(estanteId);
    }
}
