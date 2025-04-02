package com.sdi.web.rest;

import static com.sdi.domain.ActionRequestAsserts.*;
import static com.sdi.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdi.IntegrationTest;
import com.sdi.domain.ActionRequest;
import com.sdi.domain.enumeration.ActionRequestStatus;
import com.sdi.repository.ActionRequestRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ActionRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActionRequestResourceIT {

    private static final String DEFAULT_ENTITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_DATA = "AAAAAAAAAA";
    private static final String UPDATED_NEW_DATA = "BBBBBBBBBB";

    private static final ActionRequestStatus DEFAULT_STATUS = ActionRequestStatus.PENDING;
    private static final ActionRequestStatus UPDATED_STATUS = ActionRequestStatus.APPROVED;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVED_BY = "AAAAAAAAAA";
    private static final String UPDATED_APPROVED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/action-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ActionRequestRepository actionRequestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActionRequestMockMvc;

    private ActionRequest actionRequest;

    private ActionRequest insertedActionRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActionRequest createEntity() {
        return new ActionRequest()
            .entityType(DEFAULT_ENTITY_TYPE)
            .newData(DEFAULT_NEW_DATA)
            .status(DEFAULT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .approvedBy(DEFAULT_APPROVED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActionRequest createUpdatedEntity() {
        return new ActionRequest()
            .entityType(UPDATED_ENTITY_TYPE)
            .newData(UPDATED_NEW_DATA)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .approvedBy(UPDATED_APPROVED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
    }

    @BeforeEach
    public void initTest() {
        actionRequest = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedActionRequest != null) {
            actionRequestRepository.delete(insertedActionRequest);
            insertedActionRequest = null;
        }
    }

    @Test
    @Transactional
    void createActionRequest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ActionRequest
        var returnedActionRequest = om.readValue(
            restActionRequestMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(actionRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ActionRequest.class
        );

        // Validate the ActionRequest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertActionRequestUpdatableFieldsEquals(returnedActionRequest, getPersistedActionRequest(returnedActionRequest));

        insertedActionRequest = returnedActionRequest;
    }

    @Test
    @Transactional
    void createActionRequestWithExistingId() throws Exception {
        // Create the ActionRequest with an existing ID
        actionRequest.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(actionRequest)))
            .andExpect(status().isBadRequest());

        // Validate the ActionRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEntityTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        actionRequest.setEntityType(null);

        // Create the ActionRequest, which fails.

        restActionRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(actionRequest)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        actionRequest.setStatus(null);

        // Create the ActionRequest, which fails.

        restActionRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(actionRequest)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        actionRequest.setCreatedBy(null);

        // Create the ActionRequest, which fails.

        restActionRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(actionRequest)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllActionRequests() throws Exception {
        // Initialize the database
        insertedActionRequest = actionRequestRepository.saveAndFlush(actionRequest);

        // Get all the actionRequestList
        restActionRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actionRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE)))
            .andExpect(jsonPath("$.[*].newData").value(hasItem(DEFAULT_NEW_DATA)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getActionRequest() throws Exception {
        // Initialize the database
        insertedActionRequest = actionRequestRepository.saveAndFlush(actionRequest);

        // Get the actionRequest
        restActionRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, actionRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(actionRequest.getId().intValue()))
            .andExpect(jsonPath("$.entityType").value(DEFAULT_ENTITY_TYPE))
            .andExpect(jsonPath("$.newData").value(DEFAULT_NEW_DATA))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.approvedBy").value(DEFAULT_APPROVED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingActionRequest() throws Exception {
        // Get the actionRequest
        restActionRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActionRequest() throws Exception {
        // Initialize the database
        insertedActionRequest = actionRequestRepository.saveAndFlush(actionRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the actionRequest
        ActionRequest updatedActionRequest = actionRequestRepository.findById(actionRequest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedActionRequest are not directly saved in db
        em.detach(updatedActionRequest);
        updatedActionRequest
            .entityType(UPDATED_ENTITY_TYPE)
            .newData(UPDATED_NEW_DATA)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .approvedBy(UPDATED_APPROVED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restActionRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActionRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedActionRequest))
            )
            .andExpect(status().isOk());

        // Validate the ActionRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedActionRequestToMatchAllProperties(updatedActionRequest);
    }

    @Test
    @Transactional
    void putNonExistingActionRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        actionRequest.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, actionRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(actionRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActionRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActionRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        actionRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(actionRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActionRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActionRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        actionRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionRequestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(actionRequest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActionRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActionRequestWithPatch() throws Exception {
        // Initialize the database
        insertedActionRequest = actionRequestRepository.saveAndFlush(actionRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the actionRequest using partial update
        ActionRequest partialUpdatedActionRequest = new ActionRequest();
        partialUpdatedActionRequest.setId(actionRequest.getId());

        partialUpdatedActionRequest.status(UPDATED_STATUS).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restActionRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActionRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedActionRequest))
            )
            .andExpect(status().isOk());

        // Validate the ActionRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertActionRequestUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedActionRequest, actionRequest),
            getPersistedActionRequest(actionRequest)
        );
    }

    @Test
    @Transactional
    void fullUpdateActionRequestWithPatch() throws Exception {
        // Initialize the database
        insertedActionRequest = actionRequestRepository.saveAndFlush(actionRequest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the actionRequest using partial update
        ActionRequest partialUpdatedActionRequest = new ActionRequest();
        partialUpdatedActionRequest.setId(actionRequest.getId());

        partialUpdatedActionRequest
            .entityType(UPDATED_ENTITY_TYPE)
            .newData(UPDATED_NEW_DATA)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .approvedBy(UPDATED_APPROVED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restActionRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActionRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedActionRequest))
            )
            .andExpect(status().isOk());

        // Validate the ActionRequest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertActionRequestUpdatableFieldsEquals(partialUpdatedActionRequest, getPersistedActionRequest(partialUpdatedActionRequest));
    }

    @Test
    @Transactional
    void patchNonExistingActionRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        actionRequest.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, actionRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(actionRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActionRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActionRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        actionRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(actionRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActionRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActionRequest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        actionRequest.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActionRequestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(actionRequest)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActionRequest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActionRequest() throws Exception {
        // Initialize the database
        insertedActionRequest = actionRequestRepository.saveAndFlush(actionRequest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the actionRequest
        restActionRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, actionRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return actionRequestRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected ActionRequest getPersistedActionRequest(ActionRequest actionRequest) {
        return actionRequestRepository.findById(actionRequest.getId()).orElseThrow();
    }

    protected void assertPersistedActionRequestToMatchAllProperties(ActionRequest expectedActionRequest) {
        assertActionRequestAllPropertiesEquals(expectedActionRequest, getPersistedActionRequest(expectedActionRequest));
    }

    protected void assertPersistedActionRequestToMatchUpdatableProperties(ActionRequest expectedActionRequest) {
        assertActionRequestAllUpdatablePropertiesEquals(expectedActionRequest, getPersistedActionRequest(expectedActionRequest));
    }
}
