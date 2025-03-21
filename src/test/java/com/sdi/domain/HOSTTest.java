package com.sdi.domain;

import static com.sdi.domain.HOSTTestSamples.*;
import static com.sdi.domain.ProductDeployementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class HOSTTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HOST.class);
        HOST hOST1 = getHOSTSample1();
        HOST hOST2 = new HOST();
        assertThat(hOST1).isNotEqualTo(hOST2);

        hOST2.setId(hOST1.getId());
        assertThat(hOST1).isEqualTo(hOST2);

        hOST2 = getHOSTSample2();
        assertThat(hOST1).isNotEqualTo(hOST2);
    }

    @Test
    void productDeployementTest() {
        HOST hOST = getHOSTRandomSampleGenerator();
        ProductDeployement productDeployementBack = getProductDeployementRandomSampleGenerator();

        hOST.addProductDeployement(productDeployementBack);
        assertThat(hOST.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getHost()).isEqualTo(hOST);

        hOST.removeProductDeployement(productDeployementBack);
        assertThat(hOST.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getHost()).isNull();

        hOST.productDeployements(new HashSet<>(Set.of(productDeployementBack)));
        assertThat(hOST.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getHost()).isEqualTo(hOST);

        hOST.setProductDeployements(new HashSet<>());
        assertThat(hOST.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getHost()).isNull();
    }
}
