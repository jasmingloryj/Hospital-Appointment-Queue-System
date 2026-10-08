package com.hospital.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientMedicalRecordService {

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
    // GET COMPLETED CONSULTATIONS FOR A PATIENT
    // ---------------------------------------------------------

    public ResultSet getPatientConsultations(int patientId)
            throws SQLException {

        String sql = """
                SELECT
                    c.consultation_id,
                    c.appointment_id,
                    c.doctor_id,
                    u.full_name AS doctor_name,
                    dep.department_name,
                    c.diagnosis,
                    c.advice,
                    c.follow_up_required,
                    c.follow_up_date,
                    c.consultation_date
                FROM consultations c
                JOIN doctors d
                    ON c.doctor_id = d.doctor_id
                JOIN users u
                    ON d.doctor_id = u.user_id
                JOIN departments dep
                    ON d.department_id = dep.department_id
                JOIN appointments a
                    ON c.appointment_id = a.appointment_id
                WHERE a.patient_id = ?
                ORDER BY c.consultation_date DESC
                """;

        Connection con = getConnection();

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setInt(1, patientId);

        return ps.executeQuery();
    }

    // ---------------------------------------------------------
    // GET PRESCRIPTIONS FOR A CONSULTATION
    // ---------------------------------------------------------

    public ResultSet getPrescriptions(int consultationId)
            throws SQLException {

        String sql = """
                SELECT
                    prescription_item_id,
                    medicine_name,
                    dosage,
                    frequency,
                    duration,
                    instructions
                FROM prescription_items
                WHERE consultation_id = ?
                ORDER BY prescription_item_id
                """;

        Connection con = getConnection();

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setInt(1, consultationId);

        return ps.executeQuery();
    }
}