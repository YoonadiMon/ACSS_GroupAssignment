package Customer;

public class Customer {

    private String username;
    private String email;
    private String password;
    private boolean isApproved;

    public Customer(String name, String email, String password) {
        this.username = name;
        this.email = email;
        this.password = password;
        this.isApproved = false;  // Default to not approved
    }

    // Constructor with explicit approval status
    public Customer(String name, String email, String password, boolean isApproved) {
        this.username = name;
        this.email = email;
        this.password = password;
        this.isApproved = isApproved;
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