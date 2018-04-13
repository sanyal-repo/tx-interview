package com.learnvest.nmlv.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InstitutionControllerTest {

  @Autowired
  private InstitutionController institutionController;

  @Test
  public void getInstitutionsTest() {
    Assert.assertEquals(13065, institutionController.getInstitutions().size());
  }
}