package com.banco.icai.pat.spring.proyecto.validacion;

import com.banco.icai.pat.spring.proyecto.util.BancoTools;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IbanValidator implements ConstraintValidator<ValidIban, String> {

    @Override
    public void initialize(ValidIban constraintAnnotation) {
        // Si necesitas inicializar algo, lo haces aquí.
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;  // O puedes manejar como un caso de "NotNull" si deseas.
        }
        return BancoTools.validarIban(value);  // Llamas a la función de validación.
    }
}