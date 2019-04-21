package com.lxisoft.redalert.web.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lxisoft.redalert.service.dto.UserDomainDTO;
import com.lxisoft.redalert.web.rest.UserDomainResource;

@Controller
@RequestMapping("/redAlertUiRegistration")
public class RegistrationController {
	
	@Autowired
	UserDomainResource userDomainResource;
	

	@GetMapping("/registration")
	public String registrationPage(Model model)
	{
		
		return "userRegistration";
		
	}

}
