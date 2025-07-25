package com.isa.brtaeasyfill.repository;


import com.isa.brtaeasyfill.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}