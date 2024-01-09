package com.example.demo.api.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.api.dto.GlobalExceptionDTO;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Invalid path variable
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GlobalExceptionDTO> illegalArgumentException(IllegalArgumentException e) {
        return badRequestResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // Missing request body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalExceptionDTO> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return badRequestResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // Invalid request body
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GlobalExceptionDTO> constraintViolationException(ConstraintViolationException e) {
        return badRequestResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<GlobalExceptionDTO> badRequestResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new GlobalExceptionDTO(
                Instant.now(),
                status.value(),
                status.name(),
                message));
    }

}
