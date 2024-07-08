package com.rental.mock;

import com.rental.entity.Group;

import java.util.UUID;

/**
 * The type Group mock.
 */
public class GroupMock {

    public static Group GROUP_01 = new Group(
            UUID.randomUUID(),
            "Group GX",
            "Acura, BMW, Aston Martin or similar",
            250.00
    );

    public static Group GROUP_02 = new Group(
            UUID.randomUUID(),
            "Group GW",
            "Renegade, Compass, Tracker or similar",
            350.00
    );


    public static Group GROUP_CREATION = new Group(
            "Group GW",
            "Renegade, Compass, Tracker or similar",
            450.00
    );

    public static Group GROUP_UPDATED = new Group(
            "Group RX",
            "HB20, Corolla, Civic or similar",
            150.00
    );
}
