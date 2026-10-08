package com.hospital;

import javax.swing.SwingUtilities;

import com.hospital.ui.LoginFrame;


public class HospitalApp {

    public static void main(String[] args) {

        // Start the Swing GUI on the Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {

            LoginFrame loginFrame = new LoginFrame();

            loginFrame.setVisible(true);
        });
    }
}