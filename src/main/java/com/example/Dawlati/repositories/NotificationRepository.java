package com.example.Dawlati.repositories;

import com.example.Dawlati.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByIsSent(Integer isSent);
}
