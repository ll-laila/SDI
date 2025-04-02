package com.sdi.web.rest;

import static com.sdi.domain.DBAsserts.*;
import static com.sdi.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdi.IntegrationTest;
import com.sdi.domain.DB;
import com.sdi.repository.DBRepository;
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
 * Integration tests for the {@link DBResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DBResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREA_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREA_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dbs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DBRepository dBRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDBMockMvc;

    private DB dB;

    private DB insertedDB;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DB createEntity() {
        return new DB().name(DEFAULT_NAME).creaDate(DEFAULT_CREA_DATE).updateDate(DEFAULT_UPDATE_DATE).notes(DEFAULT_NOTES);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DB createUpdatedEntity() {
        return new DB().name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);
    }

    @BeforeEach
    public void initTest() {
        dB = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDB != null) {
            dBRepository.delete(insertedDB);
            insertedDB = null;
        }
    }

    @Test
    @Transactional
    void createDB() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DB
        var returnedDB = om.readValue(
            restDBMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dB)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DB.class
        );

        // Validate the DB in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDBUpdatableFieldsEquals(returnedDB, getPersistedDB(returnedDB));

        insertedDB = returnedDB;
    }

    @Test
    @Transactional
    void createDBWithExistingId() throws Exception {
        // Create the DB with an existing ID
        dB.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDBMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dB)))
            .andExpect(status().isBadRequest());

        // Validate the DB in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        dB.setName(null);

        // Create the DB, which fails.

        restDBMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dB)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDBS() throws Exception {
        // Initialize the database
        insertedDB = dBRepository.saveAndFlush(dB);

        // Get all the dBList
        restDBMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dB.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].creaDate").value(hasItem(DEFAULT_CREA_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getDB() throws Exception {
        // Initialize the database
        insertedDB = dBRepository.saveAndFlush(dB);

        // Get the dB
        restDBMockMvc
            .perform(get(ENTITY_API_URL_ID, dB.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dB.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.creaDate").value(DEFAULT_CREA_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingDB() throws Exception {
        // Get the dB
        restDBMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDB() throws Exception {
        // Initialize the database
        insertedDB = dBRepository.saveAndFlush(dB);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dB
        DB updatedDB = dBRepository.findById(dB.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDB are not directly saved in db
        em.detach(updatedDB);
        updatedDB.name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);

        restDBMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDB.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(updatedDB))
            )
            .andExpect(status().isOk());

        // Validate the DB in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDBToMatchAllProperties(updatedDB);
    }

    @Test
    @Transactional
    void putNonExistingDB() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dB.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDBMockMvc
            .perform(put(ENTITY_API_URL_ID, dB.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dB)))
            .andExpect(status().isBadRequest());

        // Validate the DB in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDB() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dB.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDBMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(dB))
            )
            .andExpect(status().isBadRequest());

        // Validate the DB in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDB() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dB.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDBMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(dB)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DB in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDBWithPatch() throws Exception {
        // Initialize the database
        insertedDB = dBRepository.saveAndFlush(dB);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dB using partial update
        DB partialUpdatedDB = new DB();
        partialUpdatedDB.setId(dB.getId());

        partialUpdatedDB.name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).notes(UPDATED_NOTES);

        restDBMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDB.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDB))
            )
            .andExpect(status().isOk());

        // Validate the DB in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDBUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDB, dB), getPersistedDB(dB));
    }

    @Test
    @Transactional
    void fullUpdateDBWithPatch() throws Exception {
        // Initialize the database
        insertedDB = dBRepository.saveAndFlush(dB);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the dB using partial update
        DB partialUpdatedDB = new DB();
        partialUpdatedDB.setId(dB.getId());

        partialUpdatedDB.name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);

        restDBMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDB.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDB))
            )
            .andExpect(status().isOk());

        // Validate the DB in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDBUpdatableFieldsEquals(partialUpdatedDB, getPersistedDB(partialUpdatedDB));
    }

    @Test
    @Transactional
    void patchNonExistingDB() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dB.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDBMockMvc
            .perform(patch(ENTITY_API_URL_ID, dB.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dB)))
            .andExpect(status().isBadRequest());

        // Validate the DB in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDB() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dB.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDBMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(dB))
            )
            .andExpect(status().isBadRequest());

        // Validate the DB in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDB() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        dB.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDBMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(dB)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DB in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDB() throws Exception {
        // Initialize the database
        insertedDB = dBRepository.saveAndFlush(dB);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the dB
        restDBMockMvc.perform(delete(ENTITY_API_URL_ID, dB.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return dBRepository.count();
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

    protected DB getPersistedDB(DB dB) {
        return dBRepository.findById(dB.getId()).orElseThrow();
    }

    protected void assertPersistedDBToMatchAllProperties(DB expectedDB) {
        assertDBAllPropertiesEquals(expectedDB, getPersistedDB(expectedDB));
    }

    protected void assertPersistedDBToMatchUpdatableProperties(DB expectedDB) {
        assertDBAllUpdatablePropertiesEquals(expectedDB, getPersistedDB(expectedDB));
    }
}
