package com.example.Dawlati.annotations;

import com.example.Dawlati.models.AuditLog;
import com.example.Dawlati.models.User;
import com.example.Dawlati.repositories.UserRepository;
import com.example.Dawlati.services.AuditService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@AllArgsConstructor
public class AuditAspect {
    private final AuditService auditService;
    private final UserRepository userRepository;
    @After("@annotation(audit)")
    public void logAudit(JoinPoint joinPoint, Audit audit) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        AuditLog auditLog = new AuditLog(LocalDateTime.now(), audit.details(), audit.action(), user);
        auditService.add(auditLog);
    }
}
