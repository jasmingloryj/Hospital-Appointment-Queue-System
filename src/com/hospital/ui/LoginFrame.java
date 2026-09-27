package com.hospital.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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
 * Professional login screen for the Hospital Appointment System.
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

        setTitle("Hospital Appointment System");

        /*
         * Open the application in full-screen/maximized mode.
         */
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setMinimumSize(new Dimension(1000, 650));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        createUI();
    }

    /**
     * Creates the complete login interface.
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
                new Insets(10, 40, 10, 40);
        brandGbc.anchor =
                GridBagConstraints.CENTER;

        JLabel hospitalIcon =
                new JLabel("+");

        hospitalIcon.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
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
                new JLabel(
                        "HOSPITAL+"
                );

        hospitalName.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
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
                                + "Appointment & Queue<br>"
                                + "Management System"
                                + "</div></html>"
                );

        subtitle.setFont(
                AppTheme.BODY_FONT
        );

        subtitle.setForeground(
                new Color(225, 235, 250)
        );

        brandingPanel.add(
                subtitle,
                brandGbc
        );

        brandGbc.gridy++;

        JLabel description =
                new JLabel(
                        "<html><div style='text-align:center;'>"
                                + "A simple and organized way to<br>"
                                + "manage hospital appointments."
                                + "</div></html>"
                );

        description.setFont(
                AppTheme.SMALL_FONT
        );

        description.setForeground(
                new Color(190, 210, 235)
        );

        brandingPanel.add(
                description,
                brandGbc
        );

        /*
         * -------------------------------------------------
         * RIGHT LOGIN AREA
         * -------------------------------------------------
         */
        JPanel rightPanel =
                new JPanel(
                        new GridBagLayout()
                );

        rightPanel.setBackground(
                AppTheme.BACKGROUND
        );

        JPanel loginCard =
                new JPanel(
                        new GridBagLayout()
                );

        loginCard.setBackground(
                AppTheme.SURFACE
        );

        loginCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                AppTheme.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                35, 45, 35, 45
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
        gbc.insets =
                new Insets(5, 5, 5, 5);

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome back"
                );

        welcomeLabel.setFont(
                AppTheme.TITLE_FONT
        );

        welcomeLabel.setForeground(
                AppTheme.TEXT_PRIMARY
        );

        loginCard.add(
                welcomeLabel,
                gbc
        );

        gbc.gridy++;

        JLabel instructionLabel =
                new JLabel(
                        "Sign in to continue to your dashboard"
                );

        instructionLabel.setFont(
                AppTheme.BODY_FONT
        );

        instructionLabel.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        gbc.insets =
                new Insets(
                        0, 5, 25, 5
                );

        loginCard.add(
                instructionLabel,
                gbc
        );

        /*
         * Username label
         */
        gbc.gridy++;
        gbc.gridwidth = 1;

        gbc.insets =
                new Insets(
                        8, 5, 5, 5
                );

        JLabel usernameLabel =
                new JLabel("Username");

        usernameLabel.setFont(
                AppTheme.SUBHEADING_FONT
        );

        usernameLabel.setForeground(
                AppTheme.TEXT_PRIMARY
        );

        loginCard.add(
                usernameLabel,
                gbc
        );

        /*
         * Username field
         */
        gbc.gridy++;

        gbc.gridwidth = 2;

        usernameField =
                new JTextField();

        AppTheme.styleTextField(
                usernameField
        );

        loginCard.add(
                usernameField,
                gbc
        );

        /*
         * Password label
         */
        gbc.gridy++;

        gbc.gridwidth = 1;

        JLabel passwordLabel =
                new JLabel("Password");

        passwordLabel.setFont(
                AppTheme.SUBHEADING_FONT
        );

        passwordLabel.setForeground(
                AppTheme.TEXT_PRIMARY
        );

        loginCard.add(
                passwordLabel,
                gbc
        );

        /*
         * Password field
         */
        gbc.gridy++;

        gbc.gridwidth = 2;

        passwordField =
                new JPasswordField();

        AppTheme.stylePasswordField(
                passwordField
        );

        loginCard.add(
                passwordField,
                gbc
        );

        /*
         * Role label
         */
        gbc.gridy++;

        gbc.gridwidth = 1;

        JLabel roleLabel =
                new JLabel("Role");

        roleLabel.setFont(
                AppTheme.SUBHEADING_FONT
        );

        roleLabel.setForeground(
                AppTheme.TEXT_PRIMARY
        );

        loginCard.add(
                roleLabel,
                gbc
        );

        /*
         * Role selector
         */
        gbc.gridy++;

        gbc.gridwidth = 2;

        roleComboBox =
                new JComboBox<>(
                        new String[]{
                                "PATIENT",
                                "DOCTOR",
                                "ADMIN"
                        }
                );

        roleComboBox.setFont(
                AppTheme.BODY_FONT
        );

        roleComboBox.setBackground(
                Color.WHITE
        );

        roleComboBox.setPreferredSize(
                new Dimension(
                        0,
                        42
                )
        );

        loginCard.add(
                roleComboBox,
                gbc
        );

        /*
         * Login button
         */
        gbc.gridy++;

        gbc.insets =
                new Insets(
                        25, 5, 8, 5
                );

        JButton loginButton =
                new JButton("Sign In");

        AppTheme.styleButton(
                loginButton
        );

        loginCard.add(
                loginButton,
                gbc
        );

        /*
         * Registration area
         */
        gbc.gridy++;

        JPanel registerPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                5,
                                0
                        )
                );

        registerPanel.setOpaque(
                false
        );

        JLabel registerText =
                new JLabel(
                        "New patient?"
                );

        registerText.setFont(
                AppTheme.SMALL_FONT
        );

        registerText.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        JButton registerButton =
                new JButton(
                        "Create an account"
                );

        registerButton.setFont(
                AppTheme.SMALL_FONT
        );

        registerButton.setForeground(
                AppTheme.PRIMARY
        );

        registerButton.setBackground(
                Color.WHITE
        );

        registerButton.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 5, 5, 5
                )
        );

        registerButton.setFocusPainted(
                false
        );

        registerButton.setCursor(
                new java.awt.Cursor(
                        java.awt.Cursor.HAND_CURSOR
                )
        );

        registerPanel.add(
                registerText
        );

        registerPanel.add(
                registerButton
        );

        loginCard.add(
                registerPanel,
                gbc
        );

        rightPanel.add(
                loginCard
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

        loginButton.addActionListener(
                e -> login()
        );

        registerButton.addActionListener(
                e -> openRegistration()
        );

        /*
         * Allow pressing Enter to log in.
         */
        getRootPane().setDefaultButton(
                loginButton
        );
    }

    /**
     * Attempts to authenticate the user.
     *
     * The authentication logic itself is unchanged.
     */
    private void login() {

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );

        String selectedRole =
                (String)
                        roleComboBox
                                .getSelectedItem();

        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your username "
                            + "and password.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            User user =
                    authService.login(
                            username,
                            password
                    );

            if (!user.getRole()
                    .equals(selectedRole)) {

                JOptionPane.showMessageDialog(
                        this,
                        "The selected role does not "
                                + "match your account role.",
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

        } catch (
                InvalidCredentialsException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (
                RuntimeException ex) {

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
     * Opens the appropriate dashboard.
     */
    private void openRoleDashboard(
            User user) {

        String role =
                user.getRole();

        if ("PATIENT".equals(role)) {

            new PatientDashboard(user)
                    .setVisible(true);

        } else {

            new RoleDashboard(user)
                    .setVisible(true);
        }

        dispose();
    }
}