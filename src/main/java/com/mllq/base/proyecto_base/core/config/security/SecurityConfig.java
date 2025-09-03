package com.mllq.base.proyecto_base.core.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mllq.base.proyecto_base.core.commons.models.enums.Role;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final SecurityFilter securityFilter;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint, SecurityFilter securityFilter) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .httpBasic(AbstractHttpConfigurer::disable) // Deshabilitar Basic
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(EndpointSecurityConstant.ENDPOINT_PUBLIC).permitAll()
                    .requestMatchers(EndpointSecurityConstant.ENDPOINT_PRIVATE).authenticated()
                    .requestMatchers(EndpointSecurityConstant.ENDPOINT_SWAGGER).hasAnyRole(Role.SUPPORT.name())
                    .anyRequest().authenticated())
            .exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint))
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
