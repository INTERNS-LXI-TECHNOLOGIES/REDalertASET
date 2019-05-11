package com.lxisoft.redalert.repository;

import com.lxisoft.redalert.domain.Contact;
import com.lxisoft.redalert.service.mapper.ContactMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Contact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	List<Contact> findAllByUsers(Long id);
}
