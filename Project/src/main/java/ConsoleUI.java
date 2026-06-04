import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;

public class ConsoleUI {
    private Scanner scanner;
    private Hotel myHotel;
    private BookingService bookingService;
    private GuestRepository guestRepository;
    private ReservationRepository reservationRepository;
    private RoomRepository roomRepository;
    private boolean running;

    public ConsoleUI(Hotel hotel, BookingService bookingService, GuestRepository guestRepository, ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.scanner = new Scanner(System.in);
        this.myHotel = hotel;
        this.bookingService = bookingService;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.running = true;
    }

    public void start() {
        System.out.println("Welcome to Burro Hotel!");

        while (running) {
            System.out.println("\nWYBIERZ OPCJĘ:");
            System.out.println("1. Wyświetl dostępne pokoje");
            System.out.println("2. Dokonaj rezerwacji");
            System.out.println("3. Anuluj rezerwację");
            System.out.println("4. Wygeneruj raport finansowy gości ");
            System.out.println("5. Wyjdź");

            int choice = -1;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Nieprawidłowy znak. Proszę wpisać cyfrę.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Dostępne pokoje:");
                    for (Room room : myHotel.displayAvailableRooms()){
                        System.out.println(room.getNumber() + " " + room.getRoomType() + " " + room.roomPrice());
                    }
                    break;

                case 2:
                    System.out.println("Podaj imię gościa:");
                    String firstName = scanner.nextLine();

                    System.out.println("Podaj nazwisko gościa:");
                    String lastName = scanner.nextLine();

                    System.out.println("Podaj adres email gościa:");
                    String email = scanner.nextLine();

                    Guest guest = new Guest(firstName, lastName, email);

                    System.out.println("Podaj numer pokoju:");
                    int roomNumber = scanner.nextInt();

                    Room selectedRoom = myHotel.findRoom(roomNumber);

                    if (selectedRoom != null && selectedRoom.isAvailable()) {
                        System.out.println("Na ile nocy?");
                        int numberOfNights = scanner.nextInt();

                        LocalDate startDate = LocalDate.now();
                        LocalDate endDate = startDate.plusDays(numberOfNights);
                        double totalCost = numberOfNights * selectedRoom.roomPrice();

                        System.out.println("Przetwarzanie rezerwacji dla " + firstName + " " + lastName + "...");
                        System.out.println("Całkowity koszt to: " + totalCost);

                        boolean success = bookingService.makeReservationWithTransaction(
                            guest, selectedRoom, startDate, endDate, totalCost,
                            guestRepository, reservationRepository, roomRepository
                        );

                        if (success) {
                            selectedRoom.setAvailable(false);
                            System.out.println("Rezerwacja zapisana pomyślnie w bazie danych !");
                        } else {
                            System.out.println("Błąd zapisu rezerwacji. Wycofano zmiany.");
                        }

                    } else {
                        System.out.println("Błąd: Pokój nie istnieje lub jest zajęty!");
                    }
                    break;

                case 3:
                    System.out.println("Podaj numer ID rezerwacji do anulowania:");
                    int resId = scanner.nextInt();
                    scanner.nextLine();
                    reservationRepository.cancelReservationTransaction(resId);
                    break;

                case 4:
                    guestRepository.printGuestSpendingReport();
                    break;

                case 5:
                    System.out.println("Zamykanie systemu. Do widzenia!");
                    running = false;
                    break;

                default:
                    System.out.println("Nieprawidłowa opcja. Spróbuj ponownie.");
            }
        }

        scanner.close();
    }
}
