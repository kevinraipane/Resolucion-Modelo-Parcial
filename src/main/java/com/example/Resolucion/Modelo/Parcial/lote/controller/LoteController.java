package com.example.Resolucion.Modelo.Parcial.lote.controller;

import com.example.Resolucion.Modelo.Parcial.lote.dto.ConsumoLoteDTO;
import com.example.Resolucion.Modelo.Parcial.lote.dto.LoteCreateDTO;
import com.example.Resolucion.Modelo.Parcial.lote.dto.LoteDTO;
import com.example.Resolucion.Modelo.Parcial.lote.dto.LoteUpdateDTO;
import com.example.Resolucion.Modelo.Parcial.lote.service.LoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lotes")
@RequiredArgsConstructor
public class LoteController {

    private final LoteService loteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoteDTO crear(@RequestBody @Valid LoteCreateDTO dto) {
        return loteService.crear(dto);
    }

    @PutMapping("/{id}")
    public LoteDTO actualizar(@PathVariable Integer id, @RequestBody @Valid LoteUpdateDTO dto) {
        return loteService.actualizar(id, dto);
    }

    @PatchMapping("/{id}/consumir")
    public LoteDTO consumir(@PathVariable Integer id, @RequestBody @Valid ConsumoLoteDTO dto) {
        return loteService.consumir(id, dto);
    }

    @GetMapping
    public List<LoteDTO> listarTodos() {
        return loteService.listarTodos();
    }
}
