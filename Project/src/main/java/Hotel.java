import java.util.List;
import java.util.stream.Collectors;

public class Hotel {
    private String hotelName;
    private RoomRepository roomRepository;

    public Hotel(String hotelName, RoomRepository roomRepository) {
        this.hotelName = hotelName;
        this.roomRepository = roomRepository;
    }

    public void addRoom(Room room) {
        roomRepository.saveRoom(room);
    }

    public List<Room> displayAvailableRooms() {
        return roomRepository.getAllRooms().stream()
                .filter(Room::isAvailable)
                .collect(Collectors.toList());
    }

    public Room findRoom(int roomNumber) {
        return roomRepository.getAllRooms().stream()
                .filter(room -> room.getNumber() == roomNumber)
                .findFirst()
                .orElse(null);
    }

    public void updateRoomAvailability(int roomNumber, boolean isAvailable) {
        roomRepository.updateRoomAvailability(roomNumber, isAvailable);
    }
}