package com.hospital.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrescriptionService {

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
    // ADD MEDICINE
    // ---------------------------------------------------------

    public boolean addMedicine(
            int consultationId,
            String medicineName,
            String dosage,
            String frequency,
            String duration,
            String instructions)
            throws SQLException {

        String sql = """
                INSERT INTO prescription_items
                (
                    consultation_id,
                    medicine_name,
                    dosage,
                    frequency,
                    duration,
                    instructions
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, consultationId);
            ps.setString(2, medicineName);
            ps.setString(3, dosage);
            ps.setString(4, frequency);
            ps.setString(5, duration);
            ps.setString(6, instructions);

            return ps.executeUpdate() == 1;
        }
    }

    // ---------------------------------------------------------
    // DELETE MEDICINE
    // ---------------------------------------------------------

    public boolean deleteMedicine(int prescriptionItemId)
            throws SQLException {

        String sql = """
                DELETE FROM prescription_items
                WHERE prescription_item_id = ?
                """;

        try (Connection con = getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, prescriptionItemId);

            return ps.executeUpdate() == 1;
        }
    }
}