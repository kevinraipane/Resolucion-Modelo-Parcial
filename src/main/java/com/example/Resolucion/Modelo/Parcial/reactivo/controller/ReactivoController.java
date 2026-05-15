package com.example.Resolucion.Modelo.Parcial.reactivo.controller;


import com.example.Resolucion.Modelo.Parcial.reactivo.dto.ReactivoCreateDTO;
import com.example.Resolucion.Modelo.Parcial.reactivo.dto.ReactivoDTO;
import com.example.Resolucion.Modelo.Parcial.reactivo.dto.ReactivoUpdateDTO;
import com.example.Resolucion.Modelo.Parcial.reactivo.service.ReactivoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reactivos")
@RequiredArgsConstructor
public class ReactivoController {

    private final ReactivoService reactivoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReactivoDTO crear(@RequestBody @Valid ReactivoCreateDTO dto) {
        return reactivoService.crear(dto);
    }

    @PutMapping("/{id}")
    public ReactivoDTO actualizar(@PathVariable Integer id, @RequestBody @Valid ReactivoUpdateDTO dto) {
        return reactivoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        reactivoService.eliminar(id);
    }

    @GetMapping
    public List<ReactivoDTO> filtrar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer nivelPeligro,
            @RequestParam(required = false) Boolean esPrecursor) {
        return reactivoService.filtrarReactivos(nombre, nivelPeligro, esPrecursor);
    }
}
