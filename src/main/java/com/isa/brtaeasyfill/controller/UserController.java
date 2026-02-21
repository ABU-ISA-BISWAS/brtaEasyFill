package com.isa.brtaeasyfill.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isa.brtaeasyfill.model.AppUser;
import com.isa.brtaeasyfill.model.Institute;
import com.isa.brtaeasyfill.repository.InstituteRepository;
import com.isa.brtaeasyfill.repository.UserRepository;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private InstituteRepository instituteRepo;

    // ================= Create User =================
    @PostMapping("/create")
    public AppUser createUser(@RequestBody UserRequest userRequest) {
        if (userRepo.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(userRequest.getUsername());
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword()); // encode in production!
        user.setRole(userRequest.getRole());

        Institute institute = instituteRepo.findById(userRequest.getInstituteId())
                .orElseThrow(() -> new RuntimeException("Institute not found"));

        user.setInstitute(institute);
        return userRepo.save(user);
    }


    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request,
                                                 Authentication auth) {

        String username = auth.getName(); // logged-in user
        
        Optional<AppUser> optionalUser = userRepo.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.ok("User not found");
        }

        AppUser user = optionalUser.get();


        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("User not found");
        }

        if (!user.getPassword().equals(request.getCurrentPassword())) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Current password is incorrect");
        }

        user.setPassword(request.getNewPassword()); // encode in production!
        userRepo.save(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Password successfully changed!");
    }
    
    
    @PostMapping("/change-name")
    public ResponseEntity<String> changeName(@RequestBody ChangeNameRequest request, Authentication auth) {

        String username = auth.getName(); // logged-in user
        
        Optional<AppUser> optionalUser = userRepo.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.ok("User not found");
        }

        AppUser user = optionalUser.get();


        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("User not found");
        }

        user.setName(request.getName());
        userRepo.save(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Name successfully changed!");
    }




    public static class ChangePasswordRequest {
        private String username;
        private String currentPassword;
        private String newPassword;

        public String getUsername() { return username; }
        public String getCurrentPassword() { return currentPassword; }
        public String getNewPassword() { return newPassword; }

        public void setUsername(String username) { this.username = username; }
        public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
    
    public static class ChangeNameRequest {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

    }



 // ================= Get Users by Institute =================
    @GetMapping
    public List<AppUser> getUsersByInstitute(@RequestParam Long instituteId) {
        Institute institute = instituteRepo.findById(instituteId)
                .orElseThrow(() -> new RuntimeException("Institute not found"));
        return userRepo.findByInstitute(institute);
    }


    // ================= DTO Classes =================
    public static class UserRequest {
        private String username;
        private String name;
        private String password;
        private String role;
        private Long instituteId;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public Long getInstituteId() { return instituteId; }
        public void setInstituteId(Long instituteId) { this.instituteId = instituteId; }
    }

    public static class ResetPasswordRequest {
        private String token;
        private String newPassword;

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }

        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
}
