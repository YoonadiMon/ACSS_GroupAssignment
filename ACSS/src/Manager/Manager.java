package Manager;

import java.util.Scanner;

public class ManagerFeatures {

    static Scanner scanner = new Scanner(System.in);

public class ManagerFeatures {

    static Scanner scanner = new Scanner(System.in);

    public static void managerFeatures() {
        while (true) {
            System.out.println("\n=== Manager Features Menu ===");
            System.out.println("1. Manage Staff and Salesman");
            System.out.println("2. Manage Customers");
            System.out.println("3. Manage Car Inventory");
            System.out.println("4. Payment and Feedback");
            System.out.println("5. Reports");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> manageStaffSalesman(); // 3A
                case 2 -> manageCustomer();      // 3B
                case 3 -> manageCarInventory();  // 3C
                case 4 -> paymentFeedback();     // 3D
                case 5 -> generateReports();     // 3E
                case 6 -> {
                    System.out.println("Logged Out");
                    return;
                }
                default -> System.out.println("Invalid input");
            }
        }
    }

    // Managing staff and salesman
    static void manageStaffSalesman() {
        System.out.println("[3A] Managing Staff and Salesmen...");
        // call real functionality here
    }

    // Managing customers
    static void manageCustomer() {
        System.out.println("[3B] Managing Customers...");
        // call real functionality here
    }

    // Managing car inventory
    static void manageCarInventory() {
        System.out.println("[3C] Managing Car Inventory...");
        // call real functionality here
    }

    // Payments and feedback
    static void paymentFeedback() {
        System.out.println("[3D] Handling Payment and Feedback...");
        // call real functionality here
    }

    // Generating reports
    static void generateReports() {
        System.out.println("[3E] Generating Reports...");
        // call real functionality here
    }

    // Entry point for demo
    public static void main(String[] args) {
        managerFeatures();
    }
}
anager {
}
