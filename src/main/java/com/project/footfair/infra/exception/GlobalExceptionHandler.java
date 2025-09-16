package com.project.footfair.infra.exception;

import com.project.footfair.dto.ErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFound(EntityNotFoundException ex){
        log.warn("Recurso não encontrado: {}", ex.getMessage());
        var response = new ErrorResponseDTO(
                ex.getMessage() != null ? ex.getMessage() : "Recurso não encontrado",
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleIllegalArgument(MethodArgumentNotValidException ex) {
        log.warn("Erro de validação nos campos: {}", ex.getBindingResult().getFieldErrors());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        var response = new ErrorResponseDTO(
                "Dados inválidos",
                Instant.now(),
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleUnexpectedError(Exception ex){
        log.error("Erro inesperado", ex);
        var response = new ErrorResponseDTO(
                "Ocorreu um erro inesperado. Tente Novamente mais tarde.",
                Instant.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseDTO> handleBaseException(BaseException ex) {
        var response = new ErrorResponseDTO(ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
