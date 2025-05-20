package com.banco.icai.pat.spring.proyecto.modelo;

import com.banco.icai.pat.spring.proyecto.model.CompraRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompraRequestUnitTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void compraRequestOK(){
        CompraRequest compraRequest = new CompraRequest(
                50.0, "ES88 4835 0418 7712 3456 7899", "Gafas");
        Set<ConstraintViolation<CompraRequest>> violations= validator.validate(compraRequest);
        assertTrue(violations.isEmpty());
    }
    @Test
    public void importeErroneo(){
        CompraRequest compraRequest = new CompraRequest(
                0.0, "ES88 4835 0418 7712 3456 7899", "Gafas");
        Set<ConstraintViolation<CompraRequest>> violations= validator.validate(compraRequest);
        assertTrue(violations.size() == 1);
        assertTrue(violations.iterator().next().getPropertyPath().toString().equals("importe"));
        assertTrue(violations.iterator().next().getInvalidValue().equals(0.0));
    }
    @Test
    public void ibanErroneo(){
        CompraRequest compraRequest = new CompraRequest(
                50.0, "ES11 4835 0418 7712 3456", "Gafas");
        Set<ConstraintViolation<CompraRequest>> violations= validator.validate(compraRequest);
        assertEquals(1, violations.size());
        assertEquals("iban", violations.iterator().next().getPropertyPath().toString());
        assertEquals("ES11 4835 0418 7712 3456", violations.iterator().next().getInvalidValue());
    }
    @Test
    public void articuloErroneo(){
        CompraRequest compraRequest = new CompraRequest(
                50, "ES88 4835 0418 7712 3456 7899", "");
        Set<ConstraintViolation<CompraRequest>> violations= validator.validate(compraRequest);
        assertTrue(violations.size() == 1);
        assertTrue(violations.iterator().next().getPropertyPath().toString().equals("articulo"));
        assertTrue(violations.iterator().next().getInvalidValue().equals(""));
    }
}
