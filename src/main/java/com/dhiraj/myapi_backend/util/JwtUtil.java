// ==========================================================================
// File: JwtUtil.java
// Purpose: Create and validate JWTs for user sessions
// ==========================================================================

package com.dhiraj.myapi_backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET = "ReplaceWithAStrongSecretKeyWith256bitsReplaceWithAStrongSecretKey";
    private final long EXPIRY = 1000 *  60 * 60 * 24; // 24hr
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setIssuer("MyAPI")
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRY))
                .signWith(key)
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            return null;
        }
    }
}
