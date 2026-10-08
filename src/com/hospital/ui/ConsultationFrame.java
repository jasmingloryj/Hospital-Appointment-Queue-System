package com.hospital.ui;

import com.hospital.service.ConsultationService;
import com.hospital.service.PrescriptionService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultationFrame extends JFrame {

    private final int appointmentId;
    private final int doctorId;
    private final String patientName;
    private final String symptoms;

    private final ConsultationService consultationService;
    private final PrescriptionService prescriptionService;

    private JTextArea diagnosisArea;
    private JTextArea adviceArea;

    private JCheckBox followUpCheck;
    private JTextField followUpDateField;

    private JTextField medicineField;
    private JTextField dosageField;
    private JTextField frequencyField;
    private JTextField durationField;
    private JTextField instructionsField;

    private DefaultTableModel medicineModel;

    private final List<MedicineData> medicines =
            new ArrayList<>();

    public ConsultationFrame(
            int appointmentId,
            int doctorId,
            String patientName,
            String symptoms
    ) {

        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientName = patientName;
        this.symptoms = symptoms;

        consultationService = new ConsultationService();
        prescriptionService = new PrescriptionService();

        setTitle("Patient Consultation");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 760);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 650));

        buildUI();
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
                new EmptyBorder(20, 26, 20, 26)
        );

        JPanel patientInfo = new JPanel();
        patientInfo.setOpaque(false);
        patientInfo.setLayout(
                new BoxLayout(
                        patientInfo,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title = new JLabel(
                "Patient Consultation"
        );
        title.setForeground(Color.WHITE);
        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        26
                )
        );

        JLabel patient = new JLabel(
                "Patient: " + safe(patientName)
        );
        patient.setForeground(
                new Color(215, 225, 238)
        );
        patient.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );

        patientInfo.add(title);
        patientInfo.add(
                Box.createVerticalStrut(5)
        );
        patientInfo.add(patient);

        header.add(
                patientInfo,
                BorderLayout.WEST
        );

        JButton closeButton =
                createSecondaryButton("Close");

        closeButton.addActionListener(
                e -> dispose()
        );

        header.add(
                closeButton,
                BorderLayout.EAST
        );

        root.add(
                header,
                BorderLayout.NORTH
        );

        // ---------------------------------------------------------
        // MAIN
        // ---------------------------------------------------------

        JPanel main = new JPanel(
                new BorderLayout(0, 16)
        );

        main.setBackground(
                new Color(245, 247, 251)
        );

        main.setBorder(
                new EmptyBorder(
                        20, 24, 20, 24
                )
        );

        // ---------------------------------------------------------
        // PATIENT DETAILS
        // ---------------------------------------------------------

        JPanel patientCard = createCard();
        patientCard.setLayout(
                new BorderLayout()
        );

        JLabel symptomsTitle =
                new JLabel("Reported Symptoms");

        symptomsTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        symptomsTitle.setForeground(
                AppTheme.NAVY
        );

        JTextArea symptomsArea =
                new JTextArea(
                        safe(symptoms)
                );

        symptomsArea.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        symptomsArea.setForeground(
                new Color(65, 75, 90)
        );

        symptomsArea.setBackground(
                new Color(248, 250, 253)
        );

        symptomsArea.setLineWrap(true);
        symptomsArea.setWrapStyleWord(true);
        symptomsArea.setEditable(false);

        symptomsArea.setBorder(
                new EmptyBorder(
                        10, 12, 10, 12
                )
        );

        patientCard.add(
                symptomsTitle,
                BorderLayout.NORTH
        );

        patientCard.add(
                symptomsArea,
                BorderLayout.CENTER
        );

        main.add(
                patientCard,
                BorderLayout.NORTH
        );

        // ---------------------------------------------------------
        // CENTER
        // ---------------------------------------------------------

        JPanel center = new JPanel(
                new GridLayout(1, 2, 16, 0)
        );

        center.setOpaque(false);

        center.add(
                createConsultationPanel()
        );

        center.add(
                createPrescriptionPanel()
        );

        main.add(
                center,
                BorderLayout.CENTER
        );

        // ---------------------------------------------------------
        // FOOTER
        // ---------------------------------------------------------

        JPanel footer = new JPanel(
                new BorderLayout()
        );

        footer.setOpaque(false);

        JLabel info = new JLabel(
                "Complete the consultation before finishing the patient visit."
        );

        info.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        info.setForeground(
                new Color(105, 115, 130)
        );

        JButton finishButton =
                createPrimaryButton(
                        "Finish Consultation"
                );

        finishButton.addActionListener(
                e -> finishConsultation()
        );

        footer.add(
                info,
                BorderLayout.WEST
        );

        footer.add(
                finishButton,
                BorderLayout.EAST
        );

        main.add(
                footer,
                BorderLayout.SOUTH
        );

        root.add(
                main,
                BorderLayout.CENTER
        );

        setContentPane(root);
    }

    // =============================================================
    // CONSULTATION PANEL
    // =============================================================

    private JPanel createConsultationPanel() {

        JPanel card = createCard();

        card.setLayout(
                new BorderLayout(0, 12)
        );

        JLabel title =
                new JLabel("Consultation");

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        19
                )
        );

        title.setForeground(
                AppTheme.NAVY
        );

        card.add(
                title,
                BorderLayout.NORTH
        );

        JPanel fields = new JPanel();

        fields.setOpaque(false);

        fields.setLayout(
                new BoxLayout(
                        fields,
                        BoxLayout.Y_AXIS
                )
        );

        // Diagnosis

        fields.add(
                createFieldLabel("Diagnosis")
        );

        diagnosisArea =
                new JTextArea(5, 20);

        styleTextArea(
                diagnosisArea
        );

        fields.add(
                new JScrollPane(
                        diagnosisArea
                )
        );

        fields.add(
                Box.createVerticalStrut(12)
        );

        // Advice

        fields.add(
                createFieldLabel("Advice / Treatment Notes")
        );

        adviceArea =
                new JTextArea(5, 20);

        styleTextArea(
                adviceArea
        );

        fields.add(
                new JScrollPane(
                        adviceArea
                )
        );

        fields.add(
                Box.createVerticalStrut(12)
        );

        // Follow-up

        followUpCheck =
                new JCheckBox(
                        "Follow-up required"
                );

        followUpCheck.setOpaque(false);

        followUpCheck.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        followUpCheck.setForeground(
                AppTheme.NAVY
        );

        fields.add(
                followUpCheck
        );

        fields.add(
                Box.createVerticalStrut(6)
        );

        fields.add(
                createFieldLabel(
                        "Follow-up Date (YYYY-MM-DD)"
                )
        );

        followUpDateField =
                new JTextField();

        styleTextField(
                followUpDateField
        );

        followUpDateField.setEnabled(false);

        fields.add(
                followUpDateField
        );

        followUpCheck.addActionListener(
                e -> followUpDateField.setEnabled(
                        followUpCheck.isSelected()
                )
        );

        card.add(
                fields,
                BorderLayout.CENTER
        );

        return card;
    }

    // =============================================================
    // PRESCRIPTION PANEL
    // =============================================================

    private JPanel createPrescriptionPanel() {

        JPanel card = createCard();

        card.setLayout(
                new BorderLayout(0, 10)
        );

        JLabel title =
                new JLabel("Prescription");

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        19
                )
        );

        title.setForeground(
                AppTheme.NAVY
        );

        card.add(
                title,
                BorderLayout.NORTH
        );

        JPanel prescriptionFields =
                new JPanel();

        prescriptionFields.setOpaque(false);

        prescriptionFields.setLayout(
                new GridLayout(
                        5,
                        1,
                        0,
                        6
                )
        );

        medicineField =
                createInputField();

        dosageField =
                createInputField();

        frequencyField =
                createInputField();

        durationField =
                createInputField();

        instructionsField =
                createInputField();

        prescriptionFields.add(
                createLabeledField(
                        "Medicine",
                        medicineField
                )
        );

        prescriptionFields.add(
                createLabeledField(
                        "Dosage",
                        dosageField
                )
        );

        prescriptionFields.add(
                createLabeledField(
                        "Frequency",
                        frequencyField
                )
        );

        prescriptionFields.add(
                createLabeledField(
                        "Duration",
                        durationField
                )
        );

        prescriptionFields.add(
                createLabeledField(
                        "Instructions",
                        instructionsField
                )
        );

        JPanel prescriptionTop =
                new JPanel(
                        new BorderLayout(
                                0,
                                10
                        )
                );

        prescriptionTop.setOpaque(false);

        prescriptionTop.add(
                prescriptionFields,
                BorderLayout.CENTER
        );

        JButton addButton =
                createSecondaryButton(
                        "+ Add Medicine"
                );

        addButton.addActionListener(
                e -> addMedicine()
        );

        prescriptionTop.add(
                addButton,
                BorderLayout.SOUTH
        );

        card.add(
                prescriptionTop,
                BorderLayout.NORTH
        );

        // ---------------------------------------------------------
        // TABLE
        // ---------------------------------------------------------

        medicineModel =
                new DefaultTableModel(
                        new Object[]{
                                "Medicine",
                                "Dosage",
                                "Frequency",
                                "Duration"
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

        JTable table =
                new JTable(medicineModel);

        table.setRowHeight(34);

        table.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        table.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                12
                        )
                );

        JScrollPane scroll =
                new JScrollPane(table);

        scroll.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                225,
                                230,
                                238
                        )
                )
        );

        card.add(
                scroll,
                BorderLayout.CENTER
        );

        JButton removeButton =
                createSecondaryButton(
                        "Remove Selected"
                );

        removeButton.addActionListener(
                e -> removeSelectedMedicine(table)
        );

        card.add(
                removeButton,
                BorderLayout.SOUTH
        );

        return card;
    }

    // =============================================================
    // ADD MEDICINE
    // =============================================================

    private void addMedicine() {

        String medicine =
                medicineField.getText().trim();

        String dosage =
                dosageField.getText().trim();

        String frequency =
                frequencyField.getText().trim();

        String duration =
                durationField.getText().trim();

        String instructions =
                instructionsField.getText().trim();

        if (medicine.isEmpty()
                || dosage.isEmpty()
                || frequency.isEmpty()
                || duration.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Medicine, dosage, frequency and duration "
                            + "are required.",
                    "Incomplete Prescription",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        medicines.add(
                new MedicineData(
                        medicine,
                        dosage,
                        frequency,
                        duration,
                        instructions
                )
        );

        medicineModel.addRow(
                new Object[]{
                        medicine,
                        dosage,
                        frequency,
                        duration
                }
        );

        medicineField.setText("");
        dosageField.setText("");
        frequencyField.setText("");
        durationField.setText("");
        instructionsField.setText("");

        medicineField.requestFocus();
    }

    // =============================================================
    // REMOVE MEDICINE
    // =============================================================

    private void removeSelectedMedicine(
            JTable table
    ) {

        int row =
                table.getSelectedRow();

        if (row < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a medicine first.",
                    "No Medicine Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        medicines.remove(row);
        medicineModel.removeRow(row);
    }

    public int createConsultation(
        int appointmentId,
        int doctorId,
        String diagnosis,
        String advice,
        boolean followUpRequired,
        java.util.Date followUpDate
) throws SQLException {

    java.sql.Date sqlDate = null;

    if (followUpDate != null) {
        sqlDate = new java.sql.Date(
                followUpDate.getTime()
        );
    }

    return createConsultation(
            appointmentId,
            doctorId,
            diagnosis,
            advice,
            followUpRequired,
            sqlDate
    );
}
    // =============================================================
    // FINISH CONSULTATION
    // =============================================================

    private void finishConsultation() {

        String diagnosis =
                diagnosisArea.getText().trim();

        String advice =
                adviceArea.getText().trim();

        if (diagnosis.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a diagnosis.",
                    "Diagnosis Required",
                    JOptionPane.WARNING_MESSAGE
            );

            diagnosisArea.requestFocus();
            return;
        }

        boolean followUp =
                followUpCheck.isSelected();

        java.sql.Date followUpDate = null;

        if (followUp) {

            String date =
                    followUpDateField
                            .getText()
                            .trim();

            if (date.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the follow-up date.",
                        "Follow-up Date Required",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            try {

                followUpDate =
                        java.sql.Date.valueOf(date);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Use the date format YYYY-MM-DD.",
                        "Invalid Date",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }

        try {

            int consultationId =
                    consultationService.createConsultation(
                            appointmentId,
                            doctorId,
                            diagnosis,
                            advice,
                            followUp,
                            followUpDate
                    );

            for (MedicineData medicine :
                    medicines) {

                prescriptionService.addMedicine(
                        consultationId,
                        medicine.name,
                        medicine.dosage,
                        medicine.frequency,
                        medicine.duration,
                        medicine.instructions
                );
            }

            consultationService.finishConsultation(
                    appointmentId,
                    doctorId
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Consultation completed successfully.\n\n"
                            + "Patient: "
                            + patientName
                            + "\nPrescription items: "
                            + medicines.size(),
                    "Consultation Completed",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to complete the consultation.\n\n"
                            + ex.getMessage(),
                    "Consultation Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =============================================================
    // HELPERS
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
                                16,
                                18,
                                16,
                                18
                        )
                )
        );

        return panel;
    }

    private JLabel createFieldLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        label.setForeground(
                new Color(
                        70,
                        80,
                        95
                )
        );

        label.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        4,
                        0
                )
        );

        return label;
    }

    private JPanel createLabeledField(
            String labelText,
            JTextField field
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                2
                        )
                );

        panel.setOpaque(false);

        panel.add(
                createFieldLabel(labelText),
                BorderLayout.NORTH
        );

        panel.add(
                field,
                BorderLayout.CENTER
        );

        return panel;
    }

    private JTextField createInputField() {

        JTextField field =
                new JTextField();

        styleTextField(field);

        return field;
    }

    private void styleTextField(
            JTextField field
    ) {

        field.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        210,
                                        218,
                                        230
                                )
                        ),
                        new EmptyBorder(
                                7,
                                9,
                                7,
                                9
                        )
                )
        );
    }

    private void styleTextArea(
            JTextArea area
    ) {

        area.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        area.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        210,
                                        218,
                                        230
                                )
                        ),
                        new EmptyBorder(
                                8,
                                9,
                                8,
                                9
                        )
                )
        );
    }

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

        return button;
    }

    private JButton createSecondaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
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
                                9,
                                14,
                                9,
                                14
                        )
                )
        );

        return button;
    }

    private String safe(
            String value
    ) {

        if (value == null ||
                value.trim().isEmpty()) {

            return "Not available";
        }

        return value;
    }

    // =============================================================
    // MEDICINE DATA
    // =============================================================

    private static class MedicineData {

        String name;
        String dosage;
        String frequency;
        String duration;
        String instructions;

        MedicineData(
                String name,
                String dosage,
                String frequency,
                String duration,
                String instructions
        ) {

            this.name = name;
            this.dosage = dosage;
            this.frequency = frequency;
            this.duration = duration;
            this.instructions = instructions;
        }
    }
}