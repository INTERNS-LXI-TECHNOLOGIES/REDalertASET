package com.lxisoft.redalert.web.rest.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lxisoft.redalert.domain.UserDomain;
import com.lxisoft.redalert.repository.ContactRepository;
import com.lxisoft.redalert.repository.UserDomainRepository;
import com.lxisoft.redalert.security.SecurityUtils;
import com.lxisoft.redalert.service.dto.ContactDTO;
import com.lxisoft.redalert.service.mapper.ContactMapper;

@Controller
@RequestMapping("/redAlertUiProfile")
public class ProfileController {
	
	@Autowired
	UserDomainRepository userDomainRepository;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	ContactMapper contactMapper;

	@GetMapping("/profile")
	public String displayProfile(Model model)
	{
		Optional<UserDomain> userDomainDTO=userDomainRepository.findByEmail( SecurityUtils.getCurrentUserLogin().get());
		model.addAttribute("result",userDomainDTO.get());
		
		
		List<ContactDTO> contacts=contactRepository.findAllByUsers(userDomainDTO.get().getId()).stream().map(contactMapper::toDto).collect(Collectors.toList());
		model.addAttribute("contacts",contacts);
		return "profile";
		
		
		
		
	}

}
