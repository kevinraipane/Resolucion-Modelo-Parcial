package com.example.Resolucion.Modelo.Parcial.common.exception;

import com.example.Resolucion.Modelo.Parcial.common.dto.ErrorResponseDTO;
import com.example.Resolucion.Modelo.Parcial.estante.exception.EstanteNotFoundException;
import com.example.Resolucion.Modelo.Parcial.estante.exception.RiesgoMaximoExcedidoException;
import com.example.Resolucion.Modelo.Parcial.estante.exception.SectorInvalidoParaPrecursorException;
import com.example.Resolucion.Modelo.Parcial.lote.exception.CantidadInvalidaException;
import com.example.Resolucion.Modelo.Parcial.lote.exception.FechaRecepcionInvalidaException;
import com.example.Resolucion.Modelo.Parcial.lote.exception.FechaVencimientoInvalidaException;
import com.example.Resolucion.Modelo.Parcial.lote.exception.LoteNotFoundException;
import com.example.Resolucion.Modelo.Parcial.reactivo.exception.ReactivoConLotesActivosException;
import com.example.Resolucion.Modelo.Parcial.reactivo.exception.ReactivoInactivoException;
import com.example.Resolucion.Modelo.Parcial.reactivo.exception.ReactivoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 NOT FOUND
    @ExceptionHandler(ReactivoNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleReactivoNotFound(ReactivoNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(LoteNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleLoteNotFound(LoteNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(EstanteNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEstanteNotFound(EstanteNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 409 CONFLICT

    @ExceptionHandler(ReactivoConLotesActivosException.class)
    public ResponseEntity<ErrorResponseDTO> handleReactivoConLotesActivos(ReactivoConLotesActivosException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(RiesgoMaximoExcedidoException.class)
    public ResponseEntity<ErrorResponseDTO> handleRiesgoMaximoExcedido(RiesgoMaximoExcedidoException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // 422 UNPROCESSABLE ENTITY ─
    // Reglas de negocio que hacen que los datos sean inválidos.

    @ExceptionHandler(FechaVencimientoInvalidaException.class)
    public ResponseEntity<ErrorResponseDTO> handleFechaVencimientoInvalida(FechaVencimientoInvalidaException ex) {
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(FechaRecepcionInvalidaException.class)
    public ResponseEntity<ErrorResponseDTO> handleFechaRecepcionInvalida(FechaRecepcionInvalidaException ex) {
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(CantidadInvalidaException.class)
    public ResponseEntity<ErrorResponseDTO> handleCantidadInvalida(CantidadInvalidaException ex) {
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(SectorInvalidoParaPrecursorException.class)
    public ResponseEntity<ErrorResponseDTO> handleSectorInvalido(SectorInvalidoParaPrecursorException ex) {
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(ReactivoInactivoException.class)
    public ResponseEntity<ErrorResponseDTO> handleReactivoInactivo(ReactivoInactivoException ex) {
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    // 400 BAD REQUEST — validaciones de @Valid

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildResponse(HttpStatus.BAD_REQUEST, errors);
    }

    // 500 INTERNAL SERVER ERROR — catch-all de seguridad

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleUnexpected(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor. Posta mal ahi.");
    }

    // Helper

    private ResponseEntity<ErrorResponseDTO> buildResponse(HttpStatus status, String message) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );
        return new ResponseEntity<>(error, status);
    }
}
