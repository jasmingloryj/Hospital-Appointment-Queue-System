package com.hospital.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.hospital.exception.InvalidCredentialsException;
import com.hospital.model.User;
import com.hospital.service.AuthService;

/**
 * Login screen for the Hospital Appointment System.
 *
 * Module 1:
 * User Authentication & Role Management
 */
public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    private AuthService authService;

    public LoginFrame() {

        authService = new AuthService();

        setTitle("Hospital Appointment System - Login");

        setSize(450, 350);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        createUI();
    }

    /**
     * Creates the login form.
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
                        "Hospital Appointment System",
                        JLabel.CENTER
                );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        JPanel formPanel =
                new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel usernameLabel =
                new JLabel("Username:");

        usernameField =
                new JTextField();

        JLabel passwordLabel =
                new JLabel("Password:");

        passwordField =
                new JPasswordField();

        JLabel roleLabel =
                new JLabel("Role:");

        roleComboBox =
                new JComboBox<>(
                        new String[]{
                                "PATIENT",
                                "DOCTOR",
                                "ADMIN"
                        }
                );

        JButton loginButton =
                new JButton("Login");

        JButton registerButton =
                new JButton("Create Patient Account");

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        formPanel.add(roleLabel);
        formPanel.add(roleComboBox);

        formPanel.add(loginButton);
        formPanel.add(registerButton);

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        /*
         * Login button action.
         */
        loginButton.addActionListener(
                e -> login()
        );

        /*
         * Registration button action.
         */
        registerButton.addActionListener(
                e -> openRegistration()
        );
    }

    /**
     * Attempts to authenticate the user.
     */
    private void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String selectedRole =
                (String) roleComboBox.getSelectedItem();

        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            /*
             * Authenticate using the database.
             */
            User user =
                    authService.login(
                            username,
                            password
                    );

            /*
             * Compare the selected role with the
             * actual role stored in the database.
             */
            if (!user.getRole().equals(selectedRole)) {

                JOptionPane.showMessageDialog(
                        this,
                        "The selected role does not match "
                                + "your account role.",
                        "Invalid Role",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!\n"
                            + "Welcome, "
                            + user.getFullName()
                            + "\nRole: "
                            + user.getRole(),
                    "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            openRoleDashboard(user);

        } catch (InvalidCredentialsException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (RuntimeException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to connect to the database.\n\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Opens the patient registration screen.
     */
    private void openRegistration() {

        RegisterFrame registerFrame =
                new RegisterFrame();

        registerFrame.setVisible(true);

        dispose();
    }

    /**
     * Opens the appropriate dashboard according to
     * the authenticated user's role.
     */
    private void openRoleDashboard(User user) {

        String role = user.getRole();

        if ("PATIENT".equals(role)) {

            new PatientDashboard(user).setVisible(true);

        } else {

            new RoleDashboard(user).setVisible(true);
        }

        dispose();
    }
}