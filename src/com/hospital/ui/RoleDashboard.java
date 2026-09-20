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
 * Dashboard for users whose role is not PATIENT.
 *
 * Doctor and Admin functionality will be expanded
 * when the later modules are implemented.
 */
public class RoleDashboard extends JFrame {

    private User user;

    public RoleDashboard(User user) {

        this.user = user;

        setTitle(
                "Hospital Appointment System - "
                        + user.getRole()
                        + " Dashboard"
        );

        setSize(500, 350);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        createUI();
    }

    /**
     * Creates the role dashboard.
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

        JPanel infoPanel =
                new JPanel(
                        new GridLayout(3, 1, 10, 10)
                );

        JLabel roleLabel =
                new JLabel(
                        "Role: "
                                + user.getRole(),
                        JLabel.CENTER
                );

        JLabel messageLabel =
                new JLabel(
                        "Additional "
                                + user.getRole()
                                + " features will be added later.",
                        JLabel.CENTER
                );

        JButton logoutButton =
                new JButton("Logout");

        infoPanel.add(roleLabel);
        infoPanel.add(messageLabel);
        infoPanel.add(logoutButton);

        mainPanel.add(
                infoPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        /*
         * Logout.
         */
        logoutButton.addActionListener(e -> {

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);

            dispose();
        });

        /*
         * Inform the user that later functionality
         * is not part of the current implementation.
         */
        messageLabel.setToolTipText(
                "Doctor and Admin modules will be implemented later."
        );
    }
}