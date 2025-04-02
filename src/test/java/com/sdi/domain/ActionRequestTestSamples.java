package com.sdi.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ActionRequestTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ActionRequest getActionRequestSample1() {
        return new ActionRequest().id(1L).entityType("entityType1").createdBy("createdBy1").approvedBy("approvedBy1");
    }

    public static ActionRequest getActionRequestSample2() {
        return new ActionRequest().id(2L).entityType("entityType2").createdBy("createdBy2").approvedBy("approvedBy2");
    }

    public static ActionRequest getActionRequestRandomSampleGenerator() {
        return new ActionRequest()
            .id(longCount.incrementAndGet())
            .entityType(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .approvedBy(UUID.randomUUID().toString());
    }
}
