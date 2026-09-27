package com.hospital.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hospital.model.User;

/**
 * Professional patient dashboard.
 *
 * Module 2:
 * Appointment Booking & Scheduling
 */
public class PatientDashboard extends JFrame {

    private User user;

    public PatientDashboard(User user) {

        this.user = user;

        setTitle(
                "Hospital Appointment System - Patient Dashboard"
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
     * Creates the complete patient dashboard.
     */
    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(
                AppTheme.BACKGROUND
        );

        /*
         * =================================================
         * SIDEBAR
         * =================================================
         */

        JPanel sidebar =
                new JPanel(new BorderLayout());

        sidebar.setBackground(
                AppTheme.PRIMARY_DARK
        );

        sidebar.setPreferredSize(
                new Dimension(250, 0)
        );

        /*
         * Hospital branding.
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
                        30, 10, 5, 10
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
                new JLabel("HOSPITAL+");

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

        JLabel brandSubtitle =
                new JLabel(
                        "Patient Portal"
                );

        brandSubtitle.setFont(
                AppTheme.SMALL_FONT
        );

        brandSubtitle.setForeground(
                new Color(
                        190, 210, 235
                )
        );

        brandPanel.add(
                brandSubtitle,
                brandGbc
        );

        sidebar.add(
                brandPanel,
                BorderLayout.NORTH
        );

        /*
         * Navigation buttons.
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

        navigationPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 15, 20, 15
                )
        );

        navigationPanel.setBackground(
                AppTheme.PRIMARY_DARK
        );

        JButton dashboardButton =
                createSidebarButton(
                        "Dashboard"
                );

        JButton bookButton =
                createSidebarButton(
                        "Book Appointment"
                );

        JButton historyButton =
                createSidebarButton(
                        "My Appointments"
                );

        JButton profileButton =
                createSidebarButton(
                        "My Profile"
                );

        JButton helpButton =
                createSidebarButton(
                        "Help & Support"
                );

        JButton logoutButton =
                createSidebarButton(
                        "Logout"
                );

        navigationPanel.add(
                dashboardButton
        );

        navigationPanel.add(
                bookButton
        );

        navigationPanel.add(
                historyButton
        );

        navigationPanel.add(
                profileButton
        );

        navigationPanel.add(
                helpButton
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
                        25,
                        30,
                        25,
                        30
                )
        );

        /*
         * Top header.
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
                        "Welcome back, "
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
                        "Manage your appointments and "
                                + "healthcare visits."
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

        /*
         * User information on right.
         */

        JPanel userPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        userPanel.setOpaque(
                false
        );

        JLabel userRole =
                new JLabel(
                        "PATIENT"
                );

        userRole.setFont(
                AppTheme.SUBHEADING_FONT
        );

        userRole.setForeground(
                AppTheme.PRIMARY
        );

        userPanel.add(
                userRole
        );

        headerPanel.add(
                userPanel,
                BorderLayout.EAST
        );

        contentPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        /*
         * =================================================
         * DASHBOARD BODY
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

        /*
         * Statistics cards.
         */

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
                        "Upcoming Appointments",
                        "1",
                        AppTheme.PRIMARY
                )
        );

        statsPanel.add(
                createStatCard(
                        "Completed Visits",
                        "0",
                        AppTheme.SUCCESS
                )
        );

        statsPanel.add(
                createStatCard(
                        "Emergency Requests",
                        "0",
                        AppTheme.DANGER
                )
        );

        dashboardBody.add(
                statsPanel,
                BorderLayout.NORTH
        );

        /*
         * Main action cards.
         */

