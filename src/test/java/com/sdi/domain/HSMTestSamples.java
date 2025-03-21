package com.sdi.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HSMTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static HSM getHSMSample1() {
        return new HSM().id(1L).name("name1").notes("notes1");
    }

    public static HSM getHSMSample2() {
        return new HSM().id(2L).name("name2").notes("notes2");
    }

    public static HSM getHSMRandomSampleGenerator() {
        return new HSM().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).notes(UUID.randomUUID().toString());
    }
}
