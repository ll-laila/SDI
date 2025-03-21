package com.sdi.domain;

import static com.sdi.domain.OSTestSamples.*;
import static com.sdi.domain.ProductDeployementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OSTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OS.class);
        OS oS1 = getOSSample1();
        OS oS2 = new OS();
        assertThat(oS1).isNotEqualTo(oS2);

        oS2.setId(oS1.getId());
        assertThat(oS1).isEqualTo(oS2);

        oS2 = getOSSample2();
        assertThat(oS1).isNotEqualTo(oS2);
    }

    @Test
    void productDeployementTest() {
        OS oS = getOSRandomSampleGenerator();
        ProductDeployement productDeployementBack = getProductDeployementRandomSampleGenerator();

        oS.addProductDeployement(productDeployementBack);
        assertThat(oS.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getOs()).isEqualTo(oS);

        oS.removeProductDeployement(productDeployementBack);
        assertThat(oS.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getOs()).isNull();

        oS.productDeployements(new HashSet<>(Set.of(productDeployementBack)));
        assertThat(oS.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getOs()).isEqualTo(oS);

        oS.setProductDeployements(new HashSet<>());
        assertThat(oS.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getOs()).isNull();
    }
}
