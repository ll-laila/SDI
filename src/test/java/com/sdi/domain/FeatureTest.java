package com.sdi.domain;

import static com.sdi.domain.FeatureDeployementTestSamples.*;
import static com.sdi.domain.FeatureTestSamples.*;
import static com.sdi.domain.ModuleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FeatureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Feature.class);
        Feature feature1 = getFeatureSample1();
        Feature feature2 = new Feature();
        assertThat(feature1).isNotEqualTo(feature2);

        feature2.setId(feature1.getId());
        assertThat(feature1).isEqualTo(feature2);

        feature2 = getFeatureSample2();
        assertThat(feature1).isNotEqualTo(feature2);
    }

    @Test
    void featureDeployementTest() {
        Feature feature = getFeatureRandomSampleGenerator();
        FeatureDeployement featureDeployementBack = getFeatureDeployementRandomSampleGenerator();

        feature.addFeatureDeployement(featureDeployementBack);
        assertThat(feature.getFeatureDeployements()).containsOnly(featureDeployementBack);
        assertThat(featureDeployementBack.getFeature()).isEqualTo(feature);

        feature.removeFeatureDeployement(featureDeployementBack);
        assertThat(feature.getFeatureDeployements()).doesNotContain(featureDeployementBack);
        assertThat(featureDeployementBack.getFeature()).isNull();

        feature.featureDeployements(new HashSet<>(Set.of(featureDeployementBack)));
        assertThat(feature.getFeatureDeployements()).containsOnly(featureDeployementBack);
        assertThat(featureDeployementBack.getFeature()).isEqualTo(feature);

        feature.setFeatureDeployements(new HashSet<>());
        assertThat(feature.getFeatureDeployements()).doesNotContain(featureDeployementBack);
        assertThat(featureDeployementBack.getFeature()).isNull();
    }

    @Test
    void moduleTest() {
        Feature feature = getFeatureRandomSampleGenerator();
        Module moduleBack = getModuleRandomSampleGenerator();

        feature.setModule(moduleBack);
        assertThat(feature.getModule()).isEqualTo(moduleBack);

        feature.module(null);
        assertThat(feature.getModule()).isNull();
    }
}
