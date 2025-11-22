package com.isa.brtaeasyfill.repository;

import com.isa.brtaeasyfill.model.LicenseRenewalLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LicenseRenewalLogRepository extends JpaRepository<LicenseRenewalLog, Long> {
    Optional<LicenseRenewalLog> findByLicenseKey(String licenseKey);
}