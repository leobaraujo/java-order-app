package com.example.demo.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRoleDTO(
        @NotBlank String role) {
}