        JPanel actionArea =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                20,
                                20
                        )
                );

        actionArea.setOpaque(
                false
        );

        /*
         * Book appointment card.
         */

        JPanel bookingCard =
                createContentCard();

        bookingCard.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints bookingGbc =
                new GridBagConstraints();

        bookingGbc.gridx = 0;
        bookingGbc.gridy = 0;

        bookingGbc.anchor =
                GridBagConstraints.WEST;

        bookingGbc.insets =
                new Insets(
                        10,
                        20,
                        10,
                        20
                );

        JLabel bookingTitle =
                new JLabel(
                        "Book a New Appointment"
                );

        bookingTitle.setFont(
                AppTheme.HEADING_FONT
        );

        bookingTitle.setForeground(
                AppTheme.TEXT_PRIMARY
        );

        bookingCard.add(
                bookingTitle,
                bookingGbc
        );

        bookingGbc.gridy++;

        JLabel bookingDescription =
                new JLabel(
                        "<html>Find an available doctor and "
                                + "schedule your next visit.</html>"
                );

        bookingDescription.setFont(
                AppTheme.BODY_FONT
        );

        bookingDescription.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        bookingCard.add(
                bookingDescription,
                bookingGbc
        );

        bookingGbc.gridy++;

        JButton bookNowButton =
                new JButton(
                        "Book Appointment"
                );

        AppTheme.styleButton(
                bookNowButton
        );

        bookingCard.add(
                bookNowButton,
                bookingGbc
        );

        /*
         * Appointment history card.
         */

        JPanel historyCard =
                createContentCard();

        historyCard.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints historyGbc =
                new GridBagConstraints();

        historyGbc.gridx = 0;
        historyGbc.gridy = 0;

        historyGbc.anchor =
                GridBagConstraints.WEST;

        historyGbc.insets =
                new Insets(
                        10,
                        20,
                        10,
                        20
                );

        JLabel historyTitle =
                new JLabel(
                        "My Appointments"
                );

        historyTitle.setFont(
                AppTheme.HEADING_FONT
        );

        historyTitle.setForeground(
                AppTheme.TEXT_PRIMARY
        );

        historyCard.add(
                historyTitle,
                historyGbc
        );

        historyGbc.gridy++;

        JLabel historyDescription =
                new JLabel(
                        "<html>View your booked appointments, "
                                + "dates, doctors and status.</html>"
                );

        historyDescription.setFont(
                AppTheme.BODY_FONT
        );

        historyDescription.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        historyCard.add(
                historyDescription,
                historyGbc
        );

        historyGbc.gridy++;

        JButton historyViewButton =
                new JButton(
                        "View Appointments"
                );

        AppTheme.styleButton(
                historyViewButton
        );

        historyCard.add(
                historyViewButton,
                historyGbc
        );

        actionArea.add(
                bookingCard
        );

        actionArea.add(
                historyCard
        );

        dashboardBody.add(
                actionArea,
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

        bookButton.addActionListener(
                e -> openBooking()
        );

        historyButton.addActionListener(
                e -> openHistory()
        );

        bookNowButton.addActionListener(
                e -> openBooking()
        );

        historyViewButton.addActionListener(
                e -> openHistory()
        );

        logoutButton.addActionListener(
                e -> logout()
        );

        /*
         * These are currently visual navigation items.
         * They will become functional as additional
         * modules are implemented.
         */

        dashboardButton.addActionListener(
                e -> {
                    // Already on dashboard.
                }
        );

        profileButton.addActionListener(
                e -> showComingSoon(
                        "Profile management"
                )
        );

        helpButton.addActionListener(
                e -> showComingSoon(
                        "Help & Support"
                )
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
     * Creates a standard content card.
     */
    private JPanel createContentCard() {

        JPanel card =
                new JPanel();

        card.setBackground(
                AppTheme.SURFACE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                AppTheme.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        return card;
    }

    /**
     * Opens appointment booking.
     */
    private void openBooking() {

        BookingFrame bookingFrame =
                new BookingFrame(user);

        bookingFrame.setVisible(true);

        dispose();
    }

    /**
     * Opens appointment history.
     */
    private void openHistory() {

        AppointmentHistoryFrame historyFrame =
                new AppointmentHistoryFrame(user);

        historyFrame.setVisible(true);

        dispose();
    }

    /**
     * Logs the patient out.
     */
    private void logout() {

        LoginFrame loginFrame =
                new LoginFrame();

        loginFrame.setVisible(true);
        dispose();
    }

    /**
     * Displays a temporary message for features
     * that are not implemented yet.
     */
    private void showComingSoon(
            String feature) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                feature
                        + " will be added in a future module.",
                "Coming Soon",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }
}