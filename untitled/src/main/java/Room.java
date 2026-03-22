public class Room {
    private int id;
    private int number;
    private RoomType type;
    private boolean isAvailable;

    public Room(int number, RoomType type, boolean isAvailable) {
        this.number = number;
        this.type = type;
        this.isAvailable = isAvailable;
    }

    public Room(int id, int number, RoomType type, boolean isAvailable) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public RoomType getRoomType() {
        return type;
    }

    public double roomPrice() {
        return type.getRoomPrice();
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
}