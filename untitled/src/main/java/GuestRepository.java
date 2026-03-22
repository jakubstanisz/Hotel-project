import java.sql.*;

public class GuestRepository {

    public int saveGuest(Guest guest) {
        String sql = "INSERT INTO guests (first_name, last_name, email) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, guest.getFirstName());
            pstmt.setString(2, guest.getLastName());
            pstmt.setString(3, guest.getEmail());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }
}