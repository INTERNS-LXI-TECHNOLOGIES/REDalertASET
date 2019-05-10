package com.lxisoft.redalert.service.impl;

import com.lxisoft.redalert.service.UserDomainService;
import com.lxisoft.redalert.domain.Role;
import com.lxisoft.redalert.domain.UserDomain;
import com.lxisoft.redalert.repository.RoleRepository;
import com.lxisoft.redalert.repository.UserDomainRepository;
import com.lxisoft.redalert.service.dto.UserDomainDTO;
import com.lxisoft.redalert.service.mapper.UserDomainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

/**
 * Service Implementation for managing UserDomain.
 */
@Service
@Transactional
public class UserDomainServiceImpl implements UserDomainService {

    private final Logger log = LoggerFactory.getLogger(UserDomainServiceImpl.class);

    private final UserDomainRepository userDomainRepository;

    private final UserDomainMapper userDomainMapper;
    private final RoleRepository roleRepository;

    public UserDomainServiceImpl(UserDomainRepository userDomainRepository, UserDomainMapper userDomainMapper,RoleRepository roleRepository) {
        this.userDomainRepository = userDomainRepository;
        this.userDomainMapper = userDomainMapper;
        this.roleRepository=roleRepository;
    }

    /**
     * Save a userDomain.
     *
     * @param userDomainDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserDomainDTO save(UserDomainDTO userDomainDTO) {
        log.debug("Request to save UserDomain : {}", userDomainDTO);
        
        UserDomain userDomain = userDomainMapper.toEntity(userDomainDTO);
        
        
        Role role = new Role();
		role.setId(1l);
		role.setName("USER");
		
		roleRepository.save(role);
		userDomain.setRoles(new HashSet<Role>(Arrays.asList(role)));
        userDomain = userDomainRepository.save(userDomain);
        return userDomainMapper.toDto(userDomain);
    }

    /**
     * Get all the userDomains.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserDomainDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserDomains");
        return userDomainRepository.findAll(pageable)
            .map(userDomainMapper::toDto);
    }

    /**
     * Get all the UserDomain with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<UserDomainDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userDomainRepository.findAllWithEagerRelationships(pageable).map(userDomainMapper::toDto);
    }
    

    /**
     * Get one userDomain by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserDomainDTO> findOne(Long id) {
        log.debug("Request to get UserDomain : {}", id);
        return userDomainRepository.findOneWithEagerRelationships(id)
            .map(userDomainMapper::toDto);
    }

    /**
     * Delete the userDomain by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserDomain : {}", id);        userDomainRepository.deleteById(id);
    }
}
