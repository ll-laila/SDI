package com.sdi.web.rest;

import com.sdi.domain.HSM;
import com.sdi.repository.HSMRepository;
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
 * REST controller for managing {@link com.sdi.domain.HSM}.
 */
@RestController
@RequestMapping("/api/hsms")
@Transactional
public class HSMResource {

    private static final Logger LOG = LoggerFactory.getLogger(HSMResource.class);

    private static final String ENTITY_NAME = "hSM";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HSMRepository hSMRepository;

    public HSMResource(HSMRepository hSMRepository) {
        this.hSMRepository = hSMRepository;
    }

    /**
     * {@code POST  /hsms} : Create a new hSM.
     *
     * @param hSM the hSM to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hSM, or with status {@code 400 (Bad Request)} if the hSM has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HSM> createHSM(@Valid @RequestBody HSM hSM) throws URISyntaxException {
        LOG.debug("REST request to save HSM : {}", hSM);
        if (hSM.getId() != null) {
            throw new BadRequestAlertException("A new hSM cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hSM = hSMRepository.save(hSM);
        return ResponseEntity.created(new URI("/api/hsms/" + hSM.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, hSM.getId().toString()))
            .body(hSM);
    }

    /**
     * {@code PUT  /hsms/:id} : Updates an existing hSM.
     *
     * @param id the id of the hSM to save.
     * @param hSM the hSM to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hSM,
     * or with status {@code 400 (Bad Request)} if the hSM is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hSM couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HSM> updateHSM(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody HSM hSM)
        throws URISyntaxException {
        LOG.debug("REST request to update HSM : {}, {}", id, hSM);
        if (hSM.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hSM.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hSMRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hSM = hSMRepository.save(hSM);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hSM.getId().toString()))
            .body(hSM);
    }

    /**
     * {@code PATCH  /hsms/:id} : Partial updates given fields of an existing hSM, field will ignore if it is null
     *
     * @param id the id of the hSM to save.
     * @param hSM the hSM to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hSM,
     * or with status {@code 400 (Bad Request)} if the hSM is not valid,
     * or with status {@code 404 (Not Found)} if the hSM is not found,
     * or with status {@code 500 (Internal Server Error)} if the hSM couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HSM> partialUpdateHSM(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody HSM hSM)
        throws URISyntaxException {
        LOG.debug("REST request to partial update HSM partially : {}, {}", id, hSM);
        if (hSM.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hSM.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hSMRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HSM> result = hSMRepository
            .findById(hSM.getId())
            .map(existingHSM -> {
                if (hSM.getName() != null) {
                    existingHSM.setName(hSM.getName());
                }
                if (hSM.getCreaDate() != null) {
                    existingHSM.setCreaDate(hSM.getCreaDate());
                }
                if (hSM.getUpdateDate() != null) {
                    existingHSM.setUpdateDate(hSM.getUpdateDate());
                }
                if (hSM.getNotes() != null) {
                    existingHSM.setNotes(hSM.getNotes());
                }

                return existingHSM;
            })
            .map(hSMRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hSM.getId().toString())
        );
    }

    /**
     * {@code GET  /hsms} : get all the hSMS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hSMS in body.
     */
    @GetMapping("")
    public List<HSM> getAllHSMS() {
        LOG.debug("REST request to get all HSMS");
        return hSMRepository.findAll();
    }

    /**
     * {@code GET  /hsms/:id} : get the "id" hSM.
     *
     * @param id the id of the hSM to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hSM, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HSM> getHSM(@PathVariable("id") Long id) {
        LOG.debug("REST request to get HSM : {}", id);
        Optional<HSM> hSM = hSMRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hSM);
    }

    /**
     * {@code DELETE  /hsms/:id} : delete the "id" hSM.
     *
     * @param id the id of the hSM to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHSM(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete HSM : {}", id);
        hSMRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
