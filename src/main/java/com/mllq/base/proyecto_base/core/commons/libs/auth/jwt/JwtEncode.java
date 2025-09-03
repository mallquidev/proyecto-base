package com.mllq.base.proyecto_base.core.commons.libs.auth.jwt;

import com.mllq.base.proyecto_base.core.commons.models.enums.TimeZone;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtEncode {

    private static final Logger log = LoggerFactory.getLogger(JwtEncode.class);

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME; // en segundos

    @Value("${jwt.refresh.expiration}")
    private long REFRESH_TOKEN_EXPIRATION_TIME; // en segundos

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Genera un token JWT con todos los claims principales (incluyendo companyId).
     */
    public String generateUserToken(
            Long userId,
            UUID uuid,
            List<String> roles,
            TimeZone timeZone,
            String email,
            Long companyId,
            Map<String, Object> attributes
    ) {
        log.info("Generando token para usuario con ID: {} y roles: {}", userId, roles);

        var builder = Jwts.builder()
                .setSubject(email)
                .claim("id", userId)
                .claim("uuid", uuid.toString())
                .claim("roles", roles)
                .claim("email", email)
                .claim("time_zone", timeZone.name())
                .claim("company_id", companyId)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(EXPIRATION_TIME)));

        attributes.forEach(builder::claim);

        return builder
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Genera un token JWT sin companyId.
     */
    public String generateUserToken(
            Long userId,
            UUID uuid,
            List<String> roles,
            TimeZone timeZone,
            String email,
            Map<String, Object> attributes
    ) {
        var builder = Jwts.builder()
                .setSubject(email)
                .claim("id", userId)
                .claim("uuid", uuid.toString())
                .claim("roles", roles)
                .claim("email", email)
                .claim("time_zone", timeZone.name())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(EXPIRATION_TIME)));

        attributes.forEach(builder::claim);

        return builder
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Genera un Refresh Token solo con el UUID del usuario.
     */
    public String generateRefreshToken(UUID uuid) {
        log.info("Generando refresh token para usuario con UUID: {}", uuid);

        return Jwts.builder()
                .setSubject("REFRESH")
                .claim("uuid", uuid.toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(REFRESH_TOKEN_EXPIRATION_TIME)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
