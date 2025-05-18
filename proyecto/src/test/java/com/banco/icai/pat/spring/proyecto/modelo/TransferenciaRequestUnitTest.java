package com.banco.icai.pat.spring.proyecto.modelo;

import com.banco.icai.pat.spring.proyecto.model.RegisterRequest;
import com.banco.icai.pat.spring.proyecto.model.Sucursal;
import com.banco.icai.pat.spring.proyecto.model.TransferenciaRequest;
import com.banco.icai.pat.spring.proyecto.util.BancoTools;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferenciaRequestUnitTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidRequest() {
        // Given ...
        TransferenciaRequest transferencia = new TransferenciaRequest(
                900,
                BancoTools.generarIban(Sucursal.MADRID, "1234567899"), //ES88 4835 0418 7712 3456 7899
                BancoTools.generarIban(Sucursal.MADRID, "8765432111")); //ES24 4835 0418 7687 6543 2111
        Set<ConstraintViolation<TransferenciaRequest>> violations= validator.validate(transferencia);
        assertTrue(violations.isEmpty());
    }
    @Test
    public void ibanOrigenIncorrecto(){
        TransferenciaRequest transferencia = new TransferenciaRequest(
                900,
                "ES78 4835 0418 7712 3456 7899", //Los digitos de control estan mal
                BancoTools.generarIban(Sucursal.MADRID, "8765432111"));
        Set<ConstraintViolation<TransferenciaRequest>> violations= validator.validate(transferencia);
        assertEquals(1, violations.size());
        //Comprobar que el error es origen
        assertEquals("iban_cuenta_origen", violations.iterator().next().getPropertyPath().toString());
    }
    @Test
    public void ibanDestinoIncorrecto(){
        TransferenciaRequest transferencia = new TransferenciaRequest(
                900,
                "ES88 4835 0418 7712 3456 7899",
                "ES14 4835 0418 7687 6543 2111"); //Los digitos de control estan mal
        Set<ConstraintViolation<TransferenciaRequest>> violations= validator.validate(transferencia);
        assertEquals(1, violations.size());
        //Comprobar que el error es origen
        assertEquals("iban_cuenta_destino", violations.iterator().next().getPropertyPath().toString());
    }
}
