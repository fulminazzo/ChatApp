package it.fulminazzo.chatapp.backend.security.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import it.fulminazzo.chatapp.backend.security.config.JwtKeyConfig;
import it.fulminazzo.chatapp.backend.security.exceptions.InvalidJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    public static final String ALGORITHM = "HmacSHA256";
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

    public String generateJwtTokenFromUsername(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(null)
                .signWith(getSecretKey())
                .compact();
    }

    public SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtKeyConfig.getSecretKey());
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

}
