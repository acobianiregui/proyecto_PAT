package com.banco.icai.pat.spring.proyecto.modelo;



import com.banco.icai.pat.spring.proyecto.model.RegisterRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterRequestUnitTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testRegistroValido(){
        RegisterRequest registerRequest = new RegisterRequest("12345678Z","Anton","Cobian",
                "pepelarana@gmail.com", "612345678","Aventura9");
        Set<ConstraintViolation<RegisterRequest>> violations= validator.validate(registerRequest);
        assertTrue(violations.isEmpty());

    }
    @Test
    public void dniInvalido(){
        RegisterRequest registerRequest = new RegisterRequest("1456783","Anton","Cobian",
                "pepelarana@gmail.com", "612345678","Aventura9");
        Set<ConstraintViolation<RegisterRequest>> violations= validator.validate(registerRequest);
        assertEquals(1,violations.size());
        ConstraintViolation<RegisterRequest> violation= violations.iterator().next();
        assertEquals("dni",violation.getPropertyPath().toString()); //Comprobar que dni ha sido el error
    }

    @Test
    public void emailInvalido(){
        RegisterRequest registerRequest = new RegisterRequest("12345678Z","Anton","Cobian",
                "pepelarana.c", "612345678","Aventura9");
        Set<ConstraintViolation<RegisterRequest>> violations= validator.validate(registerRequest);
        assertEquals(1,violations.size());
        ConstraintViolation<RegisterRequest> violation= violations.iterator().next();
        assertEquals("email",violation.getPropertyPath().toString());
    }
    @Test
    public void telefonoInvalido(){
        RegisterRequest registerRequest = new RegisterRequest("12345678Z","Anton","Cobian",
                "pepelarana@gmail.com", "21245678","Aventura9");
        Set<ConstraintViolation<RegisterRequest>> violations= validator.validate(registerRequest);
        assertEquals(1,violations.size());
        ConstraintViolation<RegisterRequest> violation= violations.iterator().next();
        assertEquals("telefono",violation.getPropertyPath().toString());
    }
    @Test
    public void passwordInvalido(){
        RegisterRequest registerRequest = new RegisterRequest("12345678Z","Anton","Cobian",
                "pepelarana@gmail.com", "612345678","aventura");
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);
        assertEquals(1,violations.size());
        ConstraintViolation<RegisterRequest> violation= violations.iterator().next();
        assertEquals("password",violation.getPropertyPath().toString());
    }
    @Test
    public void variosInvalidos() {
        RegisterRequest registerRequest = new RegisterRequest("1456", "Anton", "Cobian",
                "correo_mal", "123", "melon");
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);
        assertEquals(4,violations.size());
        Set<String> camposInvalidos = violations.stream()
                .map(v -> v.getPropertyPath().toString())
                .collect(Collectors.toSet());
        assertTrue(camposInvalidos.contains("dni"));
        assertTrue(camposInvalidos.contains("email"));
        assertTrue(camposInvalidos.contains("telefono"));
        assertTrue(camposInvalidos.contains("password"));

    }

}
