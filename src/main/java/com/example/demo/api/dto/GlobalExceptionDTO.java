package com.example.demo.api.dto;

import java.time.Instant;

public record GlobalExceptionDTO(
        Instant timestamp,
        Integer status,
        Object[] errors) {
}
