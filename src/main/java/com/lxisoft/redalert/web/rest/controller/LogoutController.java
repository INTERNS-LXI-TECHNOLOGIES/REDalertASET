package com.lxisoft.redalert.web.rest.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/redAlertUiLogout")
public class LogoutController {
	@PostMapping("/logout")
	public String logout()
	{
		return "redirect:/redAlertUi/home";
	}

}
