package com.banco.icai.pat.spring.proyecto.model;

import com.banco.icai.pat.spring.proyecto.validacion.ValidIban;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TransferenciaRequest(
        @NotNull
        int importe,
        @ValidIban
        String iban_cuenta_origen,
        @ValidIban
        String iban_cuenta_destino
){}
