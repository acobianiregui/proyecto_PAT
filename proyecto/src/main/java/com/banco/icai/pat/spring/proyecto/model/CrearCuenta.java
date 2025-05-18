package com.banco.icai.pat.spring.proyecto.model;

import com.banco.icai.pat.spring.proyecto.entity.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CrearCuenta(
    @Pattern(regexp = "(?i)MADRID|BARCELONA|BILBAO", message = "Sucursal inválida") //Permite mayusculas y minusculas
    String sucursal,
    @Pattern(regexp = "\\d{10}")
    String numeroCuenta
)
{}