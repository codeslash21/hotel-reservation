package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private static final Map<String, Customer> customerMap = new HashMap<>();

    /**
     * This method will add a new customer to record.
     * @param email
     * @param firstName
     * @param lastName
     */
    public static void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        customerMap.put(email, customer);
    }

    /**
     * This method will return customer which is associated with the given email
     * @param email
     * @return Customer object
     * @see model.Customer
     */
    public static Customer getCustomer(String email) {
        Customer customer = customerMap.getOrDefault(email, null);
        if(customer==null) {
            throw new IllegalArgumentException("Email does not exist!");
        }
        return customer;
    }

    /**
     * This method will return all the registered customer
     * @return list of all customers
     */
    public static Collection<Customer> getAllCustomer() {
        return customerMap.values();
    }
}
