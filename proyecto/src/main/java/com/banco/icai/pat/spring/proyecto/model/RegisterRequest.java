package com.banco.icai.pat.spring.proyecto.model;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest (
    @NotBlank
    String dni,
    @NotBlank
    String nombre,
    @NotBlank
    String apellido,
    @NotBlank
    String email,
    @NotBlank
    String telefono

)
{}
