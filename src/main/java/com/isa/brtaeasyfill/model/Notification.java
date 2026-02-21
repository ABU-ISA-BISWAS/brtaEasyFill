package com.isa.brtaeasyfill.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "read_status")
    private Boolean readStatus; // UNREAD, READ

 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Institute getInstitute() { return institute; }
    public void setInstitute(Institute institute) { this.institute = institute; }

    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public boolean getReadStatus() { return readStatus; }
    public void setReadStatus(boolean readStatus) { this.readStatus = readStatus; }
}