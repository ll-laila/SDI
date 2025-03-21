package com.sdi.domain;

import static com.sdi.domain.CustomisationLevelTestSamples.*;
import static com.sdi.domain.ModuleDeployementTestSamples.*;
import static com.sdi.domain.ModuleTestSamples.*;
import static com.sdi.domain.ProductDeployementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModuleDeployementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleDeployement.class);
        ModuleDeployement moduleDeployement1 = getModuleDeployementSample1();
        ModuleDeployement moduleDeployement2 = new ModuleDeployement();
        assertThat(moduleDeployement1).isNotEqualTo(moduleDeployement2);

        moduleDeployement2.setId(moduleDeployement1.getId());
        assertThat(moduleDeployement1).isEqualTo(moduleDeployement2);

        moduleDeployement2 = getModuleDeployementSample2();
        assertThat(moduleDeployement1).isNotEqualTo(moduleDeployement2);
    }

    @Test
    void moduleTest() {
        ModuleDeployement moduleDeployement = getModuleDeployementRandomSampleGenerator();
        Module moduleBack = getModuleRandomSampleGenerator();

        moduleDeployement.setModule(moduleBack);
        assertThat(moduleDeployement.getModule()).isEqualTo(moduleBack);

        moduleDeployement.module(null);
        assertThat(moduleDeployement.getModule()).isNull();
    }

    @Test
    void productDeployementTest() {
        ModuleDeployement moduleDeployement = getModuleDeployementRandomSampleGenerator();
        ProductDeployement productDeployementBack = getProductDeployementRandomSampleGenerator();

        moduleDeployement.setProductDeployement(productDeployementBack);
        assertThat(moduleDeployement.getProductDeployement()).isEqualTo(productDeployementBack);

        moduleDeployement.productDeployement(null);
        assertThat(moduleDeployement.getProductDeployement()).isNull();
    }

    @Test
    void custoLevelTest() {
        ModuleDeployement moduleDeployement = getModuleDeployementRandomSampleGenerator();
        CustomisationLevel customisationLevelBack = getCustomisationLevelRandomSampleGenerator();

        moduleDeployement.setCustoLevel(customisationLevelBack);
        assertThat(moduleDeployement.getCustoLevel()).isEqualTo(customisationLevelBack);

        moduleDeployement.custoLevel(null);
        assertThat(moduleDeployement.getCustoLevel()).isNull();
    }
}
