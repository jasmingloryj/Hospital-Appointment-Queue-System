package com.hospital.ui;

import com.hospital.service.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Professional patient registration screen.
 *
 * Public registration creates PATIENT accounts only.
 */
public class RegisterFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneField;

    private final AuthService authService;

    public RegisterFrame() {

        authService = new AuthService();

        setTitle(
                "Create Patient Account - Hospital System"
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(1050, 700)
        );

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        buildUI();

        setLocationRelativeTo(null);
    }

    // =========================================================
    // BUILD
    // =========================================================

    private void buildUI() {

        JPanel root =
                new JPanel(new GridLayout(1, 2));

        root.setBackground(
                AppTheme.BACKGROUND
        );

        root.add(createBrandSection());
        root.add(createRegistrationSection());

        setContentPane(root);
    }

    // =========================================================
    // BRAND SECTION
    // =========================================================

    private JPanel createBrandSection() {

        JPanel brand =
                AppTheme.createBrandPanel();

        brand.setLayout(
                new GridBagLayout()
        );

        JPanel content =
                new JPanel();

        content.setOpaque(false);

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel logo =
                new JLabel("H");

        logo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        logo.setVerticalAlignment(
                SwingConstants.CENTER
        );

        logo.setForeground(
                AppTheme.WHITE
        );

        logo.setBackground(
                AppTheme.BLUE
        );

        logo.setOpaque(true);

        logo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        34
                )
        );

        logo.setPreferredSize(
                new Dimension(72, 72)
        );

        logo.setMaximumSize(
                new Dimension(72, 72)
        );

        JLabel title =
                new JLabel(
                        "<html>Join the<br>"
                        + "Hospital Care Portal</html>"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        34
                )
        );

        title.setForeground(
                AppTheme.WHITE
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        title.setBorder(
                new EmptyBorder(
                        28,
                        0,
                        0,
                        0
                )
        );

        JLabel subtitle =
                new JLabel(
                        "<html>Create your patient account<br>"
                        + "and manage your healthcare appointments "
                        + "in one place.</html>"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        17
                )
        );

        subtitle.setForeground(
                new Color(203, 213, 225)
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        subtitle.setBorder(
                new EmptyBorder(
                        16,
                        0,
                        0,
                        0
                )
        );

        JLabel informationTitle =
                new JLabel(
                        "Your account gives you access to"
                );

        informationTitle.setFont(
                AppTheme.FONT_BODY_BOLD
        );

        informationTitle.setForeground(
                new Color(191, 219, 254)
        );

        informationTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        informationTitle.setBorder(
                new EmptyBorder(
                        45,
                        0,
                        14,
                        0
                )
        );

        content.add(logo);
        content.add(title);
        content.add(subtitle);
        content.add(informationTitle);

        content.add(
                createFeature(
                        "✓",
                        "Book appointments with available doctors"
                )
        );

        content.add(
                createFeature(
                        "✓",
                        "Track your appointment queue"
                )
        );

        content.add(
                createFeature(
                        "✓",
                        "Request emergency priority when needed"
                )
        );

        content.add(
                createFeature(
                        "✓",
                        "Access completed consultation records"
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        brand.add(content, gbc);

        return brand;
    }

    private JPanel createFeature(
            String symbol,
            String text) {

        JPanel row =
                new JPanel(
                        new BorderLayout(
                                12,
                                0
                        )
                );

        row.setOpaque(false);

        row.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        row.setMaximumSize(
                new Dimension(
                        520,
                        38
                )
        );

        JLabel icon =
                new JLabel(symbol);

        icon.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        icon.setForeground(
                new Color(96, 165, 250)
        );

        JLabel label =
                new JLabel(text);

        label.setFont(
                AppTheme.FONT_BODY
        );

        label.setForeground(
                new Color(226, 232, 240)
        );

        row.add(
                icon,
                BorderLayout.WEST
        );

        row.add(
                label,
                BorderLayout.CENTER
        );

        return row;
    }

    // =========================================================
    // REGISTRATION SECTION
    // =========================================================

    private JPanel createRegistrationSection() {

        JPanel section =
                new JPanel(new GridBagLayout());

        section.setBackground(
                AppTheme.BACKGROUND
        );

        JPanel card =
                new JPanel();

        card.setBackground(
                AppTheme.WHITE
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                AppTheme.BORDER,
                                1
                        ),
                        new EmptyBorder(
                                34,
                                42,
                                34,
                                42
                        )
                )
        );

        card.setPreferredSize(
                new Dimension(
                        500,
                        650
                )
        );

        JLabel heading =
                new JLabel("Create your account");

        heading.setFont(
                AppTheme.FONT_H1
        );

        heading.setForeground(
                AppTheme.TEXT
        );

        heading.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitle =
                new JLabel(
                        "Register as a patient to get started."
                );

        subtitle.setFont(
                AppTheme.FONT_BODY
        );

        subtitle.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(heading);

        card.add(
                Box.createVerticalStrut(6)
        );

        card.add(subtitle);

        card.add(
                Box.createVerticalStrut(24)
        );

        fullNameField =
                new JTextField();

        addField(
                card,
                "Full Name",
                fullNameField
        );

        usernameField =
                new JTextField();

        addField(
                card,
                "Username",
                usernameField
        );

        emailField =
                new JTextField();

        addField(
                card,
                "Email Address",
                emailField
        );

        phoneField =
                new JTextField();

        addField(
                card,
                "Phone Number",
                phoneField
        );

        passwordField =
                new JPasswordField();

        addField(
                card,
                "Password",
                passwordField
        );

        confirmPasswordField =
                new JPasswordField();

        addField(
                card,
                "Confirm Password",
                confirmPasswordField
        );

        card.add(
                Box.createVerticalStrut(8)
        );

        JButton registerButton =
                AppTheme.primaryButton(
                        "Create Patient Account"
                );

        registerButton.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        registerButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        AppTheme.BUTTON_HEIGHT
                )
        );

        registerButton.addActionListener(
                e -> handleRegistration()
        );

        card.add(registerButton);

        card.add(
                Box.createVerticalStrut(10)
        );

        JButton backButton =
                AppTheme.secondaryButton(
                        "Back to Sign In"
                );

        backButton.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        backButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        AppTheme.BUTTON_HEIGHT
                )
        );

        backButton.addActionListener(
                e -> returnToLogin()
        );

        card.add(backButton);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.insets =
                new Insets(
                        25,
                        45,
                        25,
                        45
                );

        section.add(card, gbc);

        return section;
    }

    // =========================================================
    // FIELD HELPER
    // =========================================================

    private void addField(
            JPanel parent,
            String labelText,
            JComponent field) {

        JLabel label =
                new JLabel(labelText);

        label.setFont(
                AppTheme.FONT_SMALL_BOLD
        );

        label.setForeground(
                AppTheme.TEXT
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        field.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        if (field instanceof JTextField) {

            AppTheme.styleTextField(
                    (JTextField) field
            );

        } else if (field instanceof JPasswordField) {

            AppTheme.stylePasswordField(
                    (JPasswordField) field
            );
        }

        parent.add(label);

        parent.add(
                Box.createVerticalStrut(6)
        );

        parent.add(field);

        parent.add(
                Box.createVerticalStrut(12)
        );
    }

    // =========================================================
    // REGISTRATION LOGIC
    // =========================================================

    private void handleRegistration() {

        String fullName =
                fullNameField
                        .getText()
                        .trim();

        String username =
                usernameField
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

        String password =
                new String(
                        passwordField.getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField
                                .getPassword()
                );

        if (fullName.isEmpty()
                || username.isEmpty()
                || email.isEmpty()
                || phone.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {

            showMessage(
                    "Please complete all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!password.equals(confirmPassword)) {

            showMessage(
                    "Passwords do not match.",
                    "Invalid Password",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (password.length() < 6) {

            showMessage(
                    "Password must contain at least 6 characters.",
                    "Invalid Password",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!email.contains("@")
                || !email.contains(".")) {

            showMessage(
                    "Please enter a valid email address.",
                    "Invalid Email",
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

            showMessage(
                    "Patient account created successfully.\n"
                    + "You can now sign in.",
                    "Registration Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            returnToLogin();

        } catch (Exception exception) {

            exception.printStackTrace();

            showMessage(
                    "Unable to create the account.\n\n"
                    + exception.getMessage(),
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // NAVIGATION
    // =========================================================

    private void returnToLogin() {

        dispose();

        new LoginFrame()
                .setVisible(true);
    }

    // =========================================================
    // MESSAGE
    // =========================================================

    private void showMessage(
            String message,
            String title,
            int messageType) {

        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                messageType
        );
    }
}