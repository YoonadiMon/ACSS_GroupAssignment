package Customer;
import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author YOON
 */
public class CustomerDataValidator {
    // Regular expression for email validation
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidEmail(String email) {
        // return true if valid, false otherwise
        if (email == null) {
            return false;
        }
        Matcher matcher = emailPattern.matcher(email);
        
        return matcher.matches() 
           && !email.contains(",") 
           && !email.contains("\"") 
           && !email.contains("\n");
    }

    public static boolean isValidPassword(String password) {
        // return true if valid, false otherwise
        // conditions are At least 8 characters with one digit and one uppercase letter
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasDigit = false;
        boolean hasUpperCase = false;
        
        if (password.contains(",") || password.contains("\"") || password.contains("\n")) {
            return false;
        }
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            }

            if (hasDigit && hasUpperCase) {
                return true;
            }
        }

        return false;
    }

    public static boolean isValidUsername(String username) {
        // return true if valid, false otherwise
        // conditions are at least 3 characters + no special characters except underscore
        if (username == null || username.length() < 3) {
            return false;
        }

        return username.matches("^[a-zA-Z0-9_]+$")
           && !username.contains(",") 
           && !username.contains("\"") 
           && !username.contains("\n");
    }
    
    public static boolean isUsernameBanned(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        
        // Search for the username in the deleted customers list
        DeletedCustomer deletedCustomer = CustomerDataIO.searchDeletedName(username);
        return deletedCustomer != null;
    }

    public static boolean isEmailBanned(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        // Search for the email in the deleted customers list
        DeletedCustomer deletedCustomer = CustomerDataIO.searchDeletedEmail(email);
        return deletedCustomer != null;
    }
}