package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.*;

public class ReservationService {
    private static final Map<String, IRoom> roomMap = new HashMap<>();
    private static final Map<Customer, List<Reservation>> reservationMap = new HashMap<>();
    public static void addRoom(IRoom room) {
        roomMap.put(room.getRoomNumber(), room);
    }
    public static IRoom getARoom(String roomId) {
        return roomMap.get(roomId);
    }

    /**
     * This method will reserve a room for a customer if that room is not already reserved with in tha given period
     * @param customer
     * @param room
     * @param checkInDate
     * @param checkOutDate
     * @return reservation if the reservation is successful otherwise null
     */
    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if(isRoomReserved(room, checkInDate, checkOutDate))
            return null;
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        if(getCustomersReservation(customer)==null)
            reservationMap.put(customer, new ArrayList());
        reservationMap.get(customer).add(reservation);
        return reservation;
    }

    /**
     * This method will check if the room si booked with in the given time period.
     * @param room
     * @param checkInDate
     * @param checkOutDate
     * @return true id room is booked otherwise false
     */
    protected static boolean isRoomReserved(IRoom room, Date checkInDate, Date checkOutDate) {
        Collection<IRoom> reservedRooms = getAllReservedRooms(checkInDate, checkOutDate);
        if(reservedRooms.contains(room))
            return true;
        return false;
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();
        Collection<IRoom> allRooms = getAllRooms();
        Collection<IRoom> reservedRooms = getAllReservedRooms(checkInDate, checkOutDate);
        for(IRoom room : allRooms) {
            if(!reservedRooms.contains(room)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public static Collection<IRoom> getAllRooms() {
        return new ArrayList<IRoom>(roomMap.values());
    }
    protected static Collection<IRoom> getAllReservedRooms(Date checkInDate, Date checkOutDate) {
        Set<IRoom> reservedRooms = new HashSet<>();
        for(List<Reservation> reservationList : reservationMap.values()) {
            for(Reservation reservation : reservationList) {
                if(reservation.isPeriodReserved(checkInDate, checkOutDate))
                    reservedRooms.add(reservation.getRoom());
            }
        }
        return reservedRooms;
    }

    public static Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservationMap.get(customer);
    }
    public static void printAllReservation() {
        if(reservationMap.size()==0) {
            System.out.println("There is no reservation at this moment.");
            return;
        }
        for(List<Reservation> reservationList : reservationMap.values()) {
            for(Reservation reservation : reservationList) {
                System.out.println(reservation.toString());
            }
        }
    }
}
