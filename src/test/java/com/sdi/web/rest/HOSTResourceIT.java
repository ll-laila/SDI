package com.sdi.web.rest;

import static com.sdi.domain.HOSTAsserts.*;
import static com.sdi.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdi.IntegrationTest;
import com.sdi.domain.HOST;
import com.sdi.repository.HOSTRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link HOSTResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HOSTResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREA_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREA_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hosts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HOSTRepository hOSTRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHOSTMockMvc;

    private HOST hOST;

    private HOST insertedHOST;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HOST createEntity() {
        return new HOST().name(DEFAULT_NAME).creaDate(DEFAULT_CREA_DATE).updateDate(DEFAULT_UPDATE_DATE).notes(DEFAULT_NOTES);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HOST createUpdatedEntity() {
        return new HOST().name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);
    }

    @BeforeEach
    public void initTest() {
        hOST = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedHOST != null) {
            hOSTRepository.delete(insertedHOST);
            insertedHOST = null;
        }
    }

    @Test
    @Transactional
    void createHOST() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HOST
        var returnedHOST = om.readValue(
            restHOSTMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hOST)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HOST.class
        );

        // Validate the HOST in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHOSTUpdatableFieldsEquals(returnedHOST, getPersistedHOST(returnedHOST));

        insertedHOST = returnedHOST;
    }

    @Test
    @Transactional
    void createHOSTWithExistingId() throws Exception {
        // Create the HOST with an existing ID
        hOST.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHOSTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hOST)))
            .andExpect(status().isBadRequest());

        // Validate the HOST in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hOST.setName(null);

        // Create the HOST, which fails.

        restHOSTMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hOST)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHOSTS() throws Exception {
        // Initialize the database
        insertedHOST = hOSTRepository.saveAndFlush(hOST);

        // Get all the hOSTList
        restHOSTMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hOST.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].creaDate").value(hasItem(DEFAULT_CREA_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getHOST() throws Exception {
        // Initialize the database
        insertedHOST = hOSTRepository.saveAndFlush(hOST);

        // Get the hOST
        restHOSTMockMvc
            .perform(get(ENTITY_API_URL_ID, hOST.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hOST.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.creaDate").value(DEFAULT_CREA_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingHOST() throws Exception {
        // Get the hOST
        restHOSTMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHOST() throws Exception {
        // Initialize the database
        insertedHOST = hOSTRepository.saveAndFlush(hOST);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hOST
        HOST updatedHOST = hOSTRepository.findById(hOST.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHOST are not directly saved in db
        em.detach(updatedHOST);
        updatedHOST.name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);

        restHOSTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHOST.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedHOST))
            )
            .andExpect(status().isOk());

        // Validate the HOST in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHOSTToMatchAllProperties(updatedHOST);
    }

    @Test
    @Transactional
    void putNonExistingHOST() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hOST.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHOSTMockMvc
            .perform(put(ENTITY_API_URL_ID, hOST.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hOST)))
            .andExpect(status().isBadRequest());

        // Validate the HOST in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHOST() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hOST.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHOSTMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hOST))
            )
            .andExpect(status().isBadRequest());

        // Validate the HOST in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHOST() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hOST.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHOSTMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hOST)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HOST in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHOSTWithPatch() throws Exception {
        // Initialize the database
        insertedHOST = hOSTRepository.saveAndFlush(hOST);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hOST using partial update
        HOST partialUpdatedHOST = new HOST();
        partialUpdatedHOST.setId(hOST.getId());

        restHOSTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHOST.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHOST))
            )
            .andExpect(status().isOk());

        // Validate the HOST in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHOSTUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedHOST, hOST), getPersistedHOST(hOST));
    }

    @Test
    @Transactional
    void fullUpdateHOSTWithPatch() throws Exception {
        // Initialize the database
        insertedHOST = hOSTRepository.saveAndFlush(hOST);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hOST using partial update
        HOST partialUpdatedHOST = new HOST();
        partialUpdatedHOST.setId(hOST.getId());

        partialUpdatedHOST.name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);

        restHOSTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHOST.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHOST))
            )
            .andExpect(status().isOk());

        // Validate the HOST in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHOSTUpdatableFieldsEquals(partialUpdatedHOST, getPersistedHOST(partialUpdatedHOST));
    }

    @Test
    @Transactional
    void patchNonExistingHOST() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hOST.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHOSTMockMvc
            .perform(patch(ENTITY_API_URL_ID, hOST.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hOST)))
            .andExpect(status().isBadRequest());

        // Validate the HOST in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHOST() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hOST.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHOSTMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hOST))
            )
            .andExpect(status().isBadRequest());

        // Validate the HOST in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHOST() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hOST.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHOSTMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hOST)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HOST in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHOST() throws Exception {
        // Initialize the database
        insertedHOST = hOSTRepository.saveAndFlush(hOST);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hOST
        restHOSTMockMvc
            .perform(delete(ENTITY_API_URL_ID, hOST.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hOSTRepository.count();
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

    protected HOST getPersistedHOST(HOST hOST) {
        return hOSTRepository.findById(hOST.getId()).orElseThrow();
    }

    protected void assertPersistedHOSTToMatchAllProperties(HOST expectedHOST) {
        assertHOSTAllPropertiesEquals(expectedHOST, getPersistedHOST(expectedHOST));
    }

    protected void assertPersistedHOSTToMatchUpdatableProperties(HOST expectedHOST) {
        assertHOSTAllUpdatablePropertiesEquals(expectedHOST, getPersistedHOST(expectedHOST));
    }
}
