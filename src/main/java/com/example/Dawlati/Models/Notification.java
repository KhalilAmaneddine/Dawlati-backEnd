package com.example.Dawlati.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "NOTIFICATION_TYPE")
    private NotificationType notificationType;

    @Column(name = "Message")
    private String message;

    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;

    @Column(name = "RETRY_COUNT")
    private Integer retryCount;

    @Column(name = "IS_SENT")
    private Boolean isSent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;
}
