package com.hospital.model;

/**
 * Base class for all people in the hospital system.
 *
 * Person is the parent class for:
 * - Patient
 * - Doctor
 * - Admin
 */

public abstract class Person {

    private int id;
    private String fullName;
    private String email;
    private String phone;

    public Person(int id, String fullName, String email, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Each type of person has its own role.
     */
    public abstract String getRole();
}