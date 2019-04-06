package com.lxisoft.redalert.web.rest;
import com.lxisoft.redalert.service.AlertService;
import com.lxisoft.redalert.web.rest.errors.BadRequestAlertException;
import com.lxisoft.redalert.web.rest.util.HeaderUtil;
import com.lxisoft.redalert.web.rest.util.PaginationUtil;
import com.lxisoft.redalert.service.dto.AlertDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Alert.
 */
@RestController
@RequestMapping("/api")
public class AlertResource {

    private final Logger log = LoggerFactory.getLogger(AlertResource.class);

    private static final String ENTITY_NAME = "alert";

    private final AlertService alertService;

    public AlertResource(AlertService alertService) {
        this.alertService = alertService;
    }

    /**
     * POST  /alerts : Create a new alert.
     *
     * @param alertDTO the alertDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alertDTO, or with status 400 (Bad Request) if the alert has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alerts")
    public ResponseEntity<AlertDTO> createAlert(@RequestBody AlertDTO alertDTO) throws URISyntaxException {
        log.debug("REST request to save Alert : {}", alertDTO);
        if (alertDTO.getId() != null) {
            throw new BadRequestAlertException("A new alert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlertDTO result = alertService.save(alertDTO);
        return ResponseEntity.created(new URI("/api/alerts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alerts : Updates an existing alert.
     *
     * @param alertDTO the alertDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alertDTO,
     * or with status 400 (Bad Request) if the alertDTO is not valid,
     * or with status 500 (Internal Server Error) if the alertDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alerts")
    public ResponseEntity<AlertDTO> updateAlert(@RequestBody AlertDTO alertDTO) throws URISyntaxException {
        log.debug("REST request to update Alert : {}", alertDTO);
        if (alertDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlertDTO result = alertService.save(alertDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, alertDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alerts : get all the alerts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of alerts in body
     */
    @GetMapping("/alerts")
    public ResponseEntity<List<AlertDTO>> getAllAlerts(Pageable pageable) {
        log.debug("REST request to get a page of Alerts");
        Page<AlertDTO> page = alertService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/alerts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /alerts/:id : get the "id" alert.
     *
     * @param id the id of the alertDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alertDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alerts/{id}")
    public ResponseEntity<AlertDTO> getAlert(@PathVariable Long id) {
        log.debug("REST request to get Alert : {}", id);
        Optional<AlertDTO> alertDTO = alertService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alertDTO);
    }

    /**
     * DELETE  /alerts/:id : delete the "id" alert.
     *
     * @param id the id of the alertDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alerts/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        log.debug("REST request to delete Alert : {}", id);
        alertService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
