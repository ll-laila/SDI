package com.sdi.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HOSTTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static HOST getHOSTSample1() {
        return new HOST().id(1L).name("name1").notes("notes1");
    }

    public static HOST getHOSTSample2() {
        return new HOST().id(2L).name("name2").notes("notes2");
    }

    public static HOST getHOSTRandomSampleGenerator() {
        return new HOST().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).notes(UUID.randomUUID().toString());
    }
}
