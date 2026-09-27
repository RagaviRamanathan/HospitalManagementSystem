CREATE DATABASE hospital_management;

USE hospital_management;


-- PATIENTS TABLE


CREATE TABLE patients (
    patient_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10),
    phone VARCHAR(15)
);


-- DOCTORS TABLE


CREATE TABLE doctors (
    doctor_id INT PRIMARY KEY AUTO_INCREMENT,
    doctor_name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100),
    phone VARCHAR(15)
);


-- APPOINTMENTS TABLE


CREATE TABLE appointments (
    appointment_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    status VARCHAR(20) DEFAULT 'Booked',

    FOREIGN KEY (patient_id)
        REFERENCES patients(patient_id),

    FOREIGN KEY (doctor_id)
        REFERENCES doctors(doctor_id)
);


-- PRESCRIPTIONS TABLE


CREATE TABLE prescriptions (
    prescription_id INT PRIMARY KEY AUTO_INCREMENT,
    appointment_id INT NOT NULL,
    medicine VARCHAR(200),
    dosage VARCHAR(100),
    instructions VARCHAR(300),

    FOREIGN KEY (appointment_id)
        REFERENCES appointments(appointment_id)
);


-- SAMPLE PATIENT DATA


INSERT INTO patients
(patient_name, age, gender, phone)
VALUES
('Ravi', 35, 'Male', '9876543210'),
('Priya', 28, 'Female', '9876501234'),
('Kumar', 45, 'Male', '9876512345');

-- SAMPLE DOCTOR DATA


INSERT INTO doctors
(doctor_name, specialization, phone)
VALUES
('Dr. Kumar', 'Cardiologist', '9876111111'),
('Dr. Meena', 'Dermatologist', '9876222222'),
('Dr. Arun', 'General Physician', '9876333333');

-- Check tables
SELECT * FROM patients;
SELECT * FROM doctors;
SELECT * FROM appointments;
SELECT * FROM prescriptions;