package com.mllq.base.proyecto_base.core.commons.libs.auth.jwt;

import com.mllq.base.proyecto_base.core.commons.libs.auth.models.UserPrincipal;
import com.mllq.base.proyecto_base.core.commons.models.enums.Role;
import com.mllq.base.proyecto_base.core.commons.models.enums.TimeZone;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Clase encargada de DECODIFICAR el JWT.
 * - Se utiliza en filtros de seguridad (JwtAuthenticationFilter)
 *   o en servicios que necesiten validar el token y reconstruir el usuario.
 */
@Component
public class JwtDecode {

    // 🔑 Se inyecta desde application.properties o .env
    // ejemplo: secret.token=miSuperClaveSecreta123
    @Value("${secret.token}")
    private String secret;

    /**
     * Retorna la llave encriptada (SecretKey) a partir del "secret".
     * Necesaria para que JJWT pueda validar la firma del token.
     */
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Obtiene TODOS los claims (información embebida en el token).
     * Esto es el corazón del decode, ya que permite acceder a cada campo.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey()) // usa la misma key que se utilizó al crear el token
                .build()
                .parseClaimsJws(token)   // valida y decodifica el token
                .getBody();
    }

    // Métodos para extraer información puntual desde el token ↓↓↓

    /** Obtiene el id del usuario (claim "id"). */
    public Long extractUserId(String token) {
        return extractAllClaims(token).get("id", Long.class);
    }

    /** Obtiene el id de la compañía (claim "companyId"). */
    public Long extractCompanyId(String token) {
        return extractAllClaims(token).get("companyId", Long.class);
    }

    /** Obtiene el email del usuario (claim "email"). */
    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    /** Obtiene el UUID del usuario (claim "uuid"). */
    public UUID extractUuid(String token) {
        String uuidString = extractAllClaims(token).get("uuid", String.class);
        return uuidString != null ? UUID.fromString(uuidString) : null;
    }

    /** Obtiene el TimeZone del usuario (claim "timeZone"). */
    public TimeZone extractTimeZone(String token) {
        String tz = extractAllClaims(token).get("timeZone", String.class);
        return tz != null ? TimeZone.valueOf(tz) : null;
    }

    /** Obtiene la dirección IP (claim "ipAddress"). */
    public String extractIpAddress(String token) {
        return extractAllClaims(token).get("ipAddress", String.class);
    }

    /**
     * Obtiene los roles del usuario (claim "roles").
     * - En el token se guardan como List<String>.
     * - Aquí los convertimos a enums Role.
     */
    public List<Role> extractRolesFromToken(String token) {
        List<String> roles = extractAllClaims(token).get("roles", List.class);
        if (roles == null) return List.of();
        return roles.stream()
                .map(Role::valueOf) // convierte "ADMIN" -> Role.ADMIN
                .collect(Collectors.toList());
    }

    /** Obtiene la fecha de expiración del token. */
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    /** Verifica si el token ya expiró. */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Reconstruye un UserPrincipal a partir del token.
     * - Este método es MUY útil en filtros de seguridad para volver a "armar" el usuario autenticado.
     * - Con este objeto ya puedes inyectar datos a Spring Security o a tu capa de servicios.
     */
    public UserPrincipal toUserPrincipal(String token) {
        Claims claims = extractAllClaims(token);

        return UserPrincipal.builder()
                .id(claims.get("id", Long.class))
                .companyId(claims.get("companyId", Long.class))
                .email(claims.get("email", String.class))
                .uuid(claims.get("uuid") != null ? UUID.fromString(claims.get("uuid", String.class)) : null)
                .timeZone(claims.get("timeZone") != null ? TimeZone.valueOf(claims.get("timeZone", String.class)) : null)
                .ipAddress(claims.get("ipAddress", String.class))
                .roles(extractRolesFromToken(token))
                .build();
    }
}
