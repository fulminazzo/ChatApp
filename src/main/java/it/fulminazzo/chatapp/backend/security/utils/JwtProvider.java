package it.fulminazzo.chatapp.backend.security.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import it.fulminazzo.chatapp.backend.security.config.JwtKeyConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtKeyConfig jwtKeyConfig;

    public String extractUsernameFromJwtToken(String token) throws JwtException, IllegalArgumentException {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtKeyConfig.getSecretKey());
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

}
