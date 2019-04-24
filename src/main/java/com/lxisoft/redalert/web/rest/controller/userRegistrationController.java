package com.lxisoft.redalert.web.rest.controller;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lxisoft.redalert.service.dto.UserDomainDTO;
import com.lxisoft.redalert.service.dto.ContactDTO;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lxisoft.redalert.service.dto.ContactDTO;
import com.lxisoft.redalert.service.dto.UserBinderDTO;
import com.lxisoft.redalert.service.dto.UserDomainDTO;
import com.lxisoft.redalert.web.rest.ContactResource;
import com.lxisoft.redalert.web.rest.UserDomainResource;




@Controller
@RequestMapping("/registration")
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
	public String getUserDetails(Model model,@ModelAttribute UserDomainDTO user,@ModelAttribute ContactDTO contactDTO2,@ModelAttribute ContactDTO contactDTO3,@ModelAttribute ContactDTO contactDTO4,@ModelAttribute ContactDTO contactDTO5) throws URISyntaxException
	{
		
	
		userDomainResource.createUserDomain(user);
		
		

		//userDomainDTO.setContacts(new HashSet<ContactDTO>());
		List<ContactDTO> contacts= new ArrayList<ContactDTO>();
		contacts.add(new ContactDTO());
		contacts.add(new ContactDTO());
		contacts.add(new ContactDTO());
		contacts.add(new ContactDTO());
		contacts.add(new ContactDTO());
		
		UserBinderDTO userData= new UserBinderDTO();
		UserDomainDTO userDomainDTO=new UserDomainDTO();
		//UserDomainDTO userDomainDTO;
		userData.setUser(userDomainDTO);
		userData.setContacts(contacts);
		model.addAttribute("userData",userData);		
		return "userRegistration";
	}
	@PostMapping("save-contact")
	public String getUserDetails(@ModelAttribute UserBinderDTO userBinder) throws URISyntaxException
	{
		
		if(userBinder!=null) {
			userBinder.getUser().setContacts(new HashSet<ContactDTO>());
			//System.out.println(userBinder);
			for(ContactDTO u: userBinder.getContacts())
			{
				//System.out.println(u);
				userBinder.getUser().getContacts().add(contactResource.createContact(u).getBody());
			}
			userDomainResource.createUserDomain(userBinder.getUser());
		
		}
		
		return "redirect:/redAlertUiLogin/login";
	}
	
	

}
