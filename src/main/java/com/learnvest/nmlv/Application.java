package com.learnvest.nmlv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.learnvest.nmlv.model.InstitutionDao;
import com.learnvest.nmlv.model.UserDao;

@SpringBootApplication
public class Application implements CommandLineRunner  {
  @Autowired
  InstitutionDao repository;
  @Autowired
  UserDao users;
  
  private Logger logger = LoggerFactory.getLogger(this.getClass());
  public static void main(String args[]) {
    SpringApplication.run(Application.class);
  }
  @Override
  public void run(String... args) throws Exception {

		logger.info("Institution id 38 -> {}", repository.findById(38L).getName());
		logger.info("User permission --> {}", users.findById(5L).isAdmin());
  }
}
