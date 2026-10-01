package hospital.dao;

import hospital.db.DBConnection;
import hospital.models.MedicalRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** MedicalRecordDAO - handles all database operations for medical records */
public class MedicalRecordDAO {

    private Connection conn;

    public MedicalRecordDAO() { this.conn = DBConnection.getConnection(); }

    public boolean addRecord(MedicalRecord r) {
        String sql = "INSERT INTO medical_records (patient_id, doctor_id, diagnosis, prescription, visit_date, notes) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt   (1, r.getPatientId());
            stmt.setInt   (2, r.getDoctorId());
            stmt.setString(3, r.getDiagnosis());
            stmt.setString(4, r.getPrescription());
            stmt.setString(5, r.getVisitDate());
            stmt.setString(6, r.getNotes());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean updateRecord(MedicalRecord r) {
        String sql = "UPDATE medical_records SET patient_id=?, doctor_id=?, diagnosis=?, prescription=?, visit_date=?, notes=? WHERE record_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt   (1, r.getPatientId());
            stmt.setInt   (2, r.getDoctorId());
            stmt.setString(3, r.getDiagnosis());
            stmt.setString(4, r.getPrescription());
            stmt.setString(5, r.getVisitDate());
            stmt.setString(6, r.getNotes());
            stmt.setInt   (7, r.getRecordId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean deleteRecord(int id) {
        String sql = "DELETE FROM medical_records WHERE record_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public List<MedicalRecord> getAllRecords() {
        List<MedicalRecord> list = new ArrayList<>();
        String sql = "SELECT * FROM medical_records ORDER BY visit_date DESC";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    public List<MedicalRecord> getRecordsByPatient(int patientId) {
        List<MedicalRecord> list = new ArrayList<>();
        String sql = "SELECT * FROM medical_records WHERE patient_id=? ORDER BY visit_date DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    private MedicalRecord mapRow(ResultSet rs) throws SQLException {
        return new MedicalRecord(
            rs.getInt   ("record_id"),
            rs.getInt   ("patient_id"),
            rs.getInt   ("doctor_id"),
            rs.getString("diagnosis"),
            rs.getString("prescription"),
            rs.getString("visit_date"),
            rs.getString("notes")
        );
    }
}
