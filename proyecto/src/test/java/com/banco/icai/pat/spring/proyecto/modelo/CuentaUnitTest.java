package com.banco.icai.pat.spring.proyecto.modelo;

import com.banco.icai.pat.spring.proyecto.entity.Cliente;
import com.banco.icai.pat.spring.proyecto.entity.Cuenta;
import com.banco.icai.pat.spring.proyecto.model.Sucursal;
import com.banco.icai.pat.spring.proyecto.util.BancoTools;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.aspectj.weaver.loadtime.Agent;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CuentaUnitTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void cuentaCorrecta(){
        Cuenta cuenta= new Cuenta();
        Cliente cliente= new Cliente();
        cuenta.setIban(BancoTools.generarIban(Sucursal.MADRID,"0001234567"));
        cuenta.setSaldo(0);
        cuenta.setSucursal(Sucursal.MADRID);
        cuenta.setCliente(cliente);

        Set<ConstraintViolation<Cuenta>> violations= validator.validate(cuenta);

        assertEquals(0,violations.size());
    }
}
