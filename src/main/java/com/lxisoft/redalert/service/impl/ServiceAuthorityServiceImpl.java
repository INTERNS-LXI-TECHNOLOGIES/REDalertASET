package com.lxisoft.redalert.service.impl;

import com.lxisoft.redalert.service.ServiceAuthorityService;
import com.lxisoft.redalert.domain.ServiceAuthority;
import com.lxisoft.redalert.repository.ServiceAuthorityRepository;
import com.lxisoft.redalert.service.dto.ServiceAuthorityDTO;
import com.lxisoft.redalert.service.mapper.ServiceAuthorityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ServiceAuthority.
 */

@Service
@Transactional
public class ServiceAuthorityServiceImpl implements ServiceAuthorityService {
	

    private final Logger log = LoggerFactory.getLogger(ServiceAuthorityServiceImpl.class);

    private final ServiceAuthorityRepository serviceAuthorityRepository;

    private final ServiceAuthorityMapper serviceAuthorityMapper;

    public ServiceAuthorityServiceImpl(ServiceAuthorityRepository serviceAuthorityRepository, ServiceAuthorityMapper serviceAuthorityMapper) {
        this.serviceAuthorityRepository = serviceAuthorityRepository;
        this.serviceAuthorityMapper = serviceAuthorityMapper;
    }

    /**
     * Save a serviceAuthority.
     *
     * @param serviceAuthorityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ServiceAuthorityDTO save(ServiceAuthorityDTO serviceAuthorityDTO) {
        log.debug("Request to save ServiceAuthority : {}", serviceAuthorityDTO);
        ServiceAuthority serviceAuthority = serviceAuthorityMapper.toEntity(serviceAuthorityDTO);
        serviceAuthority = serviceAuthorityRepository.save(serviceAuthority);
        return serviceAuthorityMapper.toDto(serviceAuthority);
    }

    /**
     * Get all the serviceAuthorities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceAuthorityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceAuthorities");
        return serviceAuthorityRepository.findAll(pageable)
            .map(serviceAuthorityMapper::toDto);
    }


    /**
     * Get one serviceAuthority by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceAuthorityDTO> findOne(Long id) {
        log.debug("Request to get ServiceAuthority : {}", id);
        return serviceAuthorityRepository.findById(id)
            .map(serviceAuthorityMapper::toDto);
    }

    /**
     * Delete the serviceAuthority by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceAuthority : {}", id);        
        serviceAuthorityRepository.deleteById(id);
    }
    
    @Override
    public ServiceAuthorityDTO getPhoneNumberOfAuthority(String d_name,String a_name) {
    	
    	return serviceAuthorityMapper.toDto(serviceAuthorityRepository.findByDistrictNameAndAuthorityName(d_name, a_name));
    	
    }
}
