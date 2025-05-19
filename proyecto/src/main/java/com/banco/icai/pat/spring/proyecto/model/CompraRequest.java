package com.banco.icai.pat.spring.proyecto.model;

import com.banco.icai.pat.spring.proyecto.validacion.ValidIban;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record CompraRequest(
    @Min(1)
    int importe,
    @ValidIban
    String iban,
    @NotEmpty
    String articulo){}

