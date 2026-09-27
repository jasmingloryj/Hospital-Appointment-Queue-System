package com.hospital.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.hospital.model.User;

/**
 * Professional dashboard for Doctor and Admin users.
 *
 * Additional Doctor and Admin functionality will be
 * implemented in later modules.
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

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setMinimumSize(
                new Dimension(1100, 700)
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();
    }

    /**
     * Creates the professional role dashboard.
     */
    private void createUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                AppTheme.BACKGROUND
        );

        /*
         * =================================================
         * SIDEBAR
         * =================================================
         */

        JPanel sidebar =
                new JPanel(
                        new BorderLayout()
                );

        sidebar.setBackground(
                AppTheme.PRIMARY_DARK
        );

        sidebar.setPreferredSize(
                new Dimension(250, 0)
        );

        /*
         * Branding
         */

        JPanel brandPanel =
                new JPanel(
                        new GridBagLayout()
                );

        brandPanel.setBackground(
                AppTheme.PRIMARY_DARK
        );

        GridBagConstraints brandGbc =
                new GridBagConstraints();

        brandGbc.gridx = 0;
        brandGbc.gridy = 0;

        brandGbc.insets =
                new Insets(
                        25, 10, 5, 10
                );

        JLabel logo =
                new JLabel("+");

        logo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        42
                )
        );

        logo.setForeground(
                Color.WHITE
        );

        brandPanel.add(
                logo,
                brandGbc
        );

        brandGbc.gridy++;

        JLabel brandName =
                new JLabel(
                        "HOSPITAL+"
                );

        brandName.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        brandName.setForeground(
                Color.WHITE
        );

        brandPanel.add(
                brandName,
                brandGbc
        );

        brandGbc.gridy++;

        JLabel portalLabel =
                new JLabel(
                        user.getRole()
                                + " Portal"
                );

        portalLabel.setFont(
                AppTheme.SMALL_FONT
        );

        portalLabel.setForeground(
                new Color(
                        190, 210, 235
                )
        );

        brandPanel.add(
                portalLabel,
                brandGbc
        );

        sidebar.add(
                brandPanel,
                BorderLayout.NORTH
        );

        /*
         * =================================================
         * NAVIGATION
         * =================================================
         */

        JPanel navigationPanel =
                new JPanel(
                        new GridLayout(
                                6,
                                1,
                                0,
                                8
                        )
                );

        navigationPanel.setBackground(
                AppTheme.PRIMARY_DARK
        );

        navigationPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        15,
                        20,
                        15
                )
        );

        JButton dashboardButton =
                createSidebarButton(
                        "Dashboard"
                );

        JButton appointmentsButton =
                createSidebarButton(
                        "Appointments"
                );

        JButton emergencyButton =
                createSidebarButton(
                        "Emergency Requests"
                );

        JButton managementButton =
                createSidebarButton(
                        "Management"
                );

        JButton profileButton =
                createSidebarButton(
                        "Profile"
                );

        JButton logoutButton =
                createSidebarButton(
                        "Logout"
                );

        navigationPanel.add(
                dashboardButton
        );

        navigationPanel.add(
                appointmentsButton
        );

        navigationPanel.add(
                emergencyButton
        );

        navigationPanel.add(
                managementButton
        );

        navigationPanel.add(
                profileButton
        );

        navigationPanel.add(
                logoutButton
        );

        sidebar.add(
                navigationPanel,
                BorderLayout.CENTER
        );

        /*
         * =================================================
         * MAIN CONTENT
         * =================================================
         */

        JPanel contentPanel =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        contentPanel.setBackground(
                AppTheme.BACKGROUND
        );

        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        35,
                        30,
                        35
                )
        );

        /*
         * Header
         */

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setOpaque(
                false
        );

        JPanel welcomePanel =
                new JPanel(
                        new GridLayout(
                                2,
                                1
                        )
                );

        welcomePanel.setOpaque(
                false
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, "
                                + user.getFullName()
                );

        welcomeLabel.setFont(
                AppTheme.TITLE_FONT
        );

        welcomeLabel.setForeground(
                AppTheme.TEXT_PRIMARY
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Hospital operations and "
                                + "appointment management portal."
                );

        subtitleLabel.setFont(
                AppTheme.BODY_FONT
        );

        subtitleLabel.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        welcomePanel.add(
                welcomeLabel
        );

        welcomePanel.add(
                subtitleLabel
        );

        headerPanel.add(
                welcomePanel,
                BorderLayout.WEST
        );

        JLabel roleLabel =
                new JLabel(
                        user.getRole()
                );

        roleLabel.setFont(
                AppTheme.SUBHEADING_FONT
        );

        roleLabel.setForeground(
                AppTheme.PRIMARY
        );

        headerPanel.add(
                roleLabel,
                BorderLayout.EAST
        );

        contentPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        /*
         * =================================================
         * STATISTICS
         * =================================================
         */

        JPanel dashboardBody =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        dashboardBody.setOpaque(
                false
        );

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                20,
                                20
                        )
                );

        statsPanel.setOpaque(
                false
        );

        statsPanel.add(
                createStatCard(
                        "Today's Appointments",
                        "0",
                        AppTheme.PRIMARY
                )
        );

        statsPanel.add(
                createStatCard(
                        "Pending Requests",
                        "0",
                        AppTheme.WARNING
                )
        );

        statsPanel.add(
                createStatCard(
                        "Emergency Cases",
                        "0",
                        AppTheme.DANGER
                )
        );

        dashboardBody.add(
                statsPanel,
                BorderLayout.NORTH
        );

        /*
         * =================================================
         * INFORMATION CARDS
         * =================================================
         */

        JPanel cardArea =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                20,
                                20
                        )
                );

        cardArea.setOpaque(
                false
        );

        cardArea.add(
                createInformationCard(
                        "Appointments",
                        "View and manage scheduled "
                                + "hospital appointments."
                )
        );

        cardArea.add(
                createInformationCard(
                        "Emergency Requests",
                        "Review emergency appointment "
                                + "requests and their status."
                )
        );

        cardArea.add(
                createInformationCard(
                        "Management",
                        "Doctor and department "
                                + "management features."
                )
        );

        cardArea.add(
                createInformationCard(
                        "Profile",
                        "View your account information "
                                + "and role details."
                )
        );

        dashboardBody.add(
                cardArea,
                BorderLayout.CENTER
        );

        contentPanel.add(
                dashboardBody,
                BorderLayout.CENTER
        );

        mainPanel.add(
                sidebar,
                BorderLayout.WEST
        );

        mainPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        /*
         * =================================================
         * ACTIONS
         * =================================================
         */

        dashboardButton.addActionListener(
                e -> {
                    // Already on dashboard.
                }
        );

        appointmentsButton.addActionListener(
                e -> showComingSoon(
                        "Appointment management"
                )
        );

        emergencyButton.addActionListener(
                e -> showComingSoon(
                        "Emergency request management"
                )
        );

        managementButton.addActionListener(
                e -> showComingSoon(
                        "Doctor and department management"
                )
        );

        profileButton.addActionListener(
                e -> showComingSoon(
                        "Profile management"
                )
        );

        logoutButton.addActionListener(
                e -> logout()
        );
    }

    /**
     * Creates a sidebar navigation button.
     */
    private JButton createSidebarButton(
            String text) {

        JButton button =
                new JButton(text);

        button.setFont(
                AppTheme.BODY_FONT
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                AppTheme.PRIMARY_DARK
        );

        button.setHorizontalAlignment(
                JButton.LEFT
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        15,
                        12,
                        15
                )
        );

        button.setCursor(
                new java.awt.Cursor(
                        java.awt.Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    /**
     * Creates a statistics card.
     */
    private JPanel createStatCard(
            String title,
            String value,
            Color accent) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                10,
                                5
                        )
                );

        card.setBackground(
                AppTheme.SURFACE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0,
                                5,
                                0,
                                0,
                                accent
                        ),
                        BorderFactory.createEmptyBorder(
                                18,
                                20,
                                18,
                                20
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                AppTheme.SMALL_FONT
        );

        titleLabel.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        valueLabel.setForeground(
                AppTheme.TEXT_PRIMARY
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return card;
    }

    /**
     * Creates an information card.
     */
    private JPanel createInformationCard(
            String title,
            String description) {

        JPanel card =
                new JPanel(
                        new GridBagLayout()
                );

        card.setBackground(
                AppTheme.SURFACE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                AppTheme.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        gbc.insets =
                new Insets(
                        5,
                        5,
                        8,
                        5
                );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                AppTheme.HEADING_FONT
        );

        titleLabel.setForeground(
                AppTheme.TEXT_PRIMARY
        );

        card.add(
                titleLabel,
                gbc
        );

        gbc.gridy++;

        JLabel descriptionLabel =
                new JLabel(
                        "<html>"
                                + description
                                + "</html>"
                );

        descriptionLabel.setFont(
                AppTheme.BODY_FONT
        );

        descriptionLabel.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        card.add(
                descriptionLabel,
                gbc
        );

        return card;
    }

    /**
     * Displays a message for features that belong
     * to later modules.
     */
    private void showComingSoon(
            String feature) {

        JOptionPane.showMessageDialog(
                this,
                feature
                        + " will be added in a future module.",
                "Coming Soon",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Logs the user out.
     */
    private void logout() {

        LoginFrame loginFrame =
                new LoginFrame();

        loginFrame.setVisible(true);

        dispose();
    }
}