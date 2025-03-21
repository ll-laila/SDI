package com.sdi.web.rest;

import static com.sdi.domain.FeatureAsserts.*;
import static com.sdi.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdi.IntegrationTest;
import com.sdi.domain.Feature;
import com.sdi.repository.FeatureRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FeatureResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FeatureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_API_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_API_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREA_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREA_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/features";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FeatureRepository featureRepository;

    @Mock
    private FeatureRepository featureRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeatureMockMvc;

    private Feature feature;

    private Feature insertedFeature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feature createEntity() {
        return new Feature()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .apiVersion(DEFAULT_API_VERSION)
            .description(DEFAULT_DESCRIPTION)
            .notes(DEFAULT_NOTES)
            .creaDate(DEFAULT_CREA_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feature createUpdatedEntity() {
        return new Feature()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .apiVersion(UPDATED_API_VERSION)
            .description(UPDATED_DESCRIPTION)
            .notes(UPDATED_NOTES)
            .creaDate(UPDATED_CREA_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
    }

    @BeforeEach
    public void initTest() {
        feature = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFeature != null) {
            featureRepository.delete(insertedFeature);
            insertedFeature = null;
        }
    }

    @Test
    @Transactional
    void createFeature() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Feature
        var returnedFeature = om.readValue(
            restFeatureMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(feature)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Feature.class
        );

        // Validate the Feature in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFeatureUpdatableFieldsEquals(returnedFeature, getPersistedFeature(returnedFeature));

        insertedFeature = returnedFeature;
    }

    @Test
    @Transactional
    void createFeatureWithExistingId() throws Exception {
        // Create the Feature with an existing ID
        feature.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(feature)))
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        feature.setName(null);

        // Create the Feature, which fails.

        restFeatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(feature)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFeatures() throws Exception {
        // Initialize the database
        insertedFeature = featureRepository.saveAndFlush(feature);

        // Get all the featureList
        restFeatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feature.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].apiVersion").value(hasItem(DEFAULT_API_VERSION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].creaDate").value(hasItem(DEFAULT_CREA_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFeaturesWithEagerRelationshipsIsEnabled() throws Exception {
        when(featureRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFeatureMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(featureRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFeaturesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(featureRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFeatureMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(featureRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFeature() throws Exception {
        // Initialize the database
        insertedFeature = featureRepository.saveAndFlush(feature);

        // Get the feature
        restFeatureMockMvc
            .perform(get(ENTITY_API_URL_ID, feature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feature.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.apiVersion").value(DEFAULT_API_VERSION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.creaDate").value(DEFAULT_CREA_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFeature() throws Exception {
        // Get the feature
        restFeatureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFeature() throws Exception {
        // Initialize the database
        insertedFeature = featureRepository.saveAndFlush(feature);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the feature
        Feature updatedFeature = featureRepository.findById(feature.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFeature are not directly saved in db
        em.detach(updatedFeature);
        updatedFeature
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .apiVersion(UPDATED_API_VERSION)
            .description(UPDATED_DESCRIPTION)
            .notes(UPDATED_NOTES)
            .creaDate(UPDATED_CREA_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restFeatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFeature.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFeature))
            )
            .andExpect(status().isOk());

        // Validate the Feature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFeatureToMatchAllProperties(updatedFeature);
    }

    @Test
    @Transactional
    void putNonExistingFeature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feature.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeatureMockMvc
            .perform(put(ENTITY_API_URL_ID, feature.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(feature)))
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(feature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeatureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(feature)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Feature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFeatureWithPatch() throws Exception {
        // Initialize the database
        insertedFeature = featureRepository.saveAndFlush(feature);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the feature using partial update
        Feature partialUpdatedFeature = new Feature();
        partialUpdatedFeature.setId(feature.getId());

        partialUpdatedFeature.name(UPDATED_NAME).code(UPDATED_CODE);

        restFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeature.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFeature))
            )
            .andExpect(status().isOk());

        // Validate the Feature in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFeatureUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFeature, feature), getPersistedFeature(feature));
    }

    @Test
    @Transactional
    void fullUpdateFeatureWithPatch() throws Exception {
        // Initialize the database
        insertedFeature = featureRepository.saveAndFlush(feature);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the feature using partial update
        Feature partialUpdatedFeature = new Feature();
        partialUpdatedFeature.setId(feature.getId());

        partialUpdatedFeature
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .apiVersion(UPDATED_API_VERSION)
            .description(UPDATED_DESCRIPTION)
            .notes(UPDATED_NOTES)
            .creaDate(UPDATED_CREA_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeature.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFeature))
            )
            .andExpect(status().isOk());

        // Validate the Feature in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFeatureUpdatableFieldsEquals(partialUpdatedFeature, getPersistedFeature(partialUpdatedFeature));
    }

    @Test
    @Transactional
    void patchNonExistingFeature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feature.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feature.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(feature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(feature))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeature() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        feature.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeatureMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(feature)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Feature in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFeature() throws Exception {
        // Initialize the database
        insertedFeature = featureRepository.saveAndFlush(feature);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the feature
        restFeatureMockMvc
            .perform(delete(ENTITY_API_URL_ID, feature.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return featureRepository.count();
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

    protected Feature getPersistedFeature(Feature feature) {
        return featureRepository.findById(feature.getId()).orElseThrow();
    }

    protected void assertPersistedFeatureToMatchAllProperties(Feature expectedFeature) {
        assertFeatureAllPropertiesEquals(expectedFeature, getPersistedFeature(expectedFeature));
    }

    protected void assertPersistedFeatureToMatchUpdatableProperties(Feature expectedFeature) {
        assertFeatureAllUpdatablePropertiesEquals(expectedFeature, getPersistedFeature(expectedFeature));
    }
}
