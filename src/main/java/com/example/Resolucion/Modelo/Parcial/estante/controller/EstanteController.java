package com.example.Resolucion.Modelo.Parcial.estante.controller;

import com.example.Resolucion.Modelo.Parcial.estante.dto.EstanteCreateDTO;
import com.example.Resolucion.Modelo.Parcial.estante.dto.EstanteDTO;
import com.example.Resolucion.Modelo.Parcial.estante.dto.EstanteUpdateDTO;
import com.example.Resolucion.Modelo.Parcial.estante.service.EstanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estantes")
@RequiredArgsConstructor
public class EstanteController {

    private final EstanteService estanteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstanteDTO crear(@RequestBody @Valid EstanteCreateDTO dto) {
        return estanteService.crear(dto);
    }

    @PutMapping("/{id}")
    public EstanteDTO actualizar(@PathVariable Integer id, @RequestBody @Valid EstanteUpdateDTO dto) {
        return estanteService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        estanteService.eliminar(id);
    }

    @GetMapping
    public List<EstanteDTO> listarTodos() {
        return estanteService.obtenerTodosOrdenadosPorRiesgo();
    }
}
