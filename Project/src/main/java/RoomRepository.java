import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int number = rs.getInt("room_number");
                String typeStr = rs.getString("room_type");
                double price = rs.getDouble("price_per_night");
                boolean available = rs.getBoolean("is_available");
                RoomType type = RoomType.valueOf(typeStr.toUpperCase());

                Room room = new Room(id, number, type, available);

                rooms.add(room);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return rooms;
    }

    public void saveRoom(Room room) {
        String sql = "INSERT INTO rooms (room_number, room_type, price_per_night, is_available) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, room.getNumber());
            pstmt.setString(2, room.getRoomType().toString());
            pstmt.setDouble(3, room.roomPrice());
            pstmt.setBoolean(4, room.isAvailable());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateRoomAvailability(int roomNumber, boolean isAvailable) {
        String sql = "UPDATE rooms SET is_available = ? WHERE room_number = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, isAvailable);
            pstmt.setInt(2, roomNumber);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}