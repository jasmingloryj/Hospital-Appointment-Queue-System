package com.hospital.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.hospital.interfaces.Bookable;

/**
 * Represents a doctor in the hospital system.
 *
 * Doctor inherits common personal information
 * from the Person class and implements Bookable
 * so that the appointment system can check
 * doctor availability.
 */
public class Doctor extends Person implements Bookable {

    private String department;
    private String availability;

    public Doctor(
            int id,
            String fullName,
            String email,
            String phone,
            String department,
            String availability) {

        super(id, fullName, email, phone);

        this.department = department;
        this.availability = availability;
    }

    public String getDepartment() {
        return department;
    }

    public String getAvailability() {
        return availability;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @Override
    public String getRole() {
        return "DOCTOR";
    }

    /**
     * Checks whether the doctor is available
     * at the requested date and time.
     *
     * The availability value is expected to contain
     * a simple time range such as:
     *
     * 09:00-17:00
     */
    @Override
    public boolean isAvailable(
            LocalDate date,
            LocalTime time) {

        if (availability == null
                || availability.trim().isEmpty()) {

            return false;
        }

        try {

            String[] timeRange =
                    availability.split("-");

            if (timeRange.length != 2) {
                return false;
            }

            LocalTime startTime =
                    LocalTime.parse(
                            timeRange[0].trim()
                    );

            LocalTime endTime =
                    LocalTime.parse(
                            timeRange[1].trim()
                    );

            return !time.isBefore(startTime)
                    && !time.isAfter(endTime);

        } catch (Exception e) {

            return false;
        }
    }

    /**
     * Controls how a Doctor appears inside a JComboBox
     * in the Swing booking screen.
     */
    @Override
    public String toString() {

        return getFullName()
                + " - "
                + department;
    }
}