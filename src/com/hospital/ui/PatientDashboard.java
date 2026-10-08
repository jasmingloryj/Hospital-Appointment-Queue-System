package com.hospital.ui;

import com.hospital.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PatientDashboard extends JFrame {

    private final User user;

    public PatientDashboard(User user) {
        this.user = user;

        setTitle("Patient Dashboard | Hospital Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1100, 700));

        buildUI();

        setLocationRelativeTo(null);
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.BACKGROUND);

        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createMainContent(), BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(AppTheme.SIDEBAR_WIDTH, 0));
        sidebar.setBackground(AppTheme.NAVY);

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(new EmptyBorder(28, 24, 20, 24));

        JLabel logo = new JLabel("MEDICARE");
        logo.setFont(new Font("SansSerif", Font.BOLD, 22));
        logo.setForeground(AppTheme.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Hospital Management");
        subtitle.setFont(AppTheme.FONT_SMALL);
        subtitle.setForeground(new Color(190, 205, 225));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        top.add(logo);
        top.add(Box.createVerticalStrut(4));
        top.add(subtitle);
        top.add(Box.createVerticalStrut(36));

        JLabel navLabel = new JLabel("PATIENT PORTAL");
        navLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        navLabel.setForeground(new Color(150, 170, 195));
        navLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        top.add(navLabel);
        top.add(Box.createVerticalStrut(12));

        JButton dashboardButton = createNavButton("Dashboard");
        dashboardButton.setBackground(AppTheme.NAVY_LIGHT);
        dashboardButton.setOpaque(true);

        JButton bookingButton = createNavButton("Book Appointment");
        bookingButton.addActionListener(e -> {
            dispose();
            new BookingFrame(user).setVisible(true);
        });

        JButton appointmentsButton = createNavButton("My Appointments");
        appointmentsButton.addActionListener(e -> {
            dispose();
            new AppointmentHistoryFrame(user).setVisible(true);
        });

        top.add(dashboardButton);
        top.add(bookingButton);
        top.add(appointmentsButton);

        sidebar.add(top, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBorder(new EmptyBorder(15, 24, 24, 24));

        JLabel nameLabel = new JLabel(user.getFullName());
        nameLabel.setFont(AppTheme.FONT_BODY_BOLD);
        nameLabel.setForeground(AppTheme.WHITE);

        JLabel roleLabel = new JLabel(user.getRole());
        roleLabel.setFont(AppTheme.FONT_SMALL);
        roleLabel.setForeground(new Color(170, 190, 215));

        JButton logoutButton = createNavButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        bottom.add(nameLabel);
        bottom.add(Box.createVerticalStrut(3));
        bottom.add(roleLabel);
        bottom.add(Box.createVerticalStrut(14));
        bottom.add(logoutButton);

        sidebar.add(bottom, BorderLayout.SOUTH);

        return sidebar;
    }

    private JPanel createMainContent() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(AppTheme.BACKGROUND);
        content.setBorder(new EmptyBorder(32, 36, 32, 36));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Patient Dashboard");
        title.setFont(AppTheme.FONT_H1);
        title.setForeground(AppTheme.TEXT);

        JLabel subtitle = new JLabel(
                "Manage your appointments and hospital visits."
        );
        subtitle.setFont(AppTheme.FONT_BODY);
        subtitle.setForeground(AppTheme.TEXT_SECONDARY);

        header.add(title);
        header.add(Box.createVerticalStrut(7));
        header.add(subtitle);

        content.add(header, BorderLayout.NORTH);

        JPanel body = new JPanel(new GridBagLayout());
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(32, 0, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 20, 0);

        body.add(createWelcomeCard(), gbc);

        gbc.gridy = 1;

        body.add(createQuickActionsCard(), gbc);

        content.add(body, BorderLayout.CENTER);

        return content;
    }

    private JPanel createWelcomeCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(AppTheme.WHITE);
        card.setBorder(AppTheme.cardBorder());

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel(
                "Welcome, " + user.getFullName()
        );
        heading.setFont(AppTheme.FONT_H2);
        heading.setForeground(AppTheme.TEXT);

        JLabel description = new JLabel(
                "<html>Book a new appointment, check your queue position, "
                        + "or review your previous appointments.</html>"
        );
        description.setFont(AppTheme.FONT_BODY);
        description.setForeground(AppTheme.TEXT_SECONDARY);

        text.add(heading);
        text.add(Box.createVerticalStrut(10));
        text.add(description);

        card.add(text, BorderLayout.CENTER);

        JLabel badge = new JLabel("PATIENT");
        badge.setFont(AppTheme.FONT_SMALL_BOLD);
        badge.setForeground(AppTheme.BLUE);
        badge.setBackground(AppTheme.BLUE_LIGHT);
        badge.setOpaque(true);
        badge.setBorder(new EmptyBorder(9, 14, 9, 14));

        card.add(badge, BorderLayout.EAST);

        return card;
    }

    private JPanel createQuickActionsCard() {
        JPanel card = new JPanel();
        card.setBackground(AppTheme.WHITE);
        card.setBorder(AppTheme.cardBorder());
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Quick Actions");
        heading.setFont(AppTheme.FONT_H2);
        heading.setForeground(AppTheme.TEXT);

        card.add(heading);
        card.add(Box.createVerticalStrut(20));

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 12, 0)
        );
        buttons.setOpaque(false);

        JButton bookButton =
                AppTheme.primaryButton("Book Appointment");

        bookButton.addActionListener(e -> {
            dispose();
            new BookingFrame(user).setVisible(true);
        });

        JButton appointmentsButton =
                AppTheme.secondaryButton("My Appointments");

        appointmentsButton.addActionListener(e -> {
            dispose();
            new AppointmentHistoryFrame(user).setVisible(true);
        });

        buttons.add(bookButton);
        buttons.add(appointmentsButton);

        card.add(buttons);

        return card;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);

        button.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 46)
        );
        button.setPreferredSize(new Dimension(200, 46));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(0, 14, 0, 10));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        button.setForeground(new Color(220, 230, 242));
        button.setFont(AppTheme.FONT_BODY_BOLD);
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        return button;
    }
}