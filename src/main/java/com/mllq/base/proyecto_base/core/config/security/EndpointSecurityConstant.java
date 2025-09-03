package com.mllq.base.proyecto_base.core.config.security;

public class EndpointSecurityConstant {
    public static final String[] ENDPOINT_PUBLIC = {
            "/api/test",
            "/api/v1/auth/login",
            "/api/public/**",
            "/h2-console/**",
    };

    public static final String[] ENDPOINT_PRIVATE = {
            "/api/v1/**",
            "/api/socket"

    };
    public static final String[] ENDPOINT_SWAGGER= {
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };
}
