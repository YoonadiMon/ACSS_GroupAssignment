package Customer;

import java.util.Random;
import java.util.UUID;

/**
 *
 * @author YOON
 */
public class GuestCustomer extends BaseCustomer  {
    // Constructor with no parameters - auto-generates all necessary details
    public GuestCustomer() {
        // Call the parent constructor with auto-generated values
        super(
            generateGuestId(),
            generateGuestUsername(),
            generateGuestEmail(),
            generateGuestPassword()
        );
    }
    
    // Implementation of the abstract method from BaseCustomer class
    @Override
    public String getUserType() {
        return "Guest";
    }
    
    private static final String GUEST_PREFIX = "guest_";
    
    // Method to generate a unique guest ID
    private static String generateGuestId() {
        // Use UUID to generate a unique ID and add a prefix
        return GUEST_PREFIX + UUID.randomUUID().toString().substring(0, 8);
    }
    
    // Method to generate a guest username
    private static String generateGuestUsername() {
        return "Guest" + System.currentTimeMillis() % 10000;
    }
    
    // Method to generate a temporary guest email
    private static String generateGuestEmail() {
        return "guest" + System.currentTimeMillis() % 10000 + "@temp.com";
    }
    
    // Method to generate a random guest password
    private static String generateGuestPassword() {
        // Generate a empty password for guest user
        return "";
    }
    
    // Convert to a regular customer (extra in case it is needed not in used for now)
    public Customer convertToCustomer(String name, String email, String password) {
        return new Customer(getUserId(), name, email, password);
    }
}
