package hospital.dao;

import hospital.db.DBConnection;
import hospital.models.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** AppointmentDAO - handles all database operations for appointments */
public class AppointmentDAO {

    private Connection conn;

    public AppointmentDAO() { this.conn = DBConnection.getConnection(); }

    public boolean addAppointment(Appointment a) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, notes) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt   (1, a.getPatientId());
            stmt.setInt   (2, a.getDoctorId());
            stmt.setString(3, a.getAppointmentDate());
            stmt.setString(4, a.getAppointmentTime());
            stmt.setString(5, a.getStatus());
            stmt.setString(6, a.getNotes());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean updateAppointment(Appointment a) {
        String sql = "UPDATE appointments SET patient_id=?, doctor_id=?, appointment_date=?, appointment_time=?, status=?, notes=? WHERE appointment_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt   (1, a.getPatientId());
            stmt.setInt   (2, a.getDoctorId());
            stmt.setString(3, a.getAppointmentDate());
            stmt.setString(4, a.getAppointmentTime());
            stmt.setString(5, a.getStatus());
            stmt.setString(6, a.getNotes());
            stmt.setInt   (7, a.getAppointmentId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean deleteAppointment(int id) {
        String sql = "DELETE FROM appointments WHERE appointment_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments ORDER BY appointment_date, appointment_time";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    private Appointment mapRow(ResultSet rs) throws SQLException {
        return new Appointment(
            rs.getInt   ("appointment_id"),
            rs.getInt   ("patient_id"),
            rs.getInt   ("doctor_id"),
            rs.getString("appointment_date"),
            rs.getString("appointment_time"),
            rs.getString("status"),
            rs.getString("notes")
        );
    }
}
