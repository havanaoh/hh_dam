package com.hh.dam.service;

import com.hh.dam.entity.Member;
import com.hh.dam.entity.Notifications;
import com.hh.dam.repository.NotificationsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NotificationsService {

    private final NotificationsRepository notificationsRepository;

    public NotificationsService(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    public void createNotification(Member member, String message){
        Notifications notifications = new Notifications();
        notifications.setMember(member);
        notifications.setMessage(message);
        notifications.setCreatedAt(LocalDate.now());
        notifications.setIsRead(false);

        notificationsRepository.save(notifications);
    }

}
