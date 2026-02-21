package com.isa.brtaeasyfill.model;

import jakarta.persistence.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "institute")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Institute {

    @Id
    private Long id;
    private String name;
    private String licenseKey;
    @Temporal(TemporalType.TIMESTAMP)
    private Date licenseExpiry;
    private String status; // ACTIVE, INACTIVE

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLicenseKey() { return licenseKey; }
    public void setLicenseKey(String licenseKey) { this.licenseKey = licenseKey; }

    public Date getLicenseExpiry() { return licenseExpiry; }
    public void setLicenseExpiry(Date licenseExpiry) { this.licenseExpiry = licenseExpiry; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
