package com.example.demo.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewOrderDTO(
        @NotBlank @Size(min = 36, max = 36) String customerId,
        @NotBlank @Size(min = 36, max = 36) String productId,
        @NotNull @Min(1) Integer quantity) {
}
