package com.hospital.model;

/**
 * Represents a user account used for authentication.
 *
 * This class stores login-related information retrieved
 * from the database.
 */
public class User {

    private int userId;
    private String username;
    private String password;
    private String role;
    private String fullName;
    private String email;
    private String phone;

    public User(
            int userId,
            String username,
            String password,
            String role,
            String fullName,
            String email,
            String phone) {

        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
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
}