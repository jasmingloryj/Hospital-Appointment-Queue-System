package com.hospital.ui;

import com.hospital.model.User;
import com.hospital.service.AdminService;
import com.hospital.service.DoctorService;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Professional role-based dashboard for Doctor and Admin users.
 *
 * Doctor:
 *  - Current patient
 *  - Today's queue
 *  - Emergency requests
 *  - Approve / Reject / Keep Position
 *  - Call Next Patient
 *
 * Admin:
 *  - Hospital statistics
 *  - Doctor / department management entry points
 *
 * Uses the existing backend services and does not modify database logic.
 */
public class RoleDashboard extends JFrame {

    // ---------------------------------------------------------
    // Theme
    // ---------------------------------------------------------

    private static final Color NAVY = new Color(18, 42, 72);
    private static final Color NAVY_LIGHT = new Color(28, 61, 99);
    private static final Color BLUE = new Color(37, 99, 235);
    private static final Color BLUE_SOFT = new Color(239, 246, 255);
    private static final Color GREEN = new Color(22, 163, 74);
    private static final Color GREEN_SOFT = new Color(240, 253, 244);
    private static final Color RED = new Color(220, 38, 38);
    private static final Color RED_SOFT = new Color(254, 242, 242);
    private static final Color AMBER = new Color(217, 119, 6);
    private static final Color AMBER_SOFT = new Color(255, 251, 235);
    private static final Color TEXT = new Color(30, 41, 59);
    private static final Color MUTED = new Color(100, 116, 139);
    private static final Color BORDER = new Color(226, 232, 240);
    private static final Color SURFACE = Color.WHITE;
    private static final Color BACKGROUND = new Color(246, 248, 252);

    private static final Font TITLE_FONT =
            new Font("SansSerif", Font.BOLD, 28);

    private static final Font SECTION_FONT =
            new Font("SansSerif", Font.BOLD, 17);

    private static final Font BODY_FONT =
            new Font("SansSerif", Font.PLAIN, 14);

    private static final Font SMALL_FONT =
            new Font("SansSerif", Font.PLAIN, 12);

    // ---------------------------------------------------------
    // User / services
    // ---------------------------------------------------------

    private final User user;

    private final DoctorService doctorService;
    private final AdminService adminService;

    // ---------------------------------------------------------
    // Main UI
    // ---------------------------------------------------------

    private JPanel contentPanel;

    private JLabel pageTitle;
    private JLabel pageSubtitle;
    private JLabel profileName;
    private JLabel profileRole;

    // Doctor dashboard
    private JLabel queueCountLabel;
    private JLabel emergencyCountLabel;
    private JLabel currentPatientLabel;
    private JLabel currentPatientDetails;

    private JTable queueTable;
    private JTable emergencyTable;

    private DefaultTableModel queueModel;
    private DefaultTableModel emergencyModel;

    private JButton callNextButton;
    private JButton refreshButton;

    // Admin dashboard
    private JLabel adminPatientCount;
    private JLabel adminDoctorCount;
    private JLabel adminDepartmentCount;
    private JLabel adminAppointmentCount;

    // ---------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------

    public RoleDashboard(User user) {
        this.user = user;

        doctorService = new DoctorService();
        adminService = new AdminService();

        setTitle("Hospital Appointment & Queue Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1180, 760));
        setSize(1380, 850);
        setLocationRelativeTo(null);

        buildInterface();
    }

    // ---------------------------------------------------------
    // Main interface
    // ---------------------------------------------------------

    private void buildInterface() {

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);

        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createMainArea(), BorderLayout.CENTER);

        setContentPane(root);

