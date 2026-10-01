package hospital.dao;

import hospital.db.DBConnection;
import hospital.models.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** BillingDAO - handles all database operations for billing */
public class BillingDAO {

    private Connection conn;

    public BillingDAO() { this.conn = DBConnection.getConnection(); }

    public boolean addBill(Bill b) {
        String sql = "INSERT INTO billing (patient_id, total_amount, paid_amount, payment_status, billing_date, description) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt   (1, b.getPatientId());
            stmt.setDouble(2, b.getTotalAmount());
            stmt.setDouble(3, b.getPaidAmount());
            stmt.setString(4, b.getPaymentStatus());
            stmt.setString(5, b.getBillingDate());
            stmt.setString(6, b.getDescription());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean updateBill(Bill b) {
        String sql = "UPDATE billing SET patient_id=?, total_amount=?, paid_amount=?, payment_status=?, billing_date=?, description=? WHERE bill_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt   (1, b.getPatientId());
            stmt.setDouble(2, b.getTotalAmount());
            stmt.setDouble(3, b.getPaidAmount());
            stmt.setString(4, b.getPaymentStatus());
            stmt.setString(5, b.getBillingDate());
            stmt.setString(6, b.getDescription());
            stmt.setInt   (7, b.getBillId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean deleteBill(int id) {
        String sql = "DELETE FROM billing WHERE bill_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public List<Bill> getAllBills() {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT * FROM billing ORDER BY billing_date DESC";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    public List<Bill> getBillsByPatient(int patientId) {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT * FROM billing WHERE patient_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    private Bill mapRow(ResultSet rs) throws SQLException {
        return new Bill(
            rs.getInt   ("bill_id"),
            rs.getInt   ("patient_id"),
            rs.getDouble("total_amount"),
            rs.getDouble("paid_amount"),
            rs.getString("payment_status"),
            rs.getString("billing_date"),
            rs.getString("description")
        );
    }
}
