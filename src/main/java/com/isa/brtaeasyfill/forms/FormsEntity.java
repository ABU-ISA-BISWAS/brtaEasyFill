//package com.isa.brtaeasyfill.forms;
//import com.isa.brtaeasyfill.model.AppUser;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.SequenceGenerator;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//@Getter
//@Setter
//@Entity
//@Table(name = "FORMS_DATA")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//
//public class FormsEntity {
//	
//	@Id
//
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "form_seq_gen")
//    @SequenceGenerator(name = "form_seq_gen", sequenceName = "FORM_SEQ", allocationSize = 1)
//    private Long id;
//
//    private String name;
//    private String address;
//    private String email;
//    private String phone;
//    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "created_by")
//    private AppUser createdBy;
//
//
//
//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//
//}
