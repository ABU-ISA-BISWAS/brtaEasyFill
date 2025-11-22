package com.isa.brtaeasyfill.repository;
import com.isa.brtaeasyfill.model.BefModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository


public interface BefRepository  extends JpaRepository<BefModel, Long> {
	
	@Query("SELECT b.createdByName, COUNT(b) FROM BefModel b GROUP BY b.createdByName")


	    List<Object[]> countFormsPerUser();

}