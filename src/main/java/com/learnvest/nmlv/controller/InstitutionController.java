package com.learnvest.nmlv.controller;

import com.learnvest.nmlv.model.Institution;
import com.learnvest.nmlv.service.InstitutionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InstitutionController {

  private final InstitutionService institutionService;

  @Autowired
  public InstitutionController(InstitutionService institutionService) {
    this.institutionService = institutionService;
  }

  @GetMapping("/institutions")
  @ResponseBody
  public List<Institution> getInstitutions() {
    return institutionService.getInstitutions();
  }
}
