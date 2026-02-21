package com.isa.brtaeasyfill.controller;

import com.isa.brtaeasyfill.model.Institute;
import com.isa.brtaeasyfill.model.LicenseRenewalLog;
import com.isa.brtaeasyfill.repository.InstituteRepository;
import com.isa.brtaeasyfill.repository.LicenseRenewalLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@PreAuthorize("hasRole('SUPERADMIN')")
@RestController
@RequestMapping("/api/institutes")
@CrossOrigin 
public class InstituteController {

@Autowired
private final InstituteRepository instituteRepository;

    @Autowired
    private LicenseRenewalLogRepository licenseRenewalLogRepository;

    @Autowired
    public InstituteController(InstituteRepository instituteRepository) {
        this.instituteRepository = instituteRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Institute>> getAllInstitutes() {
        return ResponseEntity.ok(instituteRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Institute> createInstitute(@RequestBody Institute institute) {
        Institute saved = instituteRepository.save(institute);
        return ResponseEntity.ok(saved);
    }


    @PostMapping("/renew-license/{id}")
    @Transactional
    public ResponseEntity<?> renewLicense(
            @PathVariable Long id,
            @RequestBody String licenseKey
    ) {
        licenseKey = licenseKey.trim().replace("\"", "");

        if (licenseKey.isEmpty()) {
            return ResponseEntity.badRequest().body("License key is required");
        }

        Optional<Institute> instituteOptional = instituteRepository.findById(id);
        if (instituteOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Institute not found");
        }

        Institute institute = instituteOptional.get();

        Optional<LicenseRenewalLog> keyOptional = licenseRenewalLogRepository.findByLicenseKey(licenseKey);
        if (keyOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("License key does not exist");
        }

        LicenseRenewalLog log = keyOptional.get();
        if (log.isUsed()) {
            return ResponseEntity.badRequest().body("License key has already been used");
        }


        Date originalExpiry = log.getExpiryDate();
        institute.setLicenseExpiry(originalExpiry);

        institute.setLicenseKey(licenseKey);
        institute.setStatus("ACTIVE");
        instituteRepository.save(institute);

        log.setUsed(true);
        log.setUsedFor(institute);
        log.setRenewedOn(new Date());
        log.setInstitute(institute);
        licenseRenewalLogRepository.save(log);

        return ResponseEntity.ok("License renewed successfully for institute: " + institute.getName());
    }



}