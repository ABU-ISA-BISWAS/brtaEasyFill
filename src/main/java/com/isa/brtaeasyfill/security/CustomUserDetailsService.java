package com.isa.brtaeasyfill.security;

import com.isa.brtaeasyfill.model.AppUser;
import com.isa.brtaeasyfill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        // Spring expects "ROLE_" prefix for roles
        String role = "ROLE_" + user.getRole();
        System.out.println("[DEBUG] User: " + username + ", Role from DB: " + user.getRole() + ", Granted Authority: " + role);

        return new User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}