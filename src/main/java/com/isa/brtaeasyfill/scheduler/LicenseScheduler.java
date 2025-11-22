package com.isa.brtaeasyfill.scheduler;
import com.isa.brtaeasyfill.model.Institute;
import com.isa.brtaeasyfill.model.Notification;
import com.isa.brtaeasyfill.repository.InstituteRepository;
import com.isa.brtaeasyfill.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class LicenseScheduler {

    private final InstituteRepository instituteRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public LicenseScheduler(InstituteRepository instituteRepository, NotificationRepository notificationRepository) {
        this.instituteRepository = instituteRepository;
        this.notificationRepository = notificationRepository;
    }

    // 🔁 Scheduled task
    @Scheduled(cron = "0 0 8 * * ?")
    public void checkLicenseExpiries() {
        List<Institute> institutes = instituteRepository.findAll();
        Date today = new Date();

        for (Institute institute : institutes) {
            Date expiry = institute.getLicenseExpiry();
            if (expiry != null) {
                long diff = expiry.getTime() - today.getTime();
                long daysLeft = TimeUnit.MILLISECONDS.toDays(diff);

                if (daysLeft >= 0 && daysLeft <= 5) {
                    String message = "Your license will expire in " + daysLeft + " day" + (daysLeft == 1 ? "" : "s") + ". Please renew.";

                    boolean alreadyNotified = notificationRepository.existsByInstituteAndMessageAndCreatedAtBetween(
                            institute,
                            message,
                            getStartOfDay(today),
                            getEndOfDay(today)
                    );

                    if (!alreadyNotified) {
                        Notification notification = new Notification();
                        notification.setInstitute(institute);
                        notification.setUser(null); // Optional: set to specific user
                        notification.setMessage(message);
                        notification.setCreatedAt(today);
                        notification.setReadStatus(Boolean.parseBoolean("UNREAD"));
                        notificationRepository.save(notification);
                    }
                }

                if (daysLeft < 0 && !"INACTIVE".equalsIgnoreCase(institute.getStatus())) {
                    institute.setStatus("INACTIVE");
                    instituteRepository.save(institute);
                }
            }
        }
    }

    // ✅ Helper method to get start of day
    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    // ✅ Helper method to get end of day
    private Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
}
