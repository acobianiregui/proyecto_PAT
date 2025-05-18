package com.banco.icai.pat.spring.proyecto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BizumRequest(

        @NotNull
        int importe,
        @NotNull
        String iban_origen,
        @NotNull@Pattern(regexp = "^[6789]\\d{8}$")
        String telefono_destino

){}

