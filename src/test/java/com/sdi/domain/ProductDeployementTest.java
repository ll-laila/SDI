package com.sdi.domain;

import static com.sdi.domain.ApplicationServerTestSamples.*;
import static com.sdi.domain.ClientTestSamples.*;
import static com.sdi.domain.DBTestSamples.*;
import static com.sdi.domain.DeployementTypeTestSamples.*;
import static com.sdi.domain.HATestSamples.*;
import static com.sdi.domain.HOSTTestSamples.*;
import static com.sdi.domain.HSMTestSamples.*;
import static com.sdi.domain.ModuleDeployementTestSamples.*;
import static com.sdi.domain.OSTestSamples.*;
import static com.sdi.domain.ProductDeployementTestSamples.*;
import static com.sdi.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProductDeployementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDeployement.class);
        ProductDeployement productDeployement1 = getProductDeployementSample1();
        ProductDeployement productDeployement2 = new ProductDeployement();
        assertThat(productDeployement1).isNotEqualTo(productDeployement2);

        productDeployement2.setId(productDeployement1.getId());
        assertThat(productDeployement1).isEqualTo(productDeployement2);

        productDeployement2 = getProductDeployementSample2();
        assertThat(productDeployement1).isNotEqualTo(productDeployement2);
    }

    @Test
    void moduleDeployementTest() {
        ProductDeployement productDeployement = getProductDeployementRandomSampleGenerator();
        ModuleDeployement moduleDeployementBack = getModuleDeployementRandomSampleGenerator();

        productDeployement.addModuleDeployement(moduleDeployementBack);
        assertThat(productDeployement.getModuleDeployements()).containsOnly(moduleDeployementBack);
        assertThat(moduleDeployementBack.getProductDeployement()).isEqualTo(productDeployement);

        productDeployement.removeModuleDeployement(moduleDeployementBack);
        assertThat(productDeployement.getModuleDeployements()).doesNotContain(moduleDeployementBack);
        assertThat(moduleDeployementBack.getProductDeployement()).isNull();

        productDeployement.moduleDeployements(new HashSet<>(Set.of(moduleDeployementBack)));
        assertThat(productDeployement.getModuleDeployements()).containsOnly(moduleDeployementBack);
        assertThat(moduleDeployementBack.getProductDeployement()).isEqualTo(productDeployement);

        productDeployement.setModuleDeployements(new HashSet<>());
        assertThat(productDeployement.getModuleDeployements()).doesNotContain(moduleDeployementBack);
        assertThat(moduleDeployementBack.getProductDeployement()).isNull();
    }

    @Test
    void clientTest() {
        ProductDeployement productDeployement = getProductDeployementRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        productDeployement.setClient(clientBack);
        assertThat(productDeployement.getClient()).isEqualTo(clientBack);

        productDeployement.client(null);
        assertThat(productDeployement.getClient()).isNull();
    }

    @Test
    void productTest() {
        ProductDeployement productDeployement = getProductDeployementRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        productDeployement.setProduct(productBack);
        assertThat(productDeployement.getProduct()).isEqualTo(productBack);

        productDeployement.product(null);
        assertThat(productDeployement.getProduct()).isNull();
    }

    @Test
    void deployementTypeTest() {
        ProductDeployement productDeployement = getProductDeployementRandomSampleGenerator();
        DeployementType deployementTypeBack = getDeployementTypeRandomSampleGenerator();

        productDeployement.setDeployementType(deployementTypeBack);
        assertThat(productDeployement.getDeployementType()).isEqualTo(deployementTypeBack);

        productDeployement.deployementType(null);
        assertThat(productDeployement.getDeployementType()).isNull();
    }

    @Test
    void haTest() {
        ProductDeployement productDeployement = getProductDeployementRandomSampleGenerator();
        HA hABack = getHARandomSampleGenerator();

        productDeployement.setHa(hABack);
        assertThat(productDeployement.getHa()).isEqualTo(hABack);

        productDeployement.ha(null);
        assertThat(productDeployement.getHa()).isNull();
    }

    @Test
    void hsmTest() {
        ProductDeployement productDeployement = getProductDeployementRandomSampleGenerator();
        HSM hSMBack = getHSMRandomSampleGenerator();

        productDeployement.setHsm(hSMBack);
        assertThat(productDeployement.getHsm()).isEqualTo(hSMBack);

        productDeployement.hsm(null);
        assertThat(productDeployement.getHsm()).isNull();
    }

    @Test
    void dbTest() {
        ProductDeployement productDeployement = getProductDeployementRandomSampleGenerator();
        DB dBBack = getDBRandomSampleGenerator();

        productDeployement.setDb(dBBack);
        assertThat(productDeployement.getDb()).isEqualTo(dBBack);

        productDeployement.db(null);
        assertThat(productDeployement.getDb()).isNull();
    }

    @Test
    void hostTest() {
        ProductDeployement productDeployement = getProductDeployementRandomSampleGenerator();
        HOST hOSTBack = getHOSTRandomSampleGenerator();

        productDeployement.setHost(hOSTBack);
        assertThat(productDeployement.getHost()).isEqualTo(hOSTBack);

        productDeployement.host(null);
        assertThat(productDeployement.getHost()).isNull();
    }

    @Test
    void applicationServerTest() {
        ProductDeployement productDeployement = getProductDeployementRandomSampleGenerator();
        ApplicationServer applicationServerBack = getApplicationServerRandomSampleGenerator();

        productDeployement.setApplicationServer(applicationServerBack);
        assertThat(productDeployement.getApplicationServer()).isEqualTo(applicationServerBack);

        productDeployement.applicationServer(null);
        assertThat(productDeployement.getApplicationServer()).isNull();
    }

    @Test
    void osTest() {
        ProductDeployement productDeployement = getProductDeployementRandomSampleGenerator();
        OS oSBack = getOSRandomSampleGenerator();

        productDeployement.setOs(oSBack);
        assertThat(productDeployement.getOs()).isEqualTo(oSBack);

        productDeployement.os(null);
        assertThat(productDeployement.getOs()).isNull();
    }
}
