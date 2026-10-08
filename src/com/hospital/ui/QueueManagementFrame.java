package com.hospital.ui;

import com.hospital.model.User;
import com.hospital.service.DoctorService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueueManagementFrame extends JFrame {

    private final User user;
    private final DoctorService doctorService;

    private JTable queueTable;
    private DefaultTableModel tableModel;

    private JLabel queueCountLabel;
    private JLabel currentPatientLabel;
    private JLabel statusLabel;

    public QueueManagementFrame(User user) {

        this.user = user;
        this.doctorService = new DoctorService();

        setTitle("Queue Management - Hospital Appointment System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1150, 720);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(950, 600));

        buildUI();
        loadQueue();
    }

    // =============================================================
    // UI
    // =============================================================

    private void buildUI() {

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(245, 247, 251));

        // ---------------------------------------------------------
        // HEADER
        // ---------------------------------------------------------

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppTheme.NAVY);
        header.setBorder(
                new EmptyBorder(20, 28, 20, 28)
        );

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title = new JLabel(
                "Queue Management"
        );

        title.setForeground(Color.WHITE);
        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        JLabel subtitle = new JLabel(
                "Manage today's patient queue and consultations"
        );

        subtitle.setForeground(
                new Color(205, 215, 230)
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        titlePanel.add(title);
        titlePanel.add(
                Box.createVerticalStrut(5)
        );
        titlePanel.add(subtitle);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        JButton backButton =
                createSecondaryButton(
                        "← Back to Dashboard"
                );

        backButton.addActionListener(
                e -> dispose()
        );

        header.add(
                backButton,
                BorderLayout.EAST
        );

        root.add(
                header,
                BorderLayout.NORTH
        );

        // ---------------------------------------------------------
        // CONTENT
        // ---------------------------------------------------------

        JPanel content = new JPanel(
                new BorderLayout(0, 18)
        );

        content.setBackground(
                new Color(245, 247, 251)
        );

        content.setBorder(
                new EmptyBorder(
                        22, 28, 25, 28
                )
        );

        // ---------------------------------------------------------
        // SUMMARY CARDS
        // ---------------------------------------------------------

        JPanel cards = new JPanel(
                new GridLayout(
                        1,
                        2,
                        16,
                        0
                )
        );

        cards.setOpaque(false);

        // Queue count

        JPanel queueCard = createCard();

        queueCard.setLayout(
                new BorderLayout()
        );

        JLabel queueTitle =
                new JLabel("Patients Waiting");

        queueTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        queueTitle.setForeground(
                new Color(
                        105,
                        115,
                        130
                )
        );

        queueCountLabel =
                new JLabel("0");

        queueCountLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        30
                )
        );

        queueCountLabel.setForeground(
                AppTheme.NAVY
        );

        queueCard.add(
                queueTitle,
                BorderLayout.NORTH
        );

        queueCard.add(
                queueCountLabel,
                BorderLayout.CENTER
        );

        // Current patient

        JPanel currentCard = createCard();

        currentCard.setLayout(
                new BorderLayout()
        );

        JLabel currentTitle =
                new JLabel("Current Patient");

        currentTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        currentTitle.setForeground(
                new Color(
                        105,
                        115,
                        130
                )
        );

        currentPatientLabel =
                new JLabel(
                        "No patient currently called"
                );

        currentPatientLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        currentPatientLabel.setForeground(
                AppTheme.NAVY
        );

        currentCard.add(
                currentTitle,
                BorderLayout.NORTH
        );

        currentCard.add(
                currentPatientLabel,
                BorderLayout.CENTER
        );

        cards.add(queueCard);
        cards.add(currentCard);

        content.add(
                cards,
                BorderLayout.NORTH
        );

        // ---------------------------------------------------------
        // QUEUE TABLE
        // ---------------------------------------------------------

        JPanel tableCard = createCard();

        tableCard.setLayout(
                new BorderLayout(
                        0,
                        12
                )
        );

        JLabel tableTitle =
                new JLabel("Today's Queue");

        tableTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        19
                )
        );

        tableTitle.setForeground(
                AppTheme.NAVY
        );

        JLabel tableHint =
                new JLabel(
                        "Select a patient to start a consultation."
                );

        tableHint.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        tableHint.setForeground(
                new Color(
                        105,
                        115,
                        130
                )
        );

        JPanel tableHeader =
                new JPanel(
                        new BorderLayout()
                );

        tableHeader.setOpaque(false);

        tableHeader.add(
                tableTitle,
                BorderLayout.NORTH
        );

        tableHeader.add(
                tableHint,
                BorderLayout.SOUTH
        );

        tableCard.add(
                tableHeader,
                BorderLayout.NORTH
        );

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "Position",
                                "Patient / Queue Details",
                                "Status"
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

        queueTable =
                new JTable(tableModel);

        queueTable.setRowHeight(44);

        queueTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        queueTable.setForeground(
                new Color(
                        40,
                        50,
                        65
                )
        );

        queueTable.setBackground(
                Color.WHITE
        );

        queueTable.setGridColor(
                new Color(
                        225,
                        230,
                        238
                )
        );

        queueTable.setSelectionBackground(
                new Color(
                        220,
                        232,
                        248
                )
        );

        queueTable.setSelectionForeground(
                AppTheme.NAVY
        );

        queueTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        queueTable.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                13
                        )
                );

        queueTable.getTableHeader()
                .setForeground(
                        AppTheme.NAVY
                );

        queueTable.getTableHeader()
                .setBackground(
                        new Color(
                                240,
                                244,
                                249
                        )
                );

        queueTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                40
                        )
                );

        queueTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(100);

        queueTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(650);

        queueTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(180);

        JScrollPane scrollPane =
                new JScrollPane(
                        queueTable
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                225,
                                230,
                                238
                        )
                )
        );

        tableCard.add(
                scrollPane,
                BorderLayout.CENTER
        );

        content.add(
                tableCard,
                BorderLayout.CENTER
        );

        // ---------------------------------------------------------
        // BOTTOM ACTION BAR
        // ---------------------------------------------------------

        JPanel bottom =
                new JPanel(
                        new BorderLayout()
                );

        bottom.setOpaque(false);

        statusLabel =
                new JLabel(
                        "Select a patient to begin"
                );

        statusLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        statusLabel.setForeground(
                new Color(
                        105,
                        115,
                        130
                )
        );

        JPanel actions =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        actions.setOpaque(false);

        JButton refreshButton =
                createSecondaryButton(
                        "↻ Refresh"
                );

        refreshButton.addActionListener(
                e -> loadQueue()
        );

        JButton consultationButton =
                createPrimaryButton(
                        "Start Consultation"
                );

        consultationButton.addActionListener(
                e -> openSelectedConsultation()
        );

        JButton callNextButton =
                createPrimaryButton(
                        "Call Next Patient"
                );

        callNextButton.addActionListener(
                e -> callNextPatient()
        );

        actions.add(refreshButton);
        actions.add(consultationButton);
        actions.add(callNextButton);

        bottom.add(
                statusLabel,
                BorderLayout.WEST
        );

        bottom.add(
                actions,
                BorderLayout.EAST
        );

        content.add(
                bottom,
                BorderLayout.SOUTH
        );

        root.add(
                content,
                BorderLayout.CENTER
        );

        setContentPane(root);
    }

    // =============================================================
    // LOAD QUEUE
    // =============================================================

    private void loadQueue() {

        tableModel.setRowCount(0);

        try {

            int doctorId =
                    user.getUserId();

            List<String> queue =
                    doctorService.getTodayQueue(
                            doctorId
                    );

            if (queue == null ||
                    queue.isEmpty()) {

                queueCountLabel.setText("0");

                currentPatientLabel.setText(
                        "No patients waiting"
                );

                statusLabel.setText(
                        "Queue is currently empty"
                );

                return;
            }

            queueCountLabel.setText(
                    String.valueOf(
                            queue.size()
                    )
            );

            int position = 1;

            for (String patient : queue) {

                if (patient == null ||
                        patient.trim().isEmpty()) {

                    patient =
                            "Unknown patient";
                }

                tableModel.addRow(
                        new Object[]{
                                position,
                                patient,
                                "WAITING"
                        }
                );

                position++;
            }

            currentPatientLabel.setText(
                    "Ready for next patient"
            );

            statusLabel.setText(
                    "Today's queue loaded successfully"
            );

        } catch (Exception ex) {

            queueCountLabel.setText("0");

            currentPatientLabel.setText(
                    "Unable to load"
            );

            statusLabel.setText(
                    "Queue loading failed"
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load today's queue.\n\n"
                            + ex.getMessage(),
                    "Queue Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =============================================================
    // CALL NEXT PATIENT
    // =============================================================

    private void callNextPatient() {

        try {

            int doctorId =
                    user.getUserId();

            String patient =
                    doctorService.callNextPatient(
                            doctorId
                    );

            if (patient == null ||
                    patient.trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "There are no waiting patients "
                                + "in the queue.",
                        "Queue Empty",
                        JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

            currentPatientLabel.setText(
                    patient
            );

            statusLabel.setText(
                    "Next patient has been called"
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Next patient:\n\n"
                            + patient,
                    "Patient Called",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadQueue();

            currentPatientLabel.setText(
                    patient
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to call the next patient.\n\n"
                            + ex.getMessage(),
                    "Queue Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =============================================================
    // START CONSULTATION
    // =============================================================

    private void openSelectedConsultation() {

        int selectedRow =
                queueTable.getSelectedRow();

        if (selectedRow < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient "
                            + "from the queue first.",
                    "No Patient Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String queueText =
                String.valueOf(
                        queueTable.getValueAt(
                                selectedRow,
                                1
                        )
                );

        int appointmentId =
                extractAppointmentId(
                        queueText
                );

        if (appointmentId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "The selected queue entry does not "
                            + "contain an appointment ID.\n\n"
                            + "This queue entry is:\n"
                            + queueText
                            + "\n\n"
                            + "The consultation screen is ready, "
                            + "but the current DoctorService queue "
                            + "text does not expose the appointment ID.",
                    "Appointment Information Missing",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        String patientName =
                extractPatientName(
                        queueText
                );

        ConsultationFrame consultationFrame =
                new ConsultationFrame(
                        appointmentId,
                        user.getUserId(),
                        patientName,"See appointment details"
                );

        consultationFrame.setVisible(true);
    }

    // =============================================================
    // APPOINTMENT ID EXTRACTION
    // =============================================================

    private int extractAppointmentId(
            String text
    ) {

        if (text == null) {
            return -1;
        }

        String[] patterns = {

                "(?i)appointment\\s*(?:id)?\\s*[:#-]?\\s*(\\d+)",

                "(?i)appointment\\s*#\\s*(\\d+)",

                "(?i)appt\\s*(?:id)?\\s*[:#-]?\\s*(\\d+)"

        };

        for (String expression : patterns) {

            Pattern pattern =
                    Pattern.compile(
                            expression
                    );

            Matcher matcher =
                    pattern.matcher(text);

            if (matcher.find()) {

                try {

                    return Integer.parseInt(
                            matcher.group(1)
                    );

                } catch (
                        NumberFormatException ignored
                ) {
                }
            }
        }

        return -1;
    }

    // =============================================================
    // PATIENT NAME
    // =============================================================

    private String extractPatientName(
            String text
    ) {

        if (text == null ||
                text.trim().isEmpty()) {

            return "Patient";
        }

        String cleaned =
                text.trim();

        cleaned =
                cleaned.replaceAll(
                        "(?i)appointment\\s*(?:id)?\\s*[:#-]?\\s*\\d+",
                        ""
                );

        cleaned =
                cleaned.replaceAll(
                        "(?i)queue\\s*(?:id)?\\s*[:#-]?\\s*\\d+",
                        ""
                );

        cleaned =
                cleaned.replaceAll(
                        "(?i)patient\\s*[:#-]?\\s*",
                        ""
                );

        cleaned =
                cleaned.trim();

        if (cleaned.isEmpty()) {
            return "Patient";
        }

        return cleaned;
    }

    // =============================================================
    // CARD
    // =============================================================

    private JPanel createCard() {

        JPanel panel =
                new JPanel();

        panel.setBackground(
                Color.WHITE
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        225,
                                        230,
                                        238
                                )
                        ),
                        new EmptyBorder(
                                18,
                                20,
                                18,
                                20
                        )
                )
        );

        return panel;
    }

    // =============================================================
    // PRIMARY BUTTON
    // =============================================================

    private JButton createPrimaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                AppTheme.NAVY
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setBorder(
                new EmptyBorder(
                        11,
                        18,
                        11,
                        18
                )
        );

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =============================================================
    // SECONDARY BUTTON
    // =============================================================

    private JButton createSecondaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(
                AppTheme.NAVY
        );

        button.setBackground(
                Color.WHITE
        );

        button.setFocusPainted(false);
        button.setOpaque(true);

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        210,
                                        218,
                                        230
                                )
                        ),
                        new EmptyBorder(
                                10,
                                16,
                                10,
                                16
                        )
                )
        );

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }
}