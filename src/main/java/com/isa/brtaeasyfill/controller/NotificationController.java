package com.isa.brtaeasyfill.controller;

import com.isa.brtaeasyfill.model.Notification;
import com.isa.brtaeasyfill.repository.NotificationRepository;
import com.isa.brtaeasyfill.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/institute/{instituteId}")
    public ResponseEntity<List<Notification>> getNotificationsByInstitute(@PathVariable Long instituteId) {
        List<Notification> notifications = notificationRepository
                .findByInstituteIdOrderByCreatedAtDesc(instituteId); // ✅ FIXED
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}/delete")
    public void deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
    }
}
