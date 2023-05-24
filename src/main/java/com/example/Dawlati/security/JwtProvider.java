package com.example.Dawlati.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
@Component
@AllArgsConstructor
public class JwtProvider {
    private final JwtEncoder jwtEncoder;
    public String createToken(Authentication authentication) {
        Instant now =  Instant.now();
        long expiresIn = 2;
        String authorities = authentication.getAuthorities().stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining(""));
        JwtClaimsSet claims =  JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(expiresIn, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim("authorities", authorities)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
