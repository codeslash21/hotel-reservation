package model;

/**
 * IRoom is an interface
 */
public interface IRoom {
    public String getRoomNumber();
    public double getRoomPrice();
    public RoomType getRoomType();
    public boolean isFree();
}
