package com.example.Dawlati.Repositories;

import com.example.Dawlati.models.Notification;
import com.example.Dawlati.models.NotificationType;
import com.example.Dawlati.models.User;
import com.example.Dawlati.repositories.NotificationRepository;
import com.example.Dawlati.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Test
    void findByIsSentShouldReturnNotificationsList() {
        User user = new User("John", "Doe", "johndoe@gmail.com", "password" );
        userRepository.save(user);
        Notification notification1 = new Notification(NotificationType.EMAIL, "Form Submitted",
                "SUBMITTAL", LocalDateTime.now(), 0, 0, user);
        Notification notification2 = new Notification(NotificationType.EMAIL, "Form Submitted",
                "SUBMITTAL", LocalDateTime.now(), 0, 0, user);
        Notification notification3 = new Notification(NotificationType.EMAIL, "Form Submitted",
                "SUBMITTAL", LocalDateTime.now(), 0, 1, user);
        notificationRepository.save(notification1);
        notificationRepository.save(notification2);
        notificationRepository.save(notification3);
        List<Notification> notifications = notificationRepository.findByIsSent(0);
        Assertions.assertEquals(2, notifications.size());

        for(Notification notification: notifications) {
            Assertions.assertEquals(0, notification.getIsSent());
        }
    }
}
