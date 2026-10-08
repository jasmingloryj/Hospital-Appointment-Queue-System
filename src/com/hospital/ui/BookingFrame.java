package com.hospital.ui;

import com.hospital.model.Doctor;
import com.hospital.model.User;
import com.hospital.service.AppointmentService;
import com.hospital.service.DoctorService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingFrame extends JFrame {

    private final User user;
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    private JComboBox<DoctorItem> doctorCombo;
    private JSpinner dateSpinner;
    private JSpinner timeSpinner;
    private JTextArea symptomsArea;
    private JCheckBox emergencyCheckBox;

    private JLabel availabilityLabel;
    private JLabel statusLabel;

    public BookingFrame(User user) {
        this.user = user;
        this.appointmentService = new AppointmentService();
        this.doctorService = new DoctorService();

        setTitle("Book Appointment | Hospital Management System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1100, 700));

        buildUI();
        loadDoctors();

        setLocationRelativeTo(null);
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.BACKGROUND);

        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createMainContent(), BorderLayout.CENTER);

        setContentPane(root);
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

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
        dashboardButton.addActionListener(e -> openDashboard());

        JButton appointmentsButton = createNavButton("My Appointments");
        appointmentsButton.addActionListener(e -> openAppointments());

        JButton bookingButton = createNavButton("Book Appointment");
        bookingButton.setBackground(AppTheme.NAVY_LIGHT);
        bookingButton.setOpaque(true);
        bookingButton.addActionListener(e -> {
            // Already on booking screen.
        });

        top.add(dashboardButton);
        top.add(appointmentsButton);
        top.add(bookingButton);

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
        logoutButton.addActionListener(e -> logout());

        bottom.add(nameLabel);
        bottom.add(Box.createVerticalStrut(3));
        bottom.add(roleLabel);
        bottom.add(Box.createVerticalStrut(14));
        bottom.add(logoutButton);

        sidebar.add(bottom, BorderLayout.SOUTH);

        return sidebar;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);

        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        button.setPreferredSize(new Dimension(200, 46));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(0, 14, 0, 10));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        button.setForeground(new Color(220, 230, 242));
        button.setFont(AppTheme.FONT_BODY_BOLD);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
    }

    // =========================================================
    // MAIN CONTENT
    // =========================================================

    private JPanel createMainContent() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(AppTheme.BACKGROUND);
        content.setBorder(new EmptyBorder(32, 36, 32, 36));

        content.add(createHeader(), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(28, 0, 0, 0));

        JPanel bookingCard = createBookingCard();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 20, 0);

        center.add(bookingCard, gbc);

        content.add(center, BorderLayout.CENTER);

        return content;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Book an Appointment");
        title.setFont(AppTheme.FONT_H1);
        title.setForeground(AppTheme.TEXT);

        JLabel subtitle = new JLabel(
                "Choose a doctor, appointment time, and describe your symptoms."
        );
        subtitle.setFont(AppTheme.FONT_BODY);
        subtitle.setForeground(AppTheme.TEXT_SECONDARY);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(7));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        return header;
    }

    private JPanel createBookingCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(AppTheme.WHITE);
        card.setBorder(AppTheme.cardBorder());

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 18, 0);

        // Doctor
        addFieldLabel(form, gbc, 0, "Select Doctor");

        doctorCombo = new JComboBox<>();
        doctorCombo.setFont(AppTheme.FONT_BODY);
        doctorCombo.setPreferredSize(new Dimension(500, 44));
        doctorCombo.setBackground(AppTheme.WHITE);

        doctorCombo.addActionListener(e -> updateDoctorAvailability());

        gbc.gridy++;
        form.add(doctorCombo, gbc);

        availabilityLabel = new JLabel("Select a doctor to view availability.");
        availabilityLabel.setFont(AppTheme.FONT_SMALL);
        availabilityLabel.setForeground(AppTheme.TEXT_SECONDARY);

        gbc.gridy++;
        gbc.insets = new Insets(-10, 0, 18, 0);
        form.add(availabilityLabel, gbc);

        // Date
        gbc.insets = new Insets(0, 0, 18, 0);

        addFieldLabel(form, gbc, 0, "Appointment Date");

        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateSpinner = new JSpinner(dateModel);
        dateSpinner.setEditor(
                new JSpinner.DateEditor(dateSpinner, "dd MMM yyyy")
        );
        dateSpinner.setPreferredSize(new Dimension(500, 44));
        dateSpinner.setFont(AppTheme.FONT_BODY);

        gbc.gridy++;
        form.add(dateSpinner, gbc);

        // Time
        addFieldLabel(form, gbc, 0, "Appointment Time");

        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(timeModel);
        timeSpinner.setEditor(
                new JSpinner.DateEditor(timeSpinner, "HH:mm")
        );
        timeSpinner.setPreferredSize(new Dimension(500, 44));
        timeSpinner.setFont(AppTheme.FONT_BODY);

        gbc.gridy++;
        form.add(timeSpinner, gbc);

        // Symptoms
        addFieldLabel(form, gbc, 0, "Symptoms / Reason for Visit");

        symptomsArea = new JTextArea(5, 40);
        symptomsArea.setFont(AppTheme.FONT_BODY);
        symptomsArea.setForeground(AppTheme.TEXT);
        symptomsArea.setLineWrap(true);
        symptomsArea.setWrapStyleWord(true);
        symptomsArea.setBackground(AppTheme.WHITE);
        symptomsArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER),
                new EmptyBorder(10, 12, 10, 12)
        ));

        JScrollPane symptomsScroll = new JScrollPane(symptomsArea);
        symptomsScroll.setPreferredSize(new Dimension(500, 125));
        symptomsScroll.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER));

        gbc.gridy++;
        form.add(symptomsScroll, gbc);

        // Emergency
        JPanel emergencyPanel = new JPanel(new BorderLayout());
        emergencyPanel.setBackground(AppTheme.DANGER_LIGHT);
        emergencyPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(252, 165, 165)),
                new EmptyBorder(13, 15, 13, 15)
        ));

        emergencyCheckBox = new JCheckBox(
                "Request emergency review"
        );
        emergencyCheckBox.setFont(AppTheme.FONT_BODY_BOLD);
        emergencyCheckBox.setForeground(AppTheme.DANGER);
        emergencyCheckBox.setBackground(AppTheme.DANGER_LIGHT);
        emergencyCheckBox.setFocusPainted(false);

        JLabel emergencyInfo = new JLabel(
                "<html>Emergency requests are reviewed by the doctor.<br>"
                        + "They are not automatically moved ahead in the queue.</html>"
        );
        emergencyInfo.setFont(AppTheme.FONT_SMALL);
        emergencyInfo.setForeground(AppTheme.TEXT_SECONDARY);

        JPanel emergencyText = new JPanel();
        emergencyText.setOpaque(false);
        emergencyText.setLayout(new BoxLayout(emergencyText, BoxLayout.Y_AXIS));

        emergencyText.add(emergencyCheckBox);
        emergencyText.add(Box.createVerticalStrut(4));
        emergencyText.add(emergencyInfo);

        emergencyPanel.add(emergencyText, BorderLayout.CENTER);

        gbc.gridy++;
        form.add(emergencyPanel, gbc);

        // Status
        statusLabel = new JLabel(" ");
        statusLabel.setFont(AppTheme.FONT_BODY_BOLD);
        statusLabel.setForeground(AppTheme.TEXT_SECONDARY);

        gbc.gridy++;
        gbc.insets = new Insets(4, 0, 14, 0);
        form.add(statusLabel, gbc);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);

        JButton bookButton = AppTheme.primaryButton("Confirm Appointment");
        bookButton.setPreferredSize(new Dimension(210, 44));
        bookButton.addActionListener(e -> bookAppointment());

        JButton cancelButton = AppTheme.secondaryButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(120, 44));
        cancelButton.addActionListener(e -> openDashboard());

        buttons.add(bookButton);
        buttons.add(cancelButton);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        form.add(buttons, gbc);

        card.add(form, BorderLayout.CENTER);

        return card;
    }

    private void addFieldLabel(
            JPanel panel,
            GridBagConstraints gbc,
            int column,
            String text
    ) {
        JLabel label = new JLabel(text);
        label.setFont(AppTheme.FONT_BODY_BOLD);
        label.setForeground(AppTheme.TEXT);

        gbc.gridx = column;
        gbc.gridy++;

        panel.add(label, gbc);
    }

    // =========================================================
    // DOCTOR LOADING
    // =========================================================

    private void loadDoctors() {
        doctorCombo.removeAllItems();

        try {
            List<Doctor> doctors = doctorService.getAllDoctors();

            if (doctors == null || doctors.isEmpty()) {
                availabilityLabel.setText("No doctors are currently available.");
                return;
            }

            for (Doctor doctor : doctors) {
                doctorCombo.addItem(new DoctorItem(doctor));
            }

            if (doctorCombo.getItemCount() > 0) {
                doctorCombo.setSelectedIndex(0);
            }

        } catch (Exception e) {
            e.printStackTrace();

            availabilityLabel.setText(
                    "Unable to load doctors."
            );

            showError(
                    "Unable to load doctors from the database.\n\n"
                            + e.getMessage()
            );
        }
    }

    private void updateDoctorAvailability() {
        DoctorItem item =
                (DoctorItem) doctorCombo.getSelectedItem();

        if (item == null) {
            availabilityLabel.setText(
                    "Select a doctor to view availability."
            );
            return;
        }

        Doctor doctor = item.getDoctor();

        availabilityLabel.setText(
                "Availability: " + doctor.getAvailability()
        );

        availabilityLabel.setForeground(AppTheme.SUCCESS);
    }

    // =========================================================
    // BOOKING
    // =========================================================

    private void bookAppointment() {
        clearStatus();

        DoctorItem selected =
                (DoctorItem) doctorCombo.getSelectedItem();

        if (selected == null) {
            showStatus(
                    "Please select a doctor.",
                    AppTheme.DANGER
            );
            return;
        }

        Doctor doctor = selected.getDoctor();

        LocalDate date;

        try {
            java.util.Date selectedDate =
                    (java.util.Date) dateSpinner.getValue();

            date = selectedDate.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();

        } catch (Exception e) {
            showStatus(
                    "Please select a valid appointment date.",
                    AppTheme.DANGER
            );
            return;
        }

        LocalTime time;

        try {
            java.util.Date selectedTime =
                    (java.util.Date) timeSpinner.getValue();

            time = selectedTime.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalTime()
                    .withSecond(0)
                    .withNano(0);

        } catch (Exception e) {
            showStatus(
                    "Please select a valid appointment time.",
                    AppTheme.DANGER
            );
            return;
        }

        String symptoms =
                symptomsArea.getText().trim();

        if (symptoms.isEmpty()) {
            showStatus(
                    "Please describe your symptoms or reason for the visit.",
                    AppTheme.DANGER
            );
            symptomsArea.requestFocus();
            return;
        }

        if (date.isBefore(LocalDate.now())) {
            showStatus(
                    "Appointment date cannot be in the past.",
                    AppTheme.DANGER
            );
            return;
        }

        try {
            boolean available =
                    appointmentService.isDoctorAvailable(
                            doctor.getId(),
                            date,
                            time
                    );

            if (!available) {
                showStatus(
                        "The doctor is not available at the selected date and time.",
                        AppTheme.DANGER
                );
                return;
            }

            boolean alreadyBooked =
                    appointmentService.isSlotAlreadyBooked(
                            doctor.getId(),
                            date,
                            time
                    );

            if (alreadyBooked) {
                showStatus(
                        "This appointment slot is already booked. Please choose another time.",
                        AppTheme.DANGER
                );
                return;
            }

            int appointmentId =
                    appointmentService.bookAppointment(
                            user.getUserId(),
                            doctor.getId(),
                            date,
                            time,
                            symptoms,
                            emergencyCheckBox.isSelected()
                    );

            if (appointmentId <= 0) {
                showStatus(
                        "Appointment could not be created.",
                        AppTheme.DANGER
                );
                return;
            }

            int queuePosition =
                    appointmentService.getQueuePosition(
                            appointmentId
                    );

            showSuccessDialog(
                    appointmentId,
                    queuePosition,
                    emergencyCheckBox.isSelected()
            );

        } catch (Exception e) {
            e.printStackTrace();

            showStatus(
                    "Unable to book the appointment.",
                    AppTheme.DANGER
            );

            showError(
                    "Booking failed.\n\n"
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // SUCCESS
    // =========================================================

    private void showSuccessDialog(
            int appointmentId,
            int queuePosition,
            boolean emergencyRequested
    ) {
        JDialog dialog = new JDialog(
                this,
                "Appointment Confirmed",
                true
        );

        dialog.setSize(520, 420);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.WHITE);
        root.setBorder(new EmptyBorder(28, 30, 28, 30));

        JLabel title = new JLabel("Appointment Confirmed");
        title.setFont(AppTheme.FONT_H2);
        title.setForeground(AppTheme.TEXT);

        JLabel subtitle = new JLabel(
                "Your appointment has been added to the queue."
        );
        subtitle.setFont(AppTheme.FONT_BODY);
        subtitle.setForeground(AppTheme.TEXT_SECONDARY);

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        header.add(title);
        header.add(Box.createVerticalStrut(7));
        header.add(subtitle);

        root.add(header, BorderLayout.NORTH);

        JPanel details = new JPanel();
        details.setBackground(AppTheme.BACKGROUND);
        details.setBorder(new EmptyBorder(20, 20, 20, 20));
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

        addDetail(
                details,
                "Appointment ID",
                String.valueOf(appointmentId)
        );

        addDetail(
                details,
                "Queue Position",
                queuePosition > 0
                        ? String.valueOf(queuePosition)
                        : "Assigned"
        );

        addDetail(
                details,
                "Emergency Request",
                emergencyRequested
                        ? "Pending doctor review"
                        : "No"
        );

        if (emergencyRequested) {
            JLabel note = new JLabel(
                    "<html><b>Important:</b> Your emergency request does "
                            + "not automatically change your queue position. "
                            + "The doctor will review it.</html>"
            );
            note.setFont(AppTheme.FONT_SMALL);
            note.setForeground(AppTheme.WARNING);

            details.add(Box.createVerticalStrut(10));
            details.add(note);
        }

        root.add(details, BorderLayout.CENTER);

        JPanel buttons = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0)
        );
        buttons.setOpaque(false);

        JButton historyButton =
                AppTheme.secondaryButton("View Appointments");

        historyButton.addActionListener(e -> {
            dialog.dispose();
            openAppointments();
        });

        JButton doneButton =
                AppTheme.primaryButton("Done");

        doneButton.addActionListener(e -> dialog.dispose());

        buttons.add(historyButton);
        buttons.add(doneButton);

        root.add(buttons, BorderLayout.SOUTH);

        dialog.setContentPane(root);
        dialog.setVisible(true);
    }

    private void addDetail(
            JPanel panel,
            String label,
            String value
    ) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 35)
        );

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(AppTheme.FONT_SMALL);
        labelComponent.setForeground(AppTheme.TEXT_SECONDARY);

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(AppTheme.FONT_BODY_BOLD);
        valueComponent.setForeground(AppTheme.TEXT);

        row.add(labelComponent, BorderLayout.WEST);
        row.add(valueComponent, BorderLayout.EAST);

        panel.add(row);
        panel.add(Box.createVerticalStrut(7));
    }

    // =========================================================
    // NAVIGATION
    // =========================================================

    private void openDashboard() {
        dispose();
        new PatientDashboard(user).setVisible(true);
    }

    private void openAppointments() {
        dispose();
        new AppointmentHistoryFrame(user).setVisible(true);
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }

    // =========================================================
    // STATUS / ERRORS
    // =========================================================

    private void clearStatus() {
        statusLabel.setText(" ");
    }

    private void showStatus(String message, Color color) {
        statusLabel.setText(
                "<html>" + escapeHtml(message) + "</html>"
        );
        statusLabel.setForeground(color);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Booking Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\n", "<br>");
    }

    // =========================================================
    // DOCTOR COMBO ITEM
    // =========================================================

    private static class DoctorItem {

        private final Doctor doctor;

        DoctorItem(Doctor doctor) {
            this.doctor = doctor;
        }

        Doctor getDoctor() {
            return doctor;
        }

        @Override
        public String toString() {
            return doctor.getFullName()
                    + "  •  "
                    + doctor.getDepartment()
                    + "  •  "
                    + doctor.getAvailability();
        }
    }
}