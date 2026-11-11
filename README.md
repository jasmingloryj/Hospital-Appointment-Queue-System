# Hospital Appointment and Queue Management System
##Project Overview

A Java Swing desktop application integrated with MySQL that simplifies hospital appointment scheduling, patient queue management, and consultation handling.

## Technologies Used

- Java
- Java Swing
- MySQL
- JDBC
- MySQL Connector/J

## Main Features

- Patient registration and authentication
- Role-based access for Patients, Doctors, and Admins
- Appointment booking, rescheduling, and cancellation
- Automatic queue generation
- Priority-based queue management
- Emergency request submission and doctor approval
- Doctor consultation management
- Prescription management
- Patient medical records
- Doctor and department administration

## Database

Database name: "hospital_queue_v2"

The database contains eight tables:

1. "users"
2. "patients"
3. "doctors"
4. "departments"
5. "appointments"
6. "queue"
7. "consultations"
8. "prescription_items"

The SQL export is available in the "database" folder.

## Project Structure

- "SRC/" – Java source code
- "LIB/" – Required libraries
- "database/" – SQL database export
- "screenshots/" – Application screenshots

## Setup Instructions

1. Install Java JDK and MySQL.
2. Import "database/database_final.sql" into MySQL.
3. Configure the database connection in the project.
4. Ensure MySQL Connector/J is available in the "LIB" folder.
5. Open the project in a Java IDE.
6. Run the application's main class.

## User Roles

- Patient: Book appointments, view queue status, and submit emergency requests.
- Doctor: Manage queues, review emergency requests, conduct consultations, and prescribe medicines.
- Admin: Manage doctors and departments.

## Project Purpose

Developed as an academic project to demonstrate Object-Oriented Programming, Java GUI development, database connectivity, and transaction-based data management.
