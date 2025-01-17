package com.ams.backend.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Genera una clave segura
    private static final long EXPIRATION_TIME = 86400000; // 1 día

    public String generateToken(String username, String name, String lastName) {
        // Crear un mapa de claims para incluir información adicional
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", name);
        claims.put("lastName", lastName);

        // Log de la clave (solo para pruebas, eliminar en producción)
        System.out.println("Clave segura generada: " + Base64.getEncoder().encodeToString(key.getEncoded()));


        return Jwts.builder()
                .setClaims(claims) // Agregar los claims al token
                .setSubject(username) // Usuario (email o username)
                .setIssuedAt(new Date()) // Fecha de creación del token
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Fecha de expiración
                .signWith(key) // Firmar el token con la clave secreta
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }
}

