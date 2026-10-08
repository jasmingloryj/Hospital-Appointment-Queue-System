package com.hospital.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hospital.model.Doctor;

public class DoctorService {

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

    // =========================================================
    // GET ALL DOCTORS
    // =========================================================

    public List<Doctor> getAllDoctors() throws SQLException {

        List<Doctor> doctors = new ArrayList<>();

        String sql = """
                SELECT
                    d.doctor_id,
                    u.full_name,
                    u.email,
                    u.phone,
                    dep.department_name,
                    d.availability
                FROM doctors d
                JOIN users u
                    ON d.doctor_id = u.user_id
                JOIN departments dep
                    ON d.department_id = dep.department_id
                ORDER BY u.full_name
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                doctors.add(
                        new Doctor(
                                rs.getInt("doctor_id"),
                                rs.getString("full_name"),
                                rs.getString("email"),
                                rs.getString("phone"),
                                rs.getString("department_name"),
                                rs.getString("availability")
                        )
                );
            }
        }

        return doctors;
    }

    // =========================================================
    // GET DOCTOR BY ID
    // =========================================================

    public Doctor getDoctorById(int doctorId)
            throws SQLException {

        String sql = """
                SELECT
                    d.doctor_id,
                    u.full_name,
                    u.email,
                    u.phone,
                    dep.department_name,
                    d.availability
                FROM doctors d
                JOIN users u
                    ON d.doctor_id = u.user_id
                JOIN departments dep
                    ON d.department_id = dep.department_id
                WHERE d.doctor_id = ?
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Doctor(
                            rs.getInt("doctor_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("department_name"),
                            rs.getString("availability")
                    );
                }
            }
        }

        return null;
    }

    // =========================================================
    // TODAY'S QUEUE
    // =========================================================

    public List<String> getTodayQueue(int doctorId)
            throws SQLException {

        List<String> queue = new ArrayList<>();

        String sql = """
                SELECT
                    q.queue_number,
                    q.appointment_id,
                    q.priority_level,
                    q.emergency_status,
                    q.status,
                    u.full_name AS patient_name,
                    a.symptoms
                FROM queue q
                JOIN appointments a
                    ON q.appointment_id = a.appointment_id
                JOIN users u
                    ON a.patient_id = u.user_id
                WHERE q.doctor_id = ?
                  AND q.queue_date = CURDATE()
                  AND q.status IN
                      ('WAITING', 'CALLED', 'IN_CONSULTATION')
                ORDER BY
                    q.priority_level DESC,
                    q.queue_number ASC
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    String entry =
                            "Queue No: "
                            + rs.getInt("queue_number")
                            + " | Appointment ID: "
                            + rs.getInt("appointment_id")
                            + " | Patient: "
                            + rs.getString("patient_name")
                            + " | Symptoms: "
                            + rs.getString("symptoms")
                            + " | Emergency: "
                            + rs.getString("emergency_status")
                            + " | "
                            + rs.getString("status");

                    queue.add(entry);
                }
            }
        }

        return queue;
    }

    // =========================================================
    // GET PENDING EMERGENCIES
    // =========================================================

    public List<String> getPendingEmergencies(int doctorId)
            throws SQLException {

        List<String> emergencies = new ArrayList<>();

        String sql = """
                SELECT
                    q.queue_id,
                    q.queue_number,
                    q.appointment_id,
                    q.priority_level,
                    u.full_name AS patient_name,
                    a.symptoms
                FROM queue q
                JOIN appointments a
                    ON q.appointment_id = a.appointment_id
                JOIN users u
                    ON a.patient_id = u.user_id
                WHERE q.doctor_id = ?
                  AND q.queue_date = CURDATE()
                  AND q.emergency_status = 'PENDING'
                  AND q.status = 'WAITING'
                ORDER BY q.queue_number ASC
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    String entry =
                            "Queue ID: "
                            + rs.getInt("queue_id")
                            + " | Queue No: "
                            + rs.getInt("queue_number")
                            + " | Appointment ID: "
                            + rs.getInt("appointment_id")
                            + " | Patient: "
                            + rs.getString("patient_name")
                            + " | Symptoms: "
                            + rs.getString("symptoms");

                    emergencies.add(entry);
                }
            }
        }

        return emergencies;
    }

    // =========================================================
    // PENDING EMERGENCY COUNT
    // =========================================================

    public int getPendingEmergencyCount(int doctorId)
            throws SQLException {

        String sql = """
                SELECT COUNT(*)
                FROM queue
                WHERE doctor_id = ?
                  AND queue_date = CURDATE()
                  AND emergency_status = 'PENDING'
                  AND status = 'WAITING'
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {

                rs.next();

                return rs.getInt(1);
            }
        }
    }

    // =========================================================
    // EMERGENCY REVIEW
    // =========================================================

    public boolean reviewEmergency(
            int queueId,
            boolean approve
    ) throws SQLException {

        String sql = """
                UPDATE queue
                SET emergency_status = ?,
                    priority_level = ?
                WHERE queue_id = ?
                  AND emergency_status = 'PENDING'
                  AND status = 'WAITING'
                """;

        String newStatus =
                approve
                        ? "APPROVED"
                        : "REJECTED";

        int priority =
                approve
                        ? 1
                        : 0;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, priority);
            ps.setInt(3, queueId);

            int affected =
                    ps.executeUpdate();

            return affected == 1;
        }
    }

    // =========================================================
    // KEEP EMERGENCY POSITION
    // =========================================================

    public boolean keepEmergencyPosition(
            int queueId
    ) {

        String sql = """
                UPDATE queue
                SET emergency_status = 'APPROVED',
                    priority_level = 0
                WHERE queue_id = ?
                  AND emergency_status = 'PENDING'
                  AND status = 'WAITING'
                """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, queueId);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    // =========================================================
    // GET CURRENT PATIENT
    // =========================================================

    public ResultSet getCurrentPatient(int doctorId) {

        String sql =
                "SELECT q.queue_id, "
                + "q.appointment_id, "
                + "q.queue_number, "
                + "q.emergency_status, "
                + "a.patient_id, "
                + "u.full_name AS patient_name, "
                + "u.phone, "
                + "a.symptoms, "
                + "a.appointment_date, "
                + "a.appointment_time "
                + "FROM queue q "
                + "JOIN appointments a "
                + "ON q.appointment_id = a.appointment_id "
                + "JOIN users u "
                + "ON a.patient_id = u.user_id "
                + "WHERE q.doctor_id = ? "
                + "AND q.queue_date = CURDATE() "
                + "AND q.status = 'IN_CONSULTATION' "
                + "LIMIT 1";

        try {

            Connection con =
                    getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(
                    1,
                    doctorId
            );

            return ps.executeQuery();

        } catch (SQLException e) {

            e.printStackTrace();

            return null;
        }
    }

    // =========================================================
    // CALL NEXT PATIENT
    // =========================================================

    public String callNextPatient(int doctorId)
            throws SQLException {

        String selectSql = """
                SELECT
                    q.queue_id,
                    q.appointment_id,
                    u.full_name AS patient_name
                FROM queue q
                JOIN appointments a
                    ON q.appointment_id = a.appointment_id
                JOIN users u
                    ON a.patient_id = u.user_id
                WHERE q.doctor_id = ?
                  AND q.queue_date = CURDATE()
                  AND q.status = 'WAITING'
                ORDER BY
                    q.priority_level DESC,
                    q.queue_number ASC
                LIMIT 1
                FOR UPDATE SKIP LOCKED
                """;

        String updateQueueSql = """
                UPDATE queue
                SET status = 'IN_CONSULTATION',
                    called_at = NOW()
                WHERE queue_id = ?
                  AND status = 'WAITING'
                """;

        String updateAppointmentSql = """
                UPDATE appointments
                SET status = 'IN_CONSULTATION'
                WHERE appointment_id = ?
                """;

        try (Connection con = getConnection()) {

            con.setAutoCommit(false);

            try {

                int queueId;
                int appointmentId;
                String patientName;

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     selectSql)) {

                    ps.setInt(
                            1,
                            doctorId
                    );

                    try (ResultSet rs =
                                 ps.executeQuery()) {

                        if (!rs.next()) {

                            con.rollback();

                            return null;
                        }

                        queueId =
                                rs.getInt(
                                        "queue_id"
                                );

                        appointmentId =
                                rs.getInt(
                                        "appointment_id"
                                );

                        patientName =
                                rs.getString(
                                        "patient_name"
                                );
                    }
                }

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     updateQueueSql)) {

                    ps.setInt(
                            1,
                            queueId
                    );

                    if (ps.executeUpdate() != 1) {

                        con.rollback();

                        return null;
                    }
                }

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     updateAppointmentSql)) {

                    ps.setInt(
                            1,
                            appointmentId
                    );

                    ps.executeUpdate();
                }

                con.commit();

                return patientName;

            } catch (SQLException e) {

                con.rollback();

                throw e;
            }
        }
    }
}