package com.sdi.web.rest;

import com.sdi.domain.ApplicationServer;
import com.sdi.repository.ApplicationServerRepository;
import com.sdi.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sdi.domain.ApplicationServer}.
 */
@RestController
@RequestMapping("/api/application-servers")
@Transactional
public class ApplicationServerResource {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationServerResource.class);

    private static final String ENTITY_NAME = "applicationServer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationServerRepository applicationServerRepository;

    public ApplicationServerResource(ApplicationServerRepository applicationServerRepository) {
        this.applicationServerRepository = applicationServerRepository;
    }

    /**
     * {@code POST  /application-servers} : Create a new applicationServer.
     *
     * @param applicationServer the applicationServer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationServer, or with status {@code 400 (Bad Request)} if the applicationServer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ApplicationServer> createApplicationServer(@Valid @RequestBody ApplicationServer applicationServer)
        throws URISyntaxException {
        LOG.debug("REST request to save ApplicationServer : {}", applicationServer);
        if (applicationServer.getId() != null) {
            throw new BadRequestAlertException("A new applicationServer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        applicationServer = applicationServerRepository.save(applicationServer);
        return ResponseEntity.created(new URI("/api/application-servers/" + applicationServer.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, applicationServer.getId().toString()))
            .body(applicationServer);
    }

    /**
     * {@code PUT  /application-servers/:id} : Updates an existing applicationServer.
     *
     * @param id the id of the applicationServer to save.
     * @param applicationServer the applicationServer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationServer,
     * or with status {@code 400 (Bad Request)} if the applicationServer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationServer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApplicationServer> updateApplicationServer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApplicationServer applicationServer
    ) throws URISyntaxException {
        LOG.debug("REST request to update ApplicationServer : {}, {}", id, applicationServer);
        if (applicationServer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationServer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationServerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        applicationServer = applicationServerRepository.save(applicationServer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationServer.getId().toString()))
            .body(applicationServer);
    }

    /**
     * {@code PATCH  /application-servers/:id} : Partial updates given fields of an existing applicationServer, field will ignore if it is null
     *
     * @param id the id of the applicationServer to save.
     * @param applicationServer the applicationServer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationServer,
     * or with status {@code 400 (Bad Request)} if the applicationServer is not valid,
     * or with status {@code 404 (Not Found)} if the applicationServer is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicationServer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApplicationServer> partialUpdateApplicationServer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApplicationServer applicationServer
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ApplicationServer partially : {}, {}", id, applicationServer);
        if (applicationServer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicationServer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicationServerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApplicationServer> result = applicationServerRepository
            .findById(applicationServer.getId())
            .map(existingApplicationServer -> {
                if (applicationServer.getName() != null) {
                    existingApplicationServer.setName(applicationServer.getName());
                }
                if (applicationServer.getCreaDate() != null) {
                    existingApplicationServer.setCreaDate(applicationServer.getCreaDate());
                }
                if (applicationServer.getUpdateDate() != null) {
                    existingApplicationServer.setUpdateDate(applicationServer.getUpdateDate());
                }
                if (applicationServer.getNotes() != null) {
                    existingApplicationServer.setNotes(applicationServer.getNotes());
                }

                return existingApplicationServer;
            })
            .map(applicationServerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationServer.getId().toString())
        );
    }

    /**
     * {@code GET  /application-servers} : get all the applicationServers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationServers in body.
     */
    @GetMapping("")
    public List<ApplicationServer> getAllApplicationServers() {
        LOG.debug("REST request to get all ApplicationServers");
        return applicationServerRepository.findAll();
    }

    /**
     * {@code GET  /application-servers/:id} : get the "id" applicationServer.
     *
     * @param id the id of the applicationServer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationServer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationServer> getApplicationServer(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ApplicationServer : {}", id);
        Optional<ApplicationServer> applicationServer = applicationServerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(applicationServer);
    }

    /**
     * {@code DELETE  /application-servers/:id} : delete the "id" applicationServer.
     *
     * @param id the id of the applicationServer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicationServer(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ApplicationServer : {}", id);
        applicationServerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
