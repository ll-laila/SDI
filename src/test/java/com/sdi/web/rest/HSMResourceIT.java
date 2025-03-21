package com.sdi.web.rest;

import static com.sdi.domain.HSMAsserts.*;
import static com.sdi.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdi.IntegrationTest;
import com.sdi.domain.HSM;
import com.sdi.repository.HSMRepository;
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
 * Integration tests for the {@link HSMResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HSMResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREA_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREA_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hsms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HSMRepository hSMRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHSMMockMvc;

    private HSM hSM;

    private HSM insertedHSM;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HSM createEntity() {
        return new HSM().name(DEFAULT_NAME).creaDate(DEFAULT_CREA_DATE).updateDate(DEFAULT_UPDATE_DATE).notes(DEFAULT_NOTES);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HSM createUpdatedEntity() {
        return new HSM().name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);
    }

    @BeforeEach
    public void initTest() {
        hSM = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedHSM != null) {
            hSMRepository.delete(insertedHSM);
            insertedHSM = null;
        }
    }

    @Test
    @Transactional
    void createHSM() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HSM
        var returnedHSM = om.readValue(
            restHSMMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hSM)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HSM.class
        );

        // Validate the HSM in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHSMUpdatableFieldsEquals(returnedHSM, getPersistedHSM(returnedHSM));

        insertedHSM = returnedHSM;
    }

    @Test
    @Transactional
    void createHSMWithExistingId() throws Exception {
        // Create the HSM with an existing ID
        hSM.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHSMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hSM)))
            .andExpect(status().isBadRequest());

        // Validate the HSM in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hSM.setName(null);

        // Create the HSM, which fails.

        restHSMMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hSM)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHSMS() throws Exception {
        // Initialize the database
        insertedHSM = hSMRepository.saveAndFlush(hSM);

        // Get all the hSMList
        restHSMMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hSM.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].creaDate").value(hasItem(DEFAULT_CREA_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getHSM() throws Exception {
        // Initialize the database
        insertedHSM = hSMRepository.saveAndFlush(hSM);

        // Get the hSM
        restHSMMockMvc
            .perform(get(ENTITY_API_URL_ID, hSM.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hSM.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.creaDate").value(DEFAULT_CREA_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingHSM() throws Exception {
        // Get the hSM
        restHSMMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHSM() throws Exception {
        // Initialize the database
        insertedHSM = hSMRepository.saveAndFlush(hSM);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hSM
        HSM updatedHSM = hSMRepository.findById(hSM.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHSM are not directly saved in db
        em.detach(updatedHSM);
        updatedHSM.name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);

        restHSMMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHSM.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(updatedHSM))
            )
            .andExpect(status().isOk());

        // Validate the HSM in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHSMToMatchAllProperties(updatedHSM);
    }

    @Test
    @Transactional
    void putNonExistingHSM() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hSM.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHSMMockMvc
            .perform(put(ENTITY_API_URL_ID, hSM.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hSM)))
            .andExpect(status().isBadRequest());

        // Validate the HSM in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHSM() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hSM.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHSMMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hSM))
            )
            .andExpect(status().isBadRequest());

        // Validate the HSM in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHSM() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hSM.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHSMMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hSM)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HSM in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHSMWithPatch() throws Exception {
        // Initialize the database
        insertedHSM = hSMRepository.saveAndFlush(hSM);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hSM using partial update
        HSM partialUpdatedHSM = new HSM();
        partialUpdatedHSM.setId(hSM.getId());

        partialUpdatedHSM.name(UPDATED_NAME);

        restHSMMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHSM.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHSM))
            )
            .andExpect(status().isOk());

        // Validate the HSM in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHSMUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedHSM, hSM), getPersistedHSM(hSM));
    }

    @Test
    @Transactional
    void fullUpdateHSMWithPatch() throws Exception {
        // Initialize the database
        insertedHSM = hSMRepository.saveAndFlush(hSM);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hSM using partial update
        HSM partialUpdatedHSM = new HSM();
        partialUpdatedHSM.setId(hSM.getId());

        partialUpdatedHSM.name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);

        restHSMMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHSM.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHSM))
            )
            .andExpect(status().isOk());

        // Validate the HSM in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHSMUpdatableFieldsEquals(partialUpdatedHSM, getPersistedHSM(partialUpdatedHSM));
    }

    @Test
    @Transactional
    void patchNonExistingHSM() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hSM.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHSMMockMvc
            .perform(patch(ENTITY_API_URL_ID, hSM.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hSM)))
            .andExpect(status().isBadRequest());

        // Validate the HSM in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHSM() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hSM.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHSMMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hSM))
            )
            .andExpect(status().isBadRequest());

        // Validate the HSM in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHSM() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hSM.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHSMMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hSM)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HSM in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHSM() throws Exception {
        // Initialize the database
        insertedHSM = hSMRepository.saveAndFlush(hSM);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hSM
        restHSMMockMvc.perform(delete(ENTITY_API_URL_ID, hSM.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hSMRepository.count();
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

    protected HSM getPersistedHSM(HSM hSM) {
        return hSMRepository.findById(hSM.getId()).orElseThrow();
    }

    protected void assertPersistedHSMToMatchAllProperties(HSM expectedHSM) {
        assertHSMAllPropertiesEquals(expectedHSM, getPersistedHSM(expectedHSM));
    }

    protected void assertPersistedHSMToMatchUpdatableProperties(HSM expectedHSM) {
        assertHSMAllUpdatablePropertiesEquals(expectedHSM, getPersistedHSM(expectedHSM));
    }
}
