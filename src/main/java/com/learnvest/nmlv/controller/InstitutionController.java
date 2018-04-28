package com.learnvest.nmlv.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import com.learnvest.nmlv.model.Institution;
import com.learnvest.nmlv.model.InstitutionDaoHelper;
import com.learnvest.nmlv.model.User;
import com.learnvest.nmlv.model.UserDao;
import com.learnvest.nmlv.service.InstitutionService;
import com.learnvest.nmlv.util.SearchCriteria;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class InstitutionController {

	@Autowired
	private UserDao users;
	@Autowired
	private InstitutionDaoHelper iHelper;
	private final InstitutionService institutionService;

	@Autowired
	public InstitutionController(InstitutionService institutionService) {
		this.institutionService = institutionService;
	}

	@GetMapping("/institutions/all")
	@ResponseBody
	public List<Institution> getInstitutions() {
		return institutionService.getInstitutions();
	}

	// Update active status of an Institution
	@PutMapping("/institutions/{id}")
	@ResponseBody
	public Institution updateInstitution(@PathVariable(value = "id") Long instId,
			@RequestParam(value = "isActive") int active, @RequestParam(value = "username") String username) {

		Optional<Institution> inst = Optional.of(institutionService.getInstitution(instId));
		inst.orElseThrow(() -> new IllegalArgumentException(String.format("Institution %s does not exist", instId) ));

		Optional<User> user = Optional.of(users.findByuserid(username)).filter(u -> u.isAdmin());
		user.orElseThrow(() -> new IllegalArgumentException(String.format("User %s not allowed to change status", username) ));		

		return institutionService.save(inst.get());

	}
	
    @RequestMapping(method = RequestMethod.GET, value = "/institutions")
    @ResponseBody
    public List<Institution> findAll(@RequestParam(value = "search", required = false) String search) {
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|==|~)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), 
                  matcher.group(2), matcher.group(3)));
            }
        }
        if (params.get(0).getValue().toString().length() < 3) throw new IllegalArgumentException("Search criteria must be atleast 3 characters long");
        return iHelper.searchInstitution(params);
    }

}
