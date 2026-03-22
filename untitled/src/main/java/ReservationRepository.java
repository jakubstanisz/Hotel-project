import java.sql.*;

public class ReservationRepository {

    public void saveReservation(Reservation reservation) {
        String sql = "INSERT INTO reservations (room_id, guest_id, start_date, end_date, total_cost) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reservation.getRoom().getId());
            pstmt.setInt(2, reservation.getGuest().getId());
            pstmt.setDate(3, Date.valueOf(reservation.getStartDate()));
            pstmt.setDate(4, Date.valueOf(reservation.getEndDate()));
            pstmt.setDouble(5, reservation.getTotalCost());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}