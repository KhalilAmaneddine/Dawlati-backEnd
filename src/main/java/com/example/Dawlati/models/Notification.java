package com.example.Dawlati.models;

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

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;

    @Column(name = "RETRY_COUNT")
    private Integer retryCount;

    @Column(name = "IS_SENT")
    private Integer isSent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Notification( NotificationType notificationType,String message, String subject, LocalDateTime timestamp,
                         Integer retryCount, Integer isSent, User user) {
        this.notificationType = notificationType;
        this.message = message;
        this.subject = subject;
        this.timestamp = timestamp;
        this.retryCount = retryCount;
        this.isSent = isSent;
        this.user = user;
    }
}
