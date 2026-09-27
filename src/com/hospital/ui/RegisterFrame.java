package com.hospital.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
 * Professional patient registration screen.
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

        setTitle(
                "Hospital Appointment System - Create Account"
        );

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setMinimumSize(
                new Dimension(1000, 650)
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();
    }

    /**
     * Creates the professional registration interface.
     */
    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(
                AppTheme.BACKGROUND
        );

        /*
         * -------------------------------------------------
         * LEFT BRANDING PANEL
         * -------------------------------------------------
         */

        JPanel brandingPanel =
                new JPanel(new GridBagLayout());

        brandingPanel.setBackground(
                AppTheme.PRIMARY_DARK
        );

        brandingPanel.setPreferredSize(
                new Dimension(430, 0)
        );

        GridBagConstraints brandGbc =
                new GridBagConstraints();

        brandGbc.gridx = 0;
        brandGbc.gridy = 0;

        brandGbc.insets =
                new Insets(
                        10, 40, 10, 40
                );

        brandGbc.anchor =
                GridBagConstraints.CENTER;

        JLabel hospitalIcon =
                new JLabel("+");

        hospitalIcon.setFont(
                new java.awt.Font(
                        "Segoe UI",
                        java.awt.Font.BOLD,
                        70
                )
        );

        hospitalIcon.setForeground(
                Color.WHITE
        );

        brandingPanel.add(
                hospitalIcon,
                brandGbc
        );

        brandGbc.gridy++;

        JLabel hospitalName =
                new JLabel("HOSPITAL+");

        hospitalName.setFont(
                new java.awt.Font(
                        "Segoe UI",
                        java.awt.Font.BOLD,
                        34
                )
        );

        hospitalName.setForeground(
                Color.WHITE
        );

        brandingPanel.add(
                hospitalName,
                brandGbc
        );

        brandGbc.gridy++;

        JLabel subtitle =
                new JLabel(
                        "<html><div style='text-align:center;'>"
                                + "Create your patient account<br>"
                                + "and manage your appointments"
                                + "</div></html>"
                );

        subtitle.setFont(
                AppTheme.BODY_FONT
        );

        subtitle.setForeground(
                new Color(
                        225, 235, 250
                )
        );

        brandingPanel.add(
                subtitle,
                brandGbc
        );

        /*
         * -------------------------------------------------
         * RIGHT REGISTRATION AREA
         * -------------------------------------------------
         */

        JPanel rightPanel =
                new JPanel(
                        new GridBagLayout()
                );

        rightPanel.setBackground(
                AppTheme.BACKGROUND
        );

        JPanel registrationCard =
                new JPanel(
                        new GridBagLayout()
                );

        registrationCard.setBackground(
                AppTheme.SURFACE
        );

        registrationCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                AppTheme.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                30, 45, 30, 45
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridwidth = 2;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        gbc.insets =
                new Insets(
                        5, 5, 5, 5
                );

        /*
         * Title
         */

        JLabel titleLabel =
                new JLabel(
                        "Create Patient Account"
                );

        titleLabel.setFont(
                AppTheme.TITLE_FONT
        );

        titleLabel.setForeground(
                AppTheme.TEXT_PRIMARY
        );

        registrationCard.add(
                titleLabel,
                gbc
        );

        /*
         * Subtitle
         */

        gbc.gridy++;

        JLabel instructionLabel =
                new JLabel(
                        "Enter your details to create an account"
                );

        instructionLabel.setFont(
                AppTheme.BODY_FONT
        );

        instructionLabel.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        gbc.insets =
                new Insets(
                        0, 5, 20, 5
                );

        registrationCard.add(
                instructionLabel,
                gbc
        );

        /*
         * Full Name
         */

        gbc.gridy++;

        addLabel(
                registrationCard,
                "Full Name",
                gbc
        );

        gbc.gridy++;

        fullNameField =
                new JTextField();

        AppTheme.styleTextField(
                fullNameField
        );

        registrationCard.add(
                fullNameField,
                gbc
        );

        /*
         * Username
         */

        gbc.gridy++;

        addLabel(
                registrationCard,
                "Username",
                gbc
        );

        gbc.gridy++;

        usernameField =
                new JTextField();

        AppTheme.styleTextField(
                usernameField
        );

        registrationCard.add(
                usernameField,
                gbc
        );

        /*
         * Email
         */

        gbc.gridy++;

        addLabel(
                registrationCard,
                "Email",
                gbc
        );

        gbc.gridy++;

        emailField =
                new JTextField();

        AppTheme.styleTextField(
                emailField
        );

        registrationCard.add(
                emailField,
                gbc
        );

        /*
         * Phone
         */

        gbc.gridy++;

        addLabel(
                registrationCard,
                "Phone",
                gbc
        );

        gbc.gridy++;

        phoneField =
                new JTextField();

        AppTheme.styleTextField(
                phoneField
        );

        registrationCard.add(
                phoneField,
                gbc
        );

        /*
         * Password
         */

        gbc.gridy++;

        addLabel(
                registrationCard,
                "Password",
                gbc
        );

        gbc.gridy++;

        passwordField =
                new JPasswordField();

        AppTheme.stylePasswordField(
                passwordField
        );

        registrationCard.add(
                passwordField,
                gbc
        );

        /*
         * Register button
         */

        gbc.gridy++;

        gbc.insets =
                new Insets(
                        20, 5, 8, 5
                );

        JButton registerButton =
                new JButton(
                        "Create Account"
                );

        AppTheme.styleButton(
                registerButton
        );

        registrationCard.add(
                registerButton,
                gbc
        );

        /*
         * Back button
         */

        gbc.gridy++;

        JButton backButton =
                new JButton(
                        "Back to Login"
                );

        backButton.setFont(
                AppTheme.BODY_FONT
        );

        backButton.setForeground(
                AppTheme.PRIMARY
        );

        backButton.setBackground(
                Color.WHITE
        );

        backButton.setFocusPainted(
                false
        );

        backButton.setBorder(
                BorderFactory.createEmptyBorder(
                        8, 8, 8, 8
                )
        );

        backButton.setCursor(
                new java.awt.Cursor(
                        java.awt.Cursor.HAND_CURSOR
                )
        );

        registrationCard.add(
                backButton,
                gbc
        );

        rightPanel.add(
                registrationCard
        );

        mainPanel.add(
                brandingPanel,
                BorderLayout.WEST
        );

        mainPanel.add(
                rightPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        /*
         * -------------------------------------------------
         * BUTTON ACTIONS
         * -------------------------------------------------
         */

        registerButton.addActionListener(
                e -> registerPatient()
        );

        backButton.addActionListener(
                e -> openLogin()
        );
    }

    /**
     * Adds a standard field label.
     */
    private void addLabel(
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
     * Registers a new patient account.
     *
     * Database logic remains unchanged.
     */
    private void registerPatient() {

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );

        String fullName =
                fullNameField
                        .getText()
                        .trim();

        String email =
                emailField
                        .getText()
                        .trim();

        String phone =
                phoneField
                        .getText()
                        .trim();

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