package com.sdi.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductDeployementTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ProductDeployement getProductDeployementSample1() {
        return new ProductDeployement().id(1L).code("code1").refContract("refContract1").notes("notes1");
    }

    public static ProductDeployement getProductDeployementSample2() {
        return new ProductDeployement().id(2L).code("code2").refContract("refContract2").notes("notes2");
    }

    public static ProductDeployement getProductDeployementRandomSampleGenerator() {
        return new ProductDeployement()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .refContract(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString());
    }
}
