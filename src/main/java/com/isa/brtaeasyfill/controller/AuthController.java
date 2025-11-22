package com.isa.brtaeasyfill.controller;

import com.isa.brtaeasyfill.model.AppUser;
import com.isa.brtaeasyfill.repository.UserRepository;
import com.isa.brtaeasyfill.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UserRepository appUserRepository;
    private final JwtUtil jwtUtil;
    @Autowired
    private HttpServletRequest req;

    @Autowired
    public AuthController(UserRepository appUserRepository, JwtUtil jwtUtil) {
        this.appUserRepository = appUserRepository;
        this.jwtUtil = jwtUtil;
    }
    
    private boolean isUserAuthorized(AppUser user) {
        if (!"SUPERADMIN".equalsIgnoreCase(user.getRole())) {
            return user.getInstitute() != null && "active".equalsIgnoreCase(user.getInstitute().getStatus());
        }
        return true;
    }
    
    @GetMapping("/check-institute")
    public ResponseEntity<?> checkInstitute() {

        String username = req.getUserPrincipal().getName();
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean active = isUserAuthorized(user);

        Map<String, Object> response = new HashMap<>();
        response.put("active", active);

        if (!active) {
            return ResponseEntity.status(403).body(response);
        }

        return ResponseEntity.ok(response);
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        if (req == null || req.getUsername() == null || req.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username and password must be provided");
        }

        AppUser user = appUserRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (user == null || user.getPassword() == null || !user.getPassword().equals(req.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        // Check institute status unless SUPERADMIN
        if (!"SUPERADMIN".equalsIgnoreCase(user.getRole())) {
            if (user.getInstitute() == null ||
                    !"active".equalsIgnoreCase(user.getInstitute().getStatus())) {
                return ResponseEntity.status(403).body("Your institute license has expired");
            }
        }

        // 🔐 Generate token
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        // 🔒 Remove password before sending user data
        user.setPassword(null);

        // ✅ Create response
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("instituteId", user.getInstitute() != null ? user.getInstitute().getId() : null);
        response.put("user", user);  // Full user info for frontend use

        return ResponseEntity.ok(response);
    }



    // ✅ NEW: Refresh Token API
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        String oldToken = authHeader.substring(7);

        try {
            if (jwtUtil.isTokenExpired(oldToken)) {
                return ResponseEntity.status(401).body("Token expired. Please log in again.");
            }

            String username = jwtUtil.extractUsername(oldToken);
            String role = jwtUtil.extractRole(oldToken);

            // 🔁 Issue a new token (with fresh 2h expiry)
            String newToken = jwtUtil.generateToken(username, role);

            return ResponseEntity.ok(Map.of("token", newToken));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(401).body("Token expired. Please log in again.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid token");
        }
    }

    // Auth request body
    public static class AuthRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // Auth response
    public static class AuthResponse {
        private String token;
        private Long instituteId;

        public AuthResponse(String token, Long instituteId) {
            this.token = token;
            this.instituteId = instituteId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Long getInstituteId() {
            return instituteId;
        }

        public void setInstituteId(Long instituteId) {
            this.instituteId = instituteId;
        }
    }
}
