package com.mllq.base.proyecto_base.core.config.security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.mllq.base.proyecto_base.core.commons.exception.ErrorCode;
import com.mllq.base.proyecto_base.core.commons.libs.utils.JsonUtils;
import com.mllq.base.proyecto_base.core.commons.models.ApiError;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 en vez de 403
        response.getWriter().write(JsonUtils.toJson(ApiError.unauthorized(
                request,
                ErrorCode.TOKEN_INVALID
        )));
    }
}
