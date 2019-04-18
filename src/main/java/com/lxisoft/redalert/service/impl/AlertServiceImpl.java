package com.lxisoft.redalert.service.impl;

import com.lxisoft.redalert.service.AlertService;
import com.lxisoft.redalert.domain.Alert;
import com.lxisoft.redalert.repository.AlertRepository;
import com.lxisoft.redalert.service.dto.AlertDTO;
import com.lxisoft.redalert.service.mapper.AlertMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Alert.
 */
@Service
@Transactional
public class AlertServiceImpl implements AlertService {

    private final Logger log = LoggerFactory.getLogger(AlertServiceImpl.class);

    private final AlertRepository alertRepository;

    private final AlertMapper alertMapper;

    public AlertServiceImpl(AlertRepository alertRepository, AlertMapper alertMapper) {
        this.alertRepository = alertRepository;
        this.alertMapper = alertMapper;
    }

    /**
     * Save a alert.
     *
     * @param alertDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AlertDTO save(AlertDTO alertDTO) {
        log.debug("Request to save Alert : {}", alertDTO);
        Alert alert = alertMapper.toEntity(alertDTO);
        alert = alertRepository.save(alert);
        return alertMapper.toDto(alert);
    }

    /**
     * Get all the alerts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AlertDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Alerts");
        return alertRepository.findAll(pageable)
            .map(alertMapper::toDto);
    }


    /**
     * Get one alert by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AlertDTO> findOne(Long id) {
        log.debug("Request to get Alert : {}", id);
        return alertRepository.findById(id)
            .map(alertMapper::toDto);
    }

    /**
     * Delete the alert by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Alert : {}", id);
        alertRepository.deleteById(id);
    }
}
