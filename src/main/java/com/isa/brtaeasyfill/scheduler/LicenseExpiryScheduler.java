
package com.isa.brtaeasyfill.scheduler;

import com.isa.brtaeasyfill.model.AppUser;
import com.isa.brtaeasyfill.model.Institute;
import com.isa.brtaeasyfill.model.Notification;

import com.isa.brtaeasyfill.repository.InstituteRepository;
import com.isa.brtaeasyfill.repository.NotificationRepository;
import com.isa.brtaeasyfill.repository.UserRepository;
import com.isa.brtaeasyfill.service.LicenseNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class LicenseExpiryScheduler {

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LicenseNotificationService licenseNotificationService;

    @Scheduled(cron = "0 0 10 * * ?")
    public void deactivateExpiredInstitutes() {
        Date now = new Date();
        long nowMillis = now.getTime();

        List<Institute> institutes = instituteRepository.findAll();

        System.out.println("Checking institutes at: " + now);

        for (Institute institute : institutes) {
            Date expiry = institute.getLicenseExpiry();
            String status = institute.getStatus();

            if (expiry != null) {
                long expiryMillis = expiry.getTime();

                //Trigger notification check
                AppUser adminUser = userRepository.findTopByInstituteId(institute.getId());
                if (adminUser != null) {
                    licenseNotificationService.checkAndNotifyLicenseExpiry(institute, adminUser);
                }
 
                if (expiryMillis <= nowMillis) {
                    
                    if (!"INACTIVE".equalsIgnoreCase(status)) {
                        institute.setStatus("INACTIVE");
                        instituteRepository.save(institute);
                        System.out.println("✅ '" + institute.getName() + "' marked as INACTIVE at " + now);
                    } else {
                        System.out.println("Already INACTIVE: " + institute.getName());
                    }

                } else {
                    System.out.println("Not expired yet: " + institute.getName());
                }

            }
        }
    }
}
