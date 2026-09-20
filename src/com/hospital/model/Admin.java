package com.hospital.model;

/**
 * Represents an administrator in the hospital system.
 *
 * Admin inherits common personal information
 * from the Person class.
 */
public class Admin extends Person {

    public Admin(int id, String fullName, String email, String phone) {
        super(id, fullName, email, phone);
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }
}
