package com.lxisoft.redalert.web.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/redAlertUiIndex")
public class indexController {
	

	@GetMapping("/index")
public String goToIndex()
{
	return "index";
		}

}
