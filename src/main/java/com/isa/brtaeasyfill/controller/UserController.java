//
//package com.isa.brtaeasyfill.controller;
//
//import com.isa.brtaeasyfill.model.AppUser;
//import com.isa.brtaeasyfill.model.Institute;
//import com.isa.brtaeasyfill.repository.UserRepository;
//import com.isa.brtaeasyfill.repository.InstituteRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/users")
//@CrossOrigin(origins = "http://localhost:4200")
//public class UserController {
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @Autowired
//    private InstituteRepository instituteRepo;
//
//    @PostMapping("/create")
//    public AppUser createUser(@RequestBody UserRequest userRequest) {
//        if (userRepo.existsByUsername(userRequest.getUsername())) {
//            throw new RuntimeException("Username already exists");
//        }
//
//        AppUser user = new AppUser();
//        user.setUsername(userRequest.getUsername());
//        user.setName(userRequest.getName());
//        user.setPassword(userRequest.getPassword()); // You should encode this in production!
//        user.setRole(userRequest.getRole());
//
//        Institute institute = instituteRepo.findById(userRequest.getInstituteId())
//                .orElseThrow(() -> new RuntimeException("Institute not found"));
//
//        user.setInstitute(institute);
//        return userRepo.save(user);
//    }
//
//    // DTO class
//    public static class UserRequest {
//        private String username;
//        private String name;
//        private String password;
//        private String role;
//        private Long instituteId;
//
//        // Getters and setters
//        public String getUsername() { return username; }
//        public void setUsername(String username) { this.username = username; }
//
//        public String getName() { return name; }
//        public void setName(String name) { this.name = name; }
//
//        public String getPassword() { return password; }
//        public void setPassword(String password) { this.password = password; }
//
//        public String getRole() { return role; }
//        public void setRole(String role) { this.role = role; }
//
//        public Long getInstituteId() { return instituteId; }
//        public void setInstituteId(Long instituteId) { this.instituteId = instituteId; }
//    }
//}


package com.isa.brtaeasyfill.controller;

import com.isa.brtaeasyfill.model.AppUser;
import com.isa.brtaeasyfill.model.Institute;
import com.isa.brtaeasyfill.repository.UserRepository;
import com.isa.brtaeasyfill.repository.InstituteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
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

    // ================= Reset Password =================
    @PostMapping("/change-password")
    public String changePassword(@RequestBody ChangePasswordRequest request, Authentication auth) {
        String username = auth.getName(); // logged-in user

        AppUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getCurrentPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(request.getNewPassword()); // encode in production!
        userRepo.save(user);

        return "Password successfully changed!";
    }




    public static class ChangePasswordRequest {
        private String username;
        private String currentPassword;
        private String newPassword;

        // getters
        public String getUsername() { return username; }
        public String getCurrentPassword() { return currentPassword; }
        public String getNewPassword() { return newPassword; }

        // setters
        public void setUsername(String username) { this.username = username; }
        public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
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

        // getters & setters
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
