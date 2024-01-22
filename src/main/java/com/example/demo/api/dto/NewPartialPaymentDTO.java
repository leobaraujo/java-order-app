package com.example.demo.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewPartialPaymentDTO(
        @NotNull @Size(min = 36, max = 36) String customerId,
        @NotNull Double amount,
        String note) {
}
