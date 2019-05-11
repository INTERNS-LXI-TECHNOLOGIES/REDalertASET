package com.lxisoft.redalert.web.rest.controller;



import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lxisoft.redalert.domain.enumeration.AlertType;
import com.lxisoft.redalert.service.dto.AlertDTO;
import com.lxisoft.redalert.service.dto.LocationDTO;
import com.lxisoft.redalert.service.impl.SMSService;
import com.lxisoft.redalert.web.rest.AlertResource;



@Controller
@RequestMapping("/redAlertUiHome")
public class HomeController 
{
	@Autowired
	AlertResource alertResource;
	@Autowired
	SMSService smsService ;
	  String msg="you can not choose this unwantedly Since you haven't made an alert";
	@GetMapping("/home")
	public String homePage(Model model)
	{	
		AlertDTO red = new AlertDTO();
		red.setType(AlertType.RED);
		model.addAttribute("redalert", red);
		AlertDTO orange = new AlertDTO();
		orange.setType(AlertType.ORANGE);
		model.addAttribute("orangealert", orange);
		model.addAttribute("location", new LocationDTO());
		System.out.println(model.containsAttribute("message"));
		model.addAttribute("message", msg);
		return "home";
	}
	

	@PostMapping("/alert-controller")
	public String makeAlert(@ModelAttribute AlertDTO alert,@ModelAttribute LocationDTO location, Model model,@RequestParam(name="dName") String d_name) throws URISyntaxException {
		System.out.println(alert.toString());
		System.out.println(alert.getType());
		System.out.println(location.toString());
		System.out.println("smsService"+smsService);

		String a_name=getAuthority( alert);
	    smsService.sendSms(alert.getDescription(),alert.getType(),location,d_name,a_name);

	   

	    alert.setStatus(true);
	    alert=alertResource.createAlert(alert).getBody();
		return "redirect:/redAlertUiHome/home";
	}
	@GetMapping("/green")
	 public String safeStatus(Model model)
	 {
	 //	System.out.println("smsService "+smsService);
	 //	System.out.println("alertDTO "+smsService.alertDTO);
	 //	System.out.println("isStatus "+smsService.alertDTO.isStatus());
	 	
		if	 (smsService.alertDTO.isStatus()==true)
		{
			smsService.alertDTO.setStatus(false);
			msg=" The message has been sent";
			model.addAttribute("message",msg);
		}
		else 
		{	msg="you can not choose this unwantedly Since you haven't made an alert";
			model.addAttribute("message",msg);
		}
		System.out.println(msg);
	     return "redirect:/redAlertUiHome/home";
	 }
	public String getAuthority(AlertDTO alert)
	{
		String a_name="null";
		switch(alert.getDescription())
		{
		case "WomenMolestation":
			a_name="police";
			break;
		case "Flood":
			a_name="fireStation";
			break;
		case "Earthquake":
			a_name="fireStation";
			break;
		case "Robbery":
			a_name="police";
			break;
		case "Fire Hazard":
			a_name="fireStation";
			break;
		case "Road Accident":
			a_name="ambulance";
			break;	
			 	}
		
	return a_name;	
	}
	

}
