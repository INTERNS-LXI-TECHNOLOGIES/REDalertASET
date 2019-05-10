package com.lxisoft.redalert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lxisoft.redalert.domain.ServiceAuthority;


/**
 * Spring Data  repository for the ServiceAuthority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceAuthorityRepository extends JpaRepository<ServiceAuthority, Long> {
	
	/*ss@Query(value="select phone from service_authority where  district_name ='d_name' and authority_name='a_name'")
	ServiceAuthority getPhoneNumberOfAuthority(String d_name,String a_name);*/
	
	public ServiceAuthority findByDistrictNameAndAuthorityName(String  d_name,String a_name);

}
