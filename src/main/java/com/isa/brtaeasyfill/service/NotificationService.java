package com.isa.brtaeasyfill.service;

import com.isa.brtaeasyfill.model.Notification;
import com.isa.brtaeasyfill.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
        notification.setReadStatus(true);
        notificationRepository.save(notification);
    }
    
    public void deleteNotification(Long id) {
        Notification n = notificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notificationRepository.deleteById(id); 
    }

}

