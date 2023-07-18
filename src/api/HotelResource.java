package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

/**
 * This class works as an API to the UI. This class provides methods for main menu options
 */
public class HotelResource {
    /**
     * This method returns Customer associated with the given email address
     * @param email
     * @return customer associated with the given email or null if no Customer exists
     */
    public static Customer getCustomer(String email) {
        return CustomerService.getCustomer(email);
    }

    /**
     * This methods create a Customer with the given parameters
     * @param email
     * @param firstName
     * @param lastName
     */
    public static void createACustomer(String email, String firstName, String lastName) {
        CustomerService.addCustomer(email, firstName, lastName);
    }

    /**
     * This methods returns room associated with the given roomNumber
     * @param roomNumber
     * @return Room if there exist a room with the given roomNumber or else null
     */
    public static IRoom getRoom(String roomNumber) {
        return ReservationService.getARoom(roomNumber);
    }

    /**
     * This method create a Reservation and store in the records
     * @param customerEmail
     * @param room
     * @param checkInDate
     * @param checkOutDate
     * @return Reservation
     */
    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = CustomerService.getCustomer(customerEmail);
        return ReservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    /**
     * This method returns all the reservation associated with the given custoemr email
     * @param customerEmail
     * @return list of all reservations if exists or else null
     */
    public static Collection<Reservation> getCustomerReservation(String customerEmail) {
        Customer customer = CustomerService.getCustomer(customerEmail);
        return ReservationService.getCustomersReservation(customer);
    }

    /**
     * This method to find room for the given check-in and check-out date
     * @param checkInDate
     * @param checkOutDate
     * @return
     */
    public static Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return ReservationService.findRooms(checkInDate, checkOutDate);
    }
}
