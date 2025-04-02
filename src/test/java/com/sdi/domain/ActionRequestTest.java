package com.sdi.domain;

import static com.sdi.domain.ActionRequestTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sdi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActionRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionRequest.class);
        ActionRequest actionRequest1 = getActionRequestSample1();
        ActionRequest actionRequest2 = new ActionRequest();
        assertThat(actionRequest1).isNotEqualTo(actionRequest2);

        actionRequest2.setId(actionRequest1.getId());
        assertThat(actionRequest1).isEqualTo(actionRequest2);

        actionRequest2 = getActionRequestSample2();
        assertThat(actionRequest1).isNotEqualTo(actionRequest2);
    }
}
