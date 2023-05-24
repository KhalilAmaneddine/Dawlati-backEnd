package com.example.Dawlati.services;

import com.example.Dawlati.models.Notification;
import com.example.Dawlati.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    public Notification save(Notification notification) {
        return this.notificationRepository.save(notification);
    }
    public List<Notification> findNotSentNotifications() {
        return this.notificationRepository.findByIsSent(0);
    }

    public Notification updateIsSet(Notification notification) {
        return this.notificationRepository.save(notification);
    }
}
