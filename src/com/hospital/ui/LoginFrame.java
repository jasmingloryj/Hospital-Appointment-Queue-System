package com.hospital.ui;

import com.hospital.exception.InvalidCredentialsException;
import com.hospital.model.User;
import com.hospital.service.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

/**
 * Login screen for the Hospital Appointment & Queue Management System.
 *
 * Layout:
 * 30% - hospital branding
 * 70% - authentication
 */
public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    private final AuthService authService;

    public LoginFrame() {

        authService = new AuthService();

        setTitle("Hospital Appointment & Queue Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setMinimumSize(
                new Dimension(1100, 700)
        );

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        buildUI();

        setLocationRelativeTo(null);
    }

    // =========================================================
    // MAIN LAYOUT
    // =========================================================

    private void buildUI() {

        JPanel root =
                new JPanel(
                        new GridBagLayout()
                );

        root.setBackground(
                AppTheme.BACKGROUND
        );

        // -----------------------------------------------------
        // LEFT - 30%
        // -----------------------------------------------------

        GridBagConstraints left =
                new GridBagConstraints();

        left.gridx = 0;
        left.gridy = 0;
        left.weightx = 0.30;
        left.weighty = 1.0;
        left.fill = GridBagConstraints.BOTH;

        // -----------------------------------------------------
        // RIGHT - 70%
        // -----------------------------------------------------

        GridBagConstraints right =
                new GridBagConstraints();

        right.gridx = 1;
        right.gridy = 0;
        right.weightx = 0.70;
        right.weighty = 1.0;
        right.fill = GridBagConstraints.BOTH;

        root.add(
                createBrandSection(),
                left
        );

        root.add(
                createLoginSection(),
                right
        );

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

        // -----------------------------------------------------
        // Fixed-width content column
        // -----------------------------------------------------

        JPanel content =
                new JPanel();

        content.setOpaque(false);

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        content.setPreferredSize(
                new Dimension(
                        290,
                        440
                )
        );

        // -----------------------------------------------------
        // Logo
        // -----------------------------------------------------

        JLabel logo =
                new JLabel(
                        new ImageIcon(
                                createMedicalLogo(76)
                        )
                );

        logo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // -----------------------------------------------------
        // Hospital title
        // -----------------------------------------------------

        JLabel title =
                new JLabel(
                        "<html>"
                                + "Hospital Appointment<br>"
                                + "&amp; Queue Management"
                                + "</html>"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30      
                )
        );

        title.setForeground(
                AppTheme.WHITE
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // -----------------------------------------------------
        // Tagline
        // -----------------------------------------------------

        JLabel subtitle =
                new JLabel(
                        "<html>"
                                + "Smarter care coordination.<br>"
                                + "Better patient flow."
                                + "</html>"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        19
                )
        );

        subtitle.setForeground(
                new Color(
                        203,
                        213,
                        225
                )
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // -----------------------------------------------------
        // Divider
        // -----------------------------------------------------

        JPanel divider =
                new JPanel();

        divider.setOpaque(true);

        divider.setBackground(
                new Color(
                        59,
                        130,
                        246
                )
        );

        divider.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        divider.setMaximumSize(
                new Dimension(
                        48,
                        2
                )
        );

        // -----------------------------------------------------
        // Supporting text
        // -----------------------------------------------------

        JLabel supportingText =
                new JLabel(
                        "<html>"
                                + "Secure access for patients,<br>"
                                + "doctors and administrators."
                                + "</html>"
                );

        supportingText.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        supportingText.setForeground(
                new Color(
                        148,
                        163,
                        184
                )
        );

        supportingText.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // -----------------------------------------------------
        // Assemble with controlled spacing
        // -----------------------------------------------------

        content.add(
                logo
        );

        content.add(
                Box.createVerticalStrut(30)
        );

        content.add(
                title
        );

        content.add(
                Box.createVerticalStrut(16)
        );

        content.add(
                subtitle
        );

        content.add(
                Box.createVerticalStrut(26)
        );

        content.add(
                divider
        );

        content.add(
                Box.createVerticalStrut(20)
        );

        content.add(
                supportingText
        );

        // -----------------------------------------------------
        // Center the content column in the 30% panel
        // -----------------------------------------------------

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.fill =
                GridBagConstraints.NONE;

        brand.add(
                content,
                gbc
        );

        return brand;
    }

    // =========================================================
    // MEDICAL LOGO
    // =========================================================

    private BufferedImage createMedicalLogo(
            int size
    ) {

        BufferedImage image =
                new BufferedImage(
                        size,
                        size,
                        BufferedImage.TYPE_INT_ARGB
                );

        Graphics2D g =
                image.createGraphics();

        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
        );

        double center =
                size / 2.0;

        // -----------------------------------------------------
        // Outer white circle
        // -----------------------------------------------------

        g.setColor(
                new Color(
                        255,
                        255,
                        255,
                        245
                )
        );

        g.fill(
                new Ellipse2D.Double(
                        2,
                        2,
                        size - 4,
                        size - 4
                )
        );

        // -----------------------------------------------------
        // Inner navy/blue circle
        // -----------------------------------------------------

        g.setColor(
                new Color(
                        30,
                        91,
                        180
                )
        );

        g.fill(
                new Ellipse2D.Double(
                        8,
                        8,
                        size - 16,
                        size - 16
                )
        );

        // -----------------------------------------------------
        // Staff
        // -----------------------------------------------------

        g.setColor(
                Color.WHITE
        );

        g.setStroke(
                new BasicStroke(
                        3.6f,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND
                )
        );

        g.draw(
                new Line2D.Double(
                        center,
                        18,
                        center,
                        size - 16
                )
        );

        // -----------------------------------------------------
        // Snake
        // -----------------------------------------------------

        Path2D snake =
                new Path2D.Double();

        snake.moveTo(
                center + 1,
                22
        );

        snake.curveTo(
                center - 12,
                24,
                center - 14,
                32,
                center - 3,
                38
        );

        snake.curveTo(
                center + 10,
                45,
                center + 11,
                53,
                center + 1,
                59
        );

        snake.curveTo(
                center - 10,
                66,
                center - 11,
                74,
                center - 2,
                80
        );

        snake.curveTo(
                center + 4,
                84,
                center + 5,
                87,
                center + 3,
                89
        );

        g.setStroke(
                new BasicStroke(
                        3.2f,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND
                )
        );

        g.draw(snake);

        // -----------------------------------------------------
        // Snake head
        // -----------------------------------------------------

        g.fill(
                new Ellipse2D.Double(
                        center - 2,
                        19,
                        6,
                        6
                )
        );

        // -----------------------------------------------------
        // Small cross
        // -----------------------------------------------------

        g.setStroke(
                new BasicStroke(
                        1.8f,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND
                )
        );

        g.draw(
                new Line2D.Double(
                        center - 6,
                        16,
                        center + 6,
                        16
                )
        );

        g.dispose();

        return image;
    }

    // =========================================================
    // LOGIN SECTION
    // =========================================================

    private JPanel createLoginSection() {

        JPanel section =
                new JPanel(
                        new GridBagLayout()
                );

        section.setBackground(
                AppTheme.BACKGROUND
        );

        // -----------------------------------------------------
        // Login card
        // -----------------------------------------------------

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
                                44,
                                50,
                                44,
                                50
                        )
                )
        );

        card.setPreferredSize(
                new Dimension(
                        500,
                        560
                )
        );

        card.setMaximumSize(
                new Dimension(
                        540,
                        600
                )
        );

        // -----------------------------------------------------
        // Heading
        // -----------------------------------------------------

        JLabel eyebrow =
                new JLabel(
                        "SECURE ACCESS"
                );

        eyebrow.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        eyebrow.setForeground(
                AppTheme.BLUE
        );

        eyebrow.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel heading =
                new JLabel(
                        "Welcome back"
                );

        heading.setFont(
                AppTheme.FONT_DISPLAY
        );

        heading.setForeground(
                AppTheme.TEXT
        );

        heading.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel description =
                new JLabel(
                        "Sign in to continue to your dashboard."
                );

        description.setFont(
                AppTheme.FONT_BODY
        );

        description.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        description.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(
                eyebrow
        );

        card.add(
                Box.createVerticalStrut(8)
        );

        card.add(
                heading
        );

        card.add(
                Box.createVerticalStrut(7)
        );

        card.add(
                description
        );

        card.add(
                Box.createVerticalStrut(34)
        );

        // -----------------------------------------------------
        // Username
        // -----------------------------------------------------

        card.add(
                createFieldLabel(
                        "Username"
                )
        );

        usernameField =
                new JTextField();

        AppTheme.styleTextField(
                usernameField
        );

        usernameField.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(
                usernameField
        );

        card.add(
                Box.createVerticalStrut(20)
        );

        // -----------------------------------------------------
        // Password
        // -----------------------------------------------------

        card.add(
                createFieldLabel(
                        "Password"
                )
        );

        passwordField =
                new JPasswordField();

        AppTheme.stylePasswordField(
                passwordField
        );

        passwordField.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(
                passwordField
        );

        card.add(
                Box.createVerticalStrut(20)
        );

        // -----------------------------------------------------
        // Role
        // -----------------------------------------------------

        card.add(
                createFieldLabel(
                        "Sign in as"
                )
        );

        roleComboBox =
                new JComboBox<>(
                        new String[]{
                                "PATIENT",
                                "DOCTOR",
                                "ADMIN"
                        }
                );

        AppTheme.styleComboBox(
                roleComboBox
        );

        roleComboBox.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(
                roleComboBox
        );

        card.add(
                Box.createVerticalStrut(30)
        );

        // -----------------------------------------------------
        // Sign in
        // -----------------------------------------------------

        JButton loginButton =
                AppTheme.primaryButton(
                        "Sign In"
                );

        loginButton.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        loginButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        AppTheme.BUTTON_HEIGHT
                )
        );

        loginButton.addActionListener(
                e -> handleLogin()
        );

        card.add(
                loginButton
        );

        card.add(
                Box.createVerticalStrut(12)
        );

        // -----------------------------------------------------
        // Register
        // -----------------------------------------------------

        JButton registerButton =
                AppTheme.secondaryButton(
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
                e -> openRegistration()
        );

        card.add(
                registerButton
        );

        card.add(
                Box.createVerticalGlue()
        );

        // -----------------------------------------------------
        // Footer
        // -----------------------------------------------------

        JLabel footer =
                new JLabel(
                        "Secure hospital access"
                );

        footer.setFont(
                AppTheme.FONT_SMALL
        );

        footer.setForeground(
                AppTheme.TEXT_MUTED
        );

        footer.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(
                footer
        );

        // -----------------------------------------------------
        // Center card
        // -----------------------------------------------------

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.fill =
                GridBagConstraints.NONE;

        gbc.anchor =
                GridBagConstraints.CENTER;

        gbc.insets =
                new Insets(
                        30,
                        60,
                        30,
                        60
                );

        section.add(
                card,
                gbc
        );

        return section;
    }

    // =========================================================
    // FIELD LABEL
    // =========================================================

    private JLabel createFieldLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                AppTheme.FONT_BODY_BOLD
        );

        label.setForeground(
                AppTheme.TEXT
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        label.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        7,
                        0
                )
        );

        return label;
    }

    // =========================================================
    // LOGIN LOGIC
    // =========================================================

    private void handleLogin() {

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
                String.valueOf(
                        roleComboBox
                                .getSelectedItem()
                );

        if (username.isEmpty()) {

            showMessage(
                    "Please enter your username.",
                    "Login Required",
                    JOptionPane.WARNING_MESSAGE
            );

            usernameField.requestFocus();

            return;
        }

        if (password.isEmpty()) {

            showMessage(
                    "Please enter your password.",
                    "Login Required",
                    JOptionPane.WARNING_MESSAGE
            );

            passwordField.requestFocus();

            return;
        }

        try {

            User user =
                    authService.login(
                            username,
                            password
                    );

            if (user == null) {

                showMessage(
                        "Invalid username or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            if (!user.getRole()
                    .equalsIgnoreCase(
                            selectedRole
                    )) {

                showMessage(
                        "The selected role does not match "
                                + "this account.",
                        "Role Mismatch",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            openDashboard(user);

        } catch (
                InvalidCredentialsException e
        ) {

            showMessage(
                    e.getMessage(),
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            e.printStackTrace();

            showMessage(
                    "Unable to connect to the hospital system.\n\n"
                            + e.getMessage(),
                    "System Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // DASHBOARD ROUTING
    // =========================================================

    private void openDashboard(
            User user
    ) {

        dispose();

        String role =
                user.getRole()
                        .toUpperCase();

        switch (role) {

            case "PATIENT":

                new PatientDashboard(
                        user
                ).setVisible(true);

                break;

            case "DOCTOR":
            case "ADMIN":

                new RoleDashboard(
                        user
                ).setVisible(true);

                break;

            default:

                JOptionPane.showMessageDialog(
                        null,
                        "Unknown user role: "
                                + user.getRole(),
                        "System Error",
                        JOptionPane.ERROR_MESSAGE
                );

                new LoginFrame()
                        .setVisible(true);

                break;
        }
    }

    // =========================================================
    // REGISTRATION
    // =========================================================

    private void openRegistration() {

        dispose();

        new RegisterFrame()
                .setVisible(true);
    }

    // =========================================================
    // MESSAGE
    // =========================================================

    private void showMessage(
            String message,
            String title,
            int messageType
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                messageType
        );
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    try {

                        UIManager.setLookAndFeel(
                                UIManager
                                        .getSystemLookAndFeelClassName()
                        );

                    } catch (Exception ignored) {
                    }

                    new LoginFrame()
                            .setVisible(true);
                }
        );
    }
}