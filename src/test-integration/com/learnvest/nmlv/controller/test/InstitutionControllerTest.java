package com.learnvest.nmlv.controller.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.learnvest.nmlv.controller.InstitutionController;
import com.learnvest.nmlv.util.SearchCriteria;
import com.learnvest.nmlv.model.Institution;
import com.learnvest.nmlv.model.InstitutionDaoHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InstitutionControllerTest {

  @Autowired
  private InstitutionController institutionController;
  
  @Autowired
  private InstitutionDaoHelper iHelper;

  @Test
  public void getInstitutionsTest() {
    Assert.assertEquals(13065, institutionController.getInstitutions().size());
  }
  
  //@Test
  public void saveInstitutionTest() {
	  
	  Assert.assertEquals(false, institutionController.updateInstitution(Long.valueOf(7L), 0, "admin1").getIsActive());
  }
  
  //@Test
  public void givenExactName_whenGettingListOfInst_thenCorrect() {
      final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
      params.add(new SearchCriteria("name", "==", "MCI"));
      
      final List<Institution> results = iHelper.searchInstitution(params);
      
      Assert.assertEquals(2, results.size()); 
      Assert.assertEquals("MCI", results.get(0).getName()); 

  }
  
  //@Test
  public void givenPartialName_whenGettingListOfInst_thenCorrect() {
      final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
      params.add(new SearchCriteria("name", ":", "mc"));
      

      final List<Institution> results = iHelper.searchInstitution(params);
      
      results.stream().forEach(i -> System.out.println("Matching inst: " + i.getName()));
      
      Assert.assertTrue(results.size() < 11); 

  }  
}
