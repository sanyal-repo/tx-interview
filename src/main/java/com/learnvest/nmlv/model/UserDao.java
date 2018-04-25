package com.learnvest.nmlv.model;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface UserDao extends CrudRepository<User, Long> {

  User findByuserid(String userId);
  User findById(Long id);
  
}

