package com.lxisoft.redalert.web.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lxisoft.redalert.service.dto.UserDomainDTO;
import com.lxisoft.redalert.web.rest.UserDomainResource;

@Controller
@RequestMapping("/redAlertUiProfile")
public class ProfileController {
	
	@Autowired
	UserDomainResource userDomainResource;
	

	@GetMapping("/profile")
	public String displayProfile(Model model)
	{
		UserDomainDTO userDomainDTO=userDomainResource.getUserDomain(Long.parseLong("1")).getBody();
		model.addAttribute("result",userDomainDTO);
		return "profile";
		
	}

}
