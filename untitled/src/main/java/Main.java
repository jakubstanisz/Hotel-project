public class Main {
    public static void main(String[] args) {
        RoomRepository roomRepo = new RoomRepository();
        GuestRepository guestRepo = new GuestRepository();
        ReservationRepository reservationRepo = new ReservationRepository();

        Hotel hotel = new Hotel("Grand Hotel", roomRepo);
        BookingService bookingService = new BookingService();

        ConsoleUI ui = new ConsoleUI(hotel, bookingService, guestRepo, reservationRepo);
        ui.start();
    }
}