package com.example.Dawlati.Services;

import com.example.Dawlati.Models.AuditLog;
import com.example.Dawlati.Models.User;
import com.example.Dawlati.Repositories.AuditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuditService {
    private final AuditRepository auditRepository;
    public AuditLog add(AuditLog auditLog) {
        return  auditRepository.save(auditLog);
    }

    public void deleteByUser(User user) {
        auditRepository.deleteByUser(user);
    }
}
