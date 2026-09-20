package com.hospital.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.hospital.service.AuthService;

/**
 * Patient registration screen.
 *
 * Module 1:
 * User Authentication & Role Management
 *
 * Public registration creates a PATIENT account.
 */
public class RegisterFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneField;

    private AuthService authService;

    public RegisterFrame() {

        authService = new AuthService();

        setTitle("Hospital Appointment System - Create Account");

        setSize(500, 400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        createUI();
    }

    /**
     * Creates the registration form.
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
                        "Create Patient Account",
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

        JLabel usernameLabel =
                new JLabel("Username:");

        usernameField =
                new JTextField();

        JLabel passwordLabel =
                new JLabel("Password:");

        passwordField =
                new JPasswordField();

        JLabel fullNameLabel =
                new JLabel("Full Name:");

        fullNameField =
                new JTextField();

        JLabel emailLabel =
                new JLabel("Email:");

        emailField =
                new JTextField();

        JLabel phoneLabel =
                new JLabel("Phone:");

        phoneField =
                new JTextField();

        JButton registerButton =
                new JButton("Register");

        JButton backButton =
                new JButton("Back to Login");

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        formPanel.add(fullNameLabel);
        formPanel.add(fullNameField);

        formPanel.add(emailLabel);
        formPanel.add(emailField);

        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        formPanel.add(registerButton);
        formPanel.add(backButton);

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        /*
         * Register button action.
         */
        registerButton.addActionListener(
                e -> registerPatient()
        );

        /*
         * Back button action.
         */
        backButton.addActionListener(
                e -> openLogin()
        );
    }

    /**
     * Registers a new patient account.
     */
    private void registerPatient() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String fullName =
                fullNameField.getText().trim();

        String email =
                emailField.getText().trim();

        String phone =
                phoneField.getText().trim();

        /*
         * Basic validation.
         */
        if (username.isEmpty()
                || password.isEmpty()
                || fullName.isEmpty()
                || email.isEmpty()
                || phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            authService.registerPatient(
                    username,
                    password,
                    fullName,
                    email,
                    phone
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Patient account created successfully!\n"
                            + "You can now log in.",
                    "Registration Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            openLogin();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to create account.\n\n"
                            + ex.getMessage(),
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Returns to the login screen.
     */
    private void openLogin() {

        LoginFrame loginFrame =
                new LoginFrame();

        loginFrame.setVisible(true);

        dispose();
    }
}