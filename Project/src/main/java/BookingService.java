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
}