package com.mllq.base.proyecto_base.core.config.security;

import com.mllq.base.proyecto_base.core.commons.libs.auth.jwt.JwtDecode;
import com.mllq.base.proyecto_base.core.commons.libs.auth.models.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Filtro de seguridad que se ejecuta en cada request.
 * - Extrae el token JWT del header Authorization.
 * - Lo valida con JwtDecode.
 * - Reconstruye un UserPrincipal y lo inyecta en el SecurityContext de Spring.
 */
@Slf4j
@AllArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtDecode jwtDecode;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        doBearerFilter(request, response, filterChain);
    }

    private void doBearerFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        Optional<String> authorizationHeader = Optional.ofNullable(request.getHeader("Authorization"));
        log.debug("Authorization Header: {}", authorizationHeader);

        if (authorizationHeader.isPresent() && authorizationHeader.get().startsWith("Bearer ")) {
            String bearerToken = authorizationHeader.get().substring(7);

            try {
                // 1. Decodificar el token y reconstruir el principal
                UserPrincipal userPrincipal = jwtDecode.toUserPrincipal(bearerToken);

                if (userPrincipal != null) {
                    // 2. Crear la autenticación de Spring
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userPrincipal,
                                    null,
                                    userPrincipal.getRoles().stream()
                                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                                            .collect(Collectors.toList())
                            );

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // 3. Agregar IP al UserPrincipal
                    String ipAddress = getClientIpAddress(request);
                    userPrincipal.setIpAddress(ipAddress);

                    log.debug("Autenticación exitosa para usuario: {} - IP: {}", userPrincipal.getEmail(), ipAddress);
                }

            } catch (Exception e) {
                log.error("Error al procesar el token JWT: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return; // cortar aquí para no dejar pasar requests inválidos
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    /**
     * Obtiene la IP real del cliente (soporta proxies / balanceadores).
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
