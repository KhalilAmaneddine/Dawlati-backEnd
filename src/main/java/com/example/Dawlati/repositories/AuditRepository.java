package com.example.Dawlati.repositories;

import com.example.Dawlati.models.AuditLog;
import com.example.Dawlati.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<AuditLog, Integer> {

}
