package com.mllq.base.proyecto_base.core.commons.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.mllq.base.proyecto_base.core.commons.validation.ValidUrlResponse;

public class ValidUrlResponseValidator implements ConstraintValidator<ValidUrlResponse, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Si es nulo o vacío, deja que otras validaciones como @NotBlank manejen el errorTcp
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        try {
            URL url = new URL(value);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD"); // Usamos HEAD para solo consultar encabezados
            connection.setConnectTimeout(5000); // 5 segundos de timeout
            connection.setReadTimeout(5000); // 5 segundos de timeout
            int responseCode = connection.getResponseCode();

            return responseCode >= 200 && responseCode < 400; // Considera éxito 2xx o redirección 3xx
        } catch (IOException e) {
            return false; // Cualquier errorTcp es que la URL no es válida o no responde
        }
    }
}
