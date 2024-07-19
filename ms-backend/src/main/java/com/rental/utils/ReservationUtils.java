package com.rental.utils;

import com.rental.entity.Accessory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Utility class for reservation-related calculations.
 */
public class ReservationUtils {

    /**
     * Calculates the total number of days between the pickup and return dates.
     *
     * @param pickupDateTime the pickup date and time
     * @param returnDateTime the return date and time
     * @return the total number of days
     */
    public static int calculateTotalDays(LocalDateTime pickupDateTime, LocalDateTime returnDateTime) {
        return (int) Duration.between(pickupDateTime, returnDateTime).toDays();
    }

    /**
     * Calculates the total amount for the reservation.
     *
     * @param dailyRate the daily rate of the group
     * @param totalDays the total number of days
     * @param accessories the list of accessories
     * @return the total amount
     */
    public static double calculateTotalAmount(double dailyRate, int totalDays, List<Accessory> accessories) {
        double accessoriesCost = accessories.stream().mapToDouble(Accessory::getDailyRate).sum();
        return (dailyRate + accessoriesCost) * totalDays;
    }
}
