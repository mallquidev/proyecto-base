package com.mllq.base.proyect_base.core.commons.validation;

import com.mllq.base.proyect_base.core.commons.validation.impl.ValidUrlResponseValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidUrlResponseValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUrlResponse {

    String message() default "La URL no es accesible o no responde correctamente.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}