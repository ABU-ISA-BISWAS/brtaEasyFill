package com.isa.brtaeasyfill.repository;


import com.isa.brtaeasyfill.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByInstituteIdAndUserIdAndReadStatus(Long instituteId, Long userId, String readStatus);
}
