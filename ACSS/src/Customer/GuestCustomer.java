/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Customer;

import java.util.Random;
import java.util.UUID;

/**
 *
 * @author DELL
 */
public class GuestCustomer extends User  {
        
    private static final String GUEST_PREFIX = "guest_";
    
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
    
    // Implementation of the abstract method from User class
    @Override
    public String getUserType() {
        return "Guest";
    }
    
    // Helper method to generate a unique guest ID
    private static String generateGuestId() {
        // Use UUID to generate a unique ID and add a prefix
        return GUEST_PREFIX + UUID.randomUUID().toString().substring(0, 8);
    }
    
    // Helper method to generate a guest username
    private static String generateGuestUsername() {
        return "Guest" + System.currentTimeMillis() % 10000;
    }
    
    // Helper method to generate a temporary guest email
    private static String generateGuestEmail() {
        return "guest" + System.currentTimeMillis() % 10000 + "@temp.com";
    }
    
    // Helper method to generate a random guest password
    private static String generateGuestPassword() {
        // Generate a random password with 8 characters
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        
        return sb.toString();
    }
    
    // Convert to a regular customer (if needed)
    public Customer convertToCustomer(String name, String email, String password) {
        return new Customer(getUserId(), name, email, password);
    }
}
