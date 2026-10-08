package com.hospital.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultationService {

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

    public int createConsultation(
            int appointmentId,
            int doctorId,
            String diagnosis,
            String advice,
            boolean followUpRequired,
            java.sql.Date followUpDate
    ) throws SQLException {

        String sql = """
                INSERT INTO consultations
                (
                    appointment_id,
                    doctor_id,
                    diagnosis,
                    advice,
                    follow_up_required,
                    follow_up_date
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = getConnection();
             PreparedStatement ps =
                     con.prepareStatement(
                             sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS
                     )) {

            ps.setInt(1, appointmentId);
            ps.setInt(2, doctorId);
            ps.setString(3, diagnosis);
            ps.setString(4, advice);
            ps.setBoolean(5, followUpRequired);

            if (followUpDate != null) {
                ps.setDate(6, followUpDate);
            } else {
                ps.setNull(
                        6,
                        java.sql.Types.DATE
                );
            }

            ps.executeUpdate();

            try (ResultSet rs =
                         ps.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new SQLException(
                "Unable to create consultation."
        );
    }

    public void finishConsultation(
            int appointmentId,
            int doctorId
    ) throws SQLException {

        String updateConsultationSql = """
                UPDATE consultations
                SET consultation_date = NOW()
                WHERE appointment_id = ?
                  AND doctor_id = ?
                """;

        String updateQueueSql = """
                UPDATE queue
                SET status = 'COMPLETED',
                    completed_at = NOW()
                WHERE appointment_id = ?
                  AND doctor_id = ?
                  AND status IN
                      ('CALLED', 'IN_CONSULTATION', 'WAITING')
                """;

        String updateAppointmentSql = """
                UPDATE appointments
                SET status = 'COMPLETED'
                WHERE appointment_id = ?
                  AND doctor_id = ?
                  AND status IN
                      ('BOOKED', 'WAITING', 'IN_CONSULTATION')
                """;

        try (Connection con = getConnection()) {

            con.setAutoCommit(false);

            try {

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     updateConsultationSql)) {

                    ps.setInt(1, appointmentId);
                    ps.setInt(2, doctorId);

                    ps.executeUpdate();
                }

                int queueUpdated;

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     updateQueueSql)) {

                    ps.setInt(1, appointmentId);
                    ps.setInt(2, doctorId);

                    queueUpdated =
                            ps.executeUpdate();
                }

                int appointmentUpdated;

                try (PreparedStatement ps =
                             con.prepareStatement(
                                     updateAppointmentSql)) {

                    ps.setInt(1, appointmentId);
                    ps.setInt(2, doctorId);

                    appointmentUpdated =
                            ps.executeUpdate();
                }

                if (queueUpdated == 0) {
                    throw new SQLException(
                            "The queue entry could not be completed."
                    );
                }

                if (appointmentUpdated == 0) {
                    throw new SQLException(
                            "The appointment could not be completed."
                    );
                }

                con.commit();

            } catch (SQLException e) {

                con.rollback();

                throw e;
            }
        }
    }

    public void completeConsultation(
            int appointmentId,
            int doctorId,
            String diagnosis,
            String advice
    ) throws SQLException {

        int consultationId =
                createConsultation(
                        appointmentId,
                        doctorId,
                        diagnosis,
                        advice,
                        false,
                        null
                );

        if (consultationId <= 0) {
            throw new SQLException(
                    "Consultation could not be created."
            );
        }

        finishConsultation(
                appointmentId,
                doctorId
        );
    }

    public ResultSet getConsultationByAppointment(
            int appointmentId
    ) throws SQLException {

        String sql = """
                SELECT
                    c.consultation_id,
                    c.appointment_id,
                    c.doctor_id,
                    c.diagnosis,
                    c.advice,
                    c.follow_up_required,
                    c.follow_up_date,
                    c.consultation_date
                FROM consultations c
                WHERE c.appointment_id = ?
                """;

        Connection con = getConnection();

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setInt(1, appointmentId);

        return ps.executeQuery();
    }
}