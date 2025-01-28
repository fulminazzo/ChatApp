package it.fulminazzo.chatapp.api.v1.security.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import it.fulminazzo.chatapp.api.v1.security.config.JwtKeyConfig;
import it.fulminazzo.chatapp.api.v1.security.exceptions.InvalidJwtException;
import it.fulminazzo.chatapp.structures.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtKeyConfig jwtKeyConfig;

    public String extractUsernameFromJwtToken(String token) throws InvalidJwtException {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtException();
        }
    }

    public Tuple<String, Long> generateJwtTokenFromUsername(String username) {
        long now = System.currentTimeMillis();
        long expirationDate = jwtKeyConfig.getExpirationDate();
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expirationDate))
                .signWith(getSecretKey())
                .compact();
        return new Tuple<>(token, expirationDate);
    }

    public SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtKeyConfig.getSecretKey());
        return new SecretKeySpec(keyBytes, jwtKeyConfig.getAlgorithm());
    }

}
