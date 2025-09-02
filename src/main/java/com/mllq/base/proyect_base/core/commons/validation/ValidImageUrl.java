package com.mllq.base.proyect_base.core.commons.validation;

import com.mllq.base.proyect_base.core.commons.validation.impl.ValidImageUrlValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidImageUrlValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageUrl {
    String message() default "La URL proporcionada no es una imagen válida.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
