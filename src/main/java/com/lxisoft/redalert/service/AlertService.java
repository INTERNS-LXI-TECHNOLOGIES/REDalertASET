package com.lxisoft.redalert.service;

import com.lxisoft.redalert.service.dto.AlertDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Alert.
 */
public interface AlertService {

    /**
     * Save a alert.
     *
     * @param alertDTO the entity to save
     * @return the persisted entity
     */
    AlertDTO save(AlertDTO alertDTO);

    /**
     * Get all the alerts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AlertDTO> findAll(Pageable pageable);


    /**
     * Get the "id" alert.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AlertDTO> findOne(Long id);

    /**
     * Delete the "id" alert.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
