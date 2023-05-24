package com.example.Dawlati.services;

import com.example.Dawlati.annotations.Audit;
import com.example.Dawlati.models.AuditLogAction;
import com.example.Dawlati.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    @Audit(action = AuditLogAction.Login, details = "User Login")
    public String createLoginInfo(Authentication authentication) {

        String token = jwtProvider.createToken(authentication);

        return token;
    }
}
