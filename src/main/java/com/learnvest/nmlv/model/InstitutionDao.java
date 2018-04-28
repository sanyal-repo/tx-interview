package com.learnvest.nmlv.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Transactional
public interface InstitutionDao extends CrudRepository<Institution, Long> {

  @Query(value = "SELECT * from Institution i where i.is_active = 1 LIMIT 0, 11", nativeQuery=true)	  
  List<Institution> findAll();
  @Query(value = "select * from Institution i where i.is_active = 1 and i.id = :id", nativeQuery=true)
  Institution findById(@Param("id") Long id);

}
