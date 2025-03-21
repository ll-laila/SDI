package com.sdi.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ModuleDeployementTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ModuleDeployement getModuleDeployementSample1() {
        return new ModuleDeployement().id(1L).customisationDescription("customisationDescription1").notes("notes1");
    }

    public static ModuleDeployement getModuleDeployementSample2() {
        return new ModuleDeployement().id(2L).customisationDescription("customisationDescription2").notes("notes2");
    }

    public static ModuleDeployement getModuleDeployementRandomSampleGenerator() {
        return new ModuleDeployement()
            .id(longCount.incrementAndGet())
            .customisationDescription(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString());
    }
}
