package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Setter
@ConfigurationProperties(prefix = "course-project.api")
public class JwtService {
    private String secret;
    private long expirationTime;

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey())
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Role extractRole(String token) {
        return Role.valueOf(extractAllClaims(token).get("role").toString());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claim = extractAllClaims(token);
        return claimsResolver.apply(claim);
    }

    public boolean isValid(String token) {
        final String email = extractEmail(token);
        return (!email.isEmpty() && !isExpired(token));
    }

    private boolean isExpired(String token) {
        return extractExripationDate(token).before(new Date());
    }

    private Date extractExripationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}