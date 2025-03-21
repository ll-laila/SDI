package com.sdi.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OSTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static OS getOSSample1() {
        return new OS().id(1L).name("name1").notes("notes1");
    }

    public static OS getOSSample2() {
        return new OS().id(2L).name("name2").notes("notes2");
    }

    public static OS getOSRandomSampleGenerator() {
        return new OS().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).notes(UUID.randomUUID().toString());
    }
}
