package com.example.demo.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewProductDTO(
        @NotBlank String name,
        @NotNull Double buyPrice,
        @NotNull Double sellPrice,
        @NotBlank String imgUrl,
        @NotNull Integer categoryIndex) {

}
