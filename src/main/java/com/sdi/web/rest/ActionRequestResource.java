package com.sdi.web.rest;

import com.sdi.domain.ActionRequest;
import com.sdi.repository.ActionRequestRepository;
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
 * REST controller for managing {@link com.sdi.domain.ActionRequest}.
 */
@RestController
@RequestMapping("/api/action-requests")
@Transactional
public class ActionRequestResource {

    private static final Logger LOG = LoggerFactory.getLogger(ActionRequestResource.class);

    private static final String ENTITY_NAME = "actionRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActionRequestRepository actionRequestRepository;

    public ActionRequestResource(ActionRequestRepository actionRequestRepository) {
        this.actionRequestRepository = actionRequestRepository;
    }

    /**
     * {@code POST  /action-requests} : Create a new actionRequest.
     *
     * @param actionRequest the actionRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actionRequest, or with status {@code 400 (Bad Request)} if the actionRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ActionRequest> createActionRequest(@Valid @RequestBody ActionRequest actionRequest) throws URISyntaxException {
        LOG.debug("REST request to save ActionRequest : {}", actionRequest);
        if (actionRequest.getId() != null) {
            throw new BadRequestAlertException("A new actionRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        actionRequest = actionRequestRepository.save(actionRequest);
        return ResponseEntity.created(new URI("/api/action-requests/" + actionRequest.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, actionRequest.getId().toString()))
            .body(actionRequest);
    }

    /**
     * {@code PUT  /action-requests/:id} : Updates an existing actionRequest.
     *
     * @param id the id of the actionRequest to save.
     * @param actionRequest the actionRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actionRequest,
     * or with status {@code 400 (Bad Request)} if the actionRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the actionRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ActionRequest> updateActionRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ActionRequest actionRequest
    ) throws URISyntaxException {
        LOG.debug("REST request to update ActionRequest : {}, {}", id, actionRequest);
        if (actionRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, actionRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!actionRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        actionRequest = actionRequestRepository.save(actionRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actionRequest.getId().toString()))
            .body(actionRequest);
    }

    /**
     * {@code PATCH  /action-requests/:id} : Partial updates given fields of an existing actionRequest, field will ignore if it is null
     *
     * @param id the id of the actionRequest to save.
     * @param actionRequest the actionRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actionRequest,
     * or with status {@code 400 (Bad Request)} if the actionRequest is not valid,
     * or with status {@code 404 (Not Found)} if the actionRequest is not found,
     * or with status {@code 500 (Internal Server Error)} if the actionRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ActionRequest> partialUpdateActionRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ActionRequest actionRequest
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ActionRequest partially : {}, {}", id, actionRequest);
        if (actionRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, actionRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!actionRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActionRequest> result = actionRequestRepository
            .findById(actionRequest.getId())
            .map(existingActionRequest -> {
                if (actionRequest.getEntityType() != null) {
                    existingActionRequest.setEntityType(actionRequest.getEntityType());
                }
                if (actionRequest.getNewData() != null) {
                    existingActionRequest.setNewData(actionRequest.getNewData());
                }
                if (actionRequest.getStatus() != null) {
                    existingActionRequest.setStatus(actionRequest.getStatus());
                }
                if (actionRequest.getCreatedBy() != null) {
                    existingActionRequest.setCreatedBy(actionRequest.getCreatedBy());
                }
                if (actionRequest.getApprovedBy() != null) {
                    existingActionRequest.setApprovedBy(actionRequest.getApprovedBy());
                }
                if (actionRequest.getCreatedAt() != null) {
                    existingActionRequest.setCreatedAt(actionRequest.getCreatedAt());
                }
                if (actionRequest.getUpdatedAt() != null) {
                    existingActionRequest.setUpdatedAt(actionRequest.getUpdatedAt());
                }

                return existingActionRequest;
            })
            .map(actionRequestRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actionRequest.getId().toString())
        );
    }

    /**
     * {@code GET  /action-requests} : get all the actionRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actionRequests in body.
     */
    @GetMapping("")
    public List<ActionRequest> getAllActionRequests() {
        LOG.debug("REST request to get all ActionRequests");
        return actionRequestRepository.findAll();
    }

    /**
     * {@code GET  /action-requests/:id} : get the "id" actionRequest.
     *
     * @param id the id of the actionRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the actionRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActionRequest> getActionRequest(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ActionRequest : {}", id);
        Optional<ActionRequest> actionRequest = actionRequestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(actionRequest);
    }

    /**
     * {@code DELETE  /action-requests/:id} : delete the "id" actionRequest.
     *
     * @param id the id of the actionRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActionRequest(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ActionRequest : {}", id);
        actionRequestRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
