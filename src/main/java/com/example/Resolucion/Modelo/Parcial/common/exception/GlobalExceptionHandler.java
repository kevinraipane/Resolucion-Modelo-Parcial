package com.example.Resolucion.Modelo.Parcial.common.exception;

import com.example.Resolucion.Modelo.Parcial.common.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessRulesExceptions(RuntimeException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (ex.getClass().getSimpleName().contains("NotFound")) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getClass().getSimpleName().contains("Excedido") || ex.getClass().getSimpleName().contains("Activos")) {
            status = HttpStatus.CONFLICT;
        }

        // Aquí ex.getMessage() traerá el mensaje que definimos en el Service al lanzar la excepción
        ErrorResponseDTO error = new ErrorResponseDTO(LocalDateTime.now(), status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponseDTO error = new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Error de Validación", errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
