package com.isa.brtaeasyfill.service;
import com.isa.brtaeasyfill.model.BefModel;
import java.util.List;

public interface BefService {
    BefModel saveForm(BefModel form);
    List<BefModel> getAllForms();
    BefModel getFormById(Long id);
    BefModel updateForm(Long id, BefModel form);
    void deleteForm(Long id);
}

