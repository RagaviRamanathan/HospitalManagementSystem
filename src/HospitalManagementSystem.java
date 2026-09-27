import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.Scanner;

public class HospitalManagementSystem {

    // MySQL connection details
    static String url = "jdbc:mysql://localhost:3306/hospital_management";
    static String username = "root";
    static String password = "root";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {

            // Create database connection
            Connection con = DriverManager.getConnection(
                    url,
                    username,
                    password
            );

            System.out.println("======================================");
            System.out.println("  HOSPITAL MANAGEMENT SYSTEM");
            System.out.println("======================================");
            System.out.println("Database Connected Successfully!");

            int choice;

            do {

                System.out.println();
                System.out.println("------------- MENU -------------");
                System.out.println("1. Register Patient");
                System.out.println("2. View Patients");
                System.out.println("3. Update Patient");
                System.out.println("4. Delete Patient");
                System.out.println("5. Add Doctor");
                System.out.println("6. View Doctors");
                System.out.println("7. Update Doctor");
                System.out.println("8. Delete Doctor");
                System.out.println("9. Book Appointment");
                System.out.println("10. View Appointments");
                System.out.println("11. Cancel Appointment");
                System.out.println("12. Add Prescription");
                System.out.println("13. View Prescriptions");
                System.out.println("14. View Appointment Details");
                System.out.println("15. Exit");
                System.out.println("--------------------------------");

                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        registerPatient(con);
                        break;

                    case 2:
                        viewPatients(con);
                        break;

                    case 3:
                        updatePatient(con);
                        break;

                    case 4:
                        deletePatient(con);
                        break;

                    case 5:
                        addDoctor(con);
                        break;

                    case 6:
                        viewDoctors(con);
                        break;

                    case 7:
                        updateDoctor(con);
                        break;

                    case 8:
                        deleteDoctor(con);
                        break;

                    case 9:
                        bookAppointment(con);
                        break;

                    case 10:
                        viewAppointments(con);
                        break;

                    case 11:
                        cancelAppointment(con);
                        break;

                    case 12:
                        addPrescription(con);
                        break;

                    case 13:
                        viewPrescriptions(con);
                        break;

                    case 14:
                        viewAppointmentDetails(con);
                        break;

                    case 15:
                        System.out.println("Thank you!");
                        break;

                    default:
                        System.out.println("Invalid choice!");

                }

            } while (choice != 15);

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    
    // 1. REGISTER PATIENT
    

