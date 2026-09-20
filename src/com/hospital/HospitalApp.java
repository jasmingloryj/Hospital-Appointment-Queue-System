package com.hospital;

import javax.swing.SwingUtilities;

import com.hospital.ui.LoginFrame;

/**
 * Main entry point for the Hospital Appointment and Queue Management System.
 *
 * Modules currently being implemented:
 * 1. User Authentication & Role Management
 * 2. Appointment Booking & Scheduling
 *
 * Later modules such as queue management and doctor administration
 * will be added separately.
 */
public class HospitalApp {

    public static void main(String[] args) {

        // Start the Swing GUI on the Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {

            LoginFrame loginFrame = new LoginFrame();

            loginFrame.setVisible(true);
        });
    }
}