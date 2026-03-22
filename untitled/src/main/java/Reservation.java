import java.time.LocalDate;

public class Reservation {
    private Room room;
    private Guest guest;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalCost;

    public Reservation(Room room, Guest guest, LocalDate startDate, LocalDate endDate, double totalCost) {
        this.room = room;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
    }

    public Room getRoom() {
        return room;
    }

    public Guest getGuest() {
        return guest;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getTotalCost() {
        return totalCost;
    }
}