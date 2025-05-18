package com.banco.icai.pat.spring.proyecto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TransferenciaRequest(
        @NotNull
        int importe,
        @NotNull
        String iban_cuenta_origen,
        @NotNull
        String iban_cuenta_destino
){}
