package com.example.Dawlati.Services;

import com.example.Dawlati.Security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    public String createLoginInfo(Authentication authentication) {

        String token = jwtProvider.createToken(authentication);

        return token;
    }
}
