package com.banco.icai.pat.spring.proyecto.modelo;

import com.banco.icai.pat.spring.proyecto.model.BizumRequest;
import com.banco.icai.pat.spring.proyecto.model.RegisterRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BizumRequestUnitTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void bizumRequestOK(){
        BizumRequest bizumRequest = new BizumRequest(
                50, "ES88 4835 0418 7712 3456 7899", "640453289");
        Set<ConstraintViolation<BizumRequest>> violations= validator.validate(bizumRequest);
        assertTrue(violations.isEmpty());
    }
    @Test
    public void importeErroneo(){
        BizumRequest bizumRequest = new BizumRequest(
                0, "ES88 4835 0418 7712 3456 7899", "640453289");
        Set<ConstraintViolation<BizumRequest>> violations= validator.validate(bizumRequest);
        assertEquals(1, violations.size());
        assertEquals("importe", violations.iterator().next().getPropertyPath().toString());
        assertEquals(0, violations.iterator().next().getInvalidValue());
    }
    @Test
    public void telefonoErroneo(){
        BizumRequest bizumRequest = new BizumRequest(
                50, "ES88 4835 0418 7712 3456 7899", "6404532890");
        Set<ConstraintViolation<BizumRequest>> violations= validator.validate(bizumRequest);
        assertEquals(1, violations.size());
        assertEquals("telefono_destino", violations.iterator().next().getPropertyPath().toString());
        assertEquals("6404532890", violations.iterator().next().getInvalidValue());
    }
    @Test
    public void ibanErroneo(){
        BizumRequest bizumRequest = new BizumRequest(
                50, "ES21 4835 0418 7712 3456", "640453289");
        Set<ConstraintViolation<BizumRequest>> violations= validator.validate(bizumRequest);
        assertEquals(1, violations.size());
        assertEquals("iban_origen", violations.iterator().next().getPropertyPath().toString());
        assertEquals("ES21 4835 0418 7712 3456", violations.iterator().next().getInvalidValue());
    }
}
