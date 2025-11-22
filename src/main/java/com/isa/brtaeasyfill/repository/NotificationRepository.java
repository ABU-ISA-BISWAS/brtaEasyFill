package com.isa.brtaeasyfill.repository;

import com.isa.brtaeasyfill.model.Institute;
import com.isa.brtaeasyfill.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    boolean existsByInstituteAndMessageAndCreatedAtBetween(
            Institute institute,
            String message,
            Date start,
            Date end
    );

    List<Notification> findByInstituteIdOrderByCreatedAtDesc(Long instituteId); // ✅ FIXED
}
