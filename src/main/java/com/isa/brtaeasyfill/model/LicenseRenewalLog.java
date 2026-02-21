package com.isa.brtaeasyfill.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "license_renewal_log")
public class LicenseRenewalLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "institute_id", nullable = false)
    private Institute institute;

//    @Temporal(TemporalType.DATE)
@Temporal(TemporalType.TIMESTAMP)
    private Date renewedOn;

//    @Temporal(TemporalType.DATE)
@Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    @Column(unique = true, nullable = false)
    private String licenseKey;

    private boolean used = false;

    @ManyToOne
    @JoinColumn(name = "used_for_institute_id")
    private Institute usedFor;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Institute getInstitute() { return institute; }
    public void setInstitute(Institute institute) { this.institute = institute; }

    public Date getRenewedOn() { return renewedOn; }
    public void setRenewedOn(Date renewedOn) { this.renewedOn = renewedOn; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    public String getLicenseKey() { return licenseKey; }
    public void setLicenseKey(String licenseKey) { this.licenseKey = licenseKey; }

    public boolean isUsed() { return used; }
    public void setUsed(boolean used) { this.used = used; }

    public Institute getUsedFor() { return usedFor; }
    public void setUsedFor(Institute usedFor) { this.usedFor = usedFor; }
}
