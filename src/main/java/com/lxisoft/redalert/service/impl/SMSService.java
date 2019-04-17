package com.lxisoft.redalert.service.impl;



import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

	@Service

	public class SMSService {
	
		  // Find your Account Sid and Token at twilio.com/console
	    public static final String ACCOUNT_SID = "AC47cf3168b800f0d2080e306afdb57c81";
	    public static final String AUTH_TOKEN = "91245b5af12e21fd5c0554f2ac5ab2e0";
		long postal;
		String location;
		double latitude,longitude;
		
		public void sendSms() {
			try {
			  
				
				String pos=Long.toString(postal); 
				Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
				Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+918078248075"),
                new com.twilio.type.PhoneNumber("+16605708554"),
                pos)
				.create(); 
				Message message1 = Message.creator(
				new com.twilio.type.PhoneNumber("+919847684091"),
                new com.twilio.type.PhoneNumber("+16605708554"),"I think i am not safe at ")
				.create();

       

				System.out.println(message.getSid());
				System.out.println(message1.getSid());

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
		
		
		