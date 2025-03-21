package com.sdi.domain;

import static com.sdi.domain.CustomisationLevelTestSamples.*;
import static com.sdi.domain.FeatureDeployementTestSamples.*;
import static com.sdi.domain.ModuleDeployementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CustomisationLevelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomisationLevel.class);
        CustomisationLevel customisationLevel1 = getCustomisationLevelSample1();
        CustomisationLevel customisationLevel2 = new CustomisationLevel();
        assertThat(customisationLevel1).isNotEqualTo(customisationLevel2);

        customisationLevel2.setId(customisationLevel1.getId());
        assertThat(customisationLevel1).isEqualTo(customisationLevel2);

        customisationLevel2 = getCustomisationLevelSample2();
        assertThat(customisationLevel1).isNotEqualTo(customisationLevel2);
    }

    @Test
    void moduleDeployementTest() {
        CustomisationLevel customisationLevel = getCustomisationLevelRandomSampleGenerator();
        ModuleDeployement moduleDeployementBack = getModuleDeployementRandomSampleGenerator();

        customisationLevel.addModuleDeployement(moduleDeployementBack);
        assertThat(customisationLevel.getModuleDeployements()).containsOnly(moduleDeployementBack);
        assertThat(moduleDeployementBack.getCustoLevel()).isEqualTo(customisationLevel);

        customisationLevel.removeModuleDeployement(moduleDeployementBack);
        assertThat(customisationLevel.getModuleDeployements()).doesNotContain(moduleDeployementBack);
        assertThat(moduleDeployementBack.getCustoLevel()).isNull();

        customisationLevel.moduleDeployements(new HashSet<>(Set.of(moduleDeployementBack)));
        assertThat(customisationLevel.getModuleDeployements()).containsOnly(moduleDeployementBack);
        assertThat(moduleDeployementBack.getCustoLevel()).isEqualTo(customisationLevel);

        customisationLevel.setModuleDeployements(new HashSet<>());
        assertThat(customisationLevel.getModuleDeployements()).doesNotContain(moduleDeployementBack);
        assertThat(moduleDeployementBack.getCustoLevel()).isNull();
    }

    @Test
    void featureDeployementTest() {
        CustomisationLevel customisationLevel = getCustomisationLevelRandomSampleGenerator();
        FeatureDeployement featureDeployementBack = getFeatureDeployementRandomSampleGenerator();

        customisationLevel.addFeatureDeployement(featureDeployementBack);
        assertThat(customisationLevel.getFeatureDeployements()).containsOnly(featureDeployementBack);
        assertThat(featureDeployementBack.getCustoLevel()).isEqualTo(customisationLevel);

        customisationLevel.removeFeatureDeployement(featureDeployementBack);
        assertThat(customisationLevel.getFeatureDeployements()).doesNotContain(featureDeployementBack);
        assertThat(featureDeployementBack.getCustoLevel()).isNull();

        customisationLevel.featureDeployements(new HashSet<>(Set.of(featureDeployementBack)));
        assertThat(customisationLevel.getFeatureDeployements()).containsOnly(featureDeployementBack);
        assertThat(featureDeployementBack.getCustoLevel()).isEqualTo(customisationLevel);

        customisationLevel.setFeatureDeployements(new HashSet<>());
        assertThat(customisationLevel.getFeatureDeployements()).doesNotContain(featureDeployementBack);
        assertThat(featureDeployementBack.getCustoLevel()).isNull();
    }
}
