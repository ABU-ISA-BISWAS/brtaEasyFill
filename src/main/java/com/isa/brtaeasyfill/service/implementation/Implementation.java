package com.isa.brtaeasyfill.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isa.brtaeasyfill.model.AppUser;
import com.isa.brtaeasyfill.model.BefModel;
import com.isa.brtaeasyfill.repository.BefRepository;
import com.isa.brtaeasyfill.repository.UserRepository;
import com.isa.brtaeasyfill.service.BefService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class Implementation implements BefService {
    private final BefRepository befRepository;
    private final UserRepository userRepository;
    private final HttpServletRequest request;

    public Implementation(BefRepository befRepository, UserRepository userRepository, HttpServletRequest request) {
        this.befRepository = befRepository;
        this.userRepository = userRepository;
        this.request = request;
    }

    @Override
    public BefModel saveForm(BefModel form) {
        // Get logged-in user from request
        String username = request.getUserPrincipal().getName();

        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user == null) {
            throw new RuntimeException("User not found for username: " + username);
        }

        // Attach createdBy before saving
        form.setCreatedByName(user.getName());

        return befRepository.save(form);
    }

    @Override
    public List<BefModel> getAllForms() {
        return befRepository.findAll();
    }

    @Override
    public BefModel getFormById(Long id) {
        return befRepository.findById(id).orElseThrow(() -> new RuntimeException("Form not found"));
    }

    @Override
    @Transactional
    public BefModel updateForm(Long id, BefModel form) {
        BefModel existing = getFormById(id);
        
        String username = request.getUserPrincipal().getName();

        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existing.setCreatedByName(user.getName());

        // Vehicle info
        existing.setRegAuth(form.getRegAuth());
        existing.setViType(form.getViType());
        existing.setViRegNum(form.getViRegNum());
        existing.setViChassisNo(form.getViChassisNo());
        existing.setViEnginNo(form.getViEnginNo());
        existing.setViManufacturer(form.getViManufacturer());
        existing.setViRegYear(form.getViRegYear());
        existing.setViModel(form.getViModel());
        existing.setViCC(form.getViCC());
        existing.setViPrevRegNo(form.getViPrevRegNo());
        existing.setViSellPrice(form.getViSellPrice());
        existing.setViBankName(form.getViBankName());

        // Seller info
        existing.setSeName(form.getSeName());
        existing.setSeFatherName(form.getSeFatherName());
        existing.setSeMotherName(form.getSeMotherName());
        existing.setSeNID(form.getSeNID());
        existing.setSeTIN(form.getSeTIN());
        existing.setSePermenentAdd(form.getSePermenentAdd());
        existing.setSePresentAdd(form.getSePresentAdd());
        existing.setSeNationality(form.getSeNationality());
        existing.setSeRelegion(form.getSeRelegion());
        existing.setSeProfession(form.getSeProfession());
        existing.setSePhone(form.getSePhone());
        existing.setSeSpouse(form.getSeSpouse());

        // Buyer info
        existing.setBuName(form.getBuName());
        existing.setBuFatherName(form.getBuFatherName());
        existing.setBuMotherName(form.getBuMotherName());
        existing.setBuSpouse(form.getBuSpouse());
        existing.setBuGuardianName(form.getBuGuardianName());
        existing.setBuGender(form.getBuGender());
        existing.setBuDoB(form.getBuDoB());
        existing.setBuNID(form.getBuNID());
        existing.setBuTIN(form.getBuTIN());
        existing.setBuPermenentAdd(form.getBuPermenentAdd());
        existing.setBuPresentAdd(form.getBuPresentAdd());
        existing.setBuNationality(form.getBuNationality());
        existing.setBuRelegion(form.getBuRelegion());
        existing.setBuProfession(form.getBuProfession());
        existing.setBuPhone(form.getBuPhone());

        return befRepository.save(existing);
    }

    
    @Override
    public List<Object[]> getFormCountPerUser() {
        return befRepository.countFormsPerUser();
    }
}
