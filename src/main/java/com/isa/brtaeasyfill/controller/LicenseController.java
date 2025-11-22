package com.isa.brtaeasyfill.controller;

import com.isa.brtaeasyfill.model.Institute;
import com.isa.brtaeasyfill.model.LicenseRenewalLog;
import com.isa.brtaeasyfill.repository.InstituteRepository;
import com.isa.brtaeasyfill.repository.LicenseRenewalLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/license")
public class LicenseController {

    @Autowired
    private LicenseRenewalLogRepository licenseRepo;

    @Autowired
    private InstituteRepository instituteRepo;

    //License Key Generator & Saver
    @PostMapping("/generate")
    public ResponseEntity<?> generateLicense(@RequestParam Long instituteId) {
        Optional<Institute> instituteOpt = instituteRepo.findById(instituteId);
        if (instituteOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid institute ID");
        }

        String licenseKey = generateUniqueLicenseKey();

        LicenseRenewalLog log = new LicenseRenewalLog();
        log.setInstitute(instituteOpt.get());
        log.setLicenseKey(licenseKey);
        log.setRenewedOn(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
//        calendar.add(Calendar.DAY_OF_MONTH, 4);
//        calendar.add(Calendar.MINUTE, 2);
        log.setExpiryDate(calendar.getTime());

        System.out.println("License expiry date: " + log.getExpiryDate());
        licenseRepo.save(log);
        return ResponseEntity.ok(Map.of("licenseKey", licenseKey));
    }

    // Unique License Key Generator (safe from duplicates)

    private String generateUniqueLicenseKey() {
        String licenseKey;
        do {
            licenseKey = "LIC-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
        } while (licenseRepo.findByLicenseKey(licenseKey).isPresent());
        return licenseKey;
    }

}
