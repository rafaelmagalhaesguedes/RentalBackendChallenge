package com.rental.mock;

import com.rental.entity.Accessory;

import java.util.UUID;

public class AccessoryMock {
    public static final Accessory ACCESSORY_WITH_ID_01 = new Accessory(
            UUID.randomUUID(),
            "Baby Comfort",
            "Comfort plus ergonomic master class.",
            10,
            30.00
    );

    public static final Accessory ACCESSORY_WITH_ID_02 = new Accessory(
            UUID.randomUUID(),
            "GPS",
            "Smartphone android with google maps and waze.",
            10,
            35.00
    );

    public static final Accessory ACCESSORY_CREATION = new Accessory(
            "Baby Comfort",
            "Comfort plus ergonomic master class.",
            10,
            30.00
    );

    public static final Accessory ACCESSORY_UPDATED = new Accessory(
            "Baby Comfort Plus",
            "Comfort plus top ergonomic master class.",
            20,
            50.00
    );
}