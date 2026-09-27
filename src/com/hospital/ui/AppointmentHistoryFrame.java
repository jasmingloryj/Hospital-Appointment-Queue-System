package com.hospital.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.hospital.model.User;

/**
 * Professional appointment history screen.
 *
 * Displays appointments belonging to the logged-in patient.
 */
public class AppointmentHistoryFrame extends JFrame {

    private User patient;

    private JTable appointmentTable;

    private DefaultTableModel tableModel;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/hospital_queue_db"
                    + "?useSSL=false&serverTimezone=Asia/Kolkata"
                    + "&allowPublicKeyRetrieval=true";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD =
            "Jasmin@2008";

    public AppointmentHistoryFrame(User patient) {

        this.patient = patient;

        setTitle(
                "Hospital Appointment System - My Appointments"
        );

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setMinimumSize(
                new Dimension(1100, 700)
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadAppointments();
    }

    /**
     * Creates the professional appointment history interface.
     */
    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(
                AppTheme.BACKGROUND
        );

        /*
         * =================================================
         * SIDEBAR
         * =================================================
         */

        JPanel sidebar =
                new JPanel(
                        new BorderLayout()
                );

        sidebar.setBackground(
                AppTheme.PRIMARY_DARK
        );

        sidebar.setPreferredSize(
                new Dimension(250, 0)
        );

        /*
         * Branding.
         */

        JPanel brandPanel =
                new JPanel(
                        new java.awt.GridBagLayout()
                );

        brandPanel.setBackground(
                AppTheme.PRIMARY_DARK
        );

        java.awt.GridBagConstraints brandGbc =
                new java.awt.GridBagConstraints();

        brandGbc.gridx = 0;
        brandGbc.gridy = 0;

        brandGbc.insets =
                new java.awt.Insets(
                        25, 10, 5, 10
                );

        JLabel logo =
                new JLabel("+");

        logo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        42
                )
        );

        logo.setForeground(
                Color.WHITE
        );

        brandPanel.add(
                logo,
                brandGbc
        );

        brandGbc.gridy++;

        JLabel brandName =
                new JLabel("HOSPITAL+");

        brandName.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        brandName.setForeground(
                Color.WHITE
        );

        brandPanel.add(
                brandName,
                brandGbc
        );

        brandGbc.gridy++;

        JLabel portalLabel =
                new JLabel(
                        "Patient Portal"
                );

        portalLabel.setFont(
                AppTheme.SMALL_FONT
        );

        portalLabel.setForeground(
                new Color(
                        190, 210, 235
                )
        );

        brandPanel.add(
                portalLabel,
                brandGbc
        );

        sidebar.add(
                brandPanel,
                BorderLayout.NORTH
        );

        /*
         * Navigation.
         */

        JPanel navigationPanel =
                new JPanel(
                        new java.awt.GridLayout(
                                4,
                                1,
                                0,
                                8
                        )
                );

        navigationPanel.setBackground(
                AppTheme.PRIMARY_DARK
        );

        navigationPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        15,
                        20,
                        15
                )
        );

        JButton dashboardButton =
                createSidebarButton(
                        "Dashboard"
                );

        JButton bookingButton =
                createSidebarButton(
                        "Book Appointment"
                );

        JButton historyButton =
                createSidebarButton(
                        "My Appointments"
                );

        JButton logoutButton =
                createSidebarButton(
                        "Logout"
                );

        navigationPanel.add(
                dashboardButton
        );

        navigationPanel.add(
                bookingButton
        );

        navigationPanel.add(
                historyButton
        );

        navigationPanel.add(
                logoutButton
        );

        sidebar.add(
                navigationPanel,
                BorderLayout.CENTER
        );

        /*
         * =================================================
         * MAIN CONTENT
         * =================================================
         */

        JPanel contentPanel =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        contentPanel.setBackground(
                AppTheme.BACKGROUND
        );

        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        35,
                        30,
                        35
                )
        );

        /*
         * Header.
         */

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setOpaque(
                false
        );

        JPanel headingPanel =
                new JPanel(
                        new java.awt.GridLayout(
                                2,
                                1
                        )
                );

        headingPanel.setOpaque(
                false
        );

        JLabel titleLabel =
                new JLabel(
                        "My Appointments"
                );

        titleLabel.setFont(
                AppTheme.TITLE_FONT
        );

        titleLabel.setForeground(
                AppTheme.TEXT_PRIMARY
        );

        JLabel subtitleLabel =
                new JLabel(
                        "View and track your scheduled "
                                + "hospital appointments."
                );

        subtitleLabel.setFont(
                AppTheme.BODY_FONT
        );

        subtitleLabel.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        headingPanel.add(
                titleLabel
        );

        headingPanel.add(
                subtitleLabel
        );

        headerPanel.add(
                headingPanel,
                BorderLayout.WEST
        );

        /*
         * Patient information.
         */

        JLabel patientLabel =
                new JLabel(
                        "Patient: "
                                + patient.getFullName()
                );

        patientLabel.setFont(
                AppTheme.SUBHEADING_FONT
        );

        patientLabel.setForeground(
                AppTheme.PRIMARY
        );

        headerPanel.add(
                patientLabel,
                BorderLayout.EAST
        );

        contentPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        /*
         * =================================================
         * APPOINTMENT TABLE CARD
         * =================================================
         */

        JPanel tableCard =
                new JPanel(
                        new BorderLayout()
                );

        tableCard.setBackground(
                AppTheme.SURFACE
        );

        tableCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                AppTheme.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        String[] columns = {
                "Appointment ID",
                "Doctor",
                "Department",
                "Date",
                "Time",
                "Symptoms",
                "Emergency",
                "Status"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        appointmentTable =
                new JTable(
                        tableModel
                );

        appointmentTable.setFont(
                AppTheme.BODY_FONT
        );

        appointmentTable.setRowHeight(
                38
        );

        appointmentTable.setShowGrid(
                false
        );

        appointmentTable.setIntercellSpacing(
                new Dimension(
                        0,
                        0
                )
        );

        appointmentTable.setSelectionBackground(
                new Color(
                        225,
                        238,
                        252
                )
        );

        appointmentTable.setSelectionForeground(
                AppTheme.TEXT_PRIMARY
        );

        /*
         * Table header styling.
         */

        appointmentTable.getTableHeader()
                .setFont(
                        AppTheme.SUBHEADING_FONT
                );

        appointmentTable.getTableHeader()
                .setBackground(
                        AppTheme.PRIMARY_DARK
                );

        appointmentTable.getTableHeader()
                .setForeground(
                        Color.WHITE
                );

        appointmentTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                42
                        )
                );

        /*
         * Center selected columns.
         */

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                JLabel.CENTER
        );

        appointmentTable
                .getColumnModel()
                .getColumn(0)
                .setCellRenderer(
                        centerRenderer
                );

        appointmentTable
                .getColumnModel()
                .getColumn(6)
                .setCellRenderer(
                        centerRenderer
                );

        appointmentTable
                .getColumnModel()
                .getColumn(7)
                .setCellRenderer(
                        centerRenderer
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        appointmentTable
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        AppTheme.BORDER
                )
        );

        tableCard.add(
                scrollPane,
                BorderLayout.CENTER
        );

        /*
         * Bottom controls.
         */

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setOpaque(
                false
        );

        JLabel noteLabel =
                new JLabel(
                        "Appointments are loaded directly "
                                + "from the hospital database."
                );

        noteLabel.setFont(
                AppTheme.SMALL_FONT
        );

        noteLabel.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        bottomPanel.add(
                noteLabel,
                BorderLayout.WEST
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttonPanel.setOpaque(
                false
        );

        JButton refreshButton =
                new JButton(
                        "Refresh"
                );

        AppTheme.styleButton(
                refreshButton
        );

        JButton backButton =
                new JButton(
                        "Back to Dashboard"
                );

        backButton.setFont(
                AppTheme.BODY_FONT
        );

        backButton.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        backButton.setBackground(
                Color.WHITE
        );

        backButton.setFocusPainted(
                false
        );

        backButton.setBorder(
                BorderFactory.createLineBorder(
                        AppTheme.BORDER
                )
        );

        buttonPanel.add(
                refreshButton
        );

        buttonPanel.add(
                backButton
        );

        bottomPanel.add(
                buttonPanel,
                BorderLayout.EAST
        );

        tableCard.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        contentPanel.add(
                tableCard,
                BorderLayout.CENTER
        );

        mainPanel.add(
                sidebar,
                BorderLayout.WEST
        );

        mainPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        /*
         * =================================================
         * ACTIONS
         * =================================================
         */

        dashboardButton.addActionListener(
                e -> openDashboard()
        );

        bookingButton.addActionListener(
                e -> openBooking()
        );

        historyButton.addActionListener(
                e -> {
                    // Already on appointment history.
                }
        );

        logoutButton.addActionListener(
                e -> logout()
        );

        backButton.addActionListener(
                e -> openDashboard()
        );

        refreshButton.addActionListener(
                e -> refreshAppointments()
        );
    }

    /**
     * Creates a sidebar navigation button.
     */
    private JButton createSidebarButton(
            String text) {

        JButton button =
                new JButton(text);

        button.setFont(
                AppTheme.BODY_FONT
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                AppTheme.PRIMARY_DARK
        );

        button.setHorizontalAlignment(
                JButton.LEFT
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        15,
                        12,
                        15
                )
        );

        button.setCursor(
                new java.awt.Cursor(
                        java.awt.Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    /**
     * Loads the logged-in patient's appointments
     * from the MySQL database.
     */
    private void loadAppointments() {

        String sql =
                "SELECT a.appointment_id, "
                        + "u.full_name AS doctor_name, "
                        + "d.department, "
                        + "a.appointment_date, "
                        + "a.appointment_time, "
                        + "a.symptoms, "
                        + "a.emergency_flag, "
                        + "a.status "
                        + "FROM appointments a "
                        + "JOIN doctors d "
                        + "ON a.doctor_id = d.doctor_id "
                        + "JOIN users u "
                        + "ON d.user_id = u.user_id "
                        + "WHERE a.patient_id = ? "
                        + "ORDER BY a.appointment_date DESC, "
                        + "a.appointment_time DESC";

        try (
                Connection connection =
                        java.sql.DriverManager
                                .getConnection(
                                        DB_URL,
                                        DB_USER,
                                        DB_PASSWORD
                                );

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql
                        )
        ) {

            statement.setInt(
                    1,
                    patient.getUserId()
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    Object[] row = {
                            resultSet.getInt(
                                    "appointment_id"
                            ),

                            resultSet.getString(
                                    "doctor_name"
                            ),

                            resultSet.getString(
                                    "department"
                            ),

                            resultSet.getDate(
                                    "appointment_date"
                            ),

                            resultSet.getTime(
                                    "appointment_time"
                            ),

                            resultSet.getString(
                                    "symptoms"
                            ),

                            resultSet.getBoolean(
                                    "emergency_flag"
                            )
                                    ? "YES"
                                    : "NO",

                            resultSet.getString(
                                    "status"
                            )
                    };

                    tableModel.addRow(
                            row
                    );
                }
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load appointment history.\n\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Clears and reloads the appointment table.
     */
    private void refreshAppointments() {

        tableModel.setRowCount(0);

        loadAppointments();
    }

    /**
     * Opens patient dashboard.
     */
    private void openDashboard() {

        PatientDashboard dashboard =
                new PatientDashboard(
                        patient
                );

        dashboard.setVisible(true);

        dispose();
    }

    /**
     * Opens booking screen.
     */
    private void openBooking() {

        BookingFrame bookingFrame =
                new BookingFrame(
                        patient
                );

        bookingFrame.setVisible(true);

        dispose();
    }

    /**
     * Logs the patient out.
     */
    private void logout() {

        LoginFrame loginFrame =
                new LoginFrame();

        loginFrame.setVisible(true);

        dispose();
    }
}