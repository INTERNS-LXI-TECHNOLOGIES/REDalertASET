package com.lxisoft.redalert.service;

import com.lxisoft.redalert.service.dto.EmergencyServiceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing EmergencyService.
 */
public interface EmergencyServiceService {

    /**
     * Save a emergencyService.
     *
     * @param emergencyServiceDTO the entity to save
     * @return the persisted entity
     */
    EmergencyServiceDTO save(EmergencyServiceDTO emergencyServiceDTO);

    /**
     * Get all the emergencyServices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmergencyServiceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" emergencyService.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EmergencyServiceDTO> findOne(Long id);

    /**
     * Delete the "id" emergencyService.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
