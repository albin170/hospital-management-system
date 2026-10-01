package hospital.dao;

import hospital.db.DBConnection;
import hospital.models.Medicine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** MedicineDAO - handles all database operations for medicines */
public class MedicineDAO {

    private Connection conn;

    public MedicineDAO() { this.conn = DBConnection.getConnection(); }

    public boolean addMedicine(Medicine m) {
        String sql = "INSERT INTO medicines (name, category, quantity, price, expiry_date, manufacturer) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getName());
            stmt.setString(2, m.getCategory());
            stmt.setInt   (3, m.getQuantity());
            stmt.setDouble(4, m.getPrice());
            stmt.setString(5, m.getExpiryDate());
            stmt.setString(6, m.getManufacturer());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean updateMedicine(Medicine m) {
        String sql = "UPDATE medicines SET name=?, category=?, quantity=?, price=?, expiry_date=?, manufacturer=? WHERE medicine_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getName());
            stmt.setString(2, m.getCategory());
            stmt.setInt   (3, m.getQuantity());
            stmt.setDouble(4, m.getPrice());
            stmt.setString(5, m.getExpiryDate());
            stmt.setString(6, m.getManufacturer());
            stmt.setInt   (7, m.getMedicineId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean deleteMedicine(int id) {
        String sql = "DELETE FROM medicines WHERE medicine_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public List<Medicine> getAllMedicines() {
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT * FROM medicines ORDER BY name";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    public List<Medicine> searchByName(String name) {
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT * FROM medicines WHERE name LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    private Medicine mapRow(ResultSet rs) throws SQLException {
        return new Medicine(
            rs.getInt   ("medicine_id"),
            rs.getString("name"),
            rs.getString("category"),
            rs.getInt   ("quantity"),
            rs.getDouble("price"),
            rs.getString("expiry_date"),
            rs.getString("manufacturer")
        );
    }
}
