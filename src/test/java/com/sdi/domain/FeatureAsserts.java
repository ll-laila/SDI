package com.sdi.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class FeatureAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFeatureAllPropertiesEquals(Feature expected, Feature actual) {
        assertFeatureAutoGeneratedPropertiesEquals(expected, actual);
        assertFeatureAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFeatureAllUpdatablePropertiesEquals(Feature expected, Feature actual) {
        assertFeatureUpdatableFieldsEquals(expected, actual);
        assertFeatureUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFeatureAutoGeneratedPropertiesEquals(Feature expected, Feature actual) {
        assertThat(actual)
            .as("Verify Feature auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFeatureUpdatableFieldsEquals(Feature expected, Feature actual) {
        assertThat(actual)
            .as("Verify Feature relevant properties")
            .satisfies(a -> assertThat(a.getName()).as("check name").isEqualTo(expected.getName()))
            .satisfies(a -> assertThat(a.getCode()).as("check code").isEqualTo(expected.getCode()))
            .satisfies(a -> assertThat(a.getApiVersion()).as("check apiVersion").isEqualTo(expected.getApiVersion()))
            .satisfies(a -> assertThat(a.getDescription()).as("check description").isEqualTo(expected.getDescription()))
            .satisfies(a -> assertThat(a.getNotes()).as("check notes").isEqualTo(expected.getNotes()))
            .satisfies(a -> assertThat(a.getCreaDate()).as("check creaDate").isEqualTo(expected.getCreaDate()))
            .satisfies(a -> assertThat(a.getUpdateDate()).as("check updateDate").isEqualTo(expected.getUpdateDate()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFeatureUpdatableRelationshipsEquals(Feature expected, Feature actual) {
        assertThat(actual)
            .as("Verify Feature relationships")
            .satisfies(a -> assertThat(a.getModule()).as("check module").isEqualTo(expected.getModule()));
    }
}
