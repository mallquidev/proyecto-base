package com.mllq.base.proyecto_base.core.commons.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.mllq.base.proyecto_base.core.commons.validation.impl.PasswordValidator;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordStrong {

    String message() default "La contraseña no cumple con los requisitos de seguridad.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}