-- ============================================================
--  Hospital Management System - Database Setup Script
--  Run this in MySQL Workbench or command line:
--  mysql -u root -p < hospital_db.sql
-- ============================================================

CREATE DATABASE IF NOT EXISTS hospital_db;
USE hospital_db;

-- 1. Patients Table
CREATE TABLE IF NOT EXISTS patients (
    patient_id   INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    age          INT,
    gender       VARCHAR(10),
    blood_group  VARCHAR(5),
    phone        VARCHAR(15),
    address      VARCHAR(200),
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Doctors Table
CREATE TABLE IF NOT EXISTS doctors (
    doctor_id    INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    specialization VARCHAR(100),
    phone        VARCHAR(15),
    email        VARCHAR(100),
    salary       DOUBLE,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Nurses Table
CREATE TABLE IF NOT EXISTS nurses (
    nurse_id     INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    phone        VARCHAR(15),
    ward_assigned VARCHAR(50),
    salary       DOUBLE,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Employees Table
CREATE TABLE IF NOT EXISTS employees (
    employee_id  INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    role         VARCHAR(50),
    phone        VARCHAR(15),
    email        VARCHAR(100),
    salary       DOUBLE,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. Rooms / Wards Table
CREATE TABLE IF NOT EXISTS rooms (
    room_id      INT AUTO_INCREMENT PRIMARY KEY,
    room_number  VARCHAR(20) NOT NULL,
    room_type    VARCHAR(50),
    ward         VARCHAR(50),
    status       VARCHAR(20) DEFAULT 'Available',
    price_per_day DOUBLE
);

-- 6. Appointments Table
CREATE TABLE IF NOT EXISTS appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id     INT,
    doctor_id      INT,
    appointment_date DATE,
    appointment_time VARCHAR(20),
    status         VARCHAR(20) DEFAULT 'Scheduled',
    notes          TEXT,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id)  REFERENCES doctors(doctor_id)  ON DELETE CASCADE
);

-- 7. Medicines Table
CREATE TABLE IF NOT EXISTS medicines (
    medicine_id   INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    category      VARCHAR(50),
    quantity      INT DEFAULT 0,
    price         DOUBLE,
    expiry_date   DATE,
    manufacturer  VARCHAR(100)
);

-- 8. Medical Records Table
CREATE TABLE IF NOT EXISTS medical_records (
    record_id    INT AUTO_INCREMENT PRIMARY KEY,
    patient_id   INT,
    doctor_id    INT,
    diagnosis    TEXT,
    prescription TEXT,
    visit_date   DATE,
    notes        TEXT,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id)  REFERENCES doctors(doctor_id)  ON DELETE CASCADE
);

-- 9. Billing Table
CREATE TABLE IF NOT EXISTS billing (
    bill_id        INT AUTO_INCREMENT PRIMARY KEY,
    patient_id     INT,
    total_amount   DOUBLE,
    paid_amount    DOUBLE DEFAULT 0,
    payment_status VARCHAR(20) DEFAULT 'Unpaid',
    billing_date   DATE,
    description    TEXT,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE
);

-- 10. Users Table (Login)
CREATE TABLE IF NOT EXISTS users (
    user_id   INT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50) UNIQUE NOT NULL,
    password  VARCHAR(100) NOT NULL,
    role      VARCHAR(30) DEFAULT 'staff'
);

-- Default admin: username=admin, password=admin123
INSERT INTO users (username, password, role)
VALUES ('admin', 'admin123', 'admin')
ON DUPLICATE KEY UPDATE username = username;
