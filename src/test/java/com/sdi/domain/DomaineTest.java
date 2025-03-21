package com.sdi.domain;

import static com.sdi.domain.DomaineTestSamples.*;
import static com.sdi.domain.ModuleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DomaineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Domaine.class);
        Domaine domaine1 = getDomaineSample1();
        Domaine domaine2 = new Domaine();
        assertThat(domaine1).isNotEqualTo(domaine2);

        domaine2.setId(domaine1.getId());
        assertThat(domaine1).isEqualTo(domaine2);

        domaine2 = getDomaineSample2();
        assertThat(domaine1).isNotEqualTo(domaine2);
    }

    @Test
    void moduleTest() {
        Domaine domaine = getDomaineRandomSampleGenerator();
        Module moduleBack = getModuleRandomSampleGenerator();

        domaine.addModule(moduleBack);
        assertThat(domaine.getModules()).containsOnly(moduleBack);
        assertThat(moduleBack.getDomaine()).isEqualTo(domaine);

        domaine.removeModule(moduleBack);
        assertThat(domaine.getModules()).doesNotContain(moduleBack);
        assertThat(moduleBack.getDomaine()).isNull();

        domaine.modules(new HashSet<>(Set.of(moduleBack)));
        assertThat(domaine.getModules()).containsOnly(moduleBack);
        assertThat(moduleBack.getDomaine()).isEqualTo(domaine);

        domaine.setModules(new HashSet<>());
        assertThat(domaine.getModules()).doesNotContain(moduleBack);
        assertThat(moduleBack.getDomaine()).isNull();
    }
}
