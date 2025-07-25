package com.isa.brtaeasyfill.model;


import jakarta.persistence.*;
import java.util.Date;

@Entity
public class LicenseRenewalLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @Temporal(TemporalType.DATE)
    private Date renewedOn;

    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @ManyToOne
    @JoinColumn(name = "renewed_by")
    private User renewedBy;

    // getters and setters
}
