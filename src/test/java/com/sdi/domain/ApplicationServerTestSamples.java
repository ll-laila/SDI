package com.sdi.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ApplicationServerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ApplicationServer getApplicationServerSample1() {
        return new ApplicationServer().id(1L).name("name1").notes("notes1");
    }

    public static ApplicationServer getApplicationServerSample2() {
        return new ApplicationServer().id(2L).name("name2").notes("notes2");
    }

    public static ApplicationServer getApplicationServerRandomSampleGenerator() {
        return new ApplicationServer()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString());
    }
}
