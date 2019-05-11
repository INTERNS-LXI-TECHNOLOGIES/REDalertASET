package com.lxisoft.redalert.service.impl;
import com.lxisoft.redalert.service.ServiceAuthorityService;
import com.lxisoft.redalert.service.dto.AlertDTO;
import com.lxisoft.redalert.service.dto.LocationDTO;

import com.lxisoft.redalert.service.dto.ServiceAuthorityDTO;

import com.lxisoft.redalert.web.rest.controller.HomeController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lxisoft.redalert.domain.enumeration.AlertType;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


		
	@Service

	public class SMSService {
		@Autowired
		ServiceAuthorityService serviceAuthorityService;
		ServiceAuthorityDTO serviceAuthorityDTO;
		  // Find your Account Sid and Token at twilio.com/console
	    public static final String ACCOUNT_SID = "AC35f7c23ece47cef00636cdb8e4755015";
	    public static final String AUTH_TOKEN = "94240dd789395f5ff4351237e234c950";
		long postal;
		String location;
		double latitude,longitude;
		public static AlertDTO  alertDTO=new AlertDTO();

		public void sendSms(String msg,AlertType alertType,LocationDTO locationDTO,String d_name,String a_name) {


			try {
				String lat=locationDTO.getLatitude();
				String	lng=locationDTO.getLongitude();
			if(alertType.equals(AlertType.RED))
			{
				String pos=Long.toString(postal); 
				Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

				serviceAuthorityDTO=serviceAuthorityService.getPhoneNumberOfAuthority(d_name, a_name);
				
		
				Message message1 = Message.creator(
				new com.twilio.type.PhoneNumber("+91"+Long.toString(serviceAuthorityDTO.getPhone())),
                new com.twilio.type.PhoneNumber("+12052933420"),"I am facing "+msg+"( Immediate actions to be taken.)"+" latitude is "+lat+" longitude "+lng+" http://www.google.com/maps/place/"+lat+","+lng)
						.create(); 

				System.out.println(message1.getSid());
				 this.alertDTO.setStatus(true);
				 
			}
			
			if(alertType.equals(AlertType.ORANGE))
			{
				String pos=Long.toString(postal); 
				Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

				serviceAuthorityDTO=serviceAuthorityService.getPhoneNumberOfAuthority(d_name, a_name);
				Message message1 = Message.creator(
				new com.twilio.type.PhoneNumber("+91"+Long.toString(serviceAuthorityDTO.getPhone())),
                new com.twilio.type.PhoneNumber("+12052933420"),"There is a chance of occurance of "+msg+" latitude is "+lat+" longitude "+lng+"http://www.google.com/maps/place/"+lat+","+lng)
		.create(); 


				
		
				
				System.out.println(message1.getSid());
				 this.alertDTO.setStatus(true);
			}
			

       

				

				}
			
			 catch (Exception e) {
				System.out.println("Error SMS "+e);
				
			}
		}
		public void receiveLocation(long postal,double latitude ,double longitude,String location)
	{
		this.postal=postal;
		this.latitude=latitude;
		this.longitude=longitude;
		this.location=location;
	}
}
		
		
		