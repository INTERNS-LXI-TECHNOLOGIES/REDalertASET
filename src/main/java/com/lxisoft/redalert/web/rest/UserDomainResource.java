package com.lxisoft.redalert.web.rest;
import com.lxisoft.redalert.service.UserDomainService;
import com.lxisoft.redalert.web.rest.errors.BadRequestAlertException;
import com.lxisoft.redalert.web.rest.util.HeaderUtil;
import com.lxisoft.redalert.web.rest.util.PaginationUtil;
import com.lxisoft.redalert.service.dto.UserDomainDTO;
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
 * REST controller for managing UserDomain.
 */
@RestController
@RequestMapping("/api")
public class UserDomainResource {

    private final Logger log = LoggerFactory.getLogger(UserDomainResource.class);

    private static final String ENTITY_NAME = "userDomain";

    private final UserDomainService userDomainService;

    public UserDomainResource(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    /**
     * POST  /user-domains : Create a new userDomain.
     *
     * @param userDomainDTO the userDomainDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userDomainDTO, or with status 400 (Bad Request) if the userDomain has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-domains")
    public ResponseEntity<UserDomainDTO> createUserDomain(@RequestBody UserDomainDTO userDomainDTO) throws URISyntaxException {
        log.debug("REST request to save UserDomain : {}", userDomainDTO);
        if (userDomainDTO.getId() != null) {
            throw new BadRequestAlertException("A new userDomain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        userDomainDTO.setActivated(true);
        UserDomainDTO result = userDomainService.save(userDomainDTO);
        return ResponseEntity.created(new URI("/api/user-domains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-domains : Updates an existing userDomain.
     *
     * @param userDomainDTO the userDomainDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userDomainDTO,
     * or with status 400 (Bad Request) if the userDomainDTO is not valid,
     * or with status 500 (Internal Server Error) if the userDomainDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-domains")
    public ResponseEntity<UserDomainDTO> updateUserDomain(@RequestBody UserDomainDTO userDomainDTO) throws URISyntaxException {
        log.debug("REST request to update UserDomain : {}", userDomainDTO);
        if (userDomainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserDomainDTO result = userDomainService.save(userDomainDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDomainDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-domains : get all the userDomains.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of userDomains in body
     */
    @GetMapping("/user-domains")
    public ResponseEntity<List<UserDomainDTO>> getAllUserDomains(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of UserDomains");
        Page<UserDomainDTO> page;
        if (eagerload) {
            page = userDomainService.findAllWithEagerRelationships(pageable);
        } else {
            page = userDomainService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/user-domains?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /user-domains/:id : get the "id" userDomain.
     *
     * @param id the id of the userDomainDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userDomainDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-domains/{id}")
    public ResponseEntity<UserDomainDTO> getUserDomain(@PathVariable Long id) {
        log.debug("REST request to get UserDomain : {}", id);
        Optional<UserDomainDTO> userDomainDTO = userDomainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDomainDTO);
    }

    /**
     * DELETE  /user-domains/:id : delete the "id" userDomain.
     *
     * @param id the id of the userDomainDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-domains/{id}")
    public ResponseEntity<Void> deleteUserDomain(@PathVariable Long id) {
        log.debug("REST request to delete UserDomain : {}", id);
        userDomainService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
