import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    // URL: jdbc:postgresql://[host]:[port]/[nazwa_bazy]
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/hotel";
    private static final String USER = "TWOJA_NAZWA"; // nazwa uzytkownika z whoami
    private static final String PASSWORD = ""; //moje haslo

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Hurra! Połączono z bazą danych PostgreSQL!");
            }
        } catch (SQLException e) {
            System.out.println("Błąd połączenia: " + e.getMessage());
        }
    }
}