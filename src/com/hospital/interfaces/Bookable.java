package com.hospital.interfaces;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Defines the availability behaviour required
 * for something that can be booked.
 */
public interface Bookable {

    /**
     * Checks whether the bookable resource is available
     * on the given date and time.
     *
     * @param date requested appointment date
     * @param time requested appointment time
     * @return true if available, otherwise false
     */
    boolean isAvailable(LocalDate date, LocalTime time);
}