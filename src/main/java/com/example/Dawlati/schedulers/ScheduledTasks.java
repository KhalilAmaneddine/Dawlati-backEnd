package com.example.Dawlati.schedulers;

import com.example.Dawlati.constants.Constants;
import com.example.Dawlati.models.Notification;
import com.example.Dawlati.services.EmailSenderService;
import com.example.Dawlati.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private final EmailSenderService emailSenderService;

    private String emailTo;
    private final NotificationService notificationService;
    @Scheduled(fixedRate = 10000)
    public void checkNotifications() {
        List<Notification> notifications = this.notificationService.findNotSentNotifications();
        for(Notification notification : notifications) {
            this.emailTo = Constants.DEFAULT_SOURCE_EMAIL_ADDRESS;
            if (notification.getSubject().equals("Approval")) {
                this.emailTo = notification.getUser().getEmail();
            }
            emailSenderService.sendEmail(this.emailTo, notification.getSubject(), notification.getMessage());
            notification.setIsSent(1);
            this.notificationService.updateIsSet(notification);
        }
    }
}
