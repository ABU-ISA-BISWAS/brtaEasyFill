package com.isa.brtaeasyfill.model;
import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Entity
@Table(name = "FORMS_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class BefModel {
    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "form_seq_gen")
    @SequenceGenerator(name = "form_seq_gen", sequenceName = "FORM_SEQ", allocationSize = 1)
    private Long id;

    private String name;
    private String address;
    private String email;
    private String phone;



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
