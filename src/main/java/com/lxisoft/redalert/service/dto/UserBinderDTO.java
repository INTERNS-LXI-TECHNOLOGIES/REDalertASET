package com.lxisoft.redalert.service.dto;

import java.util.ArrayList;
import java.util.List;

public class UserBinderDTO {
	private UserDomainDTO user;
	private List<ContactDTO> contacts= new ArrayList<ContactDTO>();
	@Override
	public String toString() {
		return "UserBinderDTO [user=" + user + ", contacts=" + contacts + "]";
	}
	public UserDomainDTO getUser() {
		return user;
	}
	public void setUser(UserDomainDTO user) {
		this.user = user;
	}
	public List<ContactDTO> getContacts() {
		return contacts;
	}
	public void setContacts(List<ContactDTO> contacts) {
		this.contacts = contacts;
	}
}
