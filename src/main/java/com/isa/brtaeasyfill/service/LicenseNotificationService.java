package com.isa.brtaeasyfill.service;

import com.isa.brtaeasyfill.model.AppUser;
import com.isa.brtaeasyfill.model.Institute;
import com.isa.brtaeasyfill.model.Notification;
import com.isa.brtaeasyfill.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class LicenseNotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void checkAndNotifyLicenseExpiry(Institute institute, AppUser user) {
        Date expiryDate = institute.getLicenseExpiry();
        Date today = new Date();

        long diffInMillies = expiryDate.getTime() - today.getTime();
        long daysLeft = diffInMillies / (1000 * 60 * 60 * 24);

        if (daysLeft <= 5 && daysLeft >= 0) {
            String message = "Your license will expire in " + daysLeft + " day(s).";

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date startOfDay = cal.getTime();

            cal.add(Calendar.DATE, 1);
            Date endOfDay = cal.getTime();

            boolean exists = notificationRepository.existsByInstituteAndMessageAndCreatedAtBetween(
                    institute, message, startOfDay, endOfDay
            );

            if (!exists) {
                Notification notification = new Notification();
                notification.setInstitute(institute);
                notification.setUser(user);
                notification.setMessage(message);
                notification.setCreatedAt(new Date());
                notification.setReadStatus(Boolean.parseBoolean("UNREAD"));

                notificationRepository.save(notification);
                System.out.println(" Notification created: " + message + " for " + institute.getName());
            } else {
                System.out.println(" Notification already sent today for: " + institute.getName());
            }
        }
    }
}
