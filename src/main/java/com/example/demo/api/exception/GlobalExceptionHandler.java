package com.example.demo.api.exception;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.demo.api.dto.GlobalExceptionDTO;
import com.example.demo.api.dto.InputFieldError;
import com.example.demo.domain.exception.InvalidEntityIdException;
import com.example.demo.domain.exception.InvalidProductCategoryException;
import com.example.demo.domain.exception.InvalidRoleException;
import com.example.demo.domain.exception.InvalidUserException;
import com.example.demo.domain.exception.InvalidUsernameException;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Invalid path variable
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<GlobalExceptionDTO> noResourceFoundHandler(NoResourceFoundException e) {
        return response(HttpStatus.NOT_FOUND, new String[] { e.getMessage() });
    }

    // Invalid path variable type
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GlobalExceptionDTO> illegalArgumentHandler(IllegalArgumentException e) {
        return response(HttpStatus.BAD_REQUEST, new String[] { e.getMessage() });
    }

    // Missing request body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalExceptionDTO> httpMessageNotReadableHandler(HttpMessageNotReadableException e) {
        return response(HttpStatus.BAD_REQUEST, new String[] { e.getMessage() });
    }

    // Missing field on request body
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalExceptionDTO> methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        List<InputFieldError> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new InputFieldError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        return response(HttpStatus.BAD_REQUEST, errors.toArray());
    }

    // Invalid field type in request body
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GlobalExceptionDTO> constraintViolationHandler(ConstraintViolationException e) {
        return response(HttpStatus.BAD_REQUEST, new String[] { e.getMessage() });
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<GlobalExceptionDTO> noSuchElementHandler(NoSuchElementException e) {
        return response(HttpStatus.BAD_REQUEST, new String[] { e.getMessage() });
    }

    @ExceptionHandler(InvalidProductCategoryException.class)
    public ResponseEntity<GlobalExceptionDTO> invalidProductCategoryHandler(InvalidProductCategoryException e) {
        return response(HttpStatus.BAD_REQUEST, new String[] { e.getMessage() });
    }

    @ExceptionHandler(InvalidEntityIdException.class)
    public ResponseEntity<GlobalExceptionDTO> invalidEntityIdHandler(InvalidEntityIdException e) {
        return response(HttpStatus.BAD_REQUEST, new String[] { e.getMessage() });
    }

    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<GlobalExceptionDTO> invalidUsernameHandler(InvalidUsernameException e) {
        return response(HttpStatus.BAD_REQUEST, new String[] { e.getMessage() });
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<GlobalExceptionDTO> invalidRoleHandler(InvalidRoleException e) {
        return response(HttpStatus.BAD_REQUEST, new String[] { e.getMessage() });
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<GlobalExceptionDTO> invalidUserHandler(InvalidUserException e) {
        return response(HttpStatus.NOT_FOUND, new String[] { e.getMessage() });
    }

    private ResponseEntity<GlobalExceptionDTO> response(HttpStatus status, Object[] errors) {
        return ResponseEntity.status(status).body(new GlobalExceptionDTO(
                Instant.now(),
                status.value(),
                errors));
    }

}
