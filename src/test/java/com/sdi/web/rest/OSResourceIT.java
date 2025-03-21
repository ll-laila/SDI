package com.sdi.web.rest;

import static com.sdi.domain.OSAsserts.*;
import static com.sdi.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdi.IntegrationTest;
import com.sdi.domain.OS;
import com.sdi.repository.OSRepository;
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
 * Integration tests for the {@link OSResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OSResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREA_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREA_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/os";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OSRepository oSRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOSMockMvc;

    private OS oS;

    private OS insertedOS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OS createEntity() {
        return new OS().name(DEFAULT_NAME).creaDate(DEFAULT_CREA_DATE).updateDate(DEFAULT_UPDATE_DATE).notes(DEFAULT_NOTES);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OS createUpdatedEntity() {
        return new OS().name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);
    }

    @BeforeEach
    public void initTest() {
        oS = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedOS != null) {
            oSRepository.delete(insertedOS);
            insertedOS = null;
        }
    }

    @Test
    @Transactional
    void createOS() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OS
        var returnedOS = om.readValue(
            restOSMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(oS)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OS.class
        );

        // Validate the OS in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOSUpdatableFieldsEquals(returnedOS, getPersistedOS(returnedOS));

        insertedOS = returnedOS;
    }

    @Test
    @Transactional
    void createOSWithExistingId() throws Exception {
        // Create the OS with an existing ID
        oS.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(oS)))
            .andExpect(status().isBadRequest());

        // Validate the OS in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        oS.setName(null);

        // Create the OS, which fails.

        restOSMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(oS)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOS() throws Exception {
        // Initialize the database
        insertedOS = oSRepository.saveAndFlush(oS);

        // Get all the oSList
        restOSMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oS.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].creaDate").value(hasItem(DEFAULT_CREA_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getOS() throws Exception {
        // Initialize the database
        insertedOS = oSRepository.saveAndFlush(oS);

        // Get the oS
        restOSMockMvc
            .perform(get(ENTITY_API_URL_ID, oS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(oS.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.creaDate").value(DEFAULT_CREA_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingOS() throws Exception {
        // Get the oS
        restOSMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOS() throws Exception {
        // Initialize the database
        insertedOS = oSRepository.saveAndFlush(oS);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the oS
        OS updatedOS = oSRepository.findById(oS.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOS are not directly saved in db
        em.detach(updatedOS);
        updatedOS.name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);

        restOSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOS.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(updatedOS))
            )
            .andExpect(status().isOk());

        // Validate the OS in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOSToMatchAllProperties(updatedOS);
    }

    @Test
    @Transactional
    void putNonExistingOS() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oS.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOSMockMvc
            .perform(put(ENTITY_API_URL_ID, oS.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(oS)))
            .andExpect(status().isBadRequest());

        // Validate the OS in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOS() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oS.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOSMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(oS))
            )
            .andExpect(status().isBadRequest());

        // Validate the OS in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOS() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oS.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOSMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(oS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OS in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOSWithPatch() throws Exception {
        // Initialize the database
        insertedOS = oSRepository.saveAndFlush(oS);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the oS using partial update
        OS partialUpdatedOS = new OS();
        partialUpdatedOS.setId(oS.getId());

        partialUpdatedOS.updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);

        restOSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOS.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOS))
            )
            .andExpect(status().isOk());

        // Validate the OS in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOSUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedOS, oS), getPersistedOS(oS));
    }

    @Test
    @Transactional
    void fullUpdateOSWithPatch() throws Exception {
        // Initialize the database
        insertedOS = oSRepository.saveAndFlush(oS);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the oS using partial update
        OS partialUpdatedOS = new OS();
        partialUpdatedOS.setId(oS.getId());

        partialUpdatedOS.name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);

        restOSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOS.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOS))
            )
            .andExpect(status().isOk());

        // Validate the OS in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOSUpdatableFieldsEquals(partialUpdatedOS, getPersistedOS(partialUpdatedOS));
    }

    @Test
    @Transactional
    void patchNonExistingOS() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oS.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOSMockMvc
            .perform(patch(ENTITY_API_URL_ID, oS.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(oS)))
            .andExpect(status().isBadRequest());

        // Validate the OS in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOS() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oS.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOSMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(oS))
            )
            .andExpect(status().isBadRequest());

        // Validate the OS in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOS() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        oS.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOSMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(oS)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OS in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOS() throws Exception {
        // Initialize the database
        insertedOS = oSRepository.saveAndFlush(oS);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the oS
        restOSMockMvc.perform(delete(ENTITY_API_URL_ID, oS.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return oSRepository.count();
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

    protected OS getPersistedOS(OS oS) {
        return oSRepository.findById(oS.getId()).orElseThrow();
    }

    protected void assertPersistedOSToMatchAllProperties(OS expectedOS) {
        assertOSAllPropertiesEquals(expectedOS, getPersistedOS(expectedOS));
    }

    protected void assertPersistedOSToMatchUpdatableProperties(OS expectedOS) {
        assertOSAllUpdatablePropertiesEquals(expectedOS, getPersistedOS(expectedOS));
    }
}
