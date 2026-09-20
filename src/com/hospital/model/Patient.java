package com.hospital.model;

/**
 * Represents a patient in the hospital system.
 *
 * Patient inherits common personal information
 * from the Person class.
 */
public class Patient extends Person {

    public Patient(int id, String fullName, String email, String phone) {
        super(id, fullName, email, phone);
    }

    @Override
    public String getRole() {
        return "PATIENT";
    }
}