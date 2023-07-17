package model;

/**
 * This class is to create room object. It implements IRoom
 * @see model.IRoom
 */
public class Room implements IRoom {
    protected String roomNumber;
    protected double roomPrice;
    protected RoomType roomType;
    public Room(String roomNumber, double roomPrice, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
    }
    /**
     * This method return the room number associated with the room.
     * @return roomNumber
     */
    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    /**
     * This method return the room price associated with the room.
     * @return roomPrice
     */
    @Override
    public double getRoomPrice() {
        return this.roomPrice;
    }

    /**
     * This method return the room type associated with the room.
     * @return roomType
     */
    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    /**
     * This method will return true if the room is free otherwise false.
     * @return true or false
     */
    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + " Room Type: " + roomType + " Room Price: " + roomPrice;
    }
}
