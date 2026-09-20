package com.hospital.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;

/**
 * Handles appointment booking and scheduling.
 *
 * This class implements the business logic for Module 2:
 * - Book Appointment
 * - Check Doctor Availability
 * - Flag Emergency
 */
public class AppointmentService {

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
     * Checks whether the selected doctor is available
     * according to the doctor's configured availability.
     */
    public boolean isDoctorAvailable(
            Doctor doctor,
            LocalDate date,
            LocalTime time) {

        return doctor.isAvailable(date, time);
    }

    /**
     * Checks whether the doctor already has an appointment
     * at the requested date and time.
     */
    public boolean isSlotAlreadyBooked(
            int doctorId,
            LocalDate date,
            LocalTime time) throws SQLException {

        String sql =
                "SELECT appointment_id "
                + "FROM appointments "
                + "WHERE doctor_id = ? "
                + "AND appointment_date = ? "
                + "AND appointment_time = ? "
                + "AND status = 'BOOKED'";

        try (
            Connection connection = getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(1, doctorId);
            statement.setDate(
                    2,
                    java.sql.Date.valueOf(date)
            );
            statement.setTime(
                    3,
                    java.sql.Time.valueOf(time)
            );

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    /**
     * Books a new appointment.
     *
     * @return the newly created Appointment
     */
    public Appointment bookAppointment(
            int patientId,
            int doctorId,
            LocalDate date,
            LocalTime time,
            String symptoms,
            boolean emergency)
            throws SQLException {

        String sql =
                "INSERT INTO appointments "
                + "(patient_id, doctor_id, appointment_date, "
                + "appointment_time, symptoms, emergency_flag, "
                + "status) "
                + "VALUES (?, ?, ?, ?, ?, ?, 'BOOKED')";

        try (
            Connection connection = getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(
                            sql,
                            java.sql.Statement.RETURN_GENERATED_KEYS
                    )
        ) {

            statement.setInt(1, patientId);
            statement.setInt(2, doctorId);
            statement.setDate(
                    3,
                    java.sql.Date.valueOf(date)
            );
            statement.setTime(
                    4,
                    java.sql.Time.valueOf(time)
            );
            statement.setString(5, symptoms);
            statement.setBoolean(6, emergency);

            statement.executeUpdate();

            try (ResultSet keys =
                         statement.getGeneratedKeys()) {

                if (keys.next()) {

                    int appointmentId = keys.getInt(1);

                    return new Appointment(
                            appointmentId,
                            patientId,
                            doctorId,
                            date,
                            time,
                            symptoms,
                            emergency,
                            "BOOKED"
                    );
                }
            }
        }

        throw new SQLException(
                "Appointment could not be created."
        );
    }

    /**
     * Retrieves an appointment using its ID.
     */
    public Appointment getAppointmentById(
            int appointmentId) throws SQLException {

        String sql =
                "SELECT appointment_id, patient_id, doctor_id, "
                + "appointment_date, appointment_time, symptoms, "
                + "emergency_flag, status "
                + "FROM appointments "
                + "WHERE appointment_id = ?";

        try (
            Connection connection = getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(1, appointmentId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Appointment(
                            resultSet.getInt("appointment_id"),
                            resultSet.getInt("patient_id"),
                            resultSet.getInt("doctor_id"),
                            resultSet.getDate(
                                    "appointment_date"
                            ).toLocalDate(),
                            resultSet.getTime(
                                    "appointment_time"
                            ).toLocalTime(),
                            resultSet.getString("symptoms"),
                            resultSet.getBoolean(
                                    "emergency_flag"
                            ),
                            resultSet.getString("status")
                    );
                }
            }
        }

        return null;
    }
}
