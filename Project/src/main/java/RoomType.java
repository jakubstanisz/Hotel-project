public enum RoomType {
    SINGLE(1, 150.0),
    DOUBLE(2, 250.0),
    TRIPLE(3, 340.0),
    QUADRA(4, 420.0),
    PENTA(5, 500.0);

    private final int maxCapacity;
    private final double roomPrice;
    RoomType(int maxCapacity, double roomPrice) {
        this.maxCapacity = maxCapacity;
        this.roomPrice = roomPrice;
    }
    public int getMaxCapacity() {
        return maxCapacity;
    }
    public double getRoomPrice() {
        return roomPrice;
    }
}
