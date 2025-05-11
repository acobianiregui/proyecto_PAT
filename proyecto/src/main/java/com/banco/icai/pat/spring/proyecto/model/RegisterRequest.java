package com.banco.icai.pat.spring.proyecto.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record RegisterRequest (
    @NotBlank @Pattern(regexp = "^[0-9]{8}[A-HJ-NP-TV-Z]$")
    String dni,
    @NotBlank
    String nombre,
    @NotBlank
    String apellido,
    @NotBlank @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")
    String email,
    @NotBlank @Pattern(regexp = "^[6789]\\d{8}$")
    String telefono,
    @NotBlank @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,}$")
    String password


)
{}
