package com.hospital.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.User;
import com.hospital.service.AppointmentService;
import com.hospital.service.DoctorService;

/**
 * Professional appointment booking screen.
 *
 * Module 2:
 * Appointment Booking & Scheduling
 *
 * Functions:
 * - Book Appointment
 * - Check Doctor Availability
 * - Flag Emergency
 */
public class BookingFrame extends JFrame {

    private User patient;

    private DoctorService doctorService;
    private AppointmentService appointmentService;

    private JComboBox<Doctor> doctorComboBox;

    private JTextField dateField;
    private JTextField timeField;

    private JTextArea symptomsArea;

    private JCheckBox emergencyCheckBox;

    public BookingFrame(User patient) {

        this.patient = patient;

        doctorService = new DoctorService();
        appointmentService =
                new AppointmentService();

        setTitle(
                "Hospital Appointment System - Book Appointment"
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

        loadDoctors();
    }

    /**
     * Creates the professional booking interface.
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
                        new GridBagLayout()
                );

        brandPanel.setBackground(
                AppTheme.PRIMARY_DARK
        );

        GridBagConstraints brandGbc =
                new GridBagConstraints();

        brandGbc.gridx = 0;
        brandGbc.gridy = 0;

        brandGbc.insets =
                new Insets(
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
                new JLabel(
                        "HOSPITAL+"
                );

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
                        "Appointment Portal"
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
                        new GridBagLayout()
                );

        navigationPanel.setBackground(
                AppTheme.PRIMARY_DARK
        );

        GridBagConstraints navGbc =
                new GridBagConstraints();

        navGbc.gridx = 0;
        navGbc.fill =
                GridBagConstraints.HORIZONTAL;

        navGbc.weightx = 1;

        navGbc.insets =
                new Insets(
                        6, 15, 6, 15
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

        navGbc.gridy = 0;

        navigationPanel.add(
                dashboardButton,
                navGbc
        );

        navGbc.gridy++;

        navigationPanel.add(
                bookingButton,
                navGbc
        );

        navGbc.gridy++;

        navigationPanel.add(
                historyButton,
                navGbc
        );

        navGbc.gridy++;

        navigationPanel.add(
                logoutButton,
                navGbc
        );

        sidebar.add(
                navigationPanel,
                BorderLayout.NORTH
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
         * Page header.
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
                        new GridBagLayout()
                );

        headingPanel.setOpaque(
                false
        );

        GridBagConstraints headingGbc =
                new GridBagConstraints();

        headingGbc.gridx = 0;
        headingGbc.gridy = 0;

        headingGbc.anchor =
                GridBagConstraints.WEST;

        headingGbc.insets =
                new Insets(
                        0,
                        0,
                        5,
                        0
                );

        JLabel titleLabel =
                new JLabel(
                        "Book an Appointment"
                );

        titleLabel.setFont(
                AppTheme.TITLE_FONT
        );

        titleLabel.setForeground(
                AppTheme.TEXT_PRIMARY
        );

        headingPanel.add(
                titleLabel,
                headingGbc
        );

        headingGbc.gridy++;

        JLabel subtitleLabel =
                new JLabel(
                        "Select a doctor, choose a suitable "
                                + "time and provide your symptoms."
                );

        subtitleLabel.setFont(
                AppTheme.BODY_FONT
        );

        subtitleLabel.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        headingPanel.add(
                subtitleLabel,
                headingGbc
        );

        headerPanel.add(
                headingPanel,
                BorderLayout.WEST
        );

        contentPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        /*
         * =================================================
         * BOOKING CARD
         * =================================================
         */

        JPanel bookingCard =
                new JPanel(
                        new GridBagLayout()
                );

        bookingCard.setBackground(
                AppTheme.SURFACE
        );

        bookingCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                AppTheme.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                30,
                                35,
                                30,
                                35
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.insets =
                new Insets(
                        7,
                        5,
                        7,
                        5
                );

        /*
         * Doctor
         */

        addFieldLabel(
                bookingCard,
                "Select Doctor",
                gbc
        );

        gbc.gridy++;

        doctorComboBox =
                new JComboBox<>();

        doctorComboBox.setFont(
                AppTheme.BODY_FONT
        );

        doctorComboBox.setBackground(
                Color.WHITE
        );

        doctorComboBox.setPreferredSize(
                new Dimension(
                        0,
                        42
                )
        );

        bookingCard.add(
                doctorComboBox,
                gbc
        );

        /*
         * Date
         */

        gbc.gridy++;

        addFieldLabel(
                bookingCard,
                "Appointment Date",
                gbc
        );

        gbc.gridy++;

        dateField =
                new JTextField();

        AppTheme.styleTextField(
                dateField
        );

        dateField.setToolTipText(
                "Format: YYYY-MM-DD"
        );

        bookingCard.add(
                dateField,
                gbc
        );

        /*
         * Time
         */

        gbc.gridy++;

        addFieldLabel(
                bookingCard,
                "Appointment Time",
                gbc
        );

        gbc.gridy++;

        timeField =
                new JTextField();

        AppTheme.styleTextField(
                timeField
        );

        timeField.setToolTipText(
                "Format: HH:MM"
        );

        bookingCard.add(
                timeField,
                gbc
        );

        /*
         * Symptoms
         */

        gbc.gridy++;

        addFieldLabel(
                bookingCard,
                "Symptoms / Reason for Visit",
                gbc
        );

        gbc.gridy++;

        symptomsArea =
                new JTextArea(
                        4,
                        20
                );

        symptomsArea.setFont(
                AppTheme.BODY_FONT
        );

        symptomsArea.setLineWrap(
                true
        );

        symptomsArea.setWrapStyleWord(
                true
        );

        symptomsArea.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                AppTheme.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                8,
                                10,
                                8,
                                10
                        )
                )
        );

        JScrollPane symptomsScrollPane =
                new JScrollPane(
                        symptomsArea
                );

        bookingCard.add(
                symptomsScrollPane,
                gbc
        );

        /*
         * Emergency flag
         */

        gbc.gridy++;

        JPanel emergencyPanel =
                new JPanel(
                        new BorderLayout()
                );

        emergencyPanel.setBackground(
                new Color(
                        255,
                        245,
                        245
                )
        );

        emergencyPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        245,
                                        190,
                                        190
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                10,
                                12,
                                10,
                                12
                        )
                )
        );

        emergencyCheckBox =
                new JCheckBox(
                        "Flag this appointment as an emergency"
                );

        emergencyCheckBox.setFont(
                AppTheme.SUBHEADING_FONT
        );

        emergencyCheckBox.setForeground(
                AppTheme.DANGER
        );

        emergencyCheckBox.setBackground(
                new Color(
                        255,
                        245,
                        245
                )
        );

        emergencyPanel.add(
                emergencyCheckBox,
                BorderLayout.CENTER
        );

        bookingCard.add(
                emergencyPanel,
                gbc
        );

        /*
         * Information text
         */

        gbc.gridy++;

        JLabel informationLabel =
                new JLabel(
                        "<html><i>"
                                + "Emergency requests will be "
                                + "reviewed according to hospital "
                                + "emergency procedures."
                                + "</i></html>"
                );

        informationLabel.setFont(
                AppTheme.SMALL_FONT
        );

        informationLabel.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        bookingCard.add(
                informationLabel,
                gbc
        );

        /*
         * Buttons
         */

        gbc.gridy++;

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                5
                        )
                );

        buttonPanel.setOpaque(
                false
        );

        JButton cancelButton =
                new JButton(
                        "Back"
                );

        cancelButton.setFont(
                AppTheme.BODY_FONT
        );

        cancelButton.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        cancelButton.setBackground(
                Color.WHITE
        );

        cancelButton.setFocusPainted(
                false
        );

        cancelButton.setBorder(
                BorderFactory.createLineBorder(
                        AppTheme.BORDER
                )
        );

        JButton bookButton =
                new JButton(
                        "Confirm Appointment"
                );

        AppTheme.styleButton(
                bookButton
        );

        buttonPanel.add(
                cancelButton
        );

        buttonPanel.add(
                bookButton
        );

        bookingCard.add(
                buttonPanel,
                gbc
        );

        contentPanel.add(
                bookingCard,
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
         * BUTTON ACTIONS
         * =================================================
         */

        bookButton.addActionListener(
                e -> bookAppointment()
        );

        cancelButton.addActionListener(
                e -> openDashboard()
        );

        dashboardButton.addActionListener(
                e -> openDashboard()
        );

        bookingButton.addActionListener(
                e -> {
                    // Already on booking screen.
                }
        );

        historyButton.addActionListener(
                e -> openHistory()
        );

        logoutButton.addActionListener(
                e -> logout()
        );
    }

    /**
     * Adds a standard field label.
     */
    private void addFieldLabel(
            JPanel panel,
            String text,
            GridBagConstraints gbc) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                AppTheme.SUBHEADING_FONT
        );

        label.setForeground(
                AppTheme.TEXT_PRIMARY
        );

        panel.add(
                label,
                gbc
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
     * Loads doctors from the database.
     */
    private void loadDoctors() {

        try {

            List<Doctor> doctors =
                    doctorService.getAllDoctors();

            doctorComboBox.removeAllItems();

            for (Doctor doctor : doctors) {

                doctorComboBox.addItem(
                        doctor
                );
            }

            if (doctors.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No doctors are currently available.",
                        "No Doctors",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load doctors.\n\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Handles appointment booking.
     *
     * The original appointment business logic
     * remains unchanged.
     */
    private void bookAppointment() {

        Doctor selectedDoctor =
                (Doctor)
                        doctorComboBox
                                .getSelectedItem();

        String dateText =
                dateField
                        .getText()
                        .trim();

        String timeText =
                timeField
                        .getText()
                        .trim();

        String symptoms =
                symptomsArea
                        .getText()
                        .trim();

        boolean emergency =
                emergencyCheckBox
                        .isSelected();

        if (selectedDoctor == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a doctor.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (dateText.isEmpty()
                || timeText.isEmpty()
                || symptoms.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all appointment details.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        LocalDate appointmentDate;

        LocalTime appointmentTime;

        try {

            appointmentDate =
                    LocalDate.parse(
                            dateText
                    );

            appointmentTime =
                    LocalTime.parse(
                            timeText.length() == 5
                                    ? timeText + ":00"
                                    : timeText
                    );

        } catch (
                DateTimeParseException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the date and time correctly.\n\n"
                            + "Date format: YYYY-MM-DD\n"
                            + "Time format: HH:MM",
                    "Invalid Date or Time",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            /*
             * Check doctor availability.
             */
            if (!appointmentService
                    .isDoctorAvailable(
                            selectedDoctor,
                            appointmentDate,
                            appointmentTime)) {

                JOptionPane.showMessageDialog(
                        this,
                        "The selected doctor is not available "
                                + "at this date and time.",
                        "Doctor Unavailable",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            /*
             * Check whether the slot is already booked.
             */
            if (appointmentService
                    .isSlotAlreadyBooked(
                            selectedDoctor.getId(),
                            appointmentDate,
                            appointmentTime)) {

                JOptionPane.showMessageDialog(
                        this,
                        "This appointment slot is already booked.",
                        "Slot Unavailable",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            /*
             * Create the appointment.
             */
            Appointment appointment =
                    appointmentService
                            .bookAppointment(
                                    patient.getUserId(),
                                    selectedDoctor.getId(),
                                    appointmentDate,
                                    appointmentTime,
                                    symptoms,
                                    emergency
                            );

            String emergencyMessage =
                    emergency
                            ? "\nEmergency flag: YES"
                            : "\nEmergency flag: NO";

            JOptionPane.showMessageDialog(
                    this,
                    "Appointment booked successfully!\n\n"
                            + "Appointment ID: "
                            + appointment
                                    .getAppointmentId()
                            + "\nDoctor: "
                            + selectedDoctor
                                    .getFullName()
                            + "\nDepartment: "
                            + selectedDoctor
                                    .getDepartment()
                            + "\nDate: "
                            + appointmentDate
                            + "\nTime: "
                            + appointmentTime
                            + emergencyMessage,
                    "Booking Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            openDashboard();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to book appointment.\n\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Returns to the patient dashboard.
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
     * Opens appointment history.
     */
    private void openHistory() {

        AppointmentHistoryFrame historyFrame =
                new AppointmentHistoryFrame(
                        patient
                );

        historyFrame.setVisible(true);

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