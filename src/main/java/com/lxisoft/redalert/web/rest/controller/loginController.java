package com.lxisoft.redalert.web.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/redAlertUiLogin")
public class loginController {
	
	@GetMapping("/login")
public String login()
{
	return "login";
		}
	
	@GetMapping("/register")
	public String makeRegistration()
	{
		return "userRegistration";
			}
	
}
