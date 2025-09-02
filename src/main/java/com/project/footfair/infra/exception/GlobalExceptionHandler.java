package com.project.footfair.infra.exception;

import com.project.footfair.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<Void> handleEntityNotFound(){
            return ResponseEntity.notFound().build();
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<?> handleIllegalArgument(MethodArgumentNotValidException ex){
            var errors = ex.getFieldErrors();

            return ResponseEntity.badRequest().body
                    (errors.stream()
                            .map(error -> new ErrorResponse(error.getField(), error.getDefaultMessage()))
                            .toList()
                    );
        }
}
