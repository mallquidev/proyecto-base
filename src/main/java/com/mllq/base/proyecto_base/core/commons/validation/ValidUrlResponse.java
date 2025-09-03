package com.mllq.base.proyecto_base.core.commons.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.mllq.base.proyecto_base.core.commons.validation.impl.ValidUrlResponseValidator;

@Documented
@Constraint(validatedBy = ValidUrlResponseValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUrlResponse {

    String message() default "La URL no es accesible o no responde correctamente.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}