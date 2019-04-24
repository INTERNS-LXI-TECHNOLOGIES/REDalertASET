package com.lxisoft.redalert.web.rest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lxisoft.redalert.domain.UserDomain;
import com.lxisoft.redalert.repository.UserDomainRepository;
import com.lxisoft.redalert.security.SecurityUtils;
import com.lxisoft.redalert.service.dto.UserDomainDTO;
import com.lxisoft.redalert.web.rest.UserDomainResource;

@Controller
@RequestMapping("/redAlertUiProfile")
public class ProfileController {
	
	@Autowired
	UserDomainRepository userDomainRepository;
	

	@GetMapping("/profile")
	public String displayProfile(Model model)
	{
		Optional<UserDomain> userDomainDTO=userDomainRepository.findByEmail( SecurityUtils.getCurrentUserLogin().get());
		model.addAttribute("result",userDomainDTO.get());
		return "profile";
		
	}

}
