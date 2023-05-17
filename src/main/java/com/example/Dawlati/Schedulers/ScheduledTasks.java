package com.example.Dawlati.Schedulers;

import com.example.Dawlati.Models.Notification;
import com.example.Dawlati.Services.EmailSenderService;
import com.example.Dawlati.Services.NotificationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        for (int i = 0; i < notifications.size(); i++) {
                this.emailTo = "jakedoe@gmail.com";
            if (notifications.get(i).getSubject() == "APPROVAL")
                this.emailTo = notifications.get(i).getUser().getEmail();
            emailSenderService.sendEmail(this.emailTo,
                    notifications.get(i).getSubject(), notifications.get(i).getMessage());
            notifications.get(i).setIsSent(1);
            this.notificationService.updateIsSet(notifications.get(i));
        }
    }
}
