package com.learnvest.nmlv.service;

import java.util.List;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.learnvest.nmlv.model.Institution;
import com.learnvest.nmlv.model.InstitutionDao;

@Service
public class InstitutionService {

	private final InstitutionDao institutionDao;

	public InstitutionService(InstitutionDao institutionDao) {
		this.institutionDao = institutionDao;
	}	

	public List<Institution> getInstitutions() {
		return institutionDao.findAll();
	}

	public Institution getInstitution(Long instId) {
		return institutionDao.findById(instId);

	}

	public Institution save(Institution inst) {
		return institutionDao.save(inst);
	}
	
}
