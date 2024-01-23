package com.example.demo.api.dto;

import jakarta.validation.constraints.NotNull;

public record UpdatePartialPaymentDTO(
        @NotNull Double amount,
        String note) {

}
