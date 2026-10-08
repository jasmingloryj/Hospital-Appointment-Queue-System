package com.hospital.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import com.hospital.model.Doctor;

public class AppointmentService {

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/hospital_queue_v2"
            + "?useSSL=false&serverTimezone=Asia/Kolkata"
            + "&allowPublicKeyRetrieval=true";

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Jasmin@2008";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASSWORD
        );
    }

    // ---------------------------------------------------------
    // CHECK DOCTOR AVAILABILITY
    // ---------------------------------------------------------

    public boolean isDoctorAvailable(
            int doctorId,
            LocalDate date,
            LocalTime time) throws SQLException {

        String sql = """
                SELECT availability
                FROM doctors
                WHERE doctor_id = ?
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    return false;
                }

                String availability =
                        rs.getString("availability");

                if (availability == null ||
                    !availability.contains("-")) {
                    return false;
                }

                String[] parts =
                        availability.split("-");

                if (parts.length != 2) {
                    return false;
                }

                LocalTime start =
                        LocalTime.parse(parts[0].trim());

                LocalTime end =
                        LocalTime.parse(parts[1].trim());

                return !time.isBefore(start)
                        && time.isBefore(end);
            }
        }
    }

    // ---------------------------------------------------------
    // COMPATIBILITY OVERLOAD
    // ---------------------------------------------------------

    public boolean isDoctorAvailable(
            Doctor doctor,
            LocalDate date,
            LocalTime time) {

        if (doctor == null ||
            doctor.getAvailability() == null ||
            !doctor.getAvailability().contains("-")) {

            return false;
        }

        String[] parts =
                doctor.getAvailability().split("-");

        if (parts.length != 2) {
            return false;
        }

        LocalTime start =
                LocalTime.parse(parts[0].trim());

        LocalTime end =
                LocalTime.parse(parts[1].trim());

        return !time.isBefore(start)
                && time.isBefore(end);
    }

    // ---------------------------------------------------------
    // CHECK WHETHER SLOT IS ALREADY BOOKED
    // ---------------------------------------------------------

    public boolean isSlotAlreadyBooked(
            int doctorId,
            LocalDate date,
            LocalTime time) throws SQLException {

        String sql = """
                SELECT COUNT(*)
                FROM appointments
                WHERE doctor_id = ?
                  AND appointment_date = ?
                  AND appointment_time = ?
                  AND status <> 'CANCELLED'
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setTime(3, java.sql.Time.valueOf(time));

            try (ResultSet rs = ps.executeQuery()) {

                rs.next();

                return rs.getInt(1) > 0;
            }
        }
    }

    // ---------------------------------------------------------
    // BOOK APPOINTMENT
    // ---------------------------------------------------------

    public int bookAppointment(
            int patientId,
            int doctorId,
            LocalDate date,
            LocalTime time,
            String symptoms) throws SQLException {

        if (!isDoctorAvailable(
                doctorId,
                date,
                time)) {

            throw new SQLException(
                    "Doctor is not available at the selected time."
            );
        }

        if (isSlotAlreadyBooked(
                doctorId,
                date,
                time)) {

            throw new SQLException(
                    "The selected appointment slot is already booked."
            );
        }

        String appointmentSql = """
                INSERT INTO appointments
                (
                    patient_id,
                    doctor_id,
                    appointment_date,
                    appointment_time,
                    symptoms,
                    status
                )
                VALUES (?, ?, ?, ?, ?, 'BOOKED')
                """;

        String queueNumberSql = """
                SELECT COALESCE(
                    MAX(queue_number),
                    0
                ) + 1
                FROM queue
                WHERE doctor_id = ?
                  AND queue_date = ?
                """;

        String queueSql = """
                INSERT INTO queue
                (
                    appointment_id,
                    doctor_id,
                    queue_date,
                    queue_number,
                    priority_level,
                    emergency_status,
                    status
                )
                VALUES (?, ?, ?, ?, 0, 'NONE', 'WAITING')
                """;

        try (Connection con = getConnection()) {

            con.setAutoCommit(false);

            try {

                int appointmentId;

                // 1. Create appointment
                try (PreparedStatement ps =
                             con.prepareStatement(
                                     appointmentSql,
                                     PreparedStatement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, patientId);
                    ps.setInt(2, doctorId);
                    ps.setDate(
                            3,
                            java.sql.Date.valueOf(date));
                    ps.setTime(
                            4,
                            java.sql.Time.valueOf(time));
                    ps.setString(5, symptoms);

                    ps.executeUpdate();

                    try (ResultSet keys =
                                 ps.getGeneratedKeys()) {

                        if (!keys.next()) {
                            con.rollback();

                            throw new SQLException(
                                    "Failed to create appointment."
                            );
                        }

                        appointmentId =
                                keys.getInt(1);
                    }
                }

                // 2. Find next queue number
                int queueNumber;

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     queueNumberSql)) {

                    ps.setInt(1, doctorId);
                    ps.setDate(
                            2,
                            java.sql.Date.valueOf(date));

                    try (ResultSet rs =
                                 ps.executeQuery()) {

                        rs.next();

                        queueNumber =
                                rs.getInt(1);
                    }
                }

                // 3. Add patient to queue
                try (PreparedStatement ps =
                             con.prepareStatement(queueSql)) {

                    ps.setInt(1, appointmentId);
                    ps.setInt(2, doctorId);
                    ps.setDate(
                            3,
                            java.sql.Date.valueOf(date));
                    ps.setInt(4, queueNumber);

                    ps.executeUpdate();
                }

                con.commit();

                return appointmentId;

            } catch (SQLException e) {

                con.rollback();

                throw e;
            }
        }
    }

    // ---------------------------------------------------------
    // BOOK APPOINTMENT WITH EMERGENCY REQUEST
    // ---------------------------------------------------------

    public int bookAppointment(
            int patientId,
            int doctorId,
            LocalDate date,
            LocalTime time,
            String symptoms,
            boolean emergency) throws SQLException {

        int appointmentId =
                bookAppointment(
                        patientId,
                        doctorId,
                        date,
                        time,
                        symptoms
                );

        if (emergency) {

            String sql = """
                    UPDATE queue
                    SET emergency_status = 'PENDING'
                    WHERE appointment_id = ?
                    """;

            try (Connection con = getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(sql)) {

                ps.setInt(1, appointmentId);

                ps.executeUpdate();
            }
        }

        return appointmentId;
    }

    // ---------------------------------------------------------
    // GET QUEUE POSITION
    // ---------------------------------------------------------

    public int getQueuePosition(
            int appointmentId) throws SQLException {

        String sql = """
                SELECT
                    COUNT(*)
                FROM queue q1
                JOIN queue q2
                    ON q1.doctor_id = q2.doctor_id
                    AND q1.queue_date = q2.queue_date
                WHERE q1.appointment_id = ?
                  AND q2.status = 'WAITING'
                  AND q2.queue_number <= q1.queue_number
                """;

        try (Connection con = getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, appointmentId);

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    // ---------------------------------------------------------
    // GET APPOINTMENT BY ID
    // ---------------------------------------------------------

    public ResultSet getAppointmentById(
            int appointmentId) throws SQLException {

        Connection con = getConnection();

        String sql = """
                SELECT
                    a.appointment_id,
                    a.patient_id,
                    a.doctor_id,
                    a.appointment_date,
                    a.appointment_time,
                    a.symptoms,
                    a.status,
                    q.queue_id,
                    q.queue_number,
                    q.priority_level,
                    q.emergency_status,
                    q.queue_date,
                    q.status AS queue_status
                FROM appointments a
                LEFT JOIN queue q
                    ON a.appointment_id = q.appointment_id
                WHERE a.appointment_id = ?
                """;

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setInt(1, appointmentId);

        return ps.executeQuery();
    }

    // ---------------------------------------------------------
    // CANCEL APPOINTMENT
    // ---------------------------------------------------------

    public boolean cancelAppointment(
            int appointmentId,
            int patientId) throws SQLException {

        String appointmentSql = """
                UPDATE appointments
                SET status = 'CANCELLED'
                WHERE appointment_id = ?
                  AND patient_id = ?
                  AND status IN ('BOOKED', 'WAITING')
                """;

        String queueSql = """
                UPDATE queue
                SET status = 'CANCELLED'
                WHERE appointment_id = ?
                  AND status = 'WAITING'
                """;

        try (Connection con = getConnection()) {

            con.setAutoCommit(false);

            try {

                int updated;

                // 1. Cancel appointment
                try (PreparedStatement ps =
                             con.prepareStatement(
                                     appointmentSql)) {

                    ps.setInt(1, appointmentId);
                    ps.setInt(2, patientId);

                    updated =
                            ps.executeUpdate();
                }

                if (updated != 1) {

                    con.rollback();

                    return false;
                }

                // 2. Cancel queue entry
                try (PreparedStatement ps =
                             con.prepareStatement(
                                     queueSql)) {

                    ps.setInt(1, appointmentId);

                    ps.executeUpdate();
                }

                con.commit();

                return true;

            } catch (SQLException e) {

                con.rollback();

                throw e;
            }
        }
    }

    // ---------------------------------------------------------
    // RESCHEDULE APPOINTMENT
    // ---------------------------------------------------------

    public boolean rescheduleAppointment(
            int appointmentId,
            int patientId,
            LocalDate newDate,
            LocalTime newTime) throws SQLException {

        String doctorSql = """
                SELECT doctor_id
                FROM appointments
                WHERE appointment_id = ?
                  AND patient_id = ?
                  AND status IN ('BOOKED', 'WAITING')
                """;

        int doctorId;

        try (Connection con = getConnection();
             PreparedStatement ps =
                     con.prepareStatement(doctorSql)) {

            ps.setInt(1, appointmentId);
            ps.setInt(2, patientId);

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (!rs.next()) {
                    return false;
                }

                doctorId =
                        rs.getInt("doctor_id");
            }
        }

        if (!isDoctorAvailable(
                doctorId,
                newDate,
                newTime)) {

            throw new SQLException(
                    "Doctor is not available at the selected time."
            );
        }

        if (isSlotAlreadyBooked(
                doctorId,
                newDate,
                newTime)) {

            throw new SQLException(
                    "The selected appointment slot is already booked."
            );
        }

        String appointmentSql = """
                UPDATE appointments
                SET appointment_date = ?,
                    appointment_time = ?
                WHERE appointment_id = ?
                  AND patient_id = ?
                  AND status IN ('BOOKED', 'WAITING')
                """;

        String queueNumberSql = """
                SELECT COALESCE(
                    MAX(queue_number),
                    0
                ) + 1
                FROM queue
                WHERE doctor_id = ?
                  AND queue_date = ?
                """;

        String queueSql = """
                UPDATE queue
                SET queue_date = ?,
                    queue_number = ?
                WHERE appointment_id = ?
                  AND status = 'WAITING'
                """;

        try (Connection con = getConnection()) {

            con.setAutoCommit(false);

            try {

                // 1. Update appointment date/time
                try (PreparedStatement ps =
                             con.prepareStatement(
                                     appointmentSql)) {

                    ps.setDate(
                            1,
                            java.sql.Date.valueOf(newDate));
                    ps.setTime(
                            2,
                            java.sql.Time.valueOf(newTime));
                    ps.setInt(3, appointmentId);
                    ps.setInt(4, patientId);

                    if (ps.executeUpdate() != 1) {

                        con.rollback();

                        return false;
                    }
                }

                // 2. Generate queue number for new date
                int queueNumber;

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     queueNumberSql)) {

                    ps.setInt(1, doctorId);
                    ps.setDate(
                            2,
                            java.sql.Date.valueOf(newDate));

                    try (ResultSet rs =
                                 ps.executeQuery()) {

                        rs.next();

                        queueNumber =
                                rs.getInt(1);
                    }
                }

                // 3. Move queue entry
                try (PreparedStatement ps =
                             con.prepareStatement(
                                     queueSql)) {

                    ps.setDate(
                            1,
                            java.sql.Date.valueOf(newDate));
                    ps.setInt(2, queueNumber);
                    ps.setInt(3, appointmentId);

                    ps.executeUpdate();
                }

                con.commit();

                return true;

            } catch (SQLException e) {

                con.rollback();

                throw e;
            }
        }
    }
}