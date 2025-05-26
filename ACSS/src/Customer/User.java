package Customer;

/**
 *
 * @author YOON
 */
public abstract class User {
    protected String userId;
    protected String username;
    protected String email;
    protected String password;
    
    // Constructor
    public User(String userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    // Common methods
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    // Abstract method that all users must implement
    public abstract String getUserType();
}

