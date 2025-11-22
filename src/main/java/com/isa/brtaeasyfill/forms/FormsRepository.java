//package com.isa.brtaeasyfill.forms;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import com.isa.brtaeasyfill.commonFunction.BaseRepository;
//import com.isa.brtaeasyfill.commonFunction.CommonFunction;
//import com.isa.brtaeasyfill.commonFunction.Response;
//import com.isa.brtaeasyfill.model.AppUser;
//import com.isa.brtaeasyfill.repository.UserRepository;
//import com.isa.brtaeasyfill.service.FormReportService;
//
//@Repository
//@Transactional
//public class FormsRepository implements CommonFunction {
//
//    @Autowired
//    private BaseRepository baseRepository;
//
//    private final UserRepository appUserRepository;
//    private final FormReportService formReportService;
//
//    @Autowired
//    public FormsRepository(UserRepository appUserRepository, FormReportService formReportService) {
//        this.formReportService = formReportService;
//        this.appUserRepository = appUserRepository;
//    }
//
//    private boolean isUserAuthorized(AppUser user) {
//        if (!"SUPERADMIN".equalsIgnoreCase(user.getRole())) {
//            return user.getInstitute() != null && "active".equalsIgnoreCase(user.getInstitute().getStatus());
//        }
//        return true;
//    }
//
//    public Response save(String reqObj) {
//        try {
//            // ✅ Authenticated user name from SecurityContext
//            String username = SecurityContextHolder.getContext().getAuthentication().getName();
//            AppUser user = appUserRepository.findByUsername(username);
//
//            if (user == null) {
//                return getErrorResponse("User not found!");
//            }
//
//            if (!isUserAuthorized(user)) {
//                return getErrorResponse("Your institute license has expired");
//            }
//
//            FormsEntity formsEntity = objectMapperReadValue(reqObj, FormsEntity.class);
//            if (formsEntity == null) {
//                return getErrorResponse("Invalid request data!");
//            }
//
//            Response response = baseRepository.baseOnlySave(formsEntity);
//
//            try {
//                formReportService.generateA4(formsEntity.getId());
//                formReportService.generateStamp(formsEntity.getId());
//            } catch (Exception e) {
//                System.err.println("[WARN] PDF pre-generation failed: " + e.getMessage());
//            }
//
//            return response;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return getErrorResponse("Save failed: " + e.getMessage());
//        }
//    }
//}
