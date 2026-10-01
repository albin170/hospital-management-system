package hospital.dao;

import hospital.db.DBConnection;
import hospital.models.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PatientDAO - handles all database operations for patients.
 * CRUD: Create, Read, Update, Delete + Search
 */
public class PatientDAO {

    private Connection conn;

    public PatientDAO() {
        this.conn = DBConnection.getConnection();
    }

    // ---- Add a new patient ----
    public boolean addPatient(Patient p) {
        String sql = "INSERT INTO patients (name, age, gender, blood_group, phone, address) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getName());
            stmt.setInt   (2, p.getAge());
            stmt.setString(3, p.getGender());
            stmt.setString(4, p.getBloodGroup());
            stmt.setString(5, p.getPhone());
            stmt.setString(6, p.getAddress());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding patient: " + e.getMessage());
            return false;
        }
    }

    // ---- Update an existing patient ----
    public boolean updatePatient(Patient p) {
        String sql = "UPDATE patients SET name=?, age=?, gender=?, blood_group=?, phone=?, address=? WHERE patient_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getName());
            stmt.setInt   (2, p.getAge());
            stmt.setString(3, p.getGender());
            stmt.setString(4, p.getBloodGroup());
            stmt.setString(5, p.getPhone());
            stmt.setString(6, p.getAddress());
            stmt.setInt   (7, p.getPatientId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating patient: " + e.getMessage());
            return false;
        }
    }

    // ---- Delete a patient by ID ----
    public boolean deletePatient(int patientId) {
        String sql = "DELETE FROM patients WHERE patient_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error deleting patient: " + e.getMessage());
            return false;
        }
    }

    // ---- Get all patients ----
    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY patient_id";
        try (Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching patients: " + e.getMessage());
        }
        return list;
    }

    // ---- Search patients by name ----
    public List<Patient> searchByName(String name) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE name LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error searching patient: " + e.getMessage());
        }
        return list;
    }

    // ---- Get a single patient by ID ----
    public Patient getPatientById(int id) {
        String sql = "SELECT * FROM patients WHERE patient_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.out.println("Error fetching patient: " + e.getMessage());
        }
        return null;
    }

    // Helper: map a ResultSet row to a Patient object
    private Patient mapRow(ResultSet rs) throws SQLException {
        return new Patient(
            rs.getInt   ("patient_id"),
            rs.getString("name"),
            rs.getInt   ("age"),
            rs.getString("gender"),
            rs.getString("blood_group"),
            rs.getString("phone"),
            rs.getString("address")
        );
    }
}
