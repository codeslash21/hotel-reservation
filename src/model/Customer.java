package model;
import java.util.regex.Pattern;

/**
 * This class create a customer with customer details - firstName, lastName, email
 */
public class Customer {
    protected String firstName;
    protected String lastName;
    protected String email;
    private String emailRegex = "^(.+)@(.+).(.+)$";
    private Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String firstName, String lastName, String email) {
        if(!pattern.matcher(email).matches())
            throw new IllegalArgumentException("Invalid email address. Please give valid email");
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer Name: "+firstName+" "+lastName+", Email: "+email;
    }
}
