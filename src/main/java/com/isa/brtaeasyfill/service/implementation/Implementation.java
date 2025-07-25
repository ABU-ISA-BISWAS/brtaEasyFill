package com.isa.brtaeasyfill.service.implementation;
import com.isa.brtaeasyfill.model.BefModel;
import com.isa.brtaeasyfill.repository.BefRepository;
import com.isa.brtaeasyfill.service.BefService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Implementation implements BefService {
    private final BefRepository befRepository;

    public Implementation(BefRepository befRepository) {
        this.befRepository = befRepository;
    }

    @Override
    public BefModel saveForm(BefModel form) {
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
    public BefModel updateForm(Long id, BefModel form) {
        BefModel existing = getFormById(id);
        existing.setName(form.getName());
        existing.setAddress(form.getAddress());
        existing.setEmail(form.getEmail());
        existing.setPhone(form.getPhone());
        return befRepository.save(existing);
    }

    @Override
    public void deleteForm(Long id) {
        befRepository.deleteById(id);
    }

}
