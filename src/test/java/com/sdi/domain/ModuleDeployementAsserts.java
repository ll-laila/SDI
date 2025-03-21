package com.sdi.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ModuleDeployementAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertModuleDeployementAllPropertiesEquals(ModuleDeployement expected, ModuleDeployement actual) {
        assertModuleDeployementAutoGeneratedPropertiesEquals(expected, actual);
        assertModuleDeployementAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertModuleDeployementAllUpdatablePropertiesEquals(ModuleDeployement expected, ModuleDeployement actual) {
        assertModuleDeployementUpdatableFieldsEquals(expected, actual);
        assertModuleDeployementUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertModuleDeployementAutoGeneratedPropertiesEquals(ModuleDeployement expected, ModuleDeployement actual) {
        assertThat(actual)
            .as("Verify ModuleDeployement auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertModuleDeployementUpdatableFieldsEquals(ModuleDeployement expected, ModuleDeployement actual) {
        assertThat(actual)
            .as("Verify ModuleDeployement relevant properties")
            .satisfies(a ->
                assertThat(a.getCustomisationDescription())
                    .as("check customisationDescription")
                    .isEqualTo(expected.getCustomisationDescription())
            )
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
    public static void assertModuleDeployementUpdatableRelationshipsEquals(ModuleDeployement expected, ModuleDeployement actual) {
        assertThat(actual)
            .as("Verify ModuleDeployement relationships")
            .satisfies(a -> assertThat(a.getModule()).as("check module").isEqualTo(expected.getModule()))
            .satisfies(a -> assertThat(a.getProductDeployement()).as("check productDeployement").isEqualTo(expected.getProductDeployement())
            )
            .satisfies(a -> assertThat(a.getCustoLevel()).as("check custoLevel").isEqualTo(expected.getCustoLevel()));
    }
}
