package model;

import java.util.Date;

/**
 *This class is to create reservation object
 */
public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    /**
     * This method will return the room that is reserved.
     * @return Room object
     */
    public IRoom getRoom() {
        return this.room;
    }

    /**
     * This method will check the room is booked in the given time range.
     * @param checkInDate
     * @param checkOutDate
     * @return true if the room is booked in the given time range otherwise false
     */
    public boolean isPeriodReserved(Date checkInDate, Date checkOutDate) {
        if(checkOutDate.before(this.checkInDate)||(checkInDate.after(this.checkOutDate)))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Room: "+room.getRoomNumber()+" was booked from "+checkInDate+" to "+checkOutDate+" by "+customer.toString();
    }
}
