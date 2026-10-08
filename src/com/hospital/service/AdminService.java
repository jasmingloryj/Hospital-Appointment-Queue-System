package com.hospital.service;

import com.hospital.model.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminService {

    private static final String URL =
            "jdbc:mysql://localhost:3306/hospital_queue_v2";
    private static final String USER = "root";
    private static final String PASSWORD = "Jasmin@2008";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // =========================================================
    // DOCTOR MANAGEMENT
    // =========================================================

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();

        String sql =
                "SELECT u.user_id, u.full_name, u.email, u.phone, " +
                "d.department_id, dep.department_name, " +
                "d.specialization, d.availability " +
                "FROM doctors d " +
                "JOIN users u ON d.doctor_id = u.user_id " +
                "JOIN departments dep ON d.department_id = dep.department_id " +
                "ORDER BY u.full_name";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("department_name"),
                        rs.getString("availability")
                );

                doctors.add(doctor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    /**
     * Adds a new doctor.
     *
     * The user record and doctor record are inserted inside
     * one transaction so we never create a half-complete doctor.
     */
    public boolean addDoctor(
            String username,
            String password,
            String fullName,
            String email,
            String phone,
            int departmentId,
            String specialization,
            String availability) {

        String userSql =
                "INSERT INTO users " +
                "(username, password, role, full_name, email, phone) " +
                "VALUES (?, ?, 'DOCTOR', ?, ?, ?)";

        String doctorSql =
                "INSERT INTO doctors " +
                "(doctor_id, department_id, specialization, availability) " +
                "VALUES (?, ?, ?, ?)";

        Connection con = null;

        try {
            con = getConnection();
            con.setAutoCommit(false);

            int userId;

            try (PreparedStatement ps =
                         con.prepareStatement(userSql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, fullName);
                ps.setString(4, email);
                ps.setString(5, phone);

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) {
                        throw new SQLException(
                                "Unable to obtain generated doctor user ID."
                        );
                    }

                    userId = rs.getInt(1);
                }
            }

            try (PreparedStatement ps = con.prepareStatement(doctorSql)) {

                ps.setInt(1, userId);
                ps.setInt(2, departmentId);
                ps.setString(3, specialization);
                ps.setString(4, availability);

                ps.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    rollbackError.printStackTrace();
                }
            }

            e.printStackTrace();
            return false;

        } finally {

            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException closeError) {
                    closeError.printStackTrace();
                }
            }
        }
    }

    public boolean updateDoctorAvailability(
            int doctorId,
            String availability) {

        String sql =
                "UPDATE doctors " +
                "SET availability = ? " +
                "WHERE doctor_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, availability);
            ps.setInt(2, doctorId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDoctorSpecialization(
            int doctorId,
            String specialization) {

        String sql =
                "UPDATE doctors " +
                "SET specialization = ? " +
                "WHERE doctor_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, specialization);
            ps.setInt(2, doctorId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDoctorDepartment(
            int doctorId,
            int departmentId) {

        String sql =
                "UPDATE doctors " +
                "SET department_id = ? " +
                "WHERE doctor_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, departmentId);
            ps.setInt(2, doctorId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================
    // DEPARTMENT MANAGEMENT
    // =========================================================

    public List<String> getAllDepartments() {

        List<String> departments = new ArrayList<>();

        String sql =
                "SELECT department_name " +
                "FROM departments " +
                "ORDER BY department_name";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                departments.add(
                        rs.getString("department_name")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }

    public boolean addDepartment(
            String departmentName,
            String description) {

        String sql =
                "INSERT INTO departments " +
                "(department_name, description) " +
                "VALUES (?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, departmentName);
            ps.setString(2, description);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDepartment(
            int departmentId,
            String departmentName,
            String description) {

        String sql =
                "UPDATE departments " +
                "SET department_name = ?, description = ? " +
                "WHERE department_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, departmentName);
            ps.setString(2, description);
            ps.setInt(3, departmentId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDepartment(int departmentId) {

        String sql =
                "DELETE FROM departments " +
                "WHERE department_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, departmentId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================
    // HOSPITAL STATISTICS
    // =========================================================

    public int getPatientCount() {

        String sql =
                "SELECT COUNT(*) " +
                "FROM patients";

        return getCount(sql);
    }

    public int getDoctorCount() {

        String sql =
                "SELECT COUNT(*) " +
                "FROM doctors";

        return getCount(sql);
    }

    public int getDepartmentCount() {

        String sql =
                "SELECT COUNT(*) " +
                "FROM departments";

        return getCount(sql);
    }

    public int getTodayAppointmentCount() {

        String sql =
                "SELECT COUNT(*) " +
                "FROM appointments " +
                "WHERE appointment_date = CURDATE() " +
                "AND status <> 'CANCELLED'";

        return getCount(sql);
    }

    private int getCount(String sql) {

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}