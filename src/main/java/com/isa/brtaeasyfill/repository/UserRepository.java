
package com.isa.brtaeasyfill.repository;

import com.isa.brtaeasyfill.model.AppUser;
import com.isa.brtaeasyfill.model.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;


public interface UserRepository extends JpaRepository<AppUser, Long> {
    List<AppUser> findByInstitute(Institute institute);

    Optional<AppUser> findByUsername(String username); // ✅ return Optional
    
    AppUser findTopByInstituteId(Long instituteId);
    boolean existsByUsername(String username);
    

}

