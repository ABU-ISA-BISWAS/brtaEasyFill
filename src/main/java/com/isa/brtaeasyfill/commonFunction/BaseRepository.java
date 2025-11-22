package com.isa.brtaeasyfill.commonFunction;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class BaseRepository implements CommonFunction {

@PersistenceContext
private EntityManager entityManager;


public  Response baseOnlySave(Object obj) {
		Response response = new Response();
		try {
			entityManager.persist(obj);
			response.setObj(obj);
			return getSuccessResponse("Saved Successfully", response);
		} catch (Exception e) {
			e.printStackTrace();
			return getErrorResponse("Save fail !!");
		}

	}



}

