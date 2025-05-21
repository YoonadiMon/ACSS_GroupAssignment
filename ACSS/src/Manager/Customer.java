package Manager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Customer {
    private String id;
    private String name;
    private String contact;
    private boolean approved;

    public Customer(String id, String name, String contact, boolean approved) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.approved = approved;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public boolean isApproved() { return approved; }
    public void setName(String name) { this.name = name; }
    public void setContact(String contact) { this.contact = contact; }
    public void setApproved(boolean approved) { this.approved = approved; }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Contact: %s, Approved: %s",
                id, name, contact, approved);
    }

    public String toFileString() {
        return String.join(",", id, name, contact, String.valueOf(approved));
    }

    public static Customer fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid customer data format");
        }
        return new Customer(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3]));
    }
}

public class ManageCustomer {
    private static final List<Customer> customerList = new ArrayList<>();
    private static final String FILE_NAME = "customerList.txt";

    public static void main(String[] args) {
        loadCustomersFromFile();

        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            do {
                showMenu();
                while (!scanner.hasNextInt()) {
                    System.out.println("Please enter a number!");
                    scanner.next();
                }
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> approveCustomer(scanner);
                    case 2 -> deleteCustomer(scanner);
                    case 3 -> searchCustomer(scanner);
                    case 4 -> updateCustomer(scanner);
                    case 5 -> listAllCustomers();
                    case 0 -> {
                        saveCustomersToFile();
                        System.out.println("Exiting Customer Management...");
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } while (choice != 0);
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Manage Customers Menu ---");
        System.out.println("1. Approve/Add Customer");
        System.out.println("2. Delete Customer");
        System.out.println("3. Search Customer");
        System.out.println("4. Update Customer");
        System.out.println("5. List All Customers");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void approveCustomer(Scanner scanner) {
        System.out.print("Enter Customer ID: ");
        String id = scanner.nextLine().trim();

        if (findCustomerById(id) != null) {
            System.out.println("Customer with this ID already exists!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Contact Number: ");
        String contact = scanner.nextLine().trim();

        Customer newCustomer = new Customer(id, name, contact, true);
        customerList.add(newCustomer);
        saveCustomersToFile();
        System.out.println("Customer approved and added successfully.");
    }

    private static void deleteCustomer(Scanner scanner) {
        System.out.print("Enter Customer ID to delete: ");
        String id = scanner.nextLine().trim();

        Customer found = findCustomerById(id);
        if (found != null) {
            customerList.remove(found);
            saveCustomersToFile();
            System.out.println("Customer deleted successfully.");
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void searchCustomer(Scanner scanner) {
        System.out.print("Enter Customer ID to search: ");
        String id = scanner.nextLine().trim();

        Customer found = findCustomerById(id);
        if (found != null) {
            System.out.println("Customer Found: " + found);
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void updateCustomer(Scanner scanner) {
        System.out.print("Enter Customer ID to update: ");
        String id = scanner.nextLine().trim();

        Customer found = findCustomerById(id);
        if (found != null) {
            System.out.print("Enter new Name (leave blank to keep current): ");
            String newName = scanner.nextLine().trim();
            if (!newName.isEmpty()) {
                found.setName(newName);
            }

            System.out.print("Enter new Contact Number (leave blank to keep current): ");
            String newContact = scanner.nextLine().trim();
            if (!newContact.isEmpty()) {
                found.setContact(newContact);
            }

            saveCustomersToFile();
            System.out.println("Customer information updated.");
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void listAllCustomers() {
        if (customerList.isEmpty()) {
            System.out.println("No customers to show.");
        } else {
            System.out.println("\n--- List of All Customers ---");
            customerList.forEach(System.out::println);
        }
    }

    private static Customer findCustomerById(String id) {
        return customerList.stream()
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    private static void loadCustomersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    customerList.add(Customer.fromFileString(line));
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping invalid customer data: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing customer file found. Starting fresh.");
        } catch (IOException e) {
            System.err.println("Error reading customer file: " + e.getMessage());
        }
    }

    private static void saveCustomersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Customer c : customerList) {
                writer.write(c.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving customers to file: " + e.getMessage());
        }
    }
}
