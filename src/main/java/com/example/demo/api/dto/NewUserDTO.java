package com.example.demo.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewUserDTO(
        @NotBlank @Size(min = 4, max = 32) String username,
        @NotBlank @Size(min = 4, max = 32) String password) {
}
