import java.sql.*;

public class GuestRepository {

    public int saveGuest(Guest guest) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            return saveGuest(conn, guest);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public int saveGuest(Connection conn, Guest guest) throws SQLException {
        String sql = "INSERT INTO guests (first_name, last_name, email) VALUES (?, ?, ?) RETURNING id";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, guest.getFirstName());
            pstmt.setString(2, guest.getLastName());
            pstmt.setString(3, guest.getEmail());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    public void printGuestSpendingReport() {
        String sql = "SELECT first_name, last_name, total_reservations, total_spent FROM v_guest_spending";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("--- RAPORT WYDATKÓW GOŚCI ---");
            System.out.printf("%-15s | %-15s | %-12s | %-12s%n", "Imię", "Nazwisko", "L. Rezerwacji", "Suma Wydatków");
            System.out.println("------------------------------------------------------------------");

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int totalRes = rs.getInt("total_reservations");
                double totalSpent = rs.getDouble("total_spent");

                System.out.printf("%-15s | %-15s | %-12d | %-12.2f%n", firstName, lastName, totalRes, totalSpent);
            }
            System.out.println("------------------------------------------------------------------");

        } catch (SQLException e) {
            System.err.println("Błąd podczas generowania raportu: " + e.getMessage());
        }
    }
}