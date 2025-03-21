package com.sdi.domain;

import static com.sdi.domain.DeployementTypeTestSamples.*;
import static com.sdi.domain.ProductDeployementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DeployementTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeployementType.class);
        DeployementType deployementType1 = getDeployementTypeSample1();
        DeployementType deployementType2 = new DeployementType();
        assertThat(deployementType1).isNotEqualTo(deployementType2);

        deployementType2.setId(deployementType1.getId());
        assertThat(deployementType1).isEqualTo(deployementType2);

        deployementType2 = getDeployementTypeSample2();
        assertThat(deployementType1).isNotEqualTo(deployementType2);
    }

    @Test
    void productDeployementTest() {
        DeployementType deployementType = getDeployementTypeRandomSampleGenerator();
        ProductDeployement productDeployementBack = getProductDeployementRandomSampleGenerator();

        deployementType.addProductDeployement(productDeployementBack);
        assertThat(deployementType.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getDeployementType()).isEqualTo(deployementType);

        deployementType.removeProductDeployement(productDeployementBack);
        assertThat(deployementType.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getDeployementType()).isNull();

        deployementType.productDeployements(new HashSet<>(Set.of(productDeployementBack)));
        assertThat(deployementType.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getDeployementType()).isEqualTo(deployementType);

        deployementType.setProductDeployements(new HashSet<>());
        assertThat(deployementType.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getDeployementType()).isNull();
    }
}
