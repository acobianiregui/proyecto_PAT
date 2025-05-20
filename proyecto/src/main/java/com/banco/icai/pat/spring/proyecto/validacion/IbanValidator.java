package com.banco.icai.pat.spring.proyecto.validacion;

import com.banco.icai.pat.spring.proyecto.util.BancoTools;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IbanValidator implements ConstraintValidator<ValidIban, String> {

    @Override
    public void initialize(ValidIban constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return BancoTools.validarIban(value);
    }
}