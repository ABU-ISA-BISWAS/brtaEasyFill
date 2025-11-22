//package com.isa.brtaeasyfill.model;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "app_user")
//public class AppUser {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String username;
//    private String name;
//    private String password;
//    private String role;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "institute_id")
//    private Institute institute;
//    
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getUsername() { return username; }
//    public void setUsername(String username) { this.username = username; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//
//    public String getRole() { return role; }
//    public void setRole(String role) { this.role = role; }
//
//    public Institute getInstitute() {
//        return institute;
//    }
//    public void setInstitute(Institute institute) {
//        this.institute = institute;
//    }
//}


package com.isa.brtaeasyfill.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String password;
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id")
    private Institute institute;

    // ================= Password Reset Fields =================
//    private String resetToken;
//    private LocalDateTime resetTokenExpiry;

    // ================= Getters and Setters =================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Institute getInstitute() { return institute; }
    public void setInstitute(Institute institute) { this.institute = institute; }

//    public String getResetToken() { return resetToken; }
//    public void setResetToken(String resetToken) { this.resetToken = resetToken; }
//
//    public LocalDateTime getResetTokenExpiry() { return resetTokenExpiry; }
//    public void setResetTokenExpiry(LocalDateTime resetTokenExpiry) { this.resetTokenExpiry = resetTokenExpiry; }
}
