package com.example.Dawlati.Repositories;

import com.example.Dawlati.Models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByIsSent(Integer isSent);
}
