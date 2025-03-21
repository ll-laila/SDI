package com.sdi.domain;

import static com.sdi.domain.CustomisationLevelTestSamples.*;
import static com.sdi.domain.FeatureDeployementTestSamples.*;
import static com.sdi.domain.FeatureTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeatureDeployementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeatureDeployement.class);
        FeatureDeployement featureDeployement1 = getFeatureDeployementSample1();
        FeatureDeployement featureDeployement2 = new FeatureDeployement();
        assertThat(featureDeployement1).isNotEqualTo(featureDeployement2);

        featureDeployement2.setId(featureDeployement1.getId());
        assertThat(featureDeployement1).isEqualTo(featureDeployement2);

        featureDeployement2 = getFeatureDeployementSample2();
        assertThat(featureDeployement1).isNotEqualTo(featureDeployement2);
    }

    @Test
    void featureTest() {
        FeatureDeployement featureDeployement = getFeatureDeployementRandomSampleGenerator();
        Feature featureBack = getFeatureRandomSampleGenerator();

        featureDeployement.setFeature(featureBack);
        assertThat(featureDeployement.getFeature()).isEqualTo(featureBack);

        featureDeployement.feature(null);
        assertThat(featureDeployement.getFeature()).isNull();
    }

    @Test
    void custoLevelTest() {
        FeatureDeployement featureDeployement = getFeatureDeployementRandomSampleGenerator();
        CustomisationLevel customisationLevelBack = getCustomisationLevelRandomSampleGenerator();

        featureDeployement.setCustoLevel(customisationLevelBack);
        assertThat(featureDeployement.getCustoLevel()).isEqualTo(customisationLevelBack);

        featureDeployement.custoLevel(null);
        assertThat(featureDeployement.getCustoLevel()).isNull();
    }
}
