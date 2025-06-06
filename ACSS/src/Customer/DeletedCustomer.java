package Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author YOON
 */
public class DeletedCustomer extends BaseCustomer {
    public DeletedCustomer(String customerId, String name, String email, String password) {
        super(
                customerId,
                name,
                email,
                password);
    }

    // Implementation of the abstract method from BaseCustomer class
    @Override
    public String getUserType() {
        return "Deleted Customer";
    }
    
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    
}
