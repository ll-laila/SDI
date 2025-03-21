package com.sdi.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FeatureTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Feature getFeatureSample1() {
        return new Feature().id(1L).name("name1").code("code1").apiVersion("apiVersion1").description("description1").notes("notes1");
    }

    public static Feature getFeatureSample2() {
        return new Feature().id(2L).name("name2").code("code2").apiVersion("apiVersion2").description("description2").notes("notes2");
    }

    public static Feature getFeatureRandomSampleGenerator() {
        return new Feature()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .apiVersion(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString());
    }
}
