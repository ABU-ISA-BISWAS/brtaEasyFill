package com.isa.brtaeasyfill.model;


import jakarta.persistence.*;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role; // SUPER_ADMIN, INSTITUTE_ADMIN, USER

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    private String status; // ACTIVE or INACTIVE

    // getters and setters
}
