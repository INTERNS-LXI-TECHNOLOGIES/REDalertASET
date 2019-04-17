package com.lxisoft.redalert.web.rest;
import com.lxisoft.redalert.service.EmergencyServiceService;
import com.lxisoft.redalert.web.rest.errors.BadRequestAlertException;
import com.lxisoft.redalert.web.rest.util.HeaderUtil;
import com.lxisoft.redalert.web.rest.util.PaginationUtil;
import com.lxisoft.redalert.service.dto.EmergencyServiceDTO;
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
 * REST controller for managing EmergencyService.
 */
@RestController
@RequestMapping("/api")
public class EmergencyServiceResource {

    private final Logger log = LoggerFactory.getLogger(EmergencyServiceResource.class);

    private static final String ENTITY_NAME = "emergencyService";

    private final EmergencyServiceService emergencyServiceService;

    public EmergencyServiceResource(EmergencyServiceService emergencyServiceService) {
        this.emergencyServiceService = emergencyServiceService;
    }

    /**
     * POST  /emergency-services : Create a new emergencyService.
     *
     * @param emergencyServiceDTO the emergencyServiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emergencyServiceDTO, or with status 400 (Bad Request) if the emergencyService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/emergency-services")
    public ResponseEntity<EmergencyServiceDTO> createEmergencyService(@RequestBody EmergencyServiceDTO emergencyServiceDTO) throws URISyntaxException {
        log.debug("REST request to save EmergencyService : {}", emergencyServiceDTO);
        if (emergencyServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new emergencyService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmergencyServiceDTO result = emergencyServiceService.save(emergencyServiceDTO);
        return ResponseEntity.created(new URI("/api/emergency-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /emergency-services : Updates an existing emergencyService.
     *
     * @param emergencyServiceDTO the emergencyServiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emergencyServiceDTO,
     * or with status 400 (Bad Request) if the emergencyServiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the emergencyServiceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/emergency-services")
    public ResponseEntity<EmergencyServiceDTO> updateEmergencyService(@RequestBody EmergencyServiceDTO emergencyServiceDTO) throws URISyntaxException {
        log.debug("REST request to update EmergencyService : {}", emergencyServiceDTO);
        if (emergencyServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmergencyServiceDTO result = emergencyServiceService.save(emergencyServiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emergencyServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /emergency-services : get all the emergencyServices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of emergencyServices in body
     */
    @GetMapping("/emergency-services")
    public ResponseEntity<List<EmergencyServiceDTO>> getAllEmergencyServices(Pageable pageable) {
        log.debug("REST request to get a page of EmergencyServices");
        Page<EmergencyServiceDTO> page = emergencyServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/emergency-services");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /emergency-services/:id : get the "id" emergencyService.
     *
     * @param id the id of the emergencyServiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emergencyServiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/emergency-services/{id}")
    public ResponseEntity<EmergencyServiceDTO> getEmergencyService(@PathVariable Long id) {
        log.debug("REST request to get EmergencyService : {}", id);
        Optional<EmergencyServiceDTO> emergencyServiceDTO = emergencyServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emergencyServiceDTO);
    }

    /**
     * DELETE  /emergency-services/:id : delete the "id" emergencyService.
     *
     * @param id the id of the emergencyServiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/emergency-services/{id}")
    public ResponseEntity<Void> deleteEmergencyService(@PathVariable Long id) {
        log.debug("REST request to delete EmergencyService : {}", id);
        emergencyServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