        SwingUtilities.invokeLater(() -> {
            if (isDoctor()) {
                loadDoctorDashboard();
            } else {
                loadAdminDashboard();
            }
        });
    }

    // ---------------------------------------------------------
    // Sidebar
    // ---------------------------------------------------------

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(255, 0));
        sidebar.setBackground(NAVY);
        sidebar.setBorder(new EmptyBorder(24, 18, 22, 18));

        // Brand
        JPanel brandPanel = new JPanel();
        brandPanel.setOpaque(false);
        brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.Y_AXIS));

        JPanel logoRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        logoRow.setOpaque(false);

        JLabel logo = new JLabel("✚");
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("SansSerif", Font.BOLD, 28));

        JPanel logoCircle = new JPanel(new GridBagLayout());
        logoCircle.setBackground(new Color(37, 99, 235));
        logoCircle.setPreferredSize(new Dimension(48, 48));
        logoCircle.add(logo);

        logoRow.add(logoCircle);

        brandPanel.add(logoRow);
        brandPanel.add(Box.createVerticalStrut(16));

        JLabel brandName = new JLabel("MEDICARE");
        brandName.setForeground(Color.WHITE);
        brandName.setFont(new Font("SansSerif", Font.BOLD, 20));

        JLabel brandSubtitle = new JLabel("Hospital Management");
        brandSubtitle.setForeground(new Color(174, 194, 217));
        brandSubtitle.setFont(SMALL_FONT);

        brandPanel.add(brandName);
        brandPanel.add(Box.createVerticalStrut(3));
        brandPanel.add(brandSubtitle);

        sidebar.add(brandPanel, BorderLayout.NORTH);

        // Navigation
        JPanel nav = new JPanel();
        nav.setOpaque(false);
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBorder(new EmptyBorder(42, 0, 0, 0));

        JLabel navLabel = new JLabel("NAVIGATION");
        navLabel.setForeground(new Color(137, 163, 193));
        navLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        navLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        nav.add(navLabel);
        nav.add(Box.createVerticalStrut(12));

        JButton dashboardButton =
                createSidebarButton("Dashboard", true);

        dashboardButton.addActionListener(e -> {
            if (isDoctor()) {
                loadDoctorDashboard();
            } else {
                loadAdminDashboard();
            }
        });

        nav.add(dashboardButton);
        nav.add(Box.createVerticalStrut(8));

        if (isDoctor()) {

            JButton queueButton =
        createSidebarButton("Queue Management", false);

queueButton.addActionListener(e -> {
    new QueueManagementFrame(user).setVisible(true);
});

JButton emergencyButton =
        createSidebarButton("Emergency Requests", false);

emergencyButton.addActionListener(e -> {
    new EmergencyRequestsFrame(user).setVisible(true);
});

            nav.add(queueButton);
            nav.add(Box.createVerticalStrut(8));
            nav.add(emergencyButton);

        } else {

            JButton doctorsButton =
                    createSidebarButton("Doctor Management", false);

            doctorsButton.addActionListener(e ->
                    showComingSoon("Doctor Management"));

            JButton departmentsButton =
                    createSidebarButton("Departments", false);

            departmentsButton.addActionListener(e ->
                    showComingSoon("Department Management"));

            nav.add(doctorsButton);
            nav.add(Box.createVerticalStrut(8));
            nav.add(departmentsButton);
        }

        sidebar.add(nav, BorderLayout.CENTER);

        // Bottom user section
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BorderLayout(10, 0));
        bottom.setBorder(new EmptyBorder(16, 4, 0, 4));

        JPanel avatar = new JPanel(new GridBagLayout());
        avatar.setPreferredSize(new Dimension(42, 42));
        avatar.setBackground(new Color(44, 76, 115));

        JLabel avatarText = new JLabel(
                getInitials(user.getFullName())
        );
        avatarText.setForeground(Color.WHITE);
        avatarText.setFont(new Font("SansSerif", Font.BOLD, 14));

        avatar.add(avatarText);

        JPanel userInfo = new JPanel();
        userInfo.setOpaque(false);
        userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(
                shorten(user.getFullName(), 20)
        );
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel role = new JLabel(
                isDoctor() ? "Doctor" : "Administrator"
        );
        role.setForeground(new Color(165, 185, 208));
        role.setFont(SMALL_FONT);

        userInfo.add(name);
        userInfo.add(Box.createVerticalStrut(3));
        userInfo.add(role);

        bottom.add(avatar, BorderLayout.WEST);
        bottom.add(userInfo, BorderLayout.CENTER);

        sidebar.add(bottom, BorderLayout.SOUTH);

        return sidebar;
    }

    private JButton createSidebarButton(String text, boolean selected) {

        JButton button = new JButton(text);

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(
                selected ? Color.WHITE : new Color(190, 207, 226)
        );

        button.setBackground(
                selected ? new Color(37, 72, 112) : NAVY
        );

        button.setBorder(
                new EmptyBorder(12, 16, 12, 16)
        );

        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.getBackground().equals(NAVY)) {
                    button.setBackground(NAVY_LIGHT);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!selected) {
                    button.setBackground(NAVY);
                }
            }
        });

        return button;
    }

    // ---------------------------------------------------------
    // Main area
    // ---------------------------------------------------------

    private JPanel createMainArea() {

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BACKGROUND);

        main.add(createTopBar(), BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setBackground(BACKGROUND);
        contentPanel.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(BACKGROUND);

        main.add(scrollPane, BorderLayout.CENTER);

        return main;
    }

    // ---------------------------------------------------------
    // Top bar
    // ---------------------------------------------------------

    private JPanel createTopBar() {

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        top.setBorder(new EmptyBorder(22, 30, 22, 30));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        pageTitle = new JLabel("Dashboard");
        pageTitle.setForeground(TEXT);
        pageTitle.setFont(TITLE_FONT);

        pageSubtitle = new JLabel(
                isDoctor()
                        ? "Manage today's patient flow and emergency requests."
                        : "Hospital operations overview and management."
        );

        pageSubtitle.setForeground(MUTED);
        pageSubtitle.setFont(BODY_FONT);

        titlePanel.add(pageTitle);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(pageSubtitle);

        top.add(titlePanel, BorderLayout.WEST);

        JPanel profile = new JPanel(new FlowLayout(
                FlowLayout.RIGHT, 10, 0
        ));
        profile.setOpaque(false);

        JPanel avatar = new JPanel(new GridBagLayout());
        avatar.setPreferredSize(new Dimension(42, 42));
        avatar.setBackground(BLUE);

        JLabel initials = new JLabel(
                getInitials(user.getFullName())
        );
        initials.setForeground(Color.WHITE);
        initials.setFont(new Font("SansSerif", Font.BOLD, 14));

        avatar.add(initials);

        JPanel profileText = new JPanel();
        profileText.setOpaque(false);
        profileText.setLayout(new BoxLayout(
                profileText,
                BoxLayout.Y_AXIS
        ));

        profileName = new JLabel(user.getFullName());
        profileName.setForeground(TEXT);
        profileName.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                13
        ));

        profileRole = new JLabel(
                isDoctor() ? "Doctor" : "Administrator"
        );
        profileRole.setForeground(MUTED);
        profileRole.setFont(SMALL_FONT);

        profileText.add(profileName);
        profileText.add(Box.createVerticalStrut(3));
        profileText.add(profileRole);

        profile.add(avatar);
        profile.add(profileText);

        top.add(profile, BorderLayout.EAST);

        return top;
    }

    // ---------------------------------------------------------
    // Doctor dashboard
    // ---------------------------------------------------------

    private void loadDoctorDashboard() {

        pageTitle.setText("Doctor Dashboard");
        pageSubtitle.setText(
                "Manage today's patient flow and emergency requests."
        );

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);
        root.setBorder(new EmptyBorder(28, 30, 35, 30));

        JPanel content = new JPanel();
        content.setBackground(BACKGROUND);
        content.setLayout(new BoxLayout(
                content,
                BoxLayout.Y_AXIS
        ));

        // Welcome
        content.add(createWelcomeCard());
        content.add(Box.createVerticalStrut(22));

        // Stats
        JPanel stats = new JPanel(new GridLayout(1, 3, 16, 0));
        stats.setOpaque(false);
        stats.setMaximumSize(new Dimension(
                Integer.MAX_VALUE,
                115
        ));

        queueCountLabel = new JLabel("—");
        emergencyCountLabel = new JLabel("—");
        currentPatientLabel = new JLabel("No patient");

        stats.add(createStatCard(
                "Today's Queue",
                queueCountLabel,
                "Patients waiting",
                BLUE,
                BLUE_SOFT
        ));

        stats.add(createStatCard(
                "Emergency Requests",
                emergencyCountLabel,
                "Awaiting review",
                RED,
                RED_SOFT
        ));

        stats.add(createStatCard(
                "Current Patient",
                currentPatientLabel,
                "Consultation status",
                GREEN,
                GREEN_SOFT
        ));

        content.add(stats);
        content.add(Box.createVerticalStrut(22));

        // Current patient
        content.add(createCurrentPatientCard());
        content.add(Box.createVerticalStrut(22));

        // Queue section
        content.add(createQueueSection());
        content.add(Box.createVerticalStrut(22));

        // Emergency section
        content.add(createEmergencySection());

        root.add(content, BorderLayout.CENTER);

        contentPanel.removeAll();
        contentPanel.add(root, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();

        loadDoctorData();
    }

    private JPanel createWelcomeCard() {

        JPanel card = createCard();

        card.setLayout(new BorderLayout());

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(
                left,
                BoxLayout.Y_AXIS
        ));

        JLabel greeting = new JLabel(
                getGreeting() + ", " + user.getFullName()
        );

        greeting.setForeground(TEXT);
        greeting.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                21
        ));

        JLabel description = new JLabel(
                "Here is your patient flow overview for today."
        );

        description.setForeground(MUTED);
        description.setFont(BODY_FONT);

        left.add(greeting);
        left.add(Box.createVerticalStrut(7));
        left.add(description);

        JPanel datePanel = new JPanel(new GridBagLayout());
        datePanel.setOpaque(false);

        JLabel date = new JLabel(
                LocalDate.now().format(
                        DateTimeFormatter.ofPattern(
                                "EEEE, dd MMM yyyy"
                        )
                )
        );

        date.setForeground(NAVY);
        date.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                13
        ));

        datePanel.add(date);

        card.add(left, BorderLayout.WEST);
        card.add(datePanel, BorderLayout.EAST);

        return card;
    }

    private JPanel createCurrentPatientCard() {

        JPanel card = createCard();

        card.setLayout(new BorderLayout(20, 0));

        JPanel title = new JPanel();
        title.setOpaque(false);
        title.setLayout(new BoxLayout(
                title,
                BoxLayout.Y_AXIS
        ));

        JLabel heading = new JLabel("Current Consultation");
        heading.setForeground(TEXT);
        heading.setFont(SECTION_FONT);

        JLabel subtitle = new JLabel(
                "Patient currently being attended"
        );
        subtitle.setForeground(MUTED);
        subtitle.setFont(SMALL_FONT);

        title.add(heading);
        title.add(Box.createVerticalStrut(4));
        title.add(subtitle);

        card.add(title, BorderLayout.WEST);

        JPanel patient = new JPanel();
        patient.setOpaque(false);
        patient.setLayout(new BoxLayout(
                patient,
                BoxLayout.Y_AXIS
        ));

        currentPatientLabel = new JLabel("No patient");
        currentPatientLabel.setForeground(TEXT);
        currentPatientLabel.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                18
        ));

        currentPatientDetails = new JLabel(
                "No consultation is currently active."
        );
        currentPatientDetails.setForeground(MUTED);
        currentPatientDetails.setFont(BODY_FONT);

        patient.add(currentPatientLabel);
        patient.add(Box.createVerticalStrut(4));
        patient.add(currentPatientDetails);

        card.add(patient, BorderLayout.CENTER);

        return card;
    }

    private JPanel createQueueSection() {

        JPanel outer = createCard();
        outer.setLayout(new BorderLayout(0, 16));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel title = new JPanel();
        title.setOpaque(false);
        title.setLayout(new BoxLayout(
                title,
                BoxLayout.Y_AXIS
        ));

        JLabel heading = new JLabel("Today's Patient Queue");
        heading.setForeground(TEXT);
        heading.setFont(SECTION_FONT);

        JLabel subtitle = new JLabel(
                "Patients scheduled for today's consultations."
        );
        subtitle.setForeground(MUTED);
        subtitle.setFont(SMALL_FONT);

        title.add(heading);
        title.add(Box.createVerticalStrut(4));
        title.add(subtitle);

        header.add(title, BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(
                FlowLayout.RIGHT,
                8,
                0
        ));
        actions.setOpaque(false);

        refreshButton = createSecondaryButton("Refresh");
        refreshButton.addActionListener(e -> loadDoctorData());

        callNextButton = createPrimaryButton("Call Next Patient");
        callNextButton.addActionListener(e -> callNextPatient());

        actions.add(refreshButton);
        actions.add(callNextButton);

        header.add(actions, BorderLayout.EAST);

        outer.add(header, BorderLayout.NORTH);

        queueModel = new DefaultTableModel(
                new Object[]{
                        "Queue",
                        "Patient",
                        "Appointment",
                        "Status",
                        "Priority"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        queueTable = new JTable(queueModel);

        styleTable(queueTable);

        JScrollPane scrollPane =
                new JScrollPane(queueTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );

        scrollPane.setPreferredSize(
                new Dimension(0, 245)
        );

        outer.add(scrollPane, BorderLayout.CENTER);

        return outer;
    }

    private JPanel createEmergencySection() {

        JPanel outer = createCard();
        outer.setLayout(new BorderLayout(0, 16));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel title = new JPanel();
        title.setOpaque(false);
        title.setLayout(new BoxLayout(
                title,
                BoxLayout.Y_AXIS
        ));

        JLabel heading =
                new JLabel("Emergency Requests");

        heading.setForeground(TEXT);
        heading.setFont(SECTION_FONT);

        JLabel subtitle =
                new JLabel(
                        "Review patient emergency escalation requests."
                );

        subtitle.setForeground(MUTED);
        subtitle.setFont(SMALL_FONT);

        title.add(heading);
        title.add(Box.createVerticalStrut(4));
        title.add(subtitle);

        header.add(title, BorderLayout.WEST);

        JLabel policy = new JLabel(
                "Doctor approval required"
        );

        policy.setForeground(AMBER);
        policy.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                12
        ));

        header.add(policy, BorderLayout.EAST);

        outer.add(header, BorderLayout.NORTH);

        emergencyModel = new DefaultTableModel(
                new Object[]{
                        "Queue",
                        "Patient",
                        "Appointment",
                        "Request",
                        "Action"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        emergencyTable = new JTable(emergencyModel);

        styleTable(emergencyTable);

        emergencyTable.setRowHeight(42);

        JScrollPane scrollPane =
                new JScrollPane(emergencyTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );

        scrollPane.setPreferredSize(
                new Dimension(0, 240)
        );

        outer.add(scrollPane, BorderLayout.CENTER);

        JPanel actionBar = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        8,
                        0
                )
        );

        actionBar.setOpaque(false);

        JButton reviewButton =
                createPrimaryButton("Review Selected");

        reviewButton.addActionListener(e ->
                reviewSelectedEmergency()
        );

        actionBar.add(reviewButton);

        outer.add(actionBar, BorderLayout.SOUTH);

        return outer;
    }

    // ---------------------------------------------------------
    // Doctor data
    // ---------------------------------------------------------

    private void loadDoctorData() {

        if (!isDoctor()) {
            return;
        }

        int doctorId = user.getUserId();

        try {

            loadQueueData(doctorId);
            loadEmergencyData(doctorId);
            loadCurrentPatient(doctorId);

        } catch (Exception e) {

            showError(
                    "Unable to load doctor dashboard data.",
                    e
            );
        }
    }

    private void loadQueueData(int doctorId) {

    queueModel.setRowCount(0);

    try {

        List<String> queue = doctorService.getTodayQueue(doctorId);

        if (queue == null || queue.isEmpty()) {
            queueCountLabel.setText("0");
            return;
        }

        queueCountLabel.setText(
                String.valueOf(queue.size())
        );

        int number = 1;

        for (String entry : queue) {

            String text =
                    entry == null ? "" : entry.trim();

            queueModel.addRow(
                    new Object[]{
                            number,
                            text,
                            "Today",
                            "Waiting",
                            "Normal"
                    }
            );

            number++;
        }

    } catch (Exception e) {

        queueCountLabel.setText("—");

        showError(
                "Unable to load today's queue.",
                e
        );
    }
}


    private void loadEmergencyData(int doctorId) {

    emergencyModel.setRowCount(0);

    try {

        List<String> emergencies =
                doctorService.getPendingEmergencies(
                        doctorId
                );

        if (emergencies == null
                || emergencies.isEmpty()) {

            emergencyCountLabel.setText("0");
            return;
        }

        emergencyCountLabel.setText(
                String.valueOf(
                        emergencies.size()
                )
        );

        int number = 1;

        for (String entry : emergencies) {

            String text =
                    entry == null
                            ? ""
                            : entry.trim();

            emergencyModel.addRow(
                    new Object[]{
                            number,
                            text,
                            "Today",
                            "Pending review",
                            "Review"
                    }
            );

            number++;
        }

    } catch (Exception e) {

        emergencyCountLabel.setText("—");

        showError(
                "Unable to load emergency requests.",
                e
        );
    }
}
            

    private void loadCurrentPatient(int doctorId) {

        ResultSet rs = null;

        try {

            rs = doctorService.getCurrentPatient(
                    doctorId
            );

            if (rs != null && rs.next()) {

                String patientName =
                        safeGetString(
                                rs,
                                "patient_name"
                        );

                String phone =
                        safeGetString(
                                rs,
                                "phone"
                        );

                String appointmentTime =
                        safeGetString(
                                rs,
                                "appointment_time"
                        );

                currentPatientLabel.setText(
                        patientName
                );

                currentPatientDetails.setText(
                        "Appointment: "
                                + appointmentTime
                                + "  •  Phone: "
                                + phone
                );

            } else {

                currentPatientLabel.setText(
                        "No patient"
                );

                currentPatientDetails.setText(
                        "No consultation is currently active."
                );
            }

        } catch (SQLException e) {

            currentPatientLabel.setText(
                    "Unavailable"
            );

            currentPatientDetails.setText(
                    "Could not load consultation status."
            );

        } finally {

            closeResultSet(rs);
        }
    }

    // ---------------------------------------------------------
    // Doctor actions
    // ---------------------------------------------------------

    private void callNextPatient() {

    int doctorId = user.getUserId();

    try {

        String result =
                doctorService.callNextPatient(
                        doctorId
                );

        if (result != null
                && !result.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    result,
                    "Patient Called",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadDoctorData();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "There are no waiting patients "
                            + "available to call.",
                    "Queue Empty",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

    } catch (Exception e) {

        showError(
                "Unable to call the next patient.",
                e
        );
    }
}

    private void reviewSelectedEmergency() {

        int row =
                emergencyTable.getSelectedRow();

        if (row < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select an emergency request first.",
                    "No Request Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String queueIdText =
                String.valueOf(
                        emergencyModel.getValueAt(
                                row,
                                0
                        )
                );

        String patientName =
                String.valueOf(
                        emergencyModel.getValueAt(
                                row,
                                1
                        )
                );

        int queueId;

        try {

            queueId =
                    Integer.parseInt(queueIdText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid queue record.",
                    "Data Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        Object[] options = {
                "Approve",
                "Keep Position",
                "Reject",
                "Cancel"
        };

        int choice =
                JOptionPane.showOptionDialog(
                        this,
                        "Emergency request from "
                                + patientName
                                + ".\n\n"
                                + "Choose how you want to "
                                + "handle this request.",
                        "Emergency Review",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]
                );

        if (choice == 0) {

            reviewEmergency(
                    queueId,
                    true
            );

        } else if (choice == 1) {

            keepEmergencyPosition(queueId);

        } else if (choice == 2) {

            reviewEmergency(
                    queueId,
                    false
            );
        }
    }

    private void reviewEmergency(
            int queueId,
            boolean approve
    ) {

        try {

            boolean success =
                    doctorService.reviewEmergency(
                            queueId,
                            approve
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        approve
                                ? "Emergency request approved."
                                : "Emergency request rejected.",
                        "Emergency Review",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadDoctorData();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "The emergency request could not "
                                + "be updated.",
                        "Update Failed",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (Exception e) {

            showError(
                    "Unable to review emergency request.",
                    e
            );
        }
    }

    private void keepEmergencyPosition(
            int queueId
    ) {

        try {

            boolean success =
                    doctorService.keepEmergencyPosition(
                            queueId
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Emergency request acknowledged.\n"
                                + "The patient remains in the "
                                + "current queue position.",
                        "Position Kept",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadDoctorData();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to keep the current position.",
                        "Update Failed",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (Exception e) {

            showError(
                    "Unable to update emergency request.",
                    e
            );
        }
    }

    // ---------------------------------------------------------
    // Admin dashboard
    // ---------------------------------------------------------

    private void loadAdminDashboard() {

        pageTitle.setText("Administration Dashboard");
        pageSubtitle.setText(
                "Monitor hospital activity and manage core resources."
        );

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);
        root.setBorder(new EmptyBorder(28, 30, 35, 30));

        JPanel content = new JPanel();
        content.setBackground(BACKGROUND);
        content.setLayout(new BoxLayout(
                content,
                BoxLayout.Y_AXIS
        ));

        content.add(createAdminWelcomeCard());
        content.add(Box.createVerticalStrut(22));

        JPanel stats = new JPanel(
                new GridLayout(
                        1,
                        4,
                        14,
                        0
                )
        );

        stats.setOpaque(false);
        stats.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        120
                )
        );

        adminPatientCount = new JLabel("—");
        adminDoctorCount = new JLabel("—");
        adminDepartmentCount = new JLabel("—");
        adminAppointmentCount = new JLabel("—");

        stats.add(createStatCard(
                "Patients",
                adminPatientCount,
                "Registered patients",
                BLUE,
                BLUE_SOFT
        ));

        stats.add(createStatCard(
                "Doctors",
                adminDoctorCount,
                "Active doctors",
                GREEN,
                GREEN_SOFT
        ));

        stats.add(createStatCard(
                "Departments",
                adminDepartmentCount,
                "Hospital departments",
                AMBER,
                AMBER_SOFT
        ));

        stats.add(createStatCard(
                "Today's Appointments",
                adminAppointmentCount,
                "Scheduled today",
                NAVY,
                new Color(241, 245, 249)
        ));

        content.add(stats);
        content.add(Box.createVerticalStrut(22));

        content.add(
                createAdminManagementCard()
        );

        content.add(Box.createVerticalStrut(22));

        content.add(
                createAdminInfoCard()
        );

        root.add(content, BorderLayout.CENTER);

        contentPanel.removeAll();
        contentPanel.add(root, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();

        loadAdminData();
    }

    private JPanel createAdminWelcomeCard() {

        JPanel card = createCard();
        card.setLayout(new BorderLayout());

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(
                left,
                BoxLayout.Y_AXIS
        ));

        JLabel greeting = new JLabel(
                getGreeting()
                        + ", "
                        + user.getFullName()
        );

        greeting.setForeground(TEXT);
        greeting.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                21
        ));

        JLabel description = new JLabel(
                "Monitor the hospital's appointment and queue operations."
        );

        description.setForeground(MUTED);
        description.setFont(BODY_FONT);

        left.add(greeting);
        left.add(Box.createVerticalStrut(7));
        left.add(description);

        card.add(left, BorderLayout.WEST);

        JLabel date = new JLabel(
                LocalDate.now().format(
                        DateTimeFormatter.ofPattern(
                                "EEEE, dd MMM yyyy"
                        )
                )
        );

        date.setForeground(NAVY);
        date.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                13
        ));

        card.add(date, BorderLayout.EAST);

        return card;
    }

    private JPanel createAdminManagementCard() {

        JPanel card = createCard();

        card.setLayout(new BorderLayout(0, 18));

        JPanel title = new JPanel();
        title.setOpaque(false);
        title.setLayout(new BoxLayout(
                title,
                BoxLayout.Y_AXIS
        ));

        JLabel heading =
                new JLabel("Hospital Management");

        heading.setForeground(TEXT);
        heading.setFont(SECTION_FONT);

        JLabel subtitle =
                new JLabel(
                        "Manage doctors, departments and availability."
                );

        subtitle.setForeground(MUTED);
        subtitle.setFont(SMALL_FONT);

        title.add(heading);
        title.add(Box.createVerticalStrut(4));
        title.add(subtitle);

        card.add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel(
                new GridLayout(1, 2, 14, 0)
        );

        buttons.setOpaque(false);

        JButton doctors =
                createManagementButton(
                        "Doctor Management",
                        "Add doctors, update specialization "
                                + "and manage availability."
                );

        doctors.addActionListener(e ->
                showComingSoon("Doctor Management")
        );

        JButton departments =
                createManagementButton(
                        "Department Management",
                        "Create and maintain hospital "
                                + "departments."
                );

        departments.addActionListener(e ->
                showComingSoon("Department Management")
        );

        buttons.add(doctors);
        buttons.add(departments);

        card.add(buttons, BorderLayout.CENTER);

        return card;
    }

    private JPanel createAdminInfoCard() {

        JPanel card = createCard();

        card.setLayout(new BorderLayout());

        JLabel heading =
                new JLabel("System Overview");

        heading.setForeground(TEXT);
        heading.setFont(SECTION_FONT);

        JTextArea text = new JTextArea(
                "The hospital system provides role-based access, "
                        + "appointment scheduling, doctor availability, "
                        + "queue management and controlled emergency "
                        + "prioritization."
        );

        text.setEditable(false);
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.setOpaque(false);
        text.setForeground(MUTED);
        text.setFont(BODY_FONT);
        text.setBorder(
                new EmptyBorder(
                        12,
                        0,
                        0,
                        0
                )
        );

        card.add(heading, BorderLayout.NORTH);
        card.add(text, BorderLayout.CENTER);

        return card;
    }

    private void loadAdminData() {

        try {

            adminPatientCount.setText(
                    String.valueOf(
                            adminService.getPatientCount()
                    )
            );

            adminDoctorCount.setText(
                    String.valueOf(
                            adminService.getDoctorCount()
                    )
            );

            adminDepartmentCount.setText(
                    String.valueOf(
                            adminService.getDepartmentCount()
                    )
            );

            adminAppointmentCount.setText(
                    String.valueOf(
                            adminService.getTodayAppointmentCount()
                    )
            );

        } catch (Exception e) {

            adminPatientCount.setText("—");
            adminDoctorCount.setText("—");
            adminDepartmentCount.setText("—");
            adminAppointmentCount.setText("—");

            showError(
                    "Unable to load administration statistics.",
                    e
            );
        }
    }

    // ---------------------------------------------------------
    // Reusable cards
    // ---------------------------------------------------------

    private JPanel createStatCard(
            String title,
            JLabel valueLabel,
            String subtitle,
            Color accent,
            Color softBackground
    ) {

        JPanel card = new JPanel(
                new BorderLayout(14, 0)
        );

        card.setBackground(SURFACE);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                17,
                                18,
                                17,
                                18
                        )
                )
        );

        JPanel accentBar = new JPanel();
        accentBar.setBackground(accent);
        accentBar.setPreferredSize(
                new Dimension(5, 0)
        );

        card.add(accentBar, BorderLayout.WEST);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(
                content,
                BoxLayout.Y_AXIS
        ));

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(MUTED);
        titleLabel.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                12
        ));

        valueLabel.setForeground(TEXT);
        valueLabel.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                26
        ));

        JLabel sub =
                new JLabel(subtitle);

        sub.setForeground(MUTED);
        sub.setFont(SMALL_FONT);

        content.add(titleLabel);
        content.add(Box.createVerticalStrut(5));
        content.add(valueLabel);
        content.add(Box.createVerticalStrut(2));
        content.add(sub);

        card.add(content, BorderLayout.CENTER);

        return card;
    }

    private JPanel createCard() {

        JPanel card = new JPanel();

        card.setBackground(SURFACE);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        return card;
    }

    private JButton createManagementButton(
            String title,
            String description
    ) {

        JButton button = new JButton();

        button.setLayout(new BorderLayout());
        button.setBackground(Color.WHITE);
        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                17,
                                18,
                                17,
                                18
                        )
                )
        );

        button.setFocusPainted(false);
        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(
                content,
                BoxLayout.Y_AXIS
        ));

        JLabel heading =
                new JLabel(title);

        heading.setForeground(TEXT);
        heading.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                14
        ));

        JLabel body =
                new JLabel(
                        "<html><div style='width:320px;'>"
                                + description
                                + "</div></html>"
                );

        body.setForeground(MUTED);
        body.setFont(SMALL_FONT);

        content.add(heading);
        content.add(Box.createVerticalStrut(7));
        content.add(body);

        button.add(content, BorderLayout.CENTER);

        return button;
    }
