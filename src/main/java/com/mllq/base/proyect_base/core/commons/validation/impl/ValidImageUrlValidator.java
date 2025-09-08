package com.mllq.base.proyect_base.core.commons.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.HttpURLConnection;
import java.net.URL;

import com.mllq.base.proyect_base.core.commons.validation.ValidImageUrl;

public class ValidImageUrlValidator implements ConstraintValidator<ValidImageUrl, String> {

    @Override
    public boolean isValid(String urlString, ConstraintValidatorContext context) {
        if (urlString == null || urlString.isBlank()) {
            return true;
        }

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);

            int responseCode = connection.getResponseCode();
            String contentType = connection.getContentType();

            return responseCode == 200 &&
                    contentType != null &&
                    contentType.startsWith("image/");
        } catch (Exception e) {
            return false;
        }
    }
}
