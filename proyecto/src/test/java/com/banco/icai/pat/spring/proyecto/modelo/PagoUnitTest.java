package com.banco.icai.pat.spring.proyecto.modelo;

import com.banco.icai.pat.spring.proyecto.entity.Cuenta;
import com.banco.icai.pat.spring.proyecto.entity.Pago;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PagoUnitTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void PagoCorrecto() {
        Pago pago = new Pago();
        pago.setCuenta_origen(new Cuenta());
        pago.setCuenta_destino(new Cuenta());
        pago.setTipo("transferencia");
        pago.setImporte(100);
        Set<ConstraintViolation<Pago>> violations = validator.validate(pago);
        assertTrue(violations.isEmpty());
    }
}
