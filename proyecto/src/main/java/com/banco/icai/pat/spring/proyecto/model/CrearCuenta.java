package com.banco.icai.pat.spring.proyecto.model;

import com.banco.icai.pat.spring.proyecto.entity.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearCuenta(
    @NotBlank
    String sucursal,
    @NotBlank
    String numeroCuenta,
    @NotNull
    Cliente cliente
)
{}