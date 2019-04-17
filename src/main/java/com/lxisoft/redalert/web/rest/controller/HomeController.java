package com.lxisoft.redalert.web.rest.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lxisoft.redalert.service.impl.SMSService;

import io.swagger.models.Model;


@Controller
@RequestMapping("/redAlertUiHome")
public class HomeController 
{

	
	@Autowired
	SMSService smsService ;
	
	@GetMapping("/home")
	public String homePage()
	{	
		return "home";
	}
	
	
	@PostMapping("/sendmessage")
		public String MessagepassService(Model model)
	{
	//PrintWriter out=response.getWriter();
  /*   PoliceController policeController=new PoliceController();

    long policenumber=policeController.getStationNumber(request.getParameter("city"));
    long postal=Long.parseLong(request.getParameter("postal"));

    double latitude=Double.parseDouble(request.getParameter("latitude"));
    double longitude=Double.parseDouble(request.getParameter("longitude"));
    String location=request.getParameter("city");
    sms_Service.receiveLocation(postal,latitude,longitude,location); */
    smsService.sendSms();


  /*  out.println("The message has been sent succesfully!!!");
   out.println(policenumber);
   out.println(request.getParameter("city"));
   out.println(request.getParameter("postal"));
   out.println(request.getParameter("latitude"));
   out.println(request.getParameter("longitude")); */
    return "home";
	}

}
