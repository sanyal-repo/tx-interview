package com.learnvest.nmlv.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface InstitutionDao extends CrudRepository<Institution, Long> {

  List<Institution> findAll();
  Institution findById(Long id);
}
