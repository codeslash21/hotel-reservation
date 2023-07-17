import model.Customer;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer("firstName", "last", "abc");
        System.out.println(customer);
    }
}