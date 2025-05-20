package com.banco.icai.pat.spring.proyecto.model;

import com.banco.icai.pat.spring.proyecto.validacion.ValidIban;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BizumRequest(

        @Min(1)
        double importe,
        @ValidIban
        String iban_origen,
        @Pattern(regexp = "^[6789]\\d{8}$")
        String telefono_destino
){}

