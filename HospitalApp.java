package com.hospital;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HospitalApp {

    // =====================================================
    // DATABASE CONNECTION
    // =====================================================

    static final String DB_URL =
            "jdbc:mysql://localhost:3306/hospital_queue_db"
                    + "?useSSL=false&serverTimezone=UTC";

    static final String DB_USER = "root";

    // CHANGE THIS TO YOUR MYSQL PASSWORD
    static final String DB_PASSWORD = "root";

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASSWORD
        );
    }

    // =====================================================
    // ENUMS
    // =====================================================

    enum Role {
        PATIENT,
        DOCTOR,
        ADMIN
    }

    // =====================================================
    // CUSTOM EXCEPTION
    // =====================================================

    static class InvalidCredentialsException extends Exception {

        InvalidCredentialsException(String message) {
            super(message);
        }
    }

    // =====================================================
    // USER MODEL
    // =====================================================

    static class User {

        int id;
        String username;
        String fullName;
        String email;
        String phone;
        Role role;

        User(int id,
             String username,
             String fullName,
             String email,
             String phone,
             Role role) {

            this.id = id;
            this.username = username;
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.role = role;
        }
    }

    // =====================================================
    // DOCTOR MODEL
    // =====================================================

    static class Doctor {

        int doctorId;
        int userId;
        String name;
        String department;
        String availability;

        Doctor(int doctorId,
               int userId,
               String name,
               String department,
               String availability) {

            this.doctorId = doctorId;
            this.userId = userId;
            this.name = name;
            this.department = department;
            this.availability = availability;
        }

        @Override
        public String toString() {
            return doctorId + " - " + name
                    + " | " + department
                    + " | " + availability;
        }
    }

    // =====================================================
    // APPOINTMENT MODEL
    // =====================================================

    static class Appointment {

        int appointmentId;
        int patientId;
        int doctorId;

        String patientName;
        String doctorName;

        LocalDate appointmentDate;
        LocalTime appointmentTime;

        String symptoms;
        boolean emergency;
        boolean emergencyApproved;

        int queueNumber;
        String status;

        Appointment(
                int appointmentId,
                int patientId,
                int doctorId,
                String patientName,
                String doctorName,
                LocalDate appointmentDate,
                LocalTime appointmentTime,
                String symptoms,
                boolean emergency,
                boolean emergencyApproved,
                int queueNumber,
                String status) {

            this.appointmentId = appointmentId;
            this.patientId = patientId;
            this.doctorId = doctorId;
            this.patientName = patientName;
            this.doctorName = doctorName;
            this.appointmentDate = appointmentDate;
            this.appointmentTime = appointmentTime;
            this.symptoms = symptoms;
            this.emergency = emergency;
            this.emergencyApproved = emergencyApproved;
            this.queueNumber = queueNumber;
            this.status = status;
        }
    }

    // =====================================================
    // AUTHENTICATION SERVICE
    // MODULE 1
    // =====================================================

    static class AuthService {

        User login(String username, String password)
                throws SQLException, InvalidCredentialsException {

            if (username == null || username.isBlank()
                    || password == null || password.isBlank()) {

                throw new InvalidCredentialsException(
                        "Username and password are required."
                );
            }

            String sql =
                    "SELECT * FROM users "
                            + "WHERE username=? AND password=?";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, username);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        return new User(
                                rs.getInt("user_id"),
                                rs.getString("username"),
                                rs.getString("full_name"),
                                rs.getString("email"),
                                rs.getString("phone"),
                                Role.valueOf(rs.getString("role"))
                        );
                    }
                }
            }

            throw new InvalidCredentialsException(
                    "Invalid username or password."
            );
        }

        int registerPatient(
                String username,
                String password,
                String fullName,
                String email,
                String phone
        ) throws SQLException {

            String sql =
                    "INSERT INTO users "
                            + "(username,password,role,full_name,email,phone) "
                            + "VALUES (?,?,'PATIENT',?,?,?)";

            try (Connection con = getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS
                         )) {

                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, fullName);
                ps.setString(4, email);
                ps.setString(5, phone);

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

            return -1;
        }

        User findUserById(int userId) throws SQLException {

            String sql =
                    "SELECT * FROM users WHERE user_id=?";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        return new User(
                                rs.getInt("user_id"),
                                rs.getString("username"),
                                rs.getString("full_name"),
                                rs.getString("email"),
                                rs.getString("phone"),
                                Role.valueOf(rs.getString("role"))
                        );
                    }
                }
            }

            return null;
        }
    }

    // =====================================================
    // DOCTOR SERVICE
    // MODULE 2
    // =====================================================

    static class DoctorService {

        List<Doctor> getAllDoctors() throws SQLException {

            List<Doctor> doctors = new ArrayList<>();

            String sql =
                    "SELECT d.doctor_id, d.user_id, "
                            + "u.full_name, d.department, d.availability "
                            + "FROM doctors d "
                            + "JOIN users u ON d.user_id=u.user_id "
                            + "ORDER BY u.full_name";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    doctors.add(
                            new Doctor(
                                    rs.getInt("doctor_id"),
                                    rs.getInt("user_id"),
                                    rs.getString("full_name"),
                                    rs.getString("department"),
                                    rs.getString("availability")
                            )
                    );
                }
            }

            return doctors;
        }

        Doctor getDoctorByUserId(int userId)
                throws SQLException {

            String sql =
                    "SELECT d.doctor_id, d.user_id, "
                            + "u.full_name, d.department, d.availability "
                            + "FROM doctors d "
                            + "JOIN users u ON d.user_id=u.user_id "
                            + "WHERE d.user_id=?";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        return new Doctor(
                                rs.getInt("doctor_id"),
                                rs.getInt("user_id"),
                                rs.getString("full_name"),
                                rs.getString("department"),
                                rs.getString("availability")
                        );
                    }
                }
            }

            return null;
        }

        void addDoctor(
                int userId,
                String department,
                String availability
        ) throws SQLException {

            String sql =
                    "INSERT INTO doctors "
                            + "(user_id,department,availability) "
                            + "VALUES (?,?,?)";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, userId);
                ps.setString(2, department);
                ps.setString(3, availability);

                ps.executeUpdate();
            }
        }

        void updateDoctor(
                int doctorId,
                String department,
                String availability
        ) throws SQLException {

            String sql =
                    "UPDATE doctors "
                            + "SET department=?, availability=? "
                            + "WHERE doctor_id=?";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, department);
                ps.setString(2, availability);
                ps.setInt(3, doctorId);

                ps.executeUpdate();
            }
        }
    }

    // =====================================================
    // APPOINTMENT SERVICE
    // MODULE 3
    // =====================================================

    static class AppointmentService {

        private final Object queueLock = new Object();

        int bookAppointment(
                int patientId,
                int doctorId,
                LocalDate date,
                LocalTime time,
                String symptoms,
                boolean emergency
        ) throws SQLException {

            synchronized (queueLock) {

                if (!isDoctorAvailable(
                        doctorId,
                        date,
                        time
                )) {

                    throw new SQLException(
                            "Doctor is not available at this time."
                    );
                }

                int queueNumber =
                        getNextQueueNumber(doctorId, date);

                String sql =
                        "INSERT INTO appointments "
                                + "(patient_id,doctor_id,appointment_date,"
                                + "appointment_time,symptoms,emergency_flag,"
                                + "emergency_approved,queue_number,status) "
                                + "VALUES (?,?,?,?,?,?,FALSE,?,'BOOKED')";

                try (Connection con = getConnection();
                     PreparedStatement ps =
                             con.prepareStatement(
                                     sql,
                                     Statement.RETURN_GENERATED_KEYS
                             )) {

                    ps.setInt(1, patientId);
                    ps.setInt(2, doctorId);
                    ps.setDate(3, Date.valueOf(date));
                    ps.setTime(4, Time.valueOf(time));
                    ps.setString(5, symptoms);
                    ps.setBoolean(6, emergency);
                    ps.setInt(7, queueNumber);

                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {

                        if (rs.next()) {
                            return rs.getInt(1);
                        }
                    }
                }
            }

            return -1;
        }

        boolean isDoctorAvailable(
                int doctorId,
                LocalDate date,
                LocalTime time
        ) throws SQLException {

            String sql =
                    "SELECT COUNT(*) FROM appointments "
                            + "WHERE doctor_id=? "
                            + "AND appointment_date=? "
                            + "AND appointment_time=? "
                            + "AND status='BOOKED'";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, doctorId);
                ps.setDate(2, Date.valueOf(date));
                ps.setTime(3, Time.valueOf(time));

                try (ResultSet rs = ps.executeQuery()) {

                    rs.next();

                    return rs.getInt(1) == 0;
                }
            }
        }

        int getNextQueueNumber(
                int doctorId,
                LocalDate date
        ) throws SQLException {

            String sql =
                    "SELECT COALESCE(MAX(queue_number),0)+1 "
                            + "FROM appointments "
                            + "WHERE doctor_id=? "
                            + "AND appointment_date=? "
                            + "AND status='BOOKED'";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, doctorId);
                ps.setDate(2, Date.valueOf(date));

                try (ResultSet rs = ps.executeQuery()) {

                    rs.next();

                    return rs.getInt(1);
                }
            }
        }

        List<Appointment> getPatientAppointments(
                int patientId
        ) throws SQLException {

            return getAppointments(
                    "WHERE a.patient_id=?",
                    patientId
            );
        }

        List<Appointment> getDoctorAppointments(
                int doctorId
        ) throws SQLException {

            return getAppointments(
                    "WHERE a.doctor_id=? AND a.status='BOOKED'",
                    doctorId
            );
        }

        private List<Appointment> getAppointments(
                String condition,
                int id
        ) throws SQLException {

            List<Appointment> list = new ArrayList<>();

            String sql =
                    "SELECT a.*, "
                            + "p.full_name AS patient_name, "
                            + "du.full_name AS doctor_name "
                            + "FROM appointments a "
                            + "JOIN users p ON a.patient_id=p.user_id "
                            + "JOIN doctors d ON a.doctor_id=d.doctor_id "
                            + "JOIN users du ON d.user_id=du.user_id "
                            + condition
                            + " ORDER BY a.appointment_date,"
                            + "a.appointment_time,a.queue_number";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        list.add(mapAppointment(rs));
                    }
                }
            }

            return list;
        }

        List<Appointment> getEmergencyRequests()
                throws SQLException {

            List<Appointment> list = new ArrayList<>();

            String sql =
                    "SELECT a.*, "
                            + "p.full_name AS patient_name, "
                            + "du.full_name AS doctor_name "
                            + "FROM appointments a "
                            + "JOIN users p ON a.patient_id=p.user_id "
                            + "JOIN doctors d ON a.doctor_id=d.doctor_id "
                            + "JOIN users du ON d.user_id=du.user_id "
                            + "WHERE a.emergency_flag=TRUE "
                            + "AND a.emergency_approved=FALSE "
                            + "AND a.status='BOOKED' "
                            + "ORDER BY a.created_at";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    list.add(mapAppointment(rs));
                }
            }

            return list;
        }

        void approveEmergency(int appointmentId)
                throws SQLException {

            String sql =
                    "UPDATE appointments "
                            + "SET emergency_approved=TRUE, "
                            + "queue_number=0 "
                            + "WHERE appointment_id=?";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, appointmentId);
                ps.executeUpdate();
            }
        }

        void rejectEmergency(int appointmentId)
                throws SQLException {

            String sql =
                    "UPDATE appointments "
                            + "SET emergency_flag=FALSE, "
                            + "emergency_approved=FALSE "
                            + "WHERE appointment_id=?";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, appointmentId);
                ps.executeUpdate();
            }
        }

        void cancelAppointment(
            int patientId,
                int appointmentId
        ) throws SQLException {

            String sql =
                    "UPDATE appointments "
                            + "SET status='CANCELLED' "
                            + "WHERE appointment_id=? "
                            + "AND patient_id=? "
                            + "AND status='BOOKED'";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, appointmentId);
                ps.setInt(2, patientId);

                ps.executeUpdate();
            }
        }
        void completeAppointment(
                int doctorId,
                int appointmentId
        ) throws SQLException {

            String sql =
                    "UPDATE appointments "
                            + "SET status='COMPLETED' "
                            + "WHERE appointment_id=? "
                            + "AND doctor_id=?";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, appointmentId);
                ps.setInt(2, doctorId);

                ps.executeUpdate();
            }
        }

        private Appointment mapAppointment(
                ResultSet rs
        ) throws SQLException {

            return new Appointment(
                    rs.getInt("appointment_id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getString("patient_name"),
                    rs.getString("doctor_name"),
                    rs.getDate("appointment_date").toLocalDate(),
                    rs.getTime("appointment_time").toLocalTime(),
                    rs.getString("symptoms"),
                    rs.getBoolean("emergency_flag"),
                    rs.getBoolean("emergency_approved"),
                    rs.getInt("queue_number"),
                    rs.getString("status")
            );
        }
    }
    // =====================================================
    // LOGIN GUI
    // MODULE 1
    // =====================================================

    static class LoginFrame extends JFrame {

        JTextField usernameField = new JTextField(18);
        JPasswordField passwordField = new JPasswordField(18);

        AuthService authService = new AuthService();

        LoginFrame() {

            setTitle("Hospital Appointment System - Login");
            setSize(450, 280);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            JPanel panel = new JPanel(new GridBagLayout());

            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(8, 8, 8, 8);

            g.gridx = 0;
            g.gridy = 0;
            panel.add(new JLabel("Username:"), g);

            g.gridx = 1;
            panel.add(usernameField, g);

            g.gridx = 0;
            g.gridy = 1;
            panel.add(new JLabel("Password:"), g);

            g.gridx = 1;
            panel.add(passwordField, g);

            JButton loginButton =
                    new JButton("Verify Login");

            JButton registerButton =
                    new JButton("Create Account");

            JPanel buttons = new JPanel();

            buttons.add(loginButton);
            buttons.add(registerButton);

            g.gridx = 0;
            g.gridy = 2;
            g.gridwidth = 2;

            panel.add(buttons, g);

            add(panel);
            // =====================================================
    // LOGIN GUI
    // MODULE 1
    // =====================================================

    static class LoginFrame extends JFrame {

        JTextField usernameField = new JTextField(18);
        JPasswordField passwordField = new JPasswordField(18);

        AuthService authService = new AuthService();

        LoginFrame() {

            setTitle("Hospital Appointment System - Login");
            setSize(450, 280);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            JPanel panel = new JPanel(new GridBagLayout());

            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(8, 8, 8, 8);

            g.gridx = 0;
            g.gridy = 0;
            panel.add(new JLabel("Username:"), g);

            g.gridx = 1;
            panel.add(usernameField, g);

            g.gridx = 0;
            g.gridy = 1;
            panel.add(new JLabel("Password:"), g);

            g.gridx = 1;
            panel.add(passwordField, g);

            JButton loginButton =
                    new JButton("Verify Login");

            JButton registerButton =
                    new JButton("Create Account");

            JPanel buttons = new JPanel();

            buttons.add(loginButton);
            buttons.add(registerButton);

            g.gridx = 0;
            g.gridy = 2;
            g.gridwidth = 2;

            panel.add(buttons, g);

            add(panel);
            createButton.addActionListener(e -> register());
        }

        void register() {

            try {

                int id = new AuthService().registerPatient(
                        username.getText(),
                        new String(password.getPassword()),
                        fullName.getText(),
                        email.getText(),
                        phone.getText()
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Account created successfully.\nUser ID: " + id
                );

                dispose();

            } catch (Exception ex) {

                showError(this, ex);
            }
        }
    }

    // =====================================================
    // DASHBOARD GUI
    // MODULE 2, 3, 4, 5
    // =====================================================

    static class DashboardFrame extends JFrame {

        User user;

        JTextArea output = new JTextArea();

        DoctorService doctorService =
                new DoctorService();

        AppointmentService appointmentService =
                new AppointmentService();

        DashboardFrame(User user) {

            this.user = user;

            setTitle(
                    "Hospital System - "
                            + user.role
                            + " Dashboard"
            );

            setSize(1000, 650);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            JPanel topPanel = new JPanel();

            topPanel.add(
                    new JLabel(
                            "Welcome, "
                                    + user.fullName
                                    + " | Role: "
                                    + user.role
                    )
            );

            JPanel actions = new JPanel(
                    new FlowLayout(FlowLayout.LEFT)
            );

            JButton profileButton =
                    new JButton("View Profile");

            JButton doctorsButton =
                    new JButton("View Doctors");
                    actions.add(profileButton);
            actions.add(doctorsButton);

            profileButton.addActionListener(
                    e -> showProfile()
            );

            doctorsButton.addActionListener(
                    e -> showDoctors()
            );

            // PATIENT FEATURES
            if (user.role == Role.PATIENT) {

                JButton bookButton =
                        new JButton("Book Appointment");

                JButton appointmentsButton =
                        new JButton("My Appointments");

                JButton cancelButton =
                        new JButton("Cancel Appointment");

                actions.add(bookButton);
                actions.add(appointmentsButton);
                actions.add(cancelButton);

                bookButton.addActionListener(
                        e -> new BookingFrame(user).setVisible(true)
                );

                appointmentsButton.addActionListener(
                        e -> showPatientAppointments()
                );

                cancelButton.addActionListener(
                        e -> cancelAppointment()
                );
            }

            // DOCTOR FEATURES
            if (user.role == Role.DOCTOR) {

                JButton queueButton =
                        new JButton("My Queue");

                actions.add(queueButton);

                queueButton.addActionListener(
                        e -> showDoctorQueue()
                );
            }

            // ADMIN FEATURES
            if (user.role == Role.ADMIN) {

                JButton manageDoctorButton =
                        new JButton("Manage Doctors");

                JButton emergencyButton =
                        new JButton("Emergency Requests");

                actions.add(manageDoctorButton);
                actions.add(emergencyButton);

                manageDoctorButton.addActionListener(
                        e -> new DoctorManagementFrame()
                                .setVisible(true)
                );
                emergencyButton.addActionListener(
                        e -> showEmergencyRequests()
                );
            }

            output.setEditable(false);

            output.setFont(
                    new Font(
                            "Monospaced",
                            Font.PLAIN,
                            13
                    )
            );

            add(topPanel, BorderLayout.NORTH);
            add(actions, BorderLayout.CENTER);
            add(new JScrollPane(output), BorderLayout.SOUTH);

            output.setRows(25);
        }

        void showProfile() {

            output.setText(
                    "USER PROFILE\n\n"
                            + "User ID: " + user.id + "\n"
                            + "Name: " + user.fullName + "\n"
                            + "Username: " + user.username + "\n"
                            + "Role: " + user.role + "\n"
                            + "Email: " + user.email + "\n"
                            + "Phone: " + user.phone
            );
        }

        void showDoctors() {

            try {

                StringBuilder text =
                        new StringBuilder("DOCTOR LIST\n\n");

                for (Doctor doctor :
                        doctorService.getAllDoctors()) {

                    text.append(doctor)
                            .append("\n");
                }

                output.setText(text.toString());

            } catch (Exception ex) {

                showError(this, ex);
            }
        }

        void showPatientAppointments() {

            try {

                StringBuilder text =
                        new StringBuilder("MY APPOINTMENTS\n\n");

                for (Appointment appointment :
                        appointmentService
                                .getPatientAppointments(user.id)) {

                    text.append(formatAppointment(appointment))
                            .append("\n");
                }

                output.setText(text.toString());

            } catch (Exception ex) {

                showError(this, ex);
            }
        }

        void cancelAppointment() {

            String id = JOptionPane.showInputDialog(
                    this,
                    "Enter appointment ID to cancel:"
            );

            if (id == null) return;

            try {

                appointmentService.cancelAppointment(
                        user.id,
                        Integer.parseInt(id)
                );
                showPatientAppointments();

            } catch (Exception ex) {

                showError(this, ex);
            }
        }

        void showDoctorQueue() {

            try {

                Doctor doctor =
                        doctorService.getDoctorByUserId(user.id);

                if (doctor == null) {

                    output.setText(
                            "Doctor profile is not configured."
                    );

                    return;
                }

                StringBuilder text =
                        new StringBuilder("DOCTOR QUEUE\n\n");

                for (Appointment appointment :
                        appointmentService
                                .getDoctorAppointments(
                                        doctor.doctorId
                                )) {

                    text.append(formatAppointment(appointment))
                            .append("\n");
                }

                output.setText(text.toString());

                String id = JOptionPane.showInputDialog(
                        this,
                        "Enter appointment ID to mark completed:"
                );

                if (id != null) {

                    appointmentService.completeAppointment(
                            doctor.doctorId,
                            Integer.parseInt(id)
                    );

                    showDoctorQueue();
                }

            } catch (Exception ex) {
                showError(this, ex);
            }
        }
        void showEmergencyRequests() {
                try {
                       List<Appointment> requests = appointmentService .getEmergencyRequests();
                       if (requests.isEmpty()) {
                                output.setText( "No pending emergency requests.");
                                return;
                       }
                       StringBuilder text = new StringBuilder( "EMERGENCY REQUESTS\n\n" );
                       for (Appointment appointment : requests) {
                                text.append(formatAppointment(appointment)) .append("\n");
                       }
                       output.setText(text.toString());
                       String id = JOptionPane.showInputDialog( this, "Enter appointment ID to approve:" );

                       if (id != null) {
                                appointmentService.approveEmergency( Integer.parseInt(id));
                                JOptionPane.showMessageDialog( this, "Emergency request approved." );
                                showEmergencyRequests();
                       }
                }catch(Exception ex){
                        showError(this, ex);
                }
        }
}

// =====================================================
    // BOOKING GUI
    // MODULE 3
    // =====================================================

    static class BookingFrame extends JFrame {

        User patient;

        JComboBox<Doctor> doctorBox =
                new JComboBox<>();

        JTextField dateField =
                new JTextField("2026-10-01");

        JTextField timeField =
                new JTextField("10:00");

        JTextField symptomsField =
                new JTextField();

        JCheckBox emergencyCheckBox =
                new JCheckBox("Emergency Request");

        DoctorService doctorService =
                new DoctorService();

        AppointmentService appointmentService =
                new AppointmentService();

        BookingFrame(User patient) {

            this.patient = patient;

            setTitle("Appointment Booking");
            setSize(550, 360);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel(
                    new GridLayout(6, 2, 8, 8)
            );

            panel.setBorder(
                    BorderFactory.createEmptyBorder(
                            15, 15, 15, 15
                    )
            );

            panel.add(new JLabel("Select Doctor"));
            panel.add(doctorBox);

            panel.add(new JLabel("Date (yyyy-MM-dd)"));
            panel.add(dateField);

            panel.add(new JLabel("Time (HH:mm)"));
            panel.add(timeField);

            panel.add(new JLabel("Symptoms"));
            panel.add(symptomsField);

            panel.add(new JLabel("Priority"));
            panel.add(emergencyCheckBox);

            JButton bookButton =
                    new JButton("Book Appointment");

            panel.add(new JLabel());
            panel.add(bookButton);

            add(panel);

            try {

                for (Doctor doctor :
                        doctorService.getAllDoctors()) {

                    doctorBox.addItem(doctor);
                }

            } catch (Exception ex) {

                showError(this, ex);
            }

            bookButton.addActionListener(
                    e -> bookAppointment()
            );
        }

        void bookAppointment() {

            try {

                Doctor doctor =
                        (Doctor) doctorBox.getSelectedItem();

                if (doctor == null) {

                    throw new SQLException(
                            "Please select a doctor."
                    );
                }

                LocalDate date =
                        LocalDate.parse(
                                dateField.getText()
                        );

                LocalTime time =
                        LocalTime.parse(
                                timeField.getText(),
                                DateTimeFormatter.ofPattern(
                                        "HH:mm"
                                )
                        );

                int appointmentId =
                        appointmentService.bookAppointment(
                                patient.id,
                                doctor.doctorId,
                                date,
                                time,
                                symptomsField.getText(),
                                emergencyCheckBox.isSelected()
                        );

                JOptionPane.showMessageDialog(
                        this,
                        "Appointment booked successfully!\n"
                                + "Appointment ID: "
                                + appointmentId
                );

                dispose();

            } catch (Exception ex) {

                showError(this, ex);
            }
        }
    }

    // =====================================================
    // ADMIN DOCTOR MANAGEMENT
    // MODULE 2
    // =====================================================

    static class DoctorManagementFrame extends JFrame {

        JTextField doctorIdField =
                new JTextField();

        JTextField userIdField =
                new JTextField();

        JTextField departmentField =
                new JTextField();

        JTextField availabilityField =
                new JTextField();

        JTextArea output =
                new JTextArea();

        DoctorService doctorService =
                new DoctorService();

        AuthService authService =
                new AuthService();

        DoctorManagementFrame() {

            setTitle("Admin - Doctor Management");
            setSize(750, 500);
            setLocationRelativeTo(null);

            JPanel form = new JPanel(
                    new GridLayout(5, 2, 8, 8)
            );

            form.setBorder(
                    BorderFactory.createEmptyBorder(
                            10, 10, 10, 10
                    )
            );

            form.add(new JLabel("Doctor ID for Edit"));
            form.add(doctorIdField);

            form.add(new JLabel("User ID for New Doctor"));
            form.add(userIdField);

            form.add(new JLabel("Department"));
            form.add(departmentField);

            form.add(new JLabel("Availability"));
            form.add(availabilityField);

            JButton addButton =
                    new JButton("Add Doctor");

            JButton editButton =
                    new JButton("Edit Doctor");

            form.add(addButton);
            form.add(editButton);

            JButton refreshButton =
                    new JButton("Refresh List");

            add(form, BorderLayout.NORTH);
            add(refreshButton, BorderLayout.CENTER);

            output.setEditable(false);
            output.setRows(15);

            add(new JScrollPane(output), BorderLayout.SOUTH);

            addButton.addActionListener(
                    e -> addDoctor()
            );

            editButton.addActionListener(
                    e -> editDoctor()
            );

            refreshButton.addActionListener(
                    e -> refreshDoctors()
            );

            refreshDoctors();
        }

        void addDoctor() {

            try {

                int userId =
                        Integer.parseInt(
                                userIdField.getText()
                        );

                User user =
                        authService.findUserById(userId);

                if (user == null ||
                        user.role != Role.DOCTOR) {

                    throw new SQLException(
                            "User must have DOCTOR role."
                    );
                }

                doctorService.addDoctor(
                        userId,
                        departmentField.getText(),
                        availabilityField.getText()
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Doctor added successfully."
                );

                refreshDoctors();

            } catch (Exception ex) {

                showError(this, ex);
            }
        }

        void editDoctor() {

            try {

                doctorService.updateDoctor(
                        Integer.parseInt(
                                doctorIdField.getText()
                        ),
                        departmentField.getText(),
                        availabilityField.getText()
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Doctor updated successfully."
                );

                refreshDoctors();

            } catch (Exception ex) {

                showError(this, ex);
            }
        }

        void refreshDoctors() {

            try {

                StringBuilder text =
                        new StringBuilder("DOCTOR LIST\n\n");

                for (Doctor doctor :
                        doctorService.getAllDoctors()) {

                    text.append(doctor)
                            .append("\n");
                }

                output.setText(text.toString());

            } catch (Exception ex) {

                showError(this, ex);
            }
        }
    }
        }
    // =====================================================
    // UTILITY METHODS
    // =====================================================

    static String formatAppointment(
            Appointment appointment
    ) {

        return "Appointment ID: "
                + appointment.appointmentId
                + " | Patient: "
                + appointment.patientName
                + " | Doctor: "
                + appointment.doctorName
                + " | Date: "
                + appointment.appointmentDate
                + " | Time: "
                + appointment.appointmentTime
                + " | Queue: "
                + (appointment.queueNumber == 0
                ? "EMERGENCY"
                : appointment.queueNumber)
                + " | Emergency: "
                + appointment.emergency
                + " | Status: "
                + appointment.status;
    }

    static void showError(
            Component parent,
            Exception ex
    ) {

        JOptionPane.showMessageDialog(
                parent,
                ex.getMessage(),
                "System Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );

            } catch (Exception ignored) {
            }

            new LoginFrame().setVisible(true);
        });
    }
}
}

