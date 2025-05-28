package Customer;

/**
 *
 * @author YOON
 */
public class DeletedCustomer extends BaseCustomer {
    // Implementation of the abstract method from User class
    @Override
    public String getUserType() {
        return "Deleted Customer";
    }
    
    public DeletedCustomer(String customerId, String name, String email, String password) {
        super(
            customerId, 
            name, 
            email, 
            password);
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
