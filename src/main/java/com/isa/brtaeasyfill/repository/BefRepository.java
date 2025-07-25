package com.isa.brtaeasyfill.repository;
import com.isa.brtaeasyfill.model.BefModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository


public interface BefRepository  extends JpaRepository<BefModel, Long> {

}