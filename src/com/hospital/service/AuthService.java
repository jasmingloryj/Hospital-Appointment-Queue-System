package com.hospital.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hospital.exception.InvalidCredentialsException;
import com.hospital.model.User;

/**
 * Handles user authentication and patient registration.
 *
 * This class communicates with the MySQL users table using JDBC.
 */
public class AuthService {

    /*
     * Database connection details.
     *
     * Change DB_PASSWORD later if your MySQL root password
     * is different.
     */
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/hospital_queue_v2"
                    + "?useSSL=false&serverTimezone=Asia/Kolkata"
                    + "&allowPublicKeyRetrieval=true";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD = "Jasmin@2008";

    /**
     * Creates a connection to the hospital database.
     */
    private Connection getConnection() throws SQLException {
        return java.sql.DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASSWORD
        );
    }

    /**
     * Verifies username and password.
     *
     * @param username entered username
     * @param password entered password
     * @return authenticated User object
     * @throws InvalidCredentialsException if login details are incorrect
     */
    public User login(String username, String password)
            throws InvalidCredentialsException {

        String sql =
                "SELECT user_id, username, password, role, "
                + "full_name, email, phone "
                + "FROM users "
                + "WHERE username = ?";

        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    String storedPassword =
                            resultSet.getString("password");

                    if (storedPassword.equals(password)) {

                        return new User(
                                resultSet.getInt("user_id"),
                                resultSet.getString("username"),
                                storedPassword,
                                resultSet.getString("role"),
                                resultSet.getString("full_name"),
                                resultSet.getString("email"),
                                resultSet.getString("phone")
                        );
                    }
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Database error while attempting login: "
                            + e.getMessage(),
                    e
            );
        }

        throw new InvalidCredentialsException(
                "Invalid username or password."
        );
    }

    /**
     * Creates a new patient account.
     *
     * Public registration always creates a PATIENT account.
     * Doctor and Admin accounts will be created separately.
     */
    public void registerPatient(
            String username,
            String password,
            String fullName,
            String email,
            String phone)
            throws SQLException {

        String sql =
                "INSERT INTO users "
                + "(username, password, role, full_name, email, phone) "
                + "VALUES (?, ?, 'PATIENT', ?, ?, ?)";

        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, fullName);
            statement.setString(4, email);
            statement.setString(5, phone);

            statement.executeUpdate();
        }
    }
}