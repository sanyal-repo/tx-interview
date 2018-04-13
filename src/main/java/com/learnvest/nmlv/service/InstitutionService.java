package com.learnvest.nmlv.service;

import com.learnvest.nmlv.model.Institution;
import com.learnvest.nmlv.model.InstitutionDao;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InstitutionService {

  private final InstitutionDao institutionDao;

  public InstitutionService(InstitutionDao institutionDao) {
    this.institutionDao = institutionDao;
  }

  public List<Institution> getInstitutions() {
    return institutionDao.findAll();
  }
}
