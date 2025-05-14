package Customer;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class CustomerDataIO {
    public static ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    private static final String FILE_NAME = "data/CustomersList.txt";

    public static void writeCustomer() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            for (Customer customer : allCustomers){
                writer.print(customer.getCustomerId() + ",");  // Write customer ID first
                writer.print(customer.getUsername() + ",");
                writer.print(customer.getEmail() + ",");
                writer.print(customer.getPassword() + ",");
                writer.println(customer.isApproved());
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

    // Generate a unique customer ID
    public static String generateCustomerId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}