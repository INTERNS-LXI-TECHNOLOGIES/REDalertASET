package com.lxisoft.redalert.web.rest.controller;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lxisoft.redalert.service.dto.UserDomainDTO;
import com.lxisoft.redalert.service.dto.ContactDTO;
import com.lxisoft.redalert.web.rest.ContactResource;
import com.lxisoft.redalert.web.rest.UserDomainResource;




@Controller
@RequestMapping("/redAlertUiRegistration")
public class userRegistrationController {
	
	@Autowired
	UserDomainResource userDomainResource;

	@Autowired
	ContactResource contactResource;
	
	
	
	
	@GetMapping("/register")
	public String signUp(Model model) throws URISyntaxException
	{	 
		UserDomainDTO userDomainDTO= new UserDomainDTO();
		 
		
		model.addAttribute("user",userDomainDTO);
		
		
		
		return "userRegistration";
	}
	@PostMapping("save-contact")
	public String getUserDetails(@ModelAttribute UserDomainDTO user,@ModelAttribute ContactDTO contactDTO2,@ModelAttribute ContactDTO contactDTO3,@ModelAttribute ContactDTO contactDTO4,@ModelAttribute ContactDTO contactDTO5) throws URISyntaxException
	{
		
	
		userDomainResource.createUserDomain(user);
		
		
		return "home";
	}
	
	

}
