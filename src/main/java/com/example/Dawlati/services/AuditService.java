package com.example.Dawlati.services;

import com.example.Dawlati.models.AuditLog;
import com.example.Dawlati.repositories.AuditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuditService {
    private final AuditRepository auditRepository;
    public AuditLog add(AuditLog auditLog) {
        return  auditRepository.save(auditLog);
    }


}
