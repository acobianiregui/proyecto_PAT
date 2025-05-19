package com.banco.icai.pat.spring.proyecto.modelo;

import com.banco.icai.pat.spring.proyecto.entity.Cliente;
import com.banco.icai.pat.spring.proyecto.entity.Cuenta;
import com.banco.icai.pat.spring.proyecto.model.CrearCuenta;
import com.banco.icai.pat.spring.proyecto.model.Sucursal;
import com.banco.icai.pat.spring.proyecto.util.BancoTools;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.aspectj.weaver.loadtime.Agent;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @Test
    public void ibanIncorrecto(){
        Cuenta cuenta= new Cuenta();
        Cliente cliente= new Cliente();
        cuenta.setIban("ES63 4835 0418 0001 234");
        cuenta.setSaldo(0);
        cuenta.setSucursal(Sucursal.MADRID);
        cuenta.setCliente(cliente);

        Set<ConstraintViolation<Cuenta>> violations= validator.validate(cuenta);
        assertEquals(1,violations.size());
        ConstraintViolation<Cuenta> violation= violations.iterator().next();
        assertEquals("iban",violation.getPropertyPath().toString());

    }
    @Test
    public void crearCuentaValido(){
        CrearCuenta crearCuenta= new CrearCuenta("Barcelona","0001234567");
        Set<ConstraintViolation<CrearCuenta>> violations= validator.validate(crearCuenta);
        assertEquals(0,violations.size());

    }
    @Test
    public void crearCuentaInvalido() {
        String sucursalInvalida = "Coruna";
        String numeroCuentaInvalido = "00123467";

        CrearCuenta crearCuenta = new CrearCuenta(sucursalInvalida, numeroCuentaInvalido);
        Set<ConstraintViolation<CrearCuenta>> violations = validator.validate(crearCuenta);

        assertEquals(2, violations.size());

        boolean sucursalValida = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("sucursal")
                        && sucursalInvalida.equals(v.getInvalidValue()));

        boolean numeroCuentaValida = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("numeroCuenta")
                        && numeroCuentaInvalido.equals(v.getInvalidValue()));
        violations.forEach(v -> {
            System.out.println("Campo: " + v.getPropertyPath());
            System.out.println("Valor inválido: " + v.getInvalidValue());
            System.out.println("Mensaje: " + v.getMessage());
        });


        assertTrue(sucursalValida);
        assertTrue(numeroCuentaValida);
    }

}
