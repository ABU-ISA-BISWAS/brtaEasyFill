package com.isa.brtaeasyfill.controller;

import com.isa.brtaeasyfill.model.BefModel;
import com.isa.brtaeasyfill.service.BefService;
import com.isa.brtaeasyfill.service.FormReportService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forms")
@CrossOrigin(origins = "*")
public class BefController {

    private final BefService befService;
    private final FormReportService formReportService;

    public BefController(BefService befService, FormReportService formReportService) {
        this.befService = befService;
        this.formReportService = formReportService;
    }

    // ---------------- CRUD ----------------
    @GetMapping
    public ResponseEntity<List<BefModel>> getAllForms() {
        return ResponseEntity.ok(befService.getAllForms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BefModel> getForm(@PathVariable Long id) {
        return ResponseEntity.ok(befService.getFormById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BefModel> updateForm(@PathVariable Long id, @RequestBody BefModel form) {
        return ResponseEntity.ok(befService.updateForm(id, form));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<BefModel> saveForm(@RequestBody BefModel form) {
        BefModel saved = befService.saveForm(form);

        // Auto-generate both A4 (4 pages) and Stamp (6 pages)
        try {
            formReportService.generateA4(saved.getId());
            formReportService.generateStamp(saved.getId());
        } catch (Exception ex) {
            System.err.println("[WARN] PDF pre-generation failed: " + ex.getMessage());
        }

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // ---------------- PDF ENDPOINTS ----------------
    @GetMapping("/{id}/pdf/a4")
    public ResponseEntity<byte[]> getA4Pdf(@PathVariable Long id) {
        byte[] pdf = formReportService.generateA4(id);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "inline; filename=form-" + id + "-a4.pdf")
                .body(pdf);
    }

    @GetMapping("/{id}/pdf/stamp")
    public ResponseEntity<byte[]> getStampPdf(@PathVariable Long id) {
        byte[] pdf = formReportService.generateStamp(id);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "inline; filename=form-" + id + "-stamp.pdf")
                .body(pdf);
    }
}
