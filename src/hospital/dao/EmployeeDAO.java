package hospital.dao;

import hospital.db.DBConnection;
import hospital.models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** EmployeeDAO - handles all database operations for employees */
public class EmployeeDAO {

    private Connection conn;

    public EmployeeDAO() { this.conn = DBConnection.getConnection(); }

    public boolean addEmployee(Employee e) {
        String sql = "INSERT INTO employees (name, role, phone, email, salary) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getName());
            stmt.setString(2, e.getRole());
            stmt.setString(3, e.getPhone());
            stmt.setString(4, e.getEmail());
            stmt.setDouble(5, e.getSalary());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) { System.out.println(ex.getMessage()); return false; }
    }

    public boolean updateEmployee(Employee e) {
        String sql = "UPDATE employees SET name=?, role=?, phone=?, email=?, salary=? WHERE employee_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getName());
            stmt.setString(2, e.getRole());
            stmt.setString(3, e.getPhone());
            stmt.setString(4, e.getEmail());
            stmt.setDouble(5, e.getSalary());
            stmt.setInt   (6, e.getEmployeeId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) { System.out.println(ex.getMessage()); return false; }
    }

    public boolean deleteEmployee(int employeeId) {
        String sql = "DELETE FROM employees WHERE employee_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) { System.out.println(ex.getMessage()); return false; }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY employee_id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    public List<Employee> searchByName(String name) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE name LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        return new Employee(
            rs.getInt   ("employee_id"),
            rs.getString("name"),
            rs.getString("role"),
            rs.getString("phone"),
            rs.getString("email"),
            rs.getDouble("salary")
        );
    }
}
