package hospital.dao;

import hospital.db.DBConnection;
import hospital.models.Nurse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** NurseDAO - handles all database operations for nurses */
public class NurseDAO {

    private Connection conn;

    public NurseDAO() { this.conn = DBConnection.getConnection(); }

    public boolean addNurse(Nurse n) {
        String sql = "INSERT INTO nurses (name, phone, ward_assigned, salary) VALUES (?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, n.getName());
            stmt.setString(2, n.getPhone());
            stmt.setString(3, n.getWardAssigned());
            stmt.setDouble(4, n.getSalary());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean updateNurse(Nurse n) {
        String sql = "UPDATE nurses SET name=?, phone=?, ward_assigned=?, salary=? WHERE nurse_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, n.getName());
            stmt.setString(2, n.getPhone());
            stmt.setString(3, n.getWardAssigned());
            stmt.setDouble(4, n.getSalary());
            stmt.setInt   (5, n.getNurseId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean deleteNurse(int nurseId) {
        String sql = "DELETE FROM nurses WHERE nurse_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nurseId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public List<Nurse> getAllNurses() {
        List<Nurse> list = new ArrayList<>();
        String sql = "SELECT * FROM nurses ORDER BY nurse_id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    public List<Nurse> searchByName(String name) {
        List<Nurse> list = new ArrayList<>();
        String sql = "SELECT * FROM nurses WHERE name LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    private Nurse mapRow(ResultSet rs) throws SQLException {
        return new Nurse(
            rs.getInt   ("nurse_id"),
            rs.getString("name"),
            rs.getString("phone"),
            rs.getString("ward_assigned"),
            rs.getDouble("salary")
        );
    }
}
