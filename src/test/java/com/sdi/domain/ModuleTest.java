package com.sdi.domain;

import static com.sdi.domain.DomaineTestSamples.*;
import static com.sdi.domain.FeatureTestSamples.*;
import static com.sdi.domain.ModuleDeployementTestSamples.*;
import static com.sdi.domain.ModuleTestSamples.*;
import static com.sdi.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ModuleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Module.class);
        Module module1 = getModuleSample1();
        Module module2 = new Module();
        assertThat(module1).isNotEqualTo(module2);

        module2.setId(module1.getId());
        assertThat(module1).isEqualTo(module2);

        module2 = getModuleSample2();
        assertThat(module1).isNotEqualTo(module2);
    }

    @Test
    void featureTest() {
        Module module = getModuleRandomSampleGenerator();
        Feature featureBack = getFeatureRandomSampleGenerator();

        module.addFeature(featureBack);
        assertThat(module.getFeatures()).containsOnly(featureBack);
        assertThat(featureBack.getModule()).isEqualTo(module);

        module.removeFeature(featureBack);
        assertThat(module.getFeatures()).doesNotContain(featureBack);
        assertThat(featureBack.getModule()).isNull();

        module.features(new HashSet<>(Set.of(featureBack)));
        assertThat(module.getFeatures()).containsOnly(featureBack);
        assertThat(featureBack.getModule()).isEqualTo(module);

        module.setFeatures(new HashSet<>());
        assertThat(module.getFeatures()).doesNotContain(featureBack);
        assertThat(featureBack.getModule()).isNull();
    }

    @Test
    void moduleDeployementTest() {
        Module module = getModuleRandomSampleGenerator();
        ModuleDeployement moduleDeployementBack = getModuleDeployementRandomSampleGenerator();

        module.addModuleDeployement(moduleDeployementBack);
        assertThat(module.getModuleDeployements()).containsOnly(moduleDeployementBack);
        assertThat(moduleDeployementBack.getModule()).isEqualTo(module);

        module.removeModuleDeployement(moduleDeployementBack);
        assertThat(module.getModuleDeployements()).doesNotContain(moduleDeployementBack);
        assertThat(moduleDeployementBack.getModule()).isNull();

        module.moduleDeployements(new HashSet<>(Set.of(moduleDeployementBack)));
        assertThat(module.getModuleDeployements()).containsOnly(moduleDeployementBack);
        assertThat(moduleDeployementBack.getModule()).isEqualTo(module);

        module.setModuleDeployements(new HashSet<>());
        assertThat(module.getModuleDeployements()).doesNotContain(moduleDeployementBack);
        assertThat(moduleDeployementBack.getModule()).isNull();
    }

    @Test
    void productTest() {
        Module module = getModuleRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        module.setProduct(productBack);
        assertThat(module.getProduct()).isEqualTo(productBack);

        module.product(null);
        assertThat(module.getProduct()).isNull();
    }

    @Test
    void domaineTest() {
        Module module = getModuleRandomSampleGenerator();
        Domaine domaineBack = getDomaineRandomSampleGenerator();

        module.setDomaine(domaineBack);
        assertThat(module.getDomaine()).isEqualTo(domaineBack);

        module.domaine(null);
        assertThat(module.getDomaine()).isNull();
    }
}
