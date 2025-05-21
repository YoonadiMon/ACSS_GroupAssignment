package Customer;

public class Customer extends User {
    // Implementation of the abstract method from User class
    @Override
    public String getUserType() {
        return "Customer";
    }
    
    private boolean isApproved;
    
    // Constructor for creating a new customer (generates a new ID)
    public Customer(String name, String email, String password) {
        super(CustomerDataIO.generateCustomerId(), name, email, password);
        this.isApproved = false;  // Default to not approved
    }
    
    // Constructor with approval status
    public Customer(String name, String email, String password, boolean isApproved) {
        super(CustomerDataIO.generateCustomerId(), name, email, password);
        this.isApproved = isApproved;
    }
    
    // Constructor for loading an existing customer from file (with known ID)
    public Customer(String customerId, String name, String email, String password, boolean isApproved) {
        // Call parent constructor
        super(customerId, name, email, password);
        this.isApproved = isApproved;
    }
    
    // Constructor with customerId
    public Customer(String customerId, String name, String email, String password) {
        super(customerId, name, email, password);
        this.isApproved = false; // Default to not approved
    }
    
    // Getter for customerId (convenience method that calls parent's getUserId)
    public String getCustomerId() {
        return getUserId();
    }
    
    // Getter and setter for isApproved
    public boolean isApproved() {
        return isApproved;
    }
    
    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}