// ---------------------------------------------------------
    // Table styling
    // ---------------------------------------------------------

    private void styleTable(JTable table) {

        table.setFont(BODY_FONT);
        table.setForeground(TEXT);
        table.setBackground(Color.WHITE);

        table.setRowHeight(40);

        table.setShowGrid(false);
        table.setIntercellSpacing(
                new Dimension(0, 1)
        );

        table.setSelectionBackground(
                new Color(239, 246, 255)
        );

        table.setSelectionForeground(TEXT);

        table.getTableHeader().setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        table.getTableHeader().setForeground(
                MUTED
        );

        table.getTableHeader().setBackground(
                new Color(248, 250, 252)
        );

        table.getTableHeader().setBorder(
                BorderFactory.createMatteBorder(
                        0,
                        0,
                        1,
                        0,
                        BORDER
                )
        );

        table.getTableHeader().setPreferredSize(
                new Dimension(0, 42)
        );

        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean selected,
                            boolean focused,
                            int row,
                            int column
                    ) {

                        Component component =
                                super.getTableCellRendererComponent(
                                        table,
                                        value,
                                        selected,
                                        focused,
                                        row,
                                        column
                                );

                        setBorder(
                                new EmptyBorder(
                                        0,
                                        10,
                                        0,
                                        10
                                )
                        );

                        if (!selected) {
                            setBackground(
                                    row % 2 == 0
                                            ? Color.WHITE
                                            : new Color(
                                                    250,
                                                    251,
                                                    253
                                            )
                            );
                        }

                        return component;
                    }
                };

        table.setDefaultRenderer(
                Object.class,
                renderer
        );
    }

    // ---------------------------------------------------------
    // Buttons
    // ---------------------------------------------------------

    private JButton createPrimaryButton(
            String text
    ) {

        JButton button = new JButton(text);

        button.setBackground(BLUE);
        button.setForeground(Color.WHITE);
        button.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                13
        ));

        button.setBorder(
                new EmptyBorder(
                        10,
                        16,
                        10,
                        16
                )
        );

        button.setFocusPainted(false);
        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    private JButton createSecondaryButton(
            String text
    ) {

        JButton button = new JButton(text);

        button.setBackground(Color.WHITE);
        button.setForeground(TEXT);
        button.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                13
        ));

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                9,
                                14,
                                9,
                                14
                        )
                )
        );

        button.setFocusPainted(false);
        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // ---------------------------------------------------------
    // Utility
    // ---------------------------------------------------------

    private boolean isDoctor() {

        return user != null
                && user.getRole() != null
                && user.getRole().equalsIgnoreCase(
                        "DOCTOR"
                );
    }

    private String getGreeting() {

        int hour = LocalTime.now().getHour();

        if (hour < 12) {
            return "Good morning";
        }

        if (hour < 17) {
            return "Good afternoon";
        }

        return "Good evening";
    }

    private String getInitials(String name) {

        if (name == null || name.trim().isEmpty()) {
            return "U";
        }

        String[] parts =
                name.trim().split("\\s+");

        if (parts.length == 1) {
            return parts[0]
                    .substring(
                            0,
                            Math.min(
                                    2,
                                    parts[0].length()
                            )
                    )
                    .toUpperCase();
        }

        return (
                parts[0].substring(0, 1)
                        + parts[parts.length - 1]
                        .substring(0, 1)
        ).toUpperCase();
    }

    private String shorten(
            String text,
            int max
    ) {

        if (text == null) {
            return "";
        }

        if (text.length() <= max) {
            return text;
        }

        return text.substring(0, max - 3)
                + "...";
    }

    private String formatStatus(
            String status
    ) {

        if (status == null || status.isBlank()) {
            return "Unknown";
        }

        return status
                .toLowerCase()
                .replace("_", " ")
                .replaceFirst(
                        "^.",
                        String.valueOf(
                                status
                                        .toLowerCase()
                                        .charAt(0)
                        )
                );
    }

    private String safeGetString(
            ResultSet rs,
            String column
    ) {

        try {

            String value =
                    rs.getString(column);

            return value == null
                    ? ""
                    : value;

        } catch (SQLException e) {

            return "";
        }
    }

    private void closeResultSet(
            ResultSet rs
    ) {

        if (rs == null) {
            return;
        }

        try {
            rs.close();
        } catch (SQLException ignored) {
        }
    }

    private void showError(
            String message,
            Exception exception
    ) {

        String detail =
                exception.getMessage();

        if (detail == null
                || detail.isBlank()) {
            detail = "Unknown database error.";
        }

        JOptionPane.showMessageDialog(
                this,
                message
                        + "\n\n"
                        + detail,
                "Dashboard Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void showComingSoon(
            String feature
    ) {

        JOptionPane.showMessageDialog(
                this,
                feature
                        + " is connected to the dashboard "
                        + "navigation and will use the existing "
                        + "backend service.",
                feature,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void scrollToSection(
        Component component
) {

    if (!(component instanceof JComponent)) {
        return;
    }

    JComponent target =
            (JComponent) component;

    SwingUtilities.invokeLater(() ->
            target.scrollRectToVisible(
                    new Rectangle(
                            0,
                            0,
                            1,
                            1
                    )
            )
    );
}
}