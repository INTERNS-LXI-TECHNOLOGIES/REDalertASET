package com.lxisoft.redalert.web.rest;
import com.lxisoft.redalert.domain.ServiceAuthority;
import com.lxisoft.redalert.service.ServiceAuthorityService;
import com.lxisoft.redalert.web.rest.errors.BadRequestAlertException;
import com.lxisoft.redalert.web.rest.util.HeaderUtil;
import com.lxisoft.redalert.web.rest.util.PaginationUtil;
import com.lxisoft.redalert.service.dto.ServiceAuthorityDTO;
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
 * REST controller for managing ServiceAuthority.
 */
@RestController
@RequestMapping("/api")
public class ServiceAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(ServiceAuthorityResource.class);

    private static final String ENTITY_NAME = "serviceAuthority";

    private final ServiceAuthorityService serviceAuthorityService;

    public ServiceAuthorityResource(ServiceAuthorityService serviceAuthorityService) {
        this.serviceAuthorityService = serviceAuthorityService;
    }

    /**
     * POST  /service-authorities : Create a new serviceAuthority.
     *
     * @param serviceAuthorityDTO the serviceAuthorityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceAuthorityDTO, or with status 400 (Bad Request) if the serviceAuthority has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-authorities")
    public ResponseEntity<ServiceAuthorityDTO> createServiceAuthority(@RequestBody ServiceAuthorityDTO serviceAuthorityDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceAuthority : {}", serviceAuthorityDTO);
        if (serviceAuthorityDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceAuthority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceAuthorityDTO result = serviceAuthorityService.save(serviceAuthorityDTO);
        return ResponseEntity.created(new URI("/api/service-authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-authorities : Updates an existing serviceAuthority.
     *
     * @param serviceAuthorityDTO the serviceAuthorityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceAuthorityDTO,
     * or with status 400 (Bad Request) if the serviceAuthorityDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceAuthorityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-authorities")
    public ResponseEntity<ServiceAuthorityDTO> updateServiceAuthority(@RequestBody ServiceAuthorityDTO serviceAuthorityDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceAuthority : {}", serviceAuthorityDTO);
        if (serviceAuthorityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceAuthorityDTO result = serviceAuthorityService.save(serviceAuthorityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceAuthorityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-authorities : get all the serviceAuthorities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of serviceAuthorities in body
     */
    @GetMapping("/service-authorities")
    public ResponseEntity<List<ServiceAuthorityDTO>> getAllServiceAuthorities(Pageable pageable) {
        log.debug("REST request to get a page of ServiceAuthorities");
        Page<ServiceAuthorityDTO> page = serviceAuthorityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/service-authorities");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /service-authorities/:id : get the "id" serviceAuthority.
     *
     * @param id the id of the serviceAuthorityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceAuthorityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/service-authorities/{id}")
    public ResponseEntity<ServiceAuthorityDTO> getServiceAuthority(@PathVariable Long id) {
        log.debug("REST request to get ServiceAuthority : {}", id);
        Optional<ServiceAuthorityDTO> serviceAuthorityDTO = serviceAuthorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceAuthorityDTO);
    }
    
   @GetMapping("")
   public ResponseEntity<ServiceAuthorityDTO> getPhoneNumberOfAuthority(@RequestParam String d_name,String a_name) { 
	  ServiceAuthorityDTO serviceAuthority= serviceAuthorityService.getPhoneNumberOfAuthority(d_name,a_name);
	 
	   return ResponseEntity.ok().body(serviceAuthority);
	      }

    /** 
     * DELETE  /service-authorities/:id : delete the "id" serviceAuthority.
     *
     * @param id the id of the serviceAuthorityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-authorities/{id}")
    public ResponseEntity<Void> deleteServiceAuthority(@PathVariable Long id) {
        log.debug("REST request to delete ServiceAuthority : {}", id);
        serviceAuthorityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
