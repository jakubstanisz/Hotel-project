import java.time.temporal.ChronoUnit;

public class BookingService {
    public double calculateRoomPrice(Room room, Reservation reservation) {
        long numberOfNights = ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getEndDate());

        if (numberOfNights <= 0) {
            return 0.0;
        }
        if (room.isAvailable()) {
            double totalCost = numberOfNights * room.roomPrice();
            return totalCost;
        }
        return 0.0;
    }

    public boolean makeReservationWithTransaction(Guest guest, Room room, java.time.LocalDate startDate, java.time.LocalDate endDate, double totalCost, GuestRepository guestRepo, ReservationRepository resRepo, RoomRepository roomRepo) {
        try (java.sql.Connection conn = DatabaseConnector.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int guestId = guestRepo.saveGuest(conn, guest);
                if (guestId != -1) {
                    guest.setId(guestId);
                    Reservation reservation = new Reservation(room, guest, startDate, endDate, totalCost);
                    resRepo.saveReservation(conn, reservation);
                    roomRepo.updateRoomAvailability(conn, room.getNumber(), false);
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            } catch (java.sql.SQLException e) {
                conn.rollback();
                System.err.println("Błąd transakcji, wykonano rollback: " + e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Błąd połączenia: " + e.getMessage());
            return false;
        }
    }
}