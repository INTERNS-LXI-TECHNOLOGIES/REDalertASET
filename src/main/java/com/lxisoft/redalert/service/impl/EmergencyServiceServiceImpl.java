package com.lxisoft.redalert.service.impl;

import com.lxisoft.redalert.service.EmergencyServiceService;
import com.lxisoft.redalert.domain.EmergencyService;
import com.lxisoft.redalert.repository.EmergencyServiceRepository;
import com.lxisoft.redalert.service.dto.EmergencyServiceDTO;
import com.lxisoft.redalert.service.mapper.EmergencyServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing EmergencyService.
 */
@Service
@Transactional
public class EmergencyServiceServiceImpl implements EmergencyServiceService {

    private final Logger log = LoggerFactory.getLogger(EmergencyServiceServiceImpl.class);

    private final EmergencyServiceRepository emergencyServiceRepository;

    private final EmergencyServiceMapper emergencyServiceMapper;

    public EmergencyServiceServiceImpl(EmergencyServiceRepository emergencyServiceRepository, EmergencyServiceMapper emergencyServiceMapper) {
        this.emergencyServiceRepository = emergencyServiceRepository;
        this.emergencyServiceMapper = emergencyServiceMapper;
    }

    /**
     * Save a emergencyService.
     *
     * @param emergencyServiceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmergencyServiceDTO save(EmergencyServiceDTO emergencyServiceDTO) {
        log.debug("Request to save EmergencyService : {}", emergencyServiceDTO);
        EmergencyService emergencyService = emergencyServiceMapper.toEntity(emergencyServiceDTO);
        emergencyService = emergencyServiceRepository.save(emergencyService);
        return emergencyServiceMapper.toDto(emergencyService);
    }

    /**
     * Get all the emergencyServices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmergencyServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmergencyServices");
        return emergencyServiceRepository.findAll(pageable)
            .map(emergencyServiceMapper::toDto);
    }


    /**
     * Get one emergencyService by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmergencyServiceDTO> findOne(Long id) {
        log.debug("Request to get EmergencyService : {}", id);
        return emergencyServiceRepository.findById(id)
            .map(emergencyServiceMapper::toDto);
    }

    /**
     * Delete the emergencyService by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmergencyService : {}", id);        emergencyServiceRepository.deleteById(id);
    }
}
