package com.isa.brtaeasyfill.controller;

import com.isa.brtaeasyfill.model.Institute;
import com.isa.brtaeasyfill.repository.InstituteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import java.util.Optional;


@RestController
@RequestMapping("/api/institute")
@CrossOrigin // Enable CORS for frontend
public class InstituteController {

    private final InstituteRepository instituteRepository;

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
    public ResponseEntity<?> renewLicense(@PathVariable Long id, @RequestBody String newLicenseKey) {
        Optional<Institute> optionalInstitute = instituteRepository.findById(id);
        if (!optionalInstitute.isPresent()) {
            return ResponseEntity.badRequest().body("Institute not found");
        }
        Institute institute = optionalInstitute.get();
        institute.setLicenseKey(newLicenseKey.replace("\"", "")); // Remove quotes
        Date newExpiry = new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000);
        institute.setLicenseExpiry(newExpiry);
        institute.setStatus("ACTIVE");
        Institute saved = instituteRepository.save(institute);
        return ResponseEntity.ok(saved);
    }
}