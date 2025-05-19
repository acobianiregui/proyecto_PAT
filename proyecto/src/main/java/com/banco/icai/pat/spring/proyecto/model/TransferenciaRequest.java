package com.banco.icai.pat.spring.proyecto.model;

import com.banco.icai.pat.spring.proyecto.validacion.ValidIban;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TransferenciaRequest(
        @Min(1)
        int importe,
        @ValidIban
        String iban_cuenta_origen,
        @ValidIban
        String iban_cuenta_destino
){}
