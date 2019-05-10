package com.lxisoft.redalert.service;

import com.lxisoft.redalert.domain.ServiceAuthority;
import com.lxisoft.redalert.service.dto.ServiceAuthorityDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ServiceAuthority.
 */
public interface ServiceAuthorityService {

    /**
     * Save a serviceAuthority.
     *
     * @param serviceAuthorityDTO the entity to save
     * @return the persisted entity
     */
    ServiceAuthorityDTO save(ServiceAuthorityDTO serviceAuthorityDTO);

    /**
     * Get all the serviceAuthorities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServiceAuthorityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" serviceAuthority.
     *
     * @param id the id of the entity
     * @return the entity
     *
     */
    ServiceAuthorityDTO getPhoneNumberOfAuthority(String d_name,String a_name);
    
    Optional<ServiceAuthorityDTO> findOne(Long id);

    /**
     * Delete the "id" serviceAuthority.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
