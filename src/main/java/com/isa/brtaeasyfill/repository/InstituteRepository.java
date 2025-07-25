package com.isa.brtaeasyfill.repository;



import com.isa.brtaeasyfill.model.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InstituteRepository extends JpaRepository<Institute, Long> {
    Optional<Institute> findByName(String name);
    Optional<Institute> findByLicenseKey(String licenseKey);
}