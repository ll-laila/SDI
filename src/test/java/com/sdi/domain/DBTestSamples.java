package com.sdi.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DBTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DB getDBSample1() {
        return new DB().id(1L).name("name1").notes("notes1");
    }

    public static DB getDBSample2() {
        return new DB().id(2L).name("name2").notes("notes2");
    }

    public static DB getDBRandomSampleGenerator() {
        return new DB().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).notes(UUID.randomUUID().toString());
    }
}
