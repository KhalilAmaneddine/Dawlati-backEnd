package com.example.Dawlati.Services;

import com.example.Dawlati.models.Notification;
import com.example.Dawlati.models.NotificationType;
import com.example.Dawlati.repositories.NotificationRepository;
import com.example.Dawlati.services.NotificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @Mock private NotificationRepository notificationRepository;
    @InjectMocks
    private NotificationService notificationService;

    @Test
    void SaveShouldInvokeSave() {
        Notification notification = new Notification( NotificationType.EMAIL,"User submitted a form",
                "SUBMITTAL",
                LocalDateTime.now(),
                0, 0, null);
        when(notificationRepository.save(notification))
                .thenReturn(notification);
        Notification expected = notificationService.save(notification);
        Assertions.assertEquals("User submitted a form", expected.getMessage());
        verify(notificationRepository).save(notification);
    }

    @Test
    void FindNotSentNotificationsShouldInvokeFindByIsSent() {
        Notification notification = new Notification( NotificationType.EMAIL,"User submitted a form",
                "SUBMITTAL",
                LocalDateTime.now(),
                0, 0, null);
        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification);
        Integer isSent = 0;
        when(notificationRepository.findByIsSent(isSent))
                .thenReturn(notifications);
        List<Notification> expected = notificationService.findNotSentNotifications();
        verify(notificationRepository).findByIsSent(isSent);
    }


}
