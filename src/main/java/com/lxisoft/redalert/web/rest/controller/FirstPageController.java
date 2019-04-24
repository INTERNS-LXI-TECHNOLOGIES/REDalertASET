package com.lxisoft.redalert.web.rest.controller;

import java.net.URISyntaxException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstPageController {

	@GetMapping("/")
	public String redirectProfilePage( Model model) throws URISyntaxException {
	
		
		return "redirect:/redAlertUiProfile/profile";
	}
	
}
