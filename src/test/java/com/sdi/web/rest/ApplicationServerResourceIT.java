package com.sdi.web.rest;

import static com.sdi.domain.ApplicationServerAsserts.*;
import static com.sdi.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdi.IntegrationTest;
import com.sdi.domain.ApplicationServer;
import com.sdi.repository.ApplicationServerRepository;
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
 * Integration tests for the {@link ApplicationServerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApplicationServerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREA_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREA_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/application-servers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ApplicationServerRepository applicationServerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationServerMockMvc;

    private ApplicationServer applicationServer;

    private ApplicationServer insertedApplicationServer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationServer createEntity() {
        return new ApplicationServer().name(DEFAULT_NAME).creaDate(DEFAULT_CREA_DATE).updateDate(DEFAULT_UPDATE_DATE).notes(DEFAULT_NOTES);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationServer createUpdatedEntity() {
        return new ApplicationServer().name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);
    }

    @BeforeEach
    public void initTest() {
        applicationServer = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedApplicationServer != null) {
            applicationServerRepository.delete(insertedApplicationServer);
            insertedApplicationServer = null;
        }
    }

    @Test
    @Transactional
    void createApplicationServer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ApplicationServer
        var returnedApplicationServer = om.readValue(
            restApplicationServerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(applicationServer)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ApplicationServer.class
        );

        // Validate the ApplicationServer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertApplicationServerUpdatableFieldsEquals(returnedApplicationServer, getPersistedApplicationServer(returnedApplicationServer));

        insertedApplicationServer = returnedApplicationServer;
    }

    @Test
    @Transactional
    void createApplicationServerWithExistingId() throws Exception {
        // Create the ApplicationServer with an existing ID
        applicationServer.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationServerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(applicationServer)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationServer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        applicationServer.setName(null);

        // Create the ApplicationServer, which fails.

        restApplicationServerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(applicationServer)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApplicationServers() throws Exception {
        // Initialize the database
        insertedApplicationServer = applicationServerRepository.saveAndFlush(applicationServer);

        // Get all the applicationServerList
        restApplicationServerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationServer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].creaDate").value(hasItem(DEFAULT_CREA_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getApplicationServer() throws Exception {
        // Initialize the database
        insertedApplicationServer = applicationServerRepository.saveAndFlush(applicationServer);

        // Get the applicationServer
        restApplicationServerMockMvc
            .perform(get(ENTITY_API_URL_ID, applicationServer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationServer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.creaDate").value(DEFAULT_CREA_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingApplicationServer() throws Exception {
        // Get the applicationServer
        restApplicationServerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApplicationServer() throws Exception {
        // Initialize the database
        insertedApplicationServer = applicationServerRepository.saveAndFlush(applicationServer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the applicationServer
        ApplicationServer updatedApplicationServer = applicationServerRepository.findById(applicationServer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedApplicationServer are not directly saved in db
        em.detach(updatedApplicationServer);
        updatedApplicationServer.name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);

        restApplicationServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApplicationServer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedApplicationServer))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationServer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedApplicationServerToMatchAllProperties(updatedApplicationServer);
    }

    @Test
    @Transactional
    void putNonExistingApplicationServer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicationServer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationServer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(applicationServer))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationServer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplicationServer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicationServer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(applicationServer))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationServer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplicationServer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicationServer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationServerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(applicationServer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationServer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationServerWithPatch() throws Exception {
        // Initialize the database
        insertedApplicationServer = applicationServerRepository.saveAndFlush(applicationServer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the applicationServer using partial update
        ApplicationServer partialUpdatedApplicationServer = new ApplicationServer();
        partialUpdatedApplicationServer.setId(applicationServer.getId());

        partialUpdatedApplicationServer.name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).notes(UPDATED_NOTES);

        restApplicationServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationServer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApplicationServer))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationServer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApplicationServerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedApplicationServer, applicationServer),
            getPersistedApplicationServer(applicationServer)
        );
    }

    @Test
    @Transactional
    void fullUpdateApplicationServerWithPatch() throws Exception {
        // Initialize the database
        insertedApplicationServer = applicationServerRepository.saveAndFlush(applicationServer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the applicationServer using partial update
        ApplicationServer partialUpdatedApplicationServer = new ApplicationServer();
        partialUpdatedApplicationServer.setId(applicationServer.getId());

        partialUpdatedApplicationServer.name(UPDATED_NAME).creaDate(UPDATED_CREA_DATE).updateDate(UPDATED_UPDATE_DATE).notes(UPDATED_NOTES);

        restApplicationServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplicationServer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApplicationServer))
            )
            .andExpect(status().isOk());

        // Validate the ApplicationServer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApplicationServerUpdatableFieldsEquals(
            partialUpdatedApplicationServer,
            getPersistedApplicationServer(partialUpdatedApplicationServer)
        );
    }

    @Test
    @Transactional
    void patchNonExistingApplicationServer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicationServer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicationServer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(applicationServer))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationServer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplicationServer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicationServer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(applicationServer))
            )
            .andExpect(status().isBadRequest());

        // Validate the ApplicationServer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplicationServer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        applicationServer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationServerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(applicationServer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ApplicationServer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplicationServer() throws Exception {
        // Initialize the database
        insertedApplicationServer = applicationServerRepository.saveAndFlush(applicationServer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the applicationServer
        restApplicationServerMockMvc
            .perform(delete(ENTITY_API_URL_ID, applicationServer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return applicationServerRepository.count();
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

    protected ApplicationServer getPersistedApplicationServer(ApplicationServer applicationServer) {
        return applicationServerRepository.findById(applicationServer.getId()).orElseThrow();
    }

    protected void assertPersistedApplicationServerToMatchAllProperties(ApplicationServer expectedApplicationServer) {
        assertApplicationServerAllPropertiesEquals(expectedApplicationServer, getPersistedApplicationServer(expectedApplicationServer));
    }

    protected void assertPersistedApplicationServerToMatchUpdatableProperties(ApplicationServer expectedApplicationServer) {
        assertApplicationServerAllUpdatablePropertiesEquals(
            expectedApplicationServer,
            getPersistedApplicationServer(expectedApplicationServer)
        );
    }
}
