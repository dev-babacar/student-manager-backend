package com.babacarmane.studentmanagerbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

// security/JwtService.java
@Service
public class JwtService {

    // Clé secrète pour signer les tokens
    // Dans application.yml — jamais en dur dans le code
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;
    // ↑ durée de validité en millisecondes (ex: 86400000 = 24h)


    // Génère un token JWT pour un utilisateur
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                // ↑ subject = email de l'utilisateur

                .setIssuedAt(new Date(System.currentTimeMillis()))
                // ↑ date de création

                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                // ↑ date d'expiration

                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                // ↑ signe avec la clé secrète
                .compact();
        // ↑ retourne le token en String
    }


    // Extrait l'email depuis le token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    // Vérifie si le token est valide
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return email.equals(userDetails.getUsername())
                && !isTokenExpired(token);
        // ↑ email correct ET token pas expiré
    }


    // ── Méthodes privées ──────────────────────

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}