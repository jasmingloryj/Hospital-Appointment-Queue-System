package com.hospital.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
import javax.swing.JTextField;

import com.hospital.model.Doctor;
import com.hospital.model.User;
import com.hospital.service.AppointmentService;
import com.hospital.service.DoctorService;

/**
 * Appointment booking screen.
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
    private JTextField symptomsField;

    private JCheckBox emergencyCheckBox;

    public BookingFrame(User patient) {

        this.patient = patient;

        doctorService = new DoctorService();
        appointmentService = new AppointmentService();

        setTitle(
                "Hospital Appointment System - Book Appointment"
        );

        setSize(550, 450);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        createUI();

        loadDoctors();
    }

    /**
     * Creates the appointment booking form.
     */
    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "Book an Appointment",
                        JLabel.CENTER
                );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        JPanel formPanel =
                new JPanel(
                        new GridLayout(6, 2, 10, 10)
                );

        JLabel doctorLabel =
                new JLabel("Doctor:");

        doctorComboBox =
                new JComboBox<>();

        JLabel dateLabel =
                new JLabel(
                        "Date (YYYY-MM-DD):"
                );

        dateField =
                new JTextField();

        JLabel timeLabel =
                new JLabel(
                        "Time (HH:MM):"
                );

        timeField =
                new JTextField();

        JLabel symptomsLabel =
                new JLabel("Symptoms:");

        symptomsField =
                new JTextField();

        JLabel emergencyLabel =
                new JLabel("Emergency:");

        emergencyCheckBox =
                new JCheckBox("Flag as Emergency");

        JButton bookButton =
                new JButton("Book Appointment");

        JButton backButton =
                new JButton("Back");

        formPanel.add(doctorLabel);
        formPanel.add(doctorComboBox);

        formPanel.add(dateLabel);
        formPanel.add(dateField);

        formPanel.add(timeLabel);
        formPanel.add(timeField);

        formPanel.add(symptomsLabel);
        formPanel.add(symptomsField);

        formPanel.add(emergencyLabel);
        formPanel.add(emergencyCheckBox);

        formPanel.add(bookButton);
        formPanel.add(backButton);

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        /*
         * Book appointment.
         */
        bookButton.addActionListener(
                e -> bookAppointment()
        );

        /*
         * Return to patient dashboard.
         */
        backButton.addActionListener(
                e -> openDashboard()
        );
    }

    /**
     * Loads all doctors from the database into
     * the doctor selection box.
     */
    private void loadDoctors() {

        try {

            List<Doctor> doctors =
                    doctorService.getAllDoctors();

            doctorComboBox.removeAllItems();

            for (Doctor doctor : doctors) {

                doctorComboBox.addItem(doctor);
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
     */
    private void bookAppointment() {

        Doctor selectedDoctor =
                (Doctor) doctorComboBox.getSelectedItem();

        String dateText =
                dateField.getText().trim();

        String timeText =
                timeField.getText().trim();

        String symptoms =
                symptomsField.getText().trim();

        boolean emergency =
                emergencyCheckBox.isSelected();

        /*
         * Validate doctor selection.
         */
        if (selectedDoctor == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a doctor.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }
        /*
         * Validate other fields.
         */
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

        /*
         * Convert the entered date and time.
         */
        try {

            appointmentDate =
                    LocalDate.parse(dateText);

            appointmentTime =
                    LocalTime.parse(
                            timeText
                                    + (timeText.length() == 5
                                    ? ":00"
                                    : "")
                    );

        } catch (DateTimeParseException ex) {

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
             * Step 1:
             * Check doctor's configured availability.
             */
            if (!appointmentService.isDoctorAvailable(
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
             * Step 2:
             * Check whether the selected time slot
             * has already been booked.
             */
            if (appointmentService.isSlotAlreadyBooked(
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
             * Step 3:
             * Create the appointment.
             */
            var appointment =
                    appointmentService.bookAppointment(
                            patient.getUserId(),
                            selectedDoctor.getId(),
                            appointmentDate,
                            appointmentTime,
                            symptoms,
                            emergency
                    );

            /*
             * Step 4:
             * Display confirmation.
             */
            String emergencyMessage =
                    emergency
                            ? "\nEmergency flag: YES"
                            : "\nEmergency flag: NO";

            JOptionPane.showMessageDialog(
                    this,
                    "Appointment booked successfully!\n\n"
                            + "Appointment ID: "
                            + appointment.getAppointmentId()
                            + "\nDoctor: "
                            + selectedDoctor.getFullName()
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
                new PatientDashboard(patient);

        dashboard.setVisible(true);

        dispose();
    }
}
