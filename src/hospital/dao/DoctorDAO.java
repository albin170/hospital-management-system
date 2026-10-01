package hospital.dao;

import hospital.db.DBConnection;
import hospital.models.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** DoctorDAO - handles all database operations for doctors */
public class DoctorDAO {

    private Connection conn;

    public DoctorDAO() { this.conn = DBConnection.getConnection(); }

    public boolean addDoctor(Doctor d) {
        String sql = "INSERT INTO doctors (name, specialization, phone, email, salary) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getName());
            stmt.setString(2, d.getSpecialization());
            stmt.setString(3, d.getPhone());
            stmt.setString(4, d.getEmail());
            stmt.setDouble(5, d.getSalary());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean updateDoctor(Doctor d) {
        String sql = "UPDATE doctors SET name=?, specialization=?, phone=?, email=?, salary=? WHERE doctor_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, d.getName());
            stmt.setString(2, d.getSpecialization());
            stmt.setString(3, d.getPhone());
            stmt.setString(4, d.getEmail());
            stmt.setDouble(5, d.getSalary());
            stmt.setInt   (6, d.getDoctorId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean deleteDoctor(int doctorId) {
        String sql = "DELETE FROM doctors WHERE doctor_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors ORDER BY doctor_id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    public List<Doctor> searchByName(String name) {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors WHERE name LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    public Doctor getDoctorById(int id) {
        String sql = "SELECT * FROM doctors WHERE doctor_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return null;
    }

    private Doctor mapRow(ResultSet rs) throws SQLException {
        return new Doctor(
            rs.getInt   ("doctor_id"),
            rs.getString("name"),
            rs.getString("specialization"),
            rs.getString("phone"),
            rs.getString("email"),
            rs.getDouble("salary")
        );
    }
}
