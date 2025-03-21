package com.sdi.domain;

import static com.sdi.domain.DBTestSamples.*;
import static com.sdi.domain.ProductDeployementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DBTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DB.class);
        DB dB1 = getDBSample1();
        DB dB2 = new DB();
        assertThat(dB1).isNotEqualTo(dB2);

        dB2.setId(dB1.getId());
        assertThat(dB1).isEqualTo(dB2);

        dB2 = getDBSample2();
        assertThat(dB1).isNotEqualTo(dB2);
    }

    @Test
    void productDeployementTest() {
        DB dB = getDBRandomSampleGenerator();
        ProductDeployement productDeployementBack = getProductDeployementRandomSampleGenerator();

        dB.addProductDeployement(productDeployementBack);
        assertThat(dB.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getDb()).isEqualTo(dB);

        dB.removeProductDeployement(productDeployementBack);
        assertThat(dB.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getDb()).isNull();

        dB.productDeployements(new HashSet<>(Set.of(productDeployementBack)));
        assertThat(dB.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getDb()).isEqualTo(dB);

        dB.setProductDeployements(new HashSet<>());
        assertThat(dB.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getDb()).isNull();
    }
}
