package com.banco.icai.pat.spring.proyecto.validacion;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IbanValidator.class)
public @interface ValidIban {

    String message() default "El IBAN no es valido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

