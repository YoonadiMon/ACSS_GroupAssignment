package Customer;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

/**
 *
 * @author YOON
 */
public class CustomerDataIO {
    public static ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    public static ArrayList<DeletedCustomer> allDeletedCustomers = new ArrayList<>();
    private static final String FILE_NAME = "data/CustomersList.txt";
    private static final String DELETED_FILE_NAME = "data/DeletedCustomersList.txt";

    public static void writeCustomer() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            for (Customer customer : allCustomers){
                writer.print(customer.getCustomerId() + ",");  
                writer.print(customer.getUsername() + ",");
                writer.print(customer.getEmail() + ",");
                writer.print(customer.getPassword() + ",");
                writer.println(customer.isApproved());
            }
        } catch(Exception e) {
            System.out.printf("Error writing to %s file: %s", FILE_NAME, e.getMessage());
        }
    }
    
    public static void writeDeletedCustomer() {
        try (PrintWriter writer = new PrintWriter(DELETED_FILE_NAME)) {
            for (DeletedCustomer customer : allDeletedCustomers){
                writer.print(customer.getUserId() + ","); 
                writer.print(customer.getUsername() + ",");
                writer.print(customer.getEmail() + ",");
                writer.println(customer.getPassword()); // Changed to println for last field
            }
        } catch(Exception e) {
            System.out.printf("Error writing to %s file: %s", DELETED_FILE_NAME, e.getMessage());
        }
    }

    public static void readCustomer() {
        try (Scanner s = new Scanner(new File(FILE_NAME))) {
            allCustomers.clear();
            while(s.hasNextLine()) {
                String line = s.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 4) {
                    String customerId = parts[0];
                    String username = parts[1];
                    String email = parts[2];
                    String password = parts[3];

                    boolean isApproved = false;
                    if (parts.length >= 5) {
                        isApproved = Boolean.parseBoolean(parts[4]);
                    }

                    Customer customer = new Customer(customerId, username, email, password, isApproved);
                    allCustomers.add(customer);
                }
            }
        } catch(Exception e) {
            System.out.println("Error reading from file: " + e.getMessage());
            // Make sure the directory exists
            File directory = new File("data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }
    }
    
    // New method to read deleted customers
    public static void readDeletedCustomer() {
        try (Scanner s = new Scanner(new File(DELETED_FILE_NAME))) {
            allDeletedCustomers.clear();
            while(s.hasNextLine()) {
                String line = s.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 4) {
                    String userId = parts[0];
                    String username = parts[1];
                    String email = parts[2];
                    String password = parts[3];

                    DeletedCustomer deletedCustomer = new DeletedCustomer(userId, username, email, password);
                    allDeletedCustomers.add(deletedCustomer);
                }
            }
        } catch(Exception e) {
            System.out.println("Error reading deleted customers from file: " + e.getMessage());
            // Make sure the directory exists
            File directory = new File("data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }
    }
    
    public static Customer searchId(String customerId) {
        for(Customer customer : allCustomers) {
            if(customerId.equals(customer.getCustomerId())) {
                return customer;
            }
        }
        return null;
    }

    public static Customer searchName(String name) {
        for(Customer customer : allCustomers) {
            if(name.equals(customer.getUsername())) {
                return customer;
            }
        }
        return null;
    }

    public static Customer searchEmail(String email) {
        for (Customer customer : allCustomers) {
            if (email.equals(customer.getEmail())) {
                return customer;
            }
        }
        return null;
    }

    // New search methods for deleted customers
    public static DeletedCustomer searchDeletedId(String userId) {
        for(DeletedCustomer customer : allDeletedCustomers) {
            if(userId.equals(customer.getUserId())) {
                return customer;
            }
        }
        return null;
    }

    public static DeletedCustomer searchDeletedName(String name) {
        for(DeletedCustomer customer : allDeletedCustomers) {
            if(name.equals(customer.getUsername())) {
                return customer;
            }
        }
        return null;
    }

    public static DeletedCustomer searchDeletedEmail(String email) {
        for (DeletedCustomer customer : allDeletedCustomers) {
            if (email.equals(customer.getEmail())) {
                return customer;
            }
        }
        return null;
    }

    public static Customer validateLogin(String usernameOrEmail, String password) {
        // First try to find the customer by username
        Customer customer = searchName(usernameOrEmail);

        // If not found by username, try by email
        if (customer == null) {
            customer = searchEmail(usernameOrEmail);
        }

        // Check if customer exists, password matches, and customer is approved
        if (customer != null && customer.getPassword().equals(password)) {
            return customer;
        }

        return null;
    }
    
    public static Customer getIDfromUsernameorEmail(String usernameOrEmail) {
        // First try to find the customer by username
        Customer customer = searchName(usernameOrEmail);

        // If not found by username, try by email
        if (customer == null) {
            customer = searchEmail(usernameOrEmail);
        }
        return customer;
    }

    // Method to find customer and check if approved
    public static boolean isCustomerApproved(String usernameOrEmail) {
        Customer customer = searchName(usernameOrEmail);
        if (customer == null) {
            customer = searchEmail(usernameOrEmail);
        }

        return customer != null && customer.isApproved();
    }

    // New method to move customer to deleted list
    public static boolean deleteCustomer(String customerId) {
        Customer customer = searchId(customerId);
        if (customer != null) {
            // Create deleted customer record
            DeletedCustomer deletedCustomer = new DeletedCustomer(
                customer.getCustomerId(), 
                customer.getUsername(), 
                customer.getEmail(), 
                customer.getPassword()
            );
            
            // Add to deleted list and remove from active list
            allDeletedCustomers.add(deletedCustomer);
            allCustomers.remove(customer);
            
            // Write both files to persist changes
            writeCustomer();
            writeDeletedCustomer();
            
            return true;
        }
        return false;
    }

    // New method to restore deleted customer
    public static boolean restoreCustomer(String userId) {
        DeletedCustomer deletedCustomer = searchDeletedId(userId);
        if (deletedCustomer != null) {
            // Create new customer record (assuming default approval status is false)
            Customer restoredCustomer = new Customer(
                deletedCustomer.getUserId(), 
                deletedCustomer.getUsername(), 
                deletedCustomer.getEmail(), 
                deletedCustomer.getPassword(),
                false // Default approval status
            );
            
            // Add to active list and remove from deleted list
            allCustomers.add(restoredCustomer);
            allDeletedCustomers.remove(deletedCustomer);
            
            // Write both files to persist changes
            writeCustomer();
            writeDeletedCustomer();
            
            return true;
        }
        return false;
    }

    // New method to permanently remove deleted customer
    public static boolean permanentlyDeleteCustomer(String userId) {
        DeletedCustomer deletedCustomer = searchDeletedId(userId);
        if (deletedCustomer != null) {
            allDeletedCustomers.remove(deletedCustomer);
            writeDeletedCustomer();
            return true;
        }
        return false;
    }

    // Generate a unique customer ID
    public static String generateCustomerId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    // Utility method to load all data at startup
    public static void loadAllData() {
        readCustomer();
        readDeletedCustomer();
    }
}