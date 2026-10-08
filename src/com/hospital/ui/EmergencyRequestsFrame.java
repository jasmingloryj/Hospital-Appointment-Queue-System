package com.hospital.ui;

import com.hospital.model.User;
import com.hospital.service.DoctorService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmergencyRequestsFrame extends JFrame {

    private final User user;
    private final DoctorService doctorService;

    private DefaultListModel<String> requestModel;
    private JList<String> requestList;
    private JLabel countLabel;
    private JLabel statusLabel;

    public EmergencyRequestsFrame(User user) {
        this.user = user;
        this.doctorService = new DoctorService();

        setTitle("Emergency Requests - Hospital Appointment System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 680);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 580));

        buildUI();
        loadRequests();
    }

    private void buildUI() {

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(245, 247, 251));

        // =========================================================
        // HEADER
        // =========================================================

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppTheme.NAVY);
        header.setBorder(new EmptyBorder(22, 28, 22, 28));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(
                titlePanel,
                BoxLayout.Y_AXIS
        ));

        JLabel title = new JLabel("Emergency Requests");
        title.setForeground(Color.WHITE);
        title.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                28
        ));

        JLabel subtitle = new JLabel(
                "Review and manage emergency priority requests"
        );
        subtitle.setForeground(new Color(205, 215, 230));
        subtitle.setFont(new Font(
                "SansSerif",
                Font.PLAIN,
                14
        ));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        JButton backButton = createSecondaryButton(
                "← Back to Dashboard"
        );

        backButton.addActionListener(e -> dispose());

        header.add(backButton, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);

        // =========================================================
        // CONTENT
        // =========================================================

        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBackground(new Color(245, 247, 251));
        content.setBorder(new EmptyBorder(
                24, 28, 24, 28
        ));

        // =========================================================
        // SUMMARY
        // =========================================================

        JPanel summary = createCard();
        summary.setLayout(new BorderLayout());

        JPanel summaryLeft = new JPanel();
        summaryLeft.setOpaque(false);
        summaryLeft.setLayout(new BoxLayout(
                summaryLeft,
                BoxLayout.Y_AXIS
        ));

        JLabel summaryTitle = new JLabel(
                "Pending Emergency Requests"
        );
        summaryTitle.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                18
        ));
        summaryTitle.setForeground(AppTheme.NAVY);

        JLabel summaryText = new JLabel(
                "Requests require doctor review before priority is changed."
        );
        summaryText.setFont(new Font(
                "SansSerif",
                Font.PLAIN,
                13
        ));
        summaryText.setForeground(
                new Color(105, 115, 130)
        );

        summaryLeft.add(summaryTitle);
        summaryLeft.add(Box.createVerticalStrut(5));
        summaryLeft.add(summaryText);

        countLabel = new JLabel("0");
        countLabel.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                30
        ));
        countLabel.setForeground(AppTheme.NAVY);

        summary.add(summaryLeft, BorderLayout.WEST);
        summary.add(countLabel, BorderLayout.EAST);

        content.add(summary, BorderLayout.NORTH);

        // =========================================================
        // REQUEST LIST
        // =========================================================

        JPanel listCard = createCard();
        listCard.setLayout(new BorderLayout(0, 12));

        JLabel listTitle = new JLabel(
                "Requests Awaiting Review"
        );
        listTitle.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                18
        ));
        listTitle.setForeground(AppTheme.NAVY);

        listCard.add(listTitle, BorderLayout.NORTH);

        requestModel = new DefaultListModel<>();

        requestList = new JList<>(requestModel);
        requestList.setFont(new Font(
                "SansSerif",
                Font.PLAIN,
                15
        ));
        requestList.setBackground(Color.WHITE);
        requestList.setForeground(
                new Color(35, 45, 60)
        );
        requestList.setSelectionBackground(
                new Color(222, 232, 247)
        );
        requestList.setSelectionForeground(
                AppTheme.NAVY
        );
        requestList.setFixedCellHeight(48);
        requestList.setBorder(
                new EmptyBorder(5, 8, 5, 8)
        );

        JScrollPane scrollPane = new JScrollPane(
                requestList
        );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 230, 238)
                )
        );

        listCard.add(scrollPane, BorderLayout.CENTER);

        content.add(listCard, BorderLayout.CENTER);

        // =========================================================
        // ACTION BAR
        // =========================================================

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font(
                "SansSerif",
                Font.PLAIN,
                13
        ));
        statusLabel.setForeground(
                new Color(105, 115, 130)
        );

        JPanel actions = new JPanel(new FlowLayout(
                FlowLayout.RIGHT,
                9,
                0
        ));
        actions.setOpaque(false);

        JButton refreshButton = createSecondaryButton(
                "↻ Refresh"
        );

        refreshButton.addActionListener(
                e -> loadRequests()
        );

        JButton keepButton = createSecondaryButton(
                "Keep Position"
        );

        keepButton.addActionListener(
                e -> keepSelected()
        );

        JButton rejectButton = createDangerButton(
                "Reject"
        );

        rejectButton.addActionListener(
                e -> rejectSelected()
        );

        JButton approveButton = createPrimaryButton(
                "Approve Emergency"
        );

        approveButton.addActionListener(
                e -> approveSelected()
        );

        actions.add(refreshButton);
        actions.add(keepButton);
        actions.add(rejectButton);
        actions.add(approveButton);

        bottom.add(statusLabel, BorderLayout.WEST);
        bottom.add(actions, BorderLayout.EAST);

        content.add(bottom, BorderLayout.SOUTH);

        root.add(content, BorderLayout.CENTER);

        setContentPane(root);
    }

    // =============================================================
    // LOAD REQUESTS
    // =============================================================

    private void loadRequests() {

        requestModel.clear();

        try {

            int doctorId = user.getUserId();

            List<String> requests =
                    doctorService.getPendingEmergencies(
                            doctorId
                    );

            if (requests == null || requests.isEmpty()) {

                countLabel.setText("0");
                statusLabel.setText(
                        "No pending emergency requests"
                );

                requestModel.addElement(
                        "No emergency requests are currently waiting for review."
                );

                return;
            }

            countLabel.setText(
                    String.valueOf(requests.size())
            );

            for (String request : requests) {

                if (request == null ||
                        request.trim().isEmpty()) {

                    request = "Emergency request";
                }

                requestModel.addElement(request);
            }

            statusLabel.setText(
                    requests.size()
                            + " emergency request(s) awaiting review"
            );

        } catch (Exception ex) {

            countLabel.setText("0");

            statusLabel.setText(
                    "Unable to load emergency requests"
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load emergency requests.\n\n"
                            + ex.getMessage(),
                    "Emergency Request Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =============================================================
    // APPROVE
    // =============================================================

    private void approveSelected() {

        Integer queueId = getSelectedQueueId();

        if (queueId == null) {
            return;
        }

        try {

            boolean success =
                    doctorService.reviewEmergency(
                            queueId,
                            true
                    );

            if (success) {

                statusLabel.setText(
                        "Emergency request approved"
                );

                loadRequests();

            } else {

                showOperationFailed();
            }

        } catch (Exception ex) {

            showServiceError(ex);
        }
    }

    // =============================================================
    // REJECT
    // =============================================================

    private void rejectSelected() {

        Integer queueId = getSelectedQueueId();

        if (queueId == null) {
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Reject this emergency request?",
                "Confirm Rejection",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            boolean success =
                    doctorService.reviewEmergency(
                            queueId,
                            false
                    );

            if (success) {

                statusLabel.setText(
                        "Emergency request rejected"
                );

                loadRequests();

            } else {

                showOperationFailed();
            }

        } catch (Exception ex) {

            showServiceError(ex);
        }
    }

    // =============================================================
    // KEEP POSITION
    // =============================================================

    private void keepSelected() {

        Integer queueId = getSelectedQueueId();

        if (queueId == null) {
            return;
        }

        try {

            boolean success =
                    doctorService.keepEmergencyPosition(
                            queueId
                    );

            if (success) {

                statusLabel.setText(
                        "Patient kept in current queue position"
                );

                loadRequests();

            } else {

                showOperationFailed();
            }

        } catch (Exception ex) {

            showServiceError(ex);
        }
    }

    // =============================================================
    // GET QUEUE ID
    // =============================================================

    private Integer getSelectedQueueId() {

        String selected =
                requestList.getSelectedValue();

        if (selected == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an emergency request first.",
                    "No Request Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return null;
        }

        /*
         * The service returns List<String>.
         * Extract the queue ID from the returned text.
         *
         * This supports formats such as:
         * "Queue ID: 12 - Patient: Aarav Sharma"
         * "Queue 12 - Aarav Sharma"
         * "12 - Aarav Sharma"
         */

        Pattern pattern = Pattern.compile(
                "(?i)(?:queue\\s*(?:id)?\\s*[:#-]?\\s*)?(\\d+)"
        );

        Matcher matcher =
                pattern.matcher(selected);

        if (matcher.find()) {

            try {

                return Integer.parseInt(
                        matcher.group(1)
                );

            } catch (NumberFormatException ignored) {
                // handled below
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "The selected emergency request does not contain "
                        + "a readable queue ID.\n\n"
                        + "Request:\n"
                        + selected,
                "Queue ID Not Found",
                JOptionPane.WARNING_MESSAGE
        );

        return null;
    }

    // =============================================================
    // CARD
    // =============================================================

    private JPanel createCard() {

        JPanel panel = new JPanel();

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 230, 238)
                        ),
                        new EmptyBorder(
                                18, 20, 18, 20
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

        JButton button = new JButton(text);

        button.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                13
        ));

        button.setForeground(Color.WHITE);
        button.setBackground(AppTheme.NAVY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setBorder(
                new EmptyBorder(
                        11, 18, 11, 18
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

        JButton button = new JButton(text);

        button.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                13
        ));

        button.setForeground(AppTheme.NAVY);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(210, 218, 230)
                        ),
                        new EmptyBorder(
                                10, 15, 10, 15
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

    // =============================================================
    // DANGER BUTTON
    // =============================================================

    private JButton createDangerButton(
            String text
    ) {

        JButton button = new JButton(text);

        button.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                13
        ));

        button.setForeground(
                new Color(180, 45, 55)
        );

        button.setBackground(Color.WHITE);

        button.setFocusPainted(false);
        button.setOpaque(true);

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 190, 195)
                        ),
                        new EmptyBorder(
                                10, 15, 10, 15
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

    // =============================================================
    // ERROR HELPERS
    // =============================================================

    private void showOperationFailed() {

        JOptionPane.showMessageDialog(
                this,
                "The operation could not be completed.",
                "Operation Failed",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showServiceError(
            Exception ex
    ) {

        JOptionPane.showMessageDialog(
                this,
                "Unable to complete the operation.\n\n"
                        + ex.getMessage(),
                "Emergency Request Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}