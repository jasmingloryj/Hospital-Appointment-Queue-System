package com.hospital.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hospital.model.User;

/**
 * Dashboard displayed after a successful patient login.
 *
 * Module 2:
 * Appointment Booking & Scheduling
 */
public class PatientDashboard extends JFrame {

    private User user;

    public PatientDashboard(User user) {

        this.user = user;

        setTitle(
                "Hospital Appointment System - Patient Dashboard"
        );

        setSize(500, 350);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        createUI();
    }

    /**
     * Creates the patient dashboard.
     */
    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, "
                                + user.getFullName()
                                + "!",
                        JLabel.CENTER
                );

        mainPanel.add(
                welcomeLabel,
                BorderLayout.NORTH
        );

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(3, 1, 10, 10)
                );

        JButton bookButton =
                new JButton("Book Appointment");

        JButton historyButton =
                new JButton("View Appointment History");

        JButton logoutButton =
                new JButton("Logout");

        buttonPanel.add(bookButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        /*
         * Open appointment booking screen.
         */
        bookButton.addActionListener(e -> {

            BookingFrame bookingFrame =
                    new BookingFrame(user);

            bookingFrame.setVisible(true);

            dispose();
        });

        /*
         * Open appointment history screen.
         */
        historyButton.addActionListener(e -> {

            AppointmentHistoryFrame historyFrame =
                    new AppointmentHistoryFrame(user);

            historyFrame.setVisible(true);

            dispose();
        });

        /*
         * Logout and return to login.
         */
        logoutButton.addActionListener(e -> {

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);

            dispose();
        });
    }
}
