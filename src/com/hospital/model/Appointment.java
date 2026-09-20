package com.hospital.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a hospital appointment.
 */
public class Appointment {

    private int appointmentId;
    private int patientId;
    private int doctorId;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    private String symptoms;
    private boolean emergency;

    private String status;

    public Appointment(
            int appointmentId,
            int patientId,
            int doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            String symptoms,
            boolean emergency,
            String status) {

        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.symptoms = symptoms;
        this.emergency = emergency;
        this.status = status;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}