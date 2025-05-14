package com.banco.icai.pat.spring.proyecto.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequest (
        @NotBlank
        String email,
        @NotBlank
        String password
)

{}
