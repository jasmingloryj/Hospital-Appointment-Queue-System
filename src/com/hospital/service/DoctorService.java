package com.hospital.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.model.Doctor;

/**
 * Handles doctor-related database operations.
 */
public class DoctorService {

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/hospital_queue_db"
                    + "?useSSL=false&serverTimezone=Asia/Kolkata";

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
     * Retrieves all doctors from the database.
     *
     * The doctors table is connected to the users table
     * so that we can obtain the doctor's name and contact details.
     */
    public List<Doctor> getAllDoctors() throws SQLException {

        List<Doctor> doctors = new ArrayList<>();

        String sql =
                "SELECT d.doctor_id, "
                + "u.full_name, u.email, u.phone, "
                + "d.department, d.availability "
                + "FROM doctors d "
                + "JOIN users u ON d.user_id = u.user_id "
                + "ORDER BY u.full_name";

        try (
            Connection connection = getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Doctor doctor = new Doctor(
                        resultSet.getInt("doctor_id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("department"),
                        resultSet.getString("availability")
                );

                doctors.add(doctor);
            }
        }

        return doctors;
    }

    /**
     * Retrieves one doctor using the doctor's ID.
     */
    public Doctor getDoctorById(int doctorId) throws SQLException {

        String sql =
                "SELECT d.doctor_id, "
                + "u.full_name, u.email, u.phone, "
                + "d.department, d.availability "
                + "FROM doctors d "
                + "JOIN users u ON d.user_id = u.user_id "
                + "WHERE d.doctor_id = ?";

        try (
            Connection connection = getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(1, doctorId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Doctor(
                            resultSet.getInt("doctor_id"),
                            resultSet.getString("full_name"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getString("department"),
                            resultSet.getString("availability")
                    );
                }
            }
        }

        return null;
    }
}