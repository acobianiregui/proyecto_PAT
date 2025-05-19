package com.banco.icai.pat.spring.proyecto.model;

import com.banco.icai.pat.spring.proyecto.validacion.ValidIban;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record SaldoModRequest(
        @ValidIban
        String iban,
        @Min(1)
        double importe,
        @Pattern(regexp = "^(ingreso|retirada)$", message = "El valor debe ser 'incrementar' o 'disminuir'")
        String tipoOperacion
){}
