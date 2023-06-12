package com.example.Dawlati.Services;

import com.example.Dawlati.models.AuditLog;
import com.example.Dawlati.models.AuditLogAction;
import com.example.Dawlati.models.User;
import com.example.Dawlati.repositories.AuditRepository;
import com.example.Dawlati.services.AuditService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuditServiceTest {

    @Mock private AuditRepository auditRepository;
    @InjectMocks private AuditService auditService;

    @Test
    void addAuditShouldInvokeSave() {
        User user = new User("John", "Doe", "johndoe@gmail.com", "password" );
        AuditLog auditLog = new AuditLog(LocalDateTime.now(), "User registered: " +
                user.getUsername(), AuditLogAction.Register, user);
        when(auditRepository.save(auditLog))
                .thenReturn(auditLog);
        AuditLog expected = auditService.add(auditLog);
        Assertions.assertEquals(user, auditLog.getUser());
        verify(auditRepository).save(auditLog);

    }
}
