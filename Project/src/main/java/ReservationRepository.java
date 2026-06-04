import java.sql.*;

public class ReservationRepository {

    public void saveReservation(Reservation reservation) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            saveReservation(conn, reservation);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveReservation(Connection conn, Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations (room_id, guest_id, start_date, end_date, total_cost) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservation.getRoom().getId());
            pstmt.setInt(2, reservation.getGuest().getId());
            pstmt.setDate(3, Date.valueOf(reservation.getStartDate()));
            pstmt.setDate(4, Date.valueOf(reservation.getEndDate()));
            pstmt.setDouble(5, reservation.getTotalCost());

            pstmt.executeUpdate();
        }
    }

    public void cancelReservationTransaction(int reservationId) {
        String findRoomSql = "SELECT room_id FROM reservations WHERE id = ?";
        String deleteResSql = "DELETE FROM reservations WHERE id = ?";
        String updateRoomSql = "UPDATE rooms SET is_available = true WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement findStmt = conn.prepareStatement(findRoomSql)) {
                findStmt.setInt(1, reservationId);
                ResultSet rs = findStmt.executeQuery();
                if (rs.next()) {
                    int roomId = rs.getInt("room_id");

                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteResSql)) {
                        deleteStmt.setInt(1, reservationId);
                        deleteStmt.executeUpdate();
                    }

                    try (PreparedStatement updateStmt = conn.prepareStatement(updateRoomSql)) {
                        updateStmt.setInt(1, roomId);
                        updateStmt.executeUpdate();
                    }

                    conn.commit();
                    System.out.println("Rezerwacja o ID " + reservationId + " została anulowana.");
                } else {
                    System.out.println("Nie znaleziono rezerwacji o ID " + reservationId);
                }
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Błąd podczas anulowania rezerwacji: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}