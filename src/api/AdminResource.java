package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

/**
 * This class works as an API. This class provides methods related to admin menu options
 */
public class AdminResource {
    public static Customer getCustomer(String customerEmail) {
        return CustomerService.getCustomer(customerEmail);
    }

    /**
     * This method add a new room to record
     * @param rooms
     */
    public static void addRoom(List<IRoom> rooms) {
        for(IRoom room : rooms) {
            ReservationService.addRoom(room);
        }
    }

    /**
     * This method return all the rooms in the record
     * @return list of all rooms
     */
    public static Collection<IRoom> getAllRooms() {
        return ReservationService.getAllRooms();
    }

    /**
     * This method returns all the registered customers
     * @return list of all customers
     */
    public static Collection<Customer> getAllCustomer() {
        return CustomerService.getAllCustomer();
    }

    /**
     * This method shows all the reservations in the record
     */
    public static void displayAllReservation() {
        ReservationService.printAllReservation();
    }
}
