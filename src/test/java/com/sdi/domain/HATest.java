package com.sdi.domain;

import static com.sdi.domain.HATestSamples.*;
import static com.sdi.domain.ProductDeployementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class HATest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HA.class);
        HA hA1 = getHASample1();
        HA hA2 = new HA();
        assertThat(hA1).isNotEqualTo(hA2);

        hA2.setId(hA1.getId());
        assertThat(hA1).isEqualTo(hA2);

        hA2 = getHASample2();
        assertThat(hA1).isNotEqualTo(hA2);
    }

    @Test
    void productDeployementTest() {
        HA hA = getHARandomSampleGenerator();
        ProductDeployement productDeployementBack = getProductDeployementRandomSampleGenerator();

        hA.addProductDeployement(productDeployementBack);
        assertThat(hA.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getHa()).isEqualTo(hA);

        hA.removeProductDeployement(productDeployementBack);
        assertThat(hA.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getHa()).isNull();

        hA.productDeployements(new HashSet<>(Set.of(productDeployementBack)));
        assertThat(hA.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getHa()).isEqualTo(hA);

        hA.setProductDeployements(new HashSet<>());
        assertThat(hA.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getHa()).isNull();
    }
}
