package com.sdi.web.rest;

import com.sdi.domain.OS;
import com.sdi.repository.OSRepository;
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
 * REST controller for managing {@link com.sdi.domain.OS}.
 */
@RestController
@RequestMapping("/api/os")
@Transactional
public class OSResource {

    private static final Logger LOG = LoggerFactory.getLogger(OSResource.class);

    private static final String ENTITY_NAME = "oS";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OSRepository oSRepository;

    public OSResource(OSRepository oSRepository) {
        this.oSRepository = oSRepository;
    }

    /**
     * {@code POST  /os} : Create a new oS.
     *
     * @param oS the oS to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oS, or with status {@code 400 (Bad Request)} if the oS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OS> createOS(@Valid @RequestBody OS oS) throws URISyntaxException {
        LOG.debug("REST request to save OS : {}", oS);
        if (oS.getId() != null) {
            throw new BadRequestAlertException("A new oS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        oS = oSRepository.save(oS);
        return ResponseEntity.created(new URI("/api/os/" + oS.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, oS.getId().toString()))
            .body(oS);
    }

    /**
     * {@code PUT  /os/:id} : Updates an existing oS.
     *
     * @param id the id of the oS to save.
     * @param oS the oS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oS,
     * or with status {@code 400 (Bad Request)} if the oS is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OS> updateOS(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody OS oS)
        throws URISyntaxException {
        LOG.debug("REST request to update OS : {}, {}", id, oS);
        if (oS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oS.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oSRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        oS = oSRepository.save(oS);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oS.getId().toString()))
            .body(oS);
    }

    /**
     * {@code PATCH  /os/:id} : Partial updates given fields of an existing oS, field will ignore if it is null
     *
     * @param id the id of the oS to save.
     * @param oS the oS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oS,
     * or with status {@code 400 (Bad Request)} if the oS is not valid,
     * or with status {@code 404 (Not Found)} if the oS is not found,
     * or with status {@code 500 (Internal Server Error)} if the oS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OS> partialUpdateOS(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody OS oS)
        throws URISyntaxException {
        LOG.debug("REST request to partial update OS partially : {}, {}", id, oS);
        if (oS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oS.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oSRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OS> result = oSRepository
            .findById(oS.getId())
            .map(existingOS -> {
                if (oS.getName() != null) {
                    existingOS.setName(oS.getName());
                }
                if (oS.getCreaDate() != null) {
                    existingOS.setCreaDate(oS.getCreaDate());
                }
                if (oS.getUpdateDate() != null) {
                    existingOS.setUpdateDate(oS.getUpdateDate());
                }
                if (oS.getNotes() != null) {
                    existingOS.setNotes(oS.getNotes());
                }

                return existingOS;
            })
            .map(oSRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oS.getId().toString())
        );
    }

    /**
     * {@code GET  /os} : get all the oS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of oS in body.
     */
    @GetMapping("")
    public List<OS> getAllOS() {
        LOG.debug("REST request to get all OS");
        return oSRepository.findAll();
    }

    /**
     * {@code GET  /os/:id} : get the "id" oS.
     *
     * @param id the id of the oS to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oS, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OS> getOS(@PathVariable("id") Long id) {
        LOG.debug("REST request to get OS : {}", id);
        Optional<OS> oS = oSRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(oS);
    }

    /**
     * {@code DELETE  /os/:id} : delete the "id" oS.
     *
     * @param id the id of the oS to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOS(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete OS : {}", id);
        oSRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
