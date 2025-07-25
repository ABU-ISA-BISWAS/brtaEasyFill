package com.isa.brtaeasyfill.scheduler;

import com.isa.brtaeasyfill.model.Institute;
import com.isa.brtaeasyfill.model.Notification;
import com.isa.brtaeasyfill.repository.InstituteRepository;
import com.isa.brtaeasyfill.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    // Run daily at 8am
    @Scheduled(cron = "0 0 8 * * ?")
    public void checkLicenseExpiries() {
        List<Institute> institutes = instituteRepository.findAll();
        Date today = new Date();

        for (Institute institute : institutes) {
            Date expiry = institute.getLicenseExpiry();
            if (expiry != null) {
                long diff = expiry.getTime() - today.getTime();
                long daysLeft = TimeUnit.MILLISECONDS.toDays(diff);

                if (daysLeft == 5) {
                    Notification notification = new Notification();
                    notification.setInstitute(institute);
                    notification.setUser(null); // Or set to Institute Admin user
                    notification.setMessage("Your license will expire in 5 days. Please renew.");
                    notification.setCreatedAt(new Date());
                    notification.setReadStatus("UNREAD");
                    notificationRepository.save(notification);
                } else if (daysLeft <= 0 && !"INACTIVE".equalsIgnoreCase(institute.getStatus())) {
                    institute.setStatus("INACTIVE");
                    instituteRepository.save(institute);
                }
            }
        }
    }
}