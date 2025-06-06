package com.ams.backend.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;

@Component
public class JwtTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final long EXPIRATION_TIME = 86400000; // 1 día

    @Value("${JWT_SECRET}")
    private String secret;

    private Key getSigningKey() {
        try {
            logger.debug("Getting signing key from secret");
            // Generate a key that's suitable for HS512
            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);
            return Keys.hmacShaKeyFor(key.getEncoded());
        } catch (Exception e) {
            logger.error("Error getting signing key: {}", e.getMessage(), e);
            throw e;
        }
    }

    public String generateToken(String username, String name, String lastName, int roleId) {
        try {
            logger.debug("Generating token for user: {}", username);
            Map<String, Object> claims = new HashMap<>();
            claims.put("name", name);
            claims.put("lastName", lastName);
            claims.put("role", roleId);

            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                    .compact();
        } catch (Exception e) {
            logger.error("Error generating token: {}", e.getMessage(), e);
            throw e;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            logger.debug("Getting username from token");
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {
            logger.error("Error getting username from token: {}", e.getMessage(), e);
            throw e;
        }
    }

    public boolean validateToken(String token) {
        try {
            logger.debug("Validating token");
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("Token has expired: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warn("Token is malformed: {}", e.getMessage());
        } catch (io.jsonwebtoken.security.SignatureException e) {
            logger.warn("Token signature is invalid: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("Token is empty or null: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error validating token: {}", e.getMessage(), e);
        }
        return false;
    }
}
