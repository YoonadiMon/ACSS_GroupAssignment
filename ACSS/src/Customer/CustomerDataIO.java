package Customer;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerDataIO {
    public static ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    private static final String FILE_NAME = "ACSS/data/CustomersList.txt";

    public static void writeCustomer() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            for (Customer customer : allCustomers){
                writer.print(customer.getUsername() + ",");
                writer.print(customer.getEmail() + ",");
                writer.print(customer.getPassword() + ",");
                writer.println(customer.isApproved()); // Add approval status to file
            }
        } catch(Exception e) {
            System.out.printf("Error writing to %s file: %s", FILE_NAME, e.getMessage());
        }
    }

    public static void readCustomer() {
        try (Scanner s = new Scanner(new File(FILE_NAME))) {
            allCustomers.clear();
            while(s.hasNextLine()) {
                String line = s.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 3) {
                    String username = parts[0];
                    String email = parts[1];
                    String password = parts[2];

                    // Handle approval status - default to false if not in file (for backward compatibility)
                    boolean isApproved = false;
                    if (parts.length >= 4) {
                        isApproved = Boolean.parseBoolean(parts[3]);
                    }

                    Customer customer = new Customer(username, email, password, isApproved);
                    allCustomers.add(customer);
                }
            }
        } catch(Exception e) {
            System.out.println("Error reading from file: " + e.getMessage());
            // Make sure the directory exists
            File directory = new File("ACSS/data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }
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

    // Method to find customer and check if approved
    public static boolean isCustomerApproved(String usernameOrEmail) {
        Customer customer = searchName(usernameOrEmail);
        if (customer == null) {
            customer = searchEmail(usernameOrEmail);
        }

        return customer != null && customer.isApproved();
    }
}