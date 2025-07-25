package com.isa.brtaeasyfill.service;

import com.isa.brtaeasyfill.model.BefModel;
import com.isa.brtaeasyfill.repository.BefRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class FormReportService {

    private static final Logger log = LoggerFactory.getLogger(FormReportService.class);

    private final BefRepository formRepository;

    public FormReportService(BefRepository formRepository) {
        this.formRepository = formRepository;
    }

    public byte[] generatePdf(Long formId, ReportSize size) {
        var form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found: " + formId));

        String jrxml = (size == ReportSize.A4)
                ? "reports/form_a4.jrxml"
                : "reports/form_legal.jrxml";

        // --- DEBUG resource existence ---
        ClassPathResource res = new ClassPathResource(jrxml);
        log.info("Loading JRXML: {} exists? {}", jrxml, res.exists());

        try (InputStream is = res.getInputStream()) {
            JasperReport jasperReport = JasperCompileManager.compileReport(is);

            Map<String, Object> params = new HashMap<>();
            JRBeanCollectionDataSource dataSource =
                    new JRBeanCollectionDataSource(Collections.singletonList(form));

            JasperPrint print = JasperFillManager.fillReport(jasperReport, params, dataSource);
            return JasperExportManager.exportReportToPdf(print);

        } catch (Exception e) {
            log.error("PDF generation failed [{}] formId={}", size, formId, e);
            throw new RuntimeException("Error generating " + size + " PDF for Form " + formId, e);
        }
    }

    public byte[] generateA4(Long formId) {
        return generatePdf(formId, ReportSize.A4);
    }

    public byte[] generateStamp(Long formId) {
        return generatePdf(formId, ReportSize.LEGAL);
    }

    public enum ReportSize {
        A4,
        LEGAL
    }
}
