package com.mllq.base.proyect_base.core.commons.validation.impl;


import com.mllq.base.proyect_base.core.commons.validation.PasswordStrong;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordValidator implements ConstraintValidator<PasswordStrong, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password==null){
            return false;
        }
        boolean valid = true;

        if (password.length() < 8) {
            buildMessage(context, "La contraseña debe tener al menos 8 caracteres");
            valid = false;
        }
        if (!password.matches(".*[A-Z].*")) {
            buildMessage(context, "La contraseña debe contener al menos una letra mayúscula");
            valid = false;
        }
        if (!password.matches(".*[a-z].*")) {
            buildMessage(context, "La contraseña debe contener al menos una letra minúscula");
            valid = false;
        }
        if (!password.matches(".*[0-9].*")) {
            buildMessage(context, "La contraseña debe contener al menos un número");
            valid = false;
        }
        if (!password.matches(".*[!@#\\$%\\^&\\*].*")) {
            buildMessage(context, "La contraseña debe contener al menos un carácter especial (!@#$%^&*)");
            valid = false;
        }

        return valid;
    }

    private void buildMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
