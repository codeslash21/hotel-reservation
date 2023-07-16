package model;

/**
 * This class extends Room class. This class about creating a room that is free of cost.
 * @see model.Room
 */
public class FreeRoom extends Room{
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, (double) 0, roomType);
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + " Room Type: " + roomType + " Room Price: $0";
    }
}
