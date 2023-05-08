package com.example.Dawlati.Repositories;

import com.example.Dawlati.Models.AuditLog;
import com.example.Dawlati.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<AuditLog, Integer> {
    void deleteByUser(User user);
}
