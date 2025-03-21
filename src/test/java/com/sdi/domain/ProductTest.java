package com.sdi.domain;

import static com.sdi.domain.ModuleTestSamples.*;
import static com.sdi.domain.ProductDeployementTestSamples.*;
import static com.sdi.domain.ProductLineTestSamples.*;
import static com.sdi.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void moduleTest() {
        Product product = getProductRandomSampleGenerator();
        Module moduleBack = getModuleRandomSampleGenerator();

        product.addModule(moduleBack);
        assertThat(product.getModules()).containsOnly(moduleBack);
        assertThat(moduleBack.getProduct()).isEqualTo(product);

        product.removeModule(moduleBack);
        assertThat(product.getModules()).doesNotContain(moduleBack);
        assertThat(moduleBack.getProduct()).isNull();

        product.modules(new HashSet<>(Set.of(moduleBack)));
        assertThat(product.getModules()).containsOnly(moduleBack);
        assertThat(moduleBack.getProduct()).isEqualTo(product);

        product.setModules(new HashSet<>());
        assertThat(product.getModules()).doesNotContain(moduleBack);
        assertThat(moduleBack.getProduct()).isNull();
    }

    @Test
    void productDeployementTest() {
        Product product = getProductRandomSampleGenerator();
        ProductDeployement productDeployementBack = getProductDeployementRandomSampleGenerator();

        product.addProductDeployement(productDeployementBack);
        assertThat(product.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getProduct()).isEqualTo(product);

        product.removeProductDeployement(productDeployementBack);
        assertThat(product.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getProduct()).isNull();

        product.productDeployements(new HashSet<>(Set.of(productDeployementBack)));
        assertThat(product.getProductDeployements()).containsOnly(productDeployementBack);
        assertThat(productDeployementBack.getProduct()).isEqualTo(product);

        product.setProductDeployements(new HashSet<>());
        assertThat(product.getProductDeployements()).doesNotContain(productDeployementBack);
        assertThat(productDeployementBack.getProduct()).isNull();
    }

    @Test
    void productlineTest() {
        Product product = getProductRandomSampleGenerator();
        ProductLine productLineBack = getProductLineRandomSampleGenerator();

        product.setProductline(productLineBack);
        assertThat(product.getProductline()).isEqualTo(productLineBack);

        product.productline(null);
        assertThat(product.getProductline()).isNull();
    }
}
