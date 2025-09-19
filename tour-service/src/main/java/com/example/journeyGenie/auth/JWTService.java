package com.example.journeyGenie.auth;

import com.example.journeyGenie.util.AppEnv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private final String secretKey;

    public JWTService() {
        this.secretKey = AppEnv.getTokenSecret();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ===================== Token Generation =====================
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        // Optional: add roles or extra claims here
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * AppEnv.getTokenValidityMinutes()))
                .and()
                .signWith(getKey())
                .compact();
    }

    // ===================== Extraction Helpers =====================
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public long getRemainingValidityMinutes(String token) {
        Date expiration = extractExpiration(token);
        long diffMillis = expiration.getTime() - System.currentTimeMillis();
        return diffMillis / (1000 * 60);
    }

    // ===================== Validation =====================
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token); // signature check is implicit in extractAllClaims()
        } catch (Exception e) {
            return false;
        }
    }

    // ===================== Additional Methods =====================
    private String getEmailFromToken(String token) {
        return extractUserName(token);
    }

    public String getEmailFromRequest(HttpServletRequest request) {
        // Check cookies
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    return getEmailFromToken(cookie.getValue());
                }
            }
        }

        // Fallback: Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return getEmailFromToken(token);
        }

        return null;
    }
}
