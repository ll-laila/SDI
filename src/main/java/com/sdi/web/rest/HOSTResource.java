package com.sdi.web.rest;

import com.sdi.domain.HOST;
import com.sdi.repository.HOSTRepository;
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
 * REST controller for managing {@link com.sdi.domain.HOST}.
 */
@RestController
@RequestMapping("/api/hosts")
@Transactional
public class HOSTResource {

    private static final Logger LOG = LoggerFactory.getLogger(HOSTResource.class);

    private static final String ENTITY_NAME = "hOST";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HOSTRepository hOSTRepository;

    public HOSTResource(HOSTRepository hOSTRepository) {
        this.hOSTRepository = hOSTRepository;
    }

    /**
     * {@code POST  /hosts} : Create a new hOST.
     *
     * @param hOST the hOST to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hOST, or with status {@code 400 (Bad Request)} if the hOST has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HOST> createHOST(@Valid @RequestBody HOST hOST) throws URISyntaxException {
        LOG.debug("REST request to save HOST : {}", hOST);
        if (hOST.getId() != null) {
            throw new BadRequestAlertException("A new hOST cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hOST = hOSTRepository.save(hOST);
        return ResponseEntity.created(new URI("/api/hosts/" + hOST.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, hOST.getId().toString()))
            .body(hOST);
    }

    /**
     * {@code PUT  /hosts/:id} : Updates an existing hOST.
     *
     * @param id the id of the hOST to save.
     * @param hOST the hOST to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hOST,
     * or with status {@code 400 (Bad Request)} if the hOST is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hOST couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HOST> updateHOST(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody HOST hOST)
        throws URISyntaxException {
        LOG.debug("REST request to update HOST : {}, {}", id, hOST);
        if (hOST.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hOST.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hOSTRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hOST = hOSTRepository.save(hOST);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hOST.getId().toString()))
            .body(hOST);
    }

    /**
     * {@code PATCH  /hosts/:id} : Partial updates given fields of an existing hOST, field will ignore if it is null
     *
     * @param id the id of the hOST to save.
     * @param hOST the hOST to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hOST,
     * or with status {@code 400 (Bad Request)} if the hOST is not valid,
     * or with status {@code 404 (Not Found)} if the hOST is not found,
     * or with status {@code 500 (Internal Server Error)} if the hOST couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HOST> partialUpdateHOST(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HOST hOST
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update HOST partially : {}, {}", id, hOST);
        if (hOST.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hOST.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hOSTRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HOST> result = hOSTRepository
            .findById(hOST.getId())
            .map(existingHOST -> {
                if (hOST.getName() != null) {
                    existingHOST.setName(hOST.getName());
                }
                if (hOST.getCreaDate() != null) {
                    existingHOST.setCreaDate(hOST.getCreaDate());
                }
                if (hOST.getUpdateDate() != null) {
                    existingHOST.setUpdateDate(hOST.getUpdateDate());
                }
                if (hOST.getNotes() != null) {
                    existingHOST.setNotes(hOST.getNotes());
                }

                return existingHOST;
            })
            .map(hOSTRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hOST.getId().toString())
        );
    }

    /**
     * {@code GET  /hosts} : get all the hOSTS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hOSTS in body.
     */
    @GetMapping("")
    public List<HOST> getAllHOSTS() {
        LOG.debug("REST request to get all HOSTS");
        return hOSTRepository.findAll();
    }

    /**
     * {@code GET  /hosts/:id} : get the "id" hOST.
     *
     * @param id the id of the hOST to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hOST, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HOST> getHOST(@PathVariable("id") Long id) {
        LOG.debug("REST request to get HOST : {}", id);
        Optional<HOST> hOST = hOSTRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hOST);
    }

    /**
     * {@code DELETE  /hosts/:id} : delete the "id" hOST.
     *
     * @param id the id of the hOST to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHOST(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete HOST : {}", id);
        hOSTRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
