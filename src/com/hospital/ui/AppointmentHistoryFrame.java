package com.hospital.ui;

import com.hospital.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Patient appointment history and queue status screen.
 */
public class AppointmentHistoryFrame extends JFrame {

    private final User user;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/hospital_queue_v2"
            + "?useSSL=false&serverTimezone=Asia/Kolkata"
            + "&allowPublicKeyRetrieval=true";

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Jasmin@2008";

    private JTable appointmentTable;
    private DefaultTableModel tableModel;

    private JLabel totalLabel;
    private JLabel upcomingLabel;
    private JLabel completedLabel;

    public AppointmentHistoryFrame(User user) {

        this.user = user;

        setTitle(
                "My Appointments - Hospital Care Portal"
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(1200, 720)
        );

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        buildUI();

        loadAppointments();

        setLocationRelativeTo(null);
    }

    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(
                AppTheme.BACKGROUND
        );

        root.add(
                createSidebar(),
                BorderLayout.WEST
        );

        root.add(
                createMainContent(),
                BorderLayout.CENTER
        );

        setContentPane(root);
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel();

        sidebar.setBackground(
                AppTheme.NAVY
        );

        sidebar.setPreferredSize(
                new Dimension(260, 0)
        );

        sidebar.setBorder(
                new EmptyBorder(
                        28,
                        22,
                        24,
                        22
                )
        );

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );

        JPanel brand =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        brand.setOpaque(false);

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
                        22
                )
        );

        logo.setPreferredSize(
                new Dimension(46, 46)
        );

        JLabel brandText =
                new JLabel(
                        "<html><b>Hospital</b><br>"
                        + "<span style='font-size:9px'>CARE PORTAL</span></html>"
                );

        brandText.setForeground(
                AppTheme.WHITE
        );

        brandText.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        17
                )
        );

        brandText.setBorder(
                new EmptyBorder(
                        0,
                        13,
                        0,
                        0
                )
        );

        brand.add(logo);
        brand.add(brandText);

        sidebar.add(brand);

        sidebar.add(
                Box.createVerticalStrut(45)
        );

        JLabel menuTitle =
                new JLabel("PATIENT MENU");

        menuTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        menuTitle.setForeground(
                new Color(148, 163, 184)
        );

        menuTitle.setBorder(
                new EmptyBorder(
                        0,
                        10,
                        12,
                        0
                )
        );

        menuTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        sidebar.add(menuTitle);

        JButton dashboard =
                createNavButton(
                        "▣   Dashboard",
                        false
                );

        dashboard.addActionListener(
                e -> openDashboard()
        );

        sidebar.add(dashboard);

        JButton appointments =
                createNavButton(
                        "▤   My Appointments",
                        true
                );

        sidebar.add(appointments);

        JButton booking =
                createNavButton(
                        "＋   Book Appointment",
                        false
                );

        booking.addActionListener(
                e -> openBooking()
        );

        sidebar.add(booking);

        JButton records =
                createNavButton(
                        "▥   Medical Records",
                        false
                );

        records.addActionListener(
                e -> JOptionPane.showMessageDialog(
                        this,
                        "Medical records will be connected "
                        + "to the completed consultation workflow.",
                        "Medical Records",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        sidebar.add(records);

        sidebar.add(
                Box.createVerticalGlue()
        );

        JButton profile =
                createNavButton(
                        "◉   My Profile",
                        false
                );

        profile.addActionListener(
                e -> showProfile()
        );

        sidebar.add(profile);

        JButton logout =
                createNavButton(
                        "↪   Sign Out",
                        false
                );

        logout.addActionListener(
                e -> logout()
        );

        sidebar.add(logout);

        return sidebar;
    }

    private JButton createNavButton(
            String text,
            boolean selected) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        button.setForeground(
                selected
                        ? AppTheme.WHITE
                        : new Color(203, 213, 225)
        );

        button.setBackground(
                selected
                        ? new Color(30, 64, 175)
                        : AppTheme.NAVY
        );

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setBorder(
                new EmptyBorder(
                        13,
                        14,
                        13,
                        14
                )
        );

        button.setFocusPainted(false);
        button.setOpaque(true);

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        48
                )
        );

        return button;
    }

    // =========================================================
    // MAIN CONTENT
    // =========================================================

    private JPanel createMainContent() {

        JPanel main =
                new JPanel(
                        new BorderLayout()
                );

        main.setBackground(
                AppTheme.BACKGROUND
        );

        main.add(
                createHeader(),
                BorderLayout.NORTH
        );

        main.add(
                createContent(),
                BorderLayout.CENTER
        );

        return main;
    }

    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                AppTheme.WHITE
        );

        header.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0,
                                0,
                                1,
                                0,
                                AppTheme.BORDER
                        ),
                        new EmptyBorder(
                                20,
                                32,
                                20,
                                32
                        )
                )
        );

        JLabel title =
                new JLabel(
                        "My Appointments"
                );

        title.setFont(
                AppTheme.FONT_H2
        );

        title.setForeground(
                AppTheme.TEXT
        );

        JLabel patient =
                new JLabel(
                        user.getFullName()
                );

        patient.setFont(
                AppTheme.FONT_BODY_BOLD
        );

        patient.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        header.add(
                title,
                BorderLayout.WEST
        );

        header.add(
                patient,
                BorderLayout.EAST
        );

        return header;
    }

    // =========================================================
    // CONTENT
    // =========================================================

    private JPanel createContent() {

        JPanel wrapper =
                new JPanel(
                        new BorderLayout()
                );

        wrapper.setOpaque(false);

        wrapper.setBorder(
                new EmptyBorder(
                        30,
                        32,
                        30,
                        32
                )
        );

        JPanel content =
                new JPanel(
                        new BorderLayout(
                                0,
                                22
                        )
                );

        content.setOpaque(false);

        content.add(
                createStats(),
                BorderLayout.NORTH
        );

        content.add(
                createTableCard(),
                BorderLayout.CENTER
        );

        wrapper.add(
                content,
                BorderLayout.CENTER
        );

        return wrapper;
    }

    // =========================================================
    // STAT CARDS
    // =========================================================

    private JPanel createStats() {

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                18,
                                0
                        )
                );

        stats.setOpaque(false);

        totalLabel =
                new JLabel("0");

        upcomingLabel =
                new JLabel("0");

        completedLabel =
                new JLabel("0");

        stats.add(
                createStatCard(
                        "TOTAL APPOINTMENTS",
                        totalLabel,
                        "All your appointments"
                )
        );

        stats.add(
                createStatCard(
                        "UPCOMING",
                        upcomingLabel,
                        "Scheduled visits"
                )
        );

        stats.add(
                createStatCard(
                        "COMPLETED",
                        completedLabel,
                        "Finished consultations"
                )
        );

        return stats;
    }

    private JPanel createStatCard(
            String title,
            JLabel valueLabel,
            String subtitle) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                AppTheme.WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                AppTheme.BORDER,
                                1
                        ),
                        new EmptyBorder(
                                20,
                                22,
                                20,
                                22
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        titleLabel.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        valueLabel.setForeground(
                AppTheme.BLUE
        );

        JLabel subtitleLabel =
                new JLabel(subtitle);

        subtitleLabel.setFont(
                AppTheme.FONT_SMALL
        );

        subtitleLabel.setForeground(
                AppTheme.TEXT_SECONDARY
        );

        JPanel center =
                new JPanel();

        center.setOpaque(false);

        center.setLayout(
                new BoxLayout(
                        center,
                        BoxLayout.Y_AXIS
                )
        );

        center.add(valueLabel);

        center.add(
                subtitleLabel
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                center,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // TABLE
    // =========================================================

    private JPanel createTableCard() {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                0,
                                16
                        )
                );

        card.setBackground(
                AppTheme.WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                AppTheme.BORDER,
                                1
                        ),
                        new EmptyBorder(
                                22,
                                22,
                                22,
                                22
                        )
                )
        );

        JPanel heading =
                new JPanel(
                        new BorderLayout()
                );

        heading.setOpaque(false);

        JLabel title =
                new JLabel(
                        "Appointment history"
                );

        title.setFont(
                AppTheme.FONT_H2
        );

        title.setForeground(
                AppTheme.TEXT
        );

        JButton refresh =
                AppTheme.secondaryButton(
                        "Refresh"
                );

        refresh.addActionListener(
                e -> loadAppointments()
        );

        heading.add(
                title,
                BorderLayout.WEST
        );

        heading.add(
                refresh,
                BorderLayout.EAST
        );

        card.add(
                heading,
                BorderLayout.NORTH
        );

        String[] columns = {
                "ID",
                "DOCTOR",
                "DEPARTMENT",
                "DATE",
                "TIME",
                "QUEUE",
                "EMERGENCY",
                "STATUS"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        appointmentTable =
                new JTable(
                        tableModel
                );

        appointmentTable.setRowHeight(48);

        appointmentTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        appointmentTable.setForeground(
                AppTheme.TEXT
        );

        appointmentTable.setBackground(
                AppTheme.WHITE
        );

        appointmentTable.setGridColor(
                new Color(226, 232, 240)
        );

        appointmentTable.setSelectionBackground(
                new Color(239, 246, 255)
        );

        appointmentTable.setSelectionForeground(
                AppTheme.TEXT
        );

        appointmentTable.setShowVerticalLines(false);

        appointmentTable.setIntercellSpacing(
                new Dimension(0, 1)
        );

        appointmentTable.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                12
                        )
                );

        appointmentTable.getTableHeader()
                .setForeground(
                        AppTheme.TEXT_SECONDARY
                );

        appointmentTable.getTableHeader()
                .setBackground(
                        new Color(248, 250, 252)
                );

        appointmentTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                42
                        )
                );

        appointmentTable.setAutoCreateRowSorter(
                true
        );

        appointmentTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(55);

        appointmentTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(180);

        appointmentTable
                .getColumnModel()
                .getColumn(2)
                                .setPreferredWidth(140);

        appointmentTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(110);

        appointmentTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(90);

        appointmentTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(70);

        appointmentTable
                .getColumnModel()
                .getColumn(6)
                .setPreferredWidth(110);

        appointmentTable
                .getColumnModel()
                .getColumn(7)
                .setPreferredWidth(130);

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        appointmentTable
                .getColumnModel()
                .getColumn(0)
                .setCellRenderer(centerRenderer);

        appointmentTable
                .getColumnModel()
                .getColumn(3)
                .setCellRenderer(centerRenderer);

        appointmentTable
                .getColumnModel()
                .getColumn(4)
                .setCellRenderer(centerRenderer);

        appointmentTable
                .getColumnModel()
                .getColumn(5)
                .setCellRenderer(centerRenderer);

        appointmentTable
                .getColumnModel()
                .getColumn(6)
                .setCellRenderer(centerRenderer);

        JScrollPane scrollPane =
                new JScrollPane(
                        appointmentTable
                );

        scrollPane.setBorder(null);

        card.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // LOAD APPOINTMENTS
    // =========================================================

    private void loadAppointments() {

        if (tableModel == null) {
            return;
        }

        tableModel.setRowCount(0);

        int total = 0;
        int upcoming = 0;
        int completed = 0;

        String sql =
                "SELECT a.appointment_id, "
                + "u.full_name AS doctor_name, "
                + "dep.department_name AS department, "
                + "a.appointment_date, "
                + "a.appointment_time, "
                + "q.queue_number, "
                + "q.emergency_status, "
                + "a.status "
                + "FROM appointments a "
                + "JOIN doctors d "
                + "ON a.doctor_id = d.doctor_id "
                + "JOIN users u "
                + "ON d.doctor_id = u.user_id "
                + "JOIN departments dep "
                + "ON d.department_id = dep.department_id "
                + "JOIN queue q "
                + "ON a.appointment_id = q.appointment_id "
                + "WHERE a.patient_id = ? "
                + "ORDER BY a.appointment_date DESC, "
                + "a.appointment_time DESC";

        try (
                Connection connection =
                        DriverManager.getConnection(
                                DB_URL,
                                DB_USER,
                                DB_PASSWORD
                        );

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    user.getUserId()
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    total++;

                    String status =
                            resultSet.getString(
                                    "status"
                            );

                    if ("COMPLETED".equals(
                            status
                    )) {

                        completed++;

                    } else if (
                            !"CANCELLED".equals(
                                    status
                            )
                    ) {

                        upcoming++;
                    }

                    tableModel.addRow(
                            new Object[] {
                                    resultSet.getInt(
                                            "appointment_id"
                                    ),
                                    resultSet.getString(
                                            "doctor_name"
                                    ),
                                    resultSet.getString(
                                            "department"
                                    ),
                                    resultSet.getDate(
                                            "appointment_date"
                                    ),
                                    resultSet.getTime(
                                            "appointment_time"
                                    ),
                                    resultSet.getInt(
                                            "queue_number"
                                    ),
                                    resultSet.getString(
                                            "emergency_status"
                                    ),
                                    status
                            }
                    );
                }
            }

            totalLabel.setText(
                    String.valueOf(total)
            );

            upcomingLabel.setText(
                    String.valueOf(upcoming)
            );

            completedLabel.setText(
                    String.valueOf(completed)
            );

        } catch (Exception exception) {

            exception.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load appointments.\n\n"
                    + exception.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // NAVIGATION
    // =========================================================

    private void openDashboard() {

        dispose();

        new PatientDashboard(user)
                .setVisible(true);
    }

    private void openBooking() {

        dispose();

        new BookingFrame(user)
                .setVisible(true);
    }

    // =========================================================
    // PROFILE
    // =========================================================

    private void showProfile() {

        JOptionPane.showMessageDialog(
                this,
                "<html>"
                + "<b>Name:</b> "
                + user.getFullName()
                + "<br><br>"
                + "<b>Username:</b> "
                + user.getUsername()
                + "<br><br>"
                + "<b>Email:</b> "
                + user.getEmail()
                + "<br><br>"
                + "<b>Phone:</b> "
                + user.getPhone()
                + "</html>",
                "My Profile",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // LOGOUT
    // =========================================================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to sign out?",
                        "Sign Out",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            dispose();

            new LoginFrame()
                    .setVisible(true);
        }
    }
}