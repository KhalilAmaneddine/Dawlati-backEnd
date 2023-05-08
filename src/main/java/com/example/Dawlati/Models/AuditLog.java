package com.example.Dawlati.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "audit_log")
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;

    @Column(name = "DETAILS", columnDefinition = "Text")
    private String details;

    @Column(name = "ACTION")
    private String action; // could be an enum.

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    public AuditLog(LocalDateTime timestamp, String details, String action, User user) {
        this.timestamp = timestamp;
        this.details = details;
        this.action = action;
        this.user = user;
    }
}
