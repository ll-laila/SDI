package com.sdi.web.rest;

import com.sdi.domain.DB;
import com.sdi.repository.DBRepository;
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
 * REST controller for managing {@link com.sdi.domain.DB}.
 */
@RestController
@RequestMapping("/api/dbs")
@Transactional
public class DBResource {

    private static final Logger LOG = LoggerFactory.getLogger(DBResource.class);

    private static final String ENTITY_NAME = "dB";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DBRepository dBRepository;

    public DBResource(DBRepository dBRepository) {
        this.dBRepository = dBRepository;
    }

    /**
     * {@code POST  /dbs} : Create a new dB.
     *
     * @param dB the dB to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dB, or with status {@code 400 (Bad Request)} if the dB has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DB> createDB(@Valid @RequestBody DB dB) throws URISyntaxException {
        LOG.debug("REST request to save DB : {}", dB);
        if (dB.getId() != null) {
            throw new BadRequestAlertException("A new dB cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dB = dBRepository.save(dB);
        return ResponseEntity.created(new URI("/api/dbs/" + dB.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dB.getId().toString()))
            .body(dB);
    }

    /**
     * {@code PUT  /dbs/:id} : Updates an existing dB.
     *
     * @param id the id of the dB to save.
     * @param dB the dB to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dB,
     * or with status {@code 400 (Bad Request)} if the dB is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dB couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DB> updateDB(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody DB dB)
        throws URISyntaxException {
        LOG.debug("REST request to update DB : {}, {}", id, dB);
        if (dB.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dB.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dBRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        dB = dBRepository.save(dB);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dB.getId().toString()))
            .body(dB);
    }

    /**
     * {@code PATCH  /dbs/:id} : Partial updates given fields of an existing dB, field will ignore if it is null
     *
     * @param id the id of the dB to save.
     * @param dB the dB to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dB,
     * or with status {@code 400 (Bad Request)} if the dB is not valid,
     * or with status {@code 404 (Not Found)} if the dB is not found,
     * or with status {@code 500 (Internal Server Error)} if the dB couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DB> partialUpdateDB(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody DB dB)
        throws URISyntaxException {
        LOG.debug("REST request to partial update DB partially : {}, {}", id, dB);
        if (dB.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dB.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dBRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DB> result = dBRepository
            .findById(dB.getId())
            .map(existingDB -> {
                if (dB.getName() != null) {
                    existingDB.setName(dB.getName());
                }
                if (dB.getCreaDate() != null) {
                    existingDB.setCreaDate(dB.getCreaDate());
                }
                if (dB.getUpdateDate() != null) {
                    existingDB.setUpdateDate(dB.getUpdateDate());
                }
                if (dB.getNotes() != null) {
                    existingDB.setNotes(dB.getNotes());
                }

                return existingDB;
            })
            .map(dBRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dB.getId().toString())
        );
    }

    /**
     * {@code GET  /dbs} : get all the dBS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dBS in body.
     */
    @GetMapping("")
    public List<DB> getAllDBS() {
        LOG.debug("REST request to get all DBS");
        return dBRepository.findAll();
    }

    /**
     * {@code GET  /dbs/:id} : get the "id" dB.
     *
     * @param id the id of the dB to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dB, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DB> getDB(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DB : {}", id);
        Optional<DB> dB = dBRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dB);
    }

    /**
     * {@code DELETE  /dbs/:id} : delete the "id" dB.
     *
     * @param id the id of the dB to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDB(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DB : {}", id);
        dBRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
