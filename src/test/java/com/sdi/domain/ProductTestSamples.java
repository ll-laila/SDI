package com.sdi.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product().id(1L).name("name1").code("code1").logo("logo1").notes("notes1");
    }

    public static Product getProductSample2() {
        return new Product().id(2L).name("name2").code("code2").logo("logo2").notes("notes2");
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .logo(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString());
    }
}