    static void registerPatient(Connection con) {

        try {

            sc.nextLine();

            System.out.print("Enter patient name: ");
            String name = sc.nextLine();

            System.out.print("Enter age: ");
            int age = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter gender: ");
            String gender = sc.nextLine();

            System.out.print("Enter phone: ");
            String phone = sc.nextLine();

            String sql =
                    "INSERT INTO patients " +
                    "(patient_name, age, gender, phone) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, phone);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Patient registered successfully!");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    
    // 2. VIEW PATIENTS
    

    static void viewPatients(Connection con) {

        try {

            String sql = "SELECT * FROM patients";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println();
            System.out.println("------------- PATIENTS -------------");

            while (rs.next()) {

                System.out.println(
                        "ID: " + rs.getInt("patient_id")
                        + " | Name: " + rs.getString("patient_name")
                        + " | Age: " + rs.getInt("age")
                        + " | Gender: " + rs.getString("gender")
                        + " | Phone: " + rs.getString("phone")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    
    // 3. UPDATE PATIENT
    

    static void updatePatient(Connection con) {

        try {

            System.out.print("Enter patient ID: ");
            int id = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter new patient name: ");
            String name = sc.nextLine();

            System.out.print("Enter new age: ");
            int age = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter new gender: ");
            String gender = sc.nextLine();

            System.out.print("Enter new phone: ");
            String phone = sc.nextLine();

            String sql =
                    "UPDATE patients SET " +
                    "patient_name=?, age=?, gender=?, phone=? " +
                    "WHERE patient_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, phone);
            ps.setInt(5, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Patient updated successfully!");
            } else {
                System.out.println("Patient not found!");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    
    // 4. DELETE PATIENT
    

    static void deletePatient(Connection con) {

        try {

            System.out.print("Enter patient ID: ");
            int id = sc.nextInt();

            String sql =
                    "DELETE FROM patients WHERE patient_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Patient deleted successfully!");
            } else {
                System.out.println("Patient not found!");
            }

        } catch (Exception e) {

            System.out.println(
                    "Cannot delete patient because appointments may exist."
            );
        }
    }

    
    // 5. ADD DOCTOR


    static void addDoctor(Connection con) {

        try {

            sc.nextLine();

            System.out.print("Enter doctor name: ");
            String name = sc.nextLine();

            System.out.print("Enter specialization: ");
            String specialization = sc.nextLine();

            System.out.print("Enter phone: ");
            String phone = sc.nextLine();

            String sql =
                    "INSERT INTO doctors " +
                    "(doctor_name, specialization, phone) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, specialization);
            ps.setString(3, phone);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Doctor added successfully!");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    
    // 6. VIEW DOCTORS

    static void viewDoctors(Connection con) {

        try {

            String sql = "SELECT * FROM doctors";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println();
            System.out.println("------------- DOCTORS -------------");

            while (rs.next()) {

                System.out.println(
                        "ID: " + rs.getInt("doctor_id")
                        + " | Name: " + rs.getString("doctor_name")
                        + " | Specialization: "
                        + rs.getString("specialization")
                        + " | Phone: "
                        + rs.getString("phone")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    
    // 7. UPDATE DOCTOR
    

    static void updateDoctor(Connection con) {

        try {

            System.out.print("Enter doctor ID: ");
            int id = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter new doctor name: ");
            String name = sc.nextLine();

            System.out.print("Enter new specialization: ");
            String specialization = sc.nextLine();

            System.out.print("Enter new phone: ");
            String phone = sc.nextLine();

            String sql =
                    "UPDATE doctors SET " +
                    "doctor_name=?, specialization=?, phone=? " +
                    "WHERE doctor_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, specialization);
            ps.setString(3, phone);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Doctor updated successfully!");
            } else {
                System.out.println("Doctor not found!");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    
    // 8. DELETE DOCTOR
   

    static void deleteDoctor(Connection con) {

        try {

            System.out.print("Enter doctor ID: ");
            int id = sc.nextInt();

            String sql =
                    "DELETE FROM doctors WHERE doctor_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Doctor deleted successfully!");
            } else {
                System.out.println("Doctor not found!");
            }

        } catch (Exception e) {

            System.out.println(
                    "Cannot delete doctor because appointments may exist."
            );
        }
    }

    
    // 9. BOOK APPOINTMENT
    

    static void bookAppointment(Connection con) {

        try {

            System.out.print("Enter patient ID: ");
            int patientId = sc.nextInt();

            System.out.print("Enter doctor ID: ");
            int doctorId = sc.nextInt();

            sc.nextLine();

            System.out.print(
                    "Enter appointment date (YYYY-MM-DD): "
            );

            String date = sc.nextLine();

            System.out.print(
                    "Enter appointment time (HH:MM:SS): "
            );

            String time = sc.nextLine();

            String sql =
                    "INSERT INTO appointments " +
                    "(patient_id, doctor_id, appointment_date, " +
                    "appointment_time, status) " +
                    "VALUES (?, ?, ?, ?, 'Booked')";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setDate(3, Date.valueOf(date));
            ps.setTime(4, Time.valueOf(time));

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println(
                        "Appointment booked successfully!"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    
    // 10. VIEW APPOINTMENTS
    

    static void viewAppointments(Connection con) {

        try {

            String sql =
                    "SELECT a.appointment_id, " +
                    "p.patient_name, " +
                    "d.doctor_name, " +
                    "d.specialization, " +
                    "a.appointment_date, " +
                    "a.appointment_time, " +
                    "a.status " +
                    "FROM appointments a " +
                    "INNER JOIN patients p " +
                    "ON a.patient_id = p.patient_id " +
                    "INNER JOIN doctors d " +
                    "ON a.doctor_id = d.doctor_id";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println();
            System.out.println("------------- APPOINTMENTS -------------");

            while (rs.next()) {

                System.out.println(
                        "Appointment ID: "
                        + rs.getInt("appointment_id")
                );

                System.out.println(
                        "Patient: "
                        + rs.getString("patient_name")
                );

                System.out.println(
                        "Doctor: "
                        + rs.getString("doctor_name")
                );

                System.out.println(
                        "Specialization: "
                        + rs.getString("specialization")
                );

                System.out.println(
                        "Date: "
                        + rs.getDate("appointment_date")
                );

                System.out.println(
                        "Time: "
                        + rs.getTime("appointment_time")
                );

                System.out.println(
                        "Status: "
                        + rs.getString("status")
                );

                System.out.println("---------------------------------------");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // 11. CANCEL APPOINTMENT

    static void cancelAppointment(Connection con) {

        try {

            System.out.print("Enter appointment ID: ");
            int id = sc.nextInt();

            String sql =
                    "UPDATE appointments " +
                    "SET status='Cancelled' " +
                    "WHERE appointment_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println(
                        "Appointment cancelled successfully!"
                );
            } else {
                System.out.println(
                        "Appointment not found!"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // 12. ADD PRESCRIPTION
    

    static void addPrescription(Connection con) {

        try {

            System.out.print("Enter appointment ID: ");
            int appointmentId = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter medicine: ");
            String medicine = sc.nextLine();

            System.out.print("Enter dosage: ");
            String dosage = sc.nextLine();

            System.out.print("Enter instructions: ");
            String instructions = sc.nextLine();

            String sql =
                    "INSERT INTO prescriptions " +
                    "(appointment_id, medicine, dosage, instructions) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, appointmentId);
            ps.setString(2, medicine);
            ps.setString(3, dosage);
            ps.setString(4, instructions);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println(
                        "Prescription added successfully!"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // 13. VIEW PRESCRIPTIONS

    static void viewPrescriptions(Connection con) {

        try {

            String sql =
                    "SELECT pr.prescription_id, " +
                    "p.patient_name, " +
                    "d.doctor_name, " +
                    "pr.medicine, " +
                    "pr.dosage, " +
                    "pr.instructions " +
                    "FROM prescriptions pr " +
                    "INNER JOIN appointments a " +
                    "ON pr.appointment_id = a.appointment_id " +
                    "INNER JOIN patients p " +
                    "ON a.patient_id = p.patient_id " +
                    "INNER JOIN doctors d " +
                    "ON a.doctor_id = d.doctor_id";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println();
            System.out.println("------------- PRESCRIPTIONS -------------");

            while (rs.next()) {

                System.out.println(
                        "Prescription ID: "
                        + rs.getInt("prescription_id")
                );

                System.out.println(
                        "Patient: "
                        + rs.getString("patient_name")
                );

                System.out.println(
                        "Doctor: "
                        + rs.getString("doctor_name")
                );

                System.out.println(
                        "Medicine: "
                        + rs.getString("medicine")
                );

                System.out.println(
                        "Dosage: "
                        + rs.getString("dosage")
                );

                System.out.println(
                        "Instructions: "
                        + rs.getString("instructions")
                );

                System.out.println("------------------------------------------");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // 14. APPOINTMENT DETAILS USING JOIN

    static void viewAppointmentDetails(Connection con) {

        try {

            String sql =
                    "SELECT " +
                    "a.appointment_id, " +
                    "p.patient_id, " +
                    "p.patient_name, " +
                    "p.age, " +
                    "p.gender, " +
                    "p.phone, " +
                    "d.doctor_id, " +
                    "d.doctor_name, " +
                    "d.specialization, " +
                    "a.appointment_date, " +
                    "a.appointment_time, " +
                    "a.status " +
                    "FROM appointments a " +
                    "INNER JOIN patients p " +
                    "ON a.patient_id = p.patient_id " +
                    "INNER JOIN doctors d " +
                    "ON a.doctor_id = d.doctor_id";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println();
            System.out.println(
                    "========= COMPLETE APPOINTMENT DETAILS ========="
            );

            while (rs.next()) {

                System.out.println(
                        "Appointment ID: "
                        + rs.getInt("appointment_id")
                );

                System.out.println(
                        "Patient ID: "
                        + rs.getInt("patient_id")
                );

                System.out.println(
                        "Patient Name: "
                        + rs.getString("patient_name")
                );

                System.out.println(
                        "Age: "
                        + rs.getInt("age")
                );

                System.out.println(
                        "Gender: "
                        + rs.getString("gender")
                );

                System.out.println(
                        "Patient Phone: "
                        + rs.getString("phone")
                );

                System.out.println(
                        "Doctor ID: "
                        + rs.getInt("doctor_id")
                );

                System.out.println(
                        "Doctor Name: "
                        + rs.getString("doctor_name")
                );

                System.out.println(
                        "Specialization: "
                        + rs.getString("specialization")
                );

                System.out.println(
                        "Appointment Date: "
                        + rs.getDate("appointment_date")
                );

                System.out.println(
                        "Appointment Time: "
                        + rs.getTime("appointment_time")
                );

                System.out.println(
                        "Status: "
                        + rs.getString("status")
                );

                System.out.println(
                        "==============================================="
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}