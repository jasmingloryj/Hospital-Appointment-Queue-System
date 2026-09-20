package com.hospital.ui;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import com.hospital.model.User;

/**
 * Displays the appointment history of the logged-in patient.
 */
public class AppointmentHistoryFrame extends JFrame {

    private User patient;

    private JTable appointmentTable;

    private DefaultTableModel tableModel;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/hospital_queue_db"
                    + "?useSSL=false&serverTimezone=Asia/Kolkata"
                    + "&allowPublicKeyRetrieval=true";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD = "Jasmin@2008";

    public AppointmentHistoryFrame(User patient) {

        this.patient = patient;

        setTitle(
                "Hospital Appointment System - Appointment History"
        );

        setSize(750, 450);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        createUI();

        loadAppointments();
    }

    /**
     * Creates the appointment history interface.
     */
    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "My Appointment History",
                        JLabel.CENTER
                );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        /*
         * Table columns.
         */
        String[] columns = {
                "Appointment ID",
                "Doctor",
                "Department",
                "Date",
                "Time",
                "Symptoms",
                "Emergency",
                "Status"
        };

        tableModel =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        appointmentTable =
                new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(appointmentTable);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JButton backButton =
                new JButton("Back to Dashboard");

        mainPanel.add(
                backButton,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        /*
         * Return to patient dashboard.
         */
        backButton.addActionListener(
                e -> openDashboard()
        );
    }

    /**
     * Loads the logged-in patient's appointments
     * from the MySQL database.
     */
    private void loadAppointments() {

        String sql =
                "SELECT a.appointment_id, "
                + "u.full_name AS doctor_name, "
                + "d.department, "
                + "a.appointment_date, "
                + "a.appointment_time, "
                + "a.symptoms, "
                + "a.emergency_flag, "
                + "a.status "
                + "FROM appointments a "
                + "JOIN doctors d "
                + "ON a.doctor_id = d.doctor_id "
                + "JOIN users u "
                + "ON d.user_id = u.user_id "
                + "WHERE a.patient_id = ? "
                + "ORDER BY a.appointment_date DESC, "
                + "a.appointment_time DESC";

        try (
            Connection connection =
                    java.sql.DriverManager.getConnection(
                            DB_URL,
                            DB_USER,
                            DB_PASSWORD
                    );

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    patient.getUserId()
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Object[] row = {
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

                            resultSet.getString(
                                    "symptoms"
                            ),

                            resultSet.getBoolean(
                                    "emergency_flag"
                            )
                                    ? "YES"
                                    : "NO",

                            resultSet.getString(
                                    "status"
                            )
                    };

                    tableModel.addRow(row);
                }
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load appointment history.\n\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Returns to the patient dashboard.
     */
    private void openDashboard() {

        PatientDashboard dashboard =
                new PatientDashboard(patient);

        dashboard.setVisible(true);

        dispose();
    }
}
