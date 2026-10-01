package hospital.dao;

import hospital.db.DBConnection;
import hospital.models.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** RoomDAO - handles all database operations for rooms/wards */
public class RoomDAO {

    private Connection conn;

    public RoomDAO() { this.conn = DBConnection.getConnection(); }

    public boolean addRoom(Room r) {
        String sql = "INSERT INTO rooms (room_number, room_type, ward, status, price_per_day) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, r.getRoomNumber());
            stmt.setString(2, r.getRoomType());
            stmt.setString(3, r.getWard());
            stmt.setString(4, r.getStatus());
            stmt.setDouble(5, r.getPricePerDay());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean updateRoom(Room r) {
        String sql = "UPDATE rooms SET room_number=?, room_type=?, ward=?, status=?, price_per_day=? WHERE room_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, r.getRoomNumber());
            stmt.setString(2, r.getRoomType());
            stmt.setString(3, r.getWard());
            stmt.setString(4, r.getStatus());
            stmt.setDouble(5, r.getPricePerDay());
            stmt.setInt   (6, r.getRoomId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public boolean deleteRoom(int roomId) {
        String sql = "DELETE FROM rooms WHERE room_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) { System.out.println(e.getMessage()); return false; }
    }

    public List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms ORDER BY room_id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    public List<Room> getAvailableRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE status='Available'";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return list;
    }

    private Room mapRow(ResultSet rs) throws SQLException {
        return new Room(
            rs.getInt   ("room_id"),
            rs.getString("room_number"),
            rs.getString("room_type"),
            rs.getString("ward"),
            rs.getString("status"),
            rs.getDouble("price_per_day")
        );
    }
}
