package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    public static Customer getCustomer(String customerEmail) {
        return CustomerService.getCustomer(customerEmail);
    }
    public static void addRoom(List<IRoom> rooms) {
        for(IRoom room : rooms) {
            ReservationService.addRoom(room);
        }
    }
    public static Collection<IRoom> getAllRooms() {
        return ReservationService.getAllRooms();
    }
    public static Collection<Customer> getAllCustomer() {
        return CustomerService.getAllCustomer();
    }
    public static void displayAllReservation() {
        ReservationService.printAllReservation();
    }
}
