package com.banco.icai.pat.spring.proyecto.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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
    String telefono,
    @NotBlank @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,}$")
    String password

)
{}
