package com.rental.mock;

import com.rental.entity.Vehicle;

import java.util.UUID;

/**
 * The type Vehicle mock.
 */
public class VehicleMock {

    /**
     * The constant VEHICLE_01.
     */
    public static Vehicle VEHICLE_01 = new Vehicle(
            UUID.randomUUID(),
            "Fiat Uno",
            "RTB-3311",
            "FIAT",
            "White",
            "2010/2011"
    );

    /**
     * The constant VEHICLE_02.
     */
    public static Vehicle VEHICLE_02 = new Vehicle(
            UUID.randomUUID(),
            "Volkswagen Gol",
            "RTB-0103",
            "Volkswagen",
            "Red",
            "2020/2021"
    );

    /**
     * The constant VEHICLE_CREATED.
     */
    public static Vehicle VEHICLE_CREATED = new Vehicle(
            "Chevrolet Onix",
            "RTA-0104",
            "Chevrolet",
            "Blue",
            "2023/2024"
    );

    /**
     * The constant VEHICLE_UPDATED.
     */
    public static Vehicle VEHICLE_UPDATED = new Vehicle(
            "Chevrolet Onix Plus",
            "RTA-0104",
            "Chevrolet",
            "Black",
            "2023/2024"
    );
}
