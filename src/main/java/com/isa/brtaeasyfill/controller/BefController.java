package com.isa.brtaeasyfill.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isa.brtaeasyfill.model.AppUser;
import com.isa.brtaeasyfill.model.BefModel;
import com.isa.brtaeasyfill.repository.UserRepository;
import com.isa.brtaeasyfill.service.BefService;
import com.isa.brtaeasyfill.service.FormReportService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/forms")
@CrossOrigin(origins = "*")
public class BefController {
    @Autowired
    private HttpServletRequest req;
    private final BefService befService;
    private final FormReportService formReportService;
    private final UserRepository appUserRepository;

    public BefController(UserRepository appUserRepository, BefService befService, FormReportService formReportService) {
        this.befService = befService;
        this.formReportService = formReportService;
        this.appUserRepository = appUserRepository;

    }
    private boolean isUserAuthorized(AppUser user) {
        if (!"SUPERADMIN".equalsIgnoreCase(user.getRole())) {
            return user.getInstitute() != null && "active".equalsIgnoreCase(user.getInstitute().getStatus());
        }
        return true;
    }

    @GetMapping
    public ResponseEntity<?> getAllForms(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestHeader(value = "Institute-Id", required = false) String instituteId
    ) {
        // Get the currently logged-in user
        String username = req.getUserPrincipal().getName(); // get logged-in username
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!isUserAuthorized(user)) {
            return ResponseEntity.status(403).body("Your institute license has expired");
        }

        return ResponseEntity.ok(befService.getAllForms());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getForm(@PathVariable Long id) {
        //  Get the currently logged-in user
        String username = req.getUserPrincipal().getName(); // get logged-in username
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!isUserAuthorized(user)) {
            return ResponseEntity.status(403).body("Your institute license has expired");
        }

        return ResponseEntity.ok(befService.getFormById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateForm(@PathVariable Long id, @RequestBody BefModel form) {
        //  Get the currently logged-in user
        String username = req.getUserPrincipal().getName(); // get logged-in username
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!isUserAuthorized(user)) {
            return ResponseEntity.status(403).body("Your institute license has expired");
        }

        return ResponseEntity.ok(befService.updateForm(id, form));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> saveForm(@RequestBody BefModel form) {
        //  Get the currently logged-in user
        String username = req.getUserPrincipal().getName(); // get logged-in username
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!isUserAuthorized(user)) {
            return ResponseEntity.status(403).body("Your institute license has expired");
        }

        BefModel saved = befService.saveForm(form);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    @GetMapping("/{id}/pdf/a4")
    public ResponseEntity<?> getA4Pdf(@PathVariable Long id) {
        String username = req.getUserPrincipal().getName(); // get logged-in username
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!isUserAuthorized(user)) {
            return ResponseEntity.status(403).body("Your institute license has expired");
        }
        //  Generate and return PDF
        byte[] pdf = formReportService.generateA4(id);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "inline; filename=form-" + id + "-a4.pdf")
                .body(pdf);
    }


    @GetMapping("/{id}/pdf/stamp")
    public ResponseEntity<?> getStampPdf(@PathVariable Long id) {
        String username = req.getUserPrincipal().getName(); 
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!isUserAuthorized(user)) {
            return ResponseEntity.status(403).body("Your institute license has expired");
        }
        // Generate and return the stamp PDF
        byte[] pdf = formReportService.generateStamp(id);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "inline; filename=form-" + id + "-stamp.pdf")
                .body(pdf);
    }
    
    @GetMapping("/dashboard/forms-per-user")
    public ResponseEntity<?> getFormsPerUser() {
        List<Object[]> stats = befService.getFormCountPerUser();

        // Convert to a more friendly JSON
        List<Map<String, Object>> response = stats.stream().map(obj -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", obj[0] != null ? obj[0] : "Unknown"); // handle nulls
            map.put("formCount", obj[1] != null ? obj[1] : 0);   // handle nulls
            return map;
        }).toList();

        return ResponseEntity.ok(response);
    }

}
