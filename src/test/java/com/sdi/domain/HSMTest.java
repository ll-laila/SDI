package com.sdi.domain;

import static com.sdi.domain.HSMTestSamples.*;
import static com.sdi.domain.ProductDeployementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class HSMTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HSM.class);
        HSM hSM1 = getHSMSample1();
        HSM hSM2 = new HSM();
        assertThat(hSM1).isNotEqualTo(hSM2);

        hSM2.setId(hSM1.getId());
        assertThat(hSM1).isEqualTo(hSM2);

        hSM2 = getHSMSample2();
        assertThat(hSM1).isNotEqualTo(hSM2);
    }

    @Test
    void productDeployementTest() {
        HSM hSM = getHSMRandomSampleGenerator();
        ProductDeployement productDeployementBack = getProductDeployementRandomSampleGenerator();

        hSM.addProductDeployement(productDeployementBack);
        assertThat(hSM.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getHsm()).isEqualTo(hSM);

        hSM.removeProductDeployement(productDeployementBack);
        assertThat(hSM.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getHsm()).isNull();

        hSM.productDeployements(new HashSet<>(Set.of(productDeployementBack)));
        assertThat(hSM.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getHsm()).isEqualTo(hSM);

        hSM.setProductDeployements(new HashSet<>());
        assertThat(hSM.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getHsm()).isNull();
    }
}
