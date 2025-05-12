package com.banco.icai.pat.spring.proyecto.validacion;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IbanValidator.class)  // El validador de la anotación
public @interface ValidIban {

    String message() default "El IBAN no es valido";  // Mensaje de error

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

