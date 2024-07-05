package com.rental.mock;

import com.rental.entity.Accessory;

import java.util.UUID;

/**
 * The type Accessory mock.
 */
public class AccessoryMock {

    /**
     * The constant ACCESSORY_WITH_ID_01.
     */
    public static final Accessory ACCESSORY_01 = new Accessory(
            UUID.randomUUID(),
            "Baby Comfort",
            "Comfort plus ergonomic master class.",
            10,
            30.00
    );

    /**
     * The constant ACCESSORY_WITH_ID_02.
     */
    public static final Accessory ACCESSORY_02 = new Accessory(
            UUID.randomUUID(),
            "GPS",
            "Smartphone android with google maps and waze.",
            10,
            35.00
    );

    /**
     * The constant ACCESSORY_CREATION.
     */
    public static final Accessory ACCESSORY_CREATION = new Accessory(
            "Baby Comfort",
            "Comfort plus ergonomic master class.",
            10,
            30.00
    );

    /**
     * The constant ACCESSORY_UPDATED.
     */
    public static final Accessory ACCESSORY_UPDATED = new Accessory(
            "Baby Comfort Plus",
            "Comfort plus top ergonomic master class.",
            20,
            50.00
    );
}