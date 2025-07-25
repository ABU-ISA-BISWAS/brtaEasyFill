package com.isa.brtaeasyfill.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Institute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String allowedIp;
    private String licenseKey;

    @Temporal(TemporalType.DATE)
    private Date licenseExpiry;

    private String status; // ACTIVE, INACTIVE

    // --- GETTERS AND SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAllowedIp() { return allowedIp; }
    public void setAllowedIp(String allowedIp) { this.allowedIp = allowedIp; }

    public String getLicenseKey() { return licenseKey; }
    public void setLicenseKey(String licenseKey) { this.licenseKey = licenseKey; }

    public Date getLicenseExpiry() { return licenseExpiry; }
    public void setLicenseExpiry(Date licenseExpiry) { this.licenseExpiry = licenseExpiry; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}