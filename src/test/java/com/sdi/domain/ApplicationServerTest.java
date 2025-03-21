package com.sdi.domain;

import static com.sdi.domain.ApplicationServerTestSamples.*;
import static com.sdi.domain.ProductDeployementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApplicationServerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationServer.class);
        ApplicationServer applicationServer1 = getApplicationServerSample1();
        ApplicationServer applicationServer2 = new ApplicationServer();
        assertThat(applicationServer1).isNotEqualTo(applicationServer2);

        applicationServer2.setId(applicationServer1.getId());
        assertThat(applicationServer1).isEqualTo(applicationServer2);

        applicationServer2 = getApplicationServerSample2();
        assertThat(applicationServer1).isNotEqualTo(applicationServer2);
    }

    @Test
    void productDeployementTest() {
        ApplicationServer applicationServer = getApplicationServerRandomSampleGenerator();
        ProductDeployement productDeployementBack = getProductDeployementRandomSampleGenerator();

        applicationServer.addProductDeployement(productDeployementBack);
        assertThat(applicationServer.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getApplicationServer()).isEqualTo(applicationServer);

        applicationServer.removeProductDeployement(productDeployementBack);
        assertThat(applicationServer.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getApplicationServer()).isNull();

        applicationServer.productDeployements(new HashSet<>(Set.of(productDeployementBack)));
        assertThat(applicationServer.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getApplicationServer()).isEqualTo(applicationServer);

        applicationServer.setProductDeployements(new HashSet<>());
        assertThat(applicationServer.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getApplicationServer()).isNull();
    }
}
