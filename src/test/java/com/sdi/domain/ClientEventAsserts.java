package com.sdi.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientEventAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertClientEventAllPropertiesEquals(ClientEvent expected, ClientEvent actual) {
        assertClientEventAutoGeneratedPropertiesEquals(expected, actual);
        assertClientEventAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertClientEventAllUpdatablePropertiesEquals(ClientEvent expected, ClientEvent actual) {
        assertClientEventUpdatableFieldsEquals(expected, actual);
        assertClientEventUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertClientEventAutoGeneratedPropertiesEquals(ClientEvent expected, ClientEvent actual) {
        assertThat(actual)
            .as("Verify ClientEvent auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertClientEventUpdatableFieldsEquals(ClientEvent expected, ClientEvent actual) {
        assertThat(actual)
            .as("Verify ClientEvent relevant properties")
            .satisfies(a -> assertThat(a.getEvent()).as("check event").isEqualTo(expected.getEvent()))
            .satisfies(a -> assertThat(a.getDescription()).as("check description").isEqualTo(expected.getDescription()))
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
    public static void assertClientEventUpdatableRelationshipsEquals(ClientEvent expected, ClientEvent actual) {
        assertThat(actual)
            .as("Verify ClientEvent relationships")
            .satisfies(a -> assertThat(a.getClient()).as("check client").isEqualTo(expected.getClient()))
            .satisfies(a -> assertThat(a.getClientEventType()).as("check clientEventType").isEqualTo(expected.getClientEventType()));
    }
}
