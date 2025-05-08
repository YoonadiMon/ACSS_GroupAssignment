package Customer;

public class Customer {
    private String customerId;
    private String username;
    private String email;
    private String password;
    private boolean isApproved;

    // Constructor for creating a new customer (generates a new ID)
    public Customer(String name, String email, String password) {
        this.customerId = CustomerDataIO.generateCustomerId();
        this.username = name;
        this.email = email;
        this.password = password;
        this.isApproved = false;  // Default to not approved
    }

    public String getCustomerId() {
        return customerId;
    }

    public Customer(String name, String email, String password, boolean isApproved) {
        this.customerId = CustomerDataIO.generateCustomerId();
        this.username = name;
        this.email = email;
        this.password = password;
        this.isApproved = isApproved;
    }

    // Constructor for loading an existing customer from file (with known ID)
    public Customer(String customerId, String name, String email, String password, boolean isApproved) {
        this.customerId = customerId;
        this.username = name;
        this.email = email;
        this.password = password;
        this.isApproved = isApproved;
    }

    public Customer(String customerId, String name, String email, String password) {
        this.customerId = customerId;
        this.username = name;
        this.email = email;
        this.password = password;
        this.isApproved = false; // Default to not approved
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isApproved() {
        return isApproved;
    }
}