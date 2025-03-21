package com.sdi.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class HAAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHAAllPropertiesEquals(HA expected, HA actual) {
        assertHAAutoGeneratedPropertiesEquals(expected, actual);
        assertHAAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHAAllUpdatablePropertiesEquals(HA expected, HA actual) {
        assertHAUpdatableFieldsEquals(expected, actual);
        assertHAUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHAAutoGeneratedPropertiesEquals(HA expected, HA actual) {
        assertThat(actual)
            .as("Verify HA auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHAUpdatableFieldsEquals(HA expected, HA actual) {
        assertThat(actual)
            .as("Verify HA relevant properties")
            .satisfies(a -> assertThat(a.getName()).as("check name").isEqualTo(expected.getName()))
            .satisfies(a -> assertThat(a.getCreaDate()).as("check creaDate").isEqualTo(expected.getCreaDate()))
            .satisfies(a -> assertThat(a.getUpdateDate()).as("check updateDate").isEqualTo(expected.getUpdateDate()))
            .satisfies(a -> assertThat(a.getNotes()).as("check notes").isEqualTo(expected.getNotes()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHAUpdatableRelationshipsEquals(HA expected, HA actual) {
        // empty method
    }
}
