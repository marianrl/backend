package com.ams.backend.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;

@Component
public class JwtTokenUtil {

    @Value("${JWT_SECRET:defaultSecretKey12345678901234567890123456789012345678901234567890}")
    private String secret;

    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private static final long EXPIRATION_TIME = 86400000; // 1 día

    public String generateToken(String username, String name, String lastName, int roleId) {
        // Crear un mapa de claims para incluir información adicional
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", name);
        claims.put("lastName", lastName);
        claims.put("role", roleId);

        return Jwts.builder()
                .setClaims(claims) // Agregar los claims al token
                .setSubject(username) // Usuario (email o username)
                .setIssuedAt(new Date()) // Fecha de creación del token
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Fecha de expiración
                .signWith(getSigningKey()) // Firmar el token con la clave secreta
                .compact();
    }

    public String getUsernameFromToken(String token) {
        // Crear un JwtParser usando JwtParserBuilder
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Usa la clave de firma para verificar el token
                .build() // Construir el parser
                .parseClaimsJws(token) // Analizar el token JWT
                .getBody(); // Obtener el cuerpo (claims)

        return claims.getSubject(); // Obtener el "subject" del token
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // Configura la clave de firma
                    .build() // Construye el parser
                    .parseClaimsJws(token); // Valida y analiza el token

            return true; // Si no lanza excepción, el token es válido
        } catch (ExpiredJwtException e) {
            System.out.println("El token ha expirado: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("El token está mal formado: " + e.getMessage());
        } catch (io.jsonwebtoken.security.SignatureException e) {
            System.out.println("La firma del token no es válida: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("El token está vacío o es nulo: " + e.getMessage());
        }

        return false; // Si alguna excepción se lanza, el token no es válido
    }
}
