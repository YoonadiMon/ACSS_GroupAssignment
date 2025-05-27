package Manager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManageStaffSalesman {
    private static final List<User> staffList = new ArrayList<>();
    private static final List<User> salesmanList = new ArrayList<>();

    record User(String id, String name) {

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name;
        }

        public String toFileString() {
            return id + "," + name;
        }
    }

    public static void main(String[] args) {
        loadUsersFromFile("data/staffList.txt", staffList);
        loadUsersFromFile("data/salesmanList.txt", salesmanList);

        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            do {
                showMenu();
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> addUser(scanner);
                    case 2 -> deleteUser(scanner);
                    case 3 -> searchUser(scanner);
                    case 4 -> updateUser(scanner);
                    case 5 -> listAllUsers();
                    case 0 -> {
                        System.out.println("Exiting system...");
                        saveUsersToFile("data/staffList.txt", staffList);
                        saveUsersToFile("data/salesmanList.txt", salesmanList);
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } while (true);
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Managing Staff Interface ---");
        System.out.println("1. Add Staff/Salesman");
        System.out.println("2. Delete Staff/Salesman");
        System.out.println("3. Search Staff/Salesman");
        System.out.println("4. Update Staff/Salesman");
        System.out.println("5. List All Users");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addUser(Scanner scanner) {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Role (Staff/Salesman): ");
        String role = scanner.nextLine().trim();

        User newUser = new User(id, name);
        if (role.equalsIgnoreCase("Staff")) {
            staffList.add(newUser);
            System.out.println("Staff added.");
        } else if (role.equalsIgnoreCase("Salesman")) {
            salesmanList.add(newUser);
            System.out.println("Salesman added.");
        } else {
            System.out.println("Invalid role! Please enter Staff or Salesman.");
        }
    }

    private static void deleteUser(Scanner scanner) {
        System.out.print("Enter ID to delete: ");
        String id = scanner.nextLine().trim();

        if (removeUserFromList(staffList, id)) {
            System.out.println("Staff deleted.");
        } else if (removeUserFromList(salesmanList, id)) {
            System.out.println("Salesman deleted.");
        } else {
            System.out.println("User not found.");
        }
    }

    private static void searchUser(Scanner scanner) {
        System.out.print("Enter ID to search: ");
        String id = scanner.nextLine().trim();

        User found = findUserById(staffList, id);
        if (found != null) {
            System.out.println("Found in Staff List: " + found);
            return;
        }

        found = findUserById(salesmanList, id);
        if (found != null) {
            System.out.println("Found in Salesman List: " + found);
        } else {
            System.out.println("User not found.");
        }
    }

    private static void updateUser(Scanner scanner) {
        System.out.print("Enter ID to update: ");
        String id = scanner.nextLine().trim();

        User user = findUserById(staffList, id);
        if (user != null) {
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine().trim();
            staffList.set(staffList.indexOf(user), new User(user.id(), newName));
            System.out.println("Staff updated.");
            return;
        }

        user = findUserById(salesmanList, id);
        if (user != null) {
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine().trim();
            salesmanList.set(salesmanList.indexOf(user), new User(user.id(), newName));
            System.out.println("Salesman updated.");
        } else {
            System.out.println("User not found.");
        }
    }

    private static void listAllUsers() {
        System.out.println("\n--- Staff List ---");
        staffList.forEach(System.out::println);

        System.out.println("\n--- Salesman List ---");
        salesmanList.forEach(System.out::println);
    }

    private static boolean removeUserFromList(List<User> list, String id) {
        return list.removeIf(user -> user.id().equalsIgnoreCase(id));
    }

    private static User findUserById(List<User> list, String id) {
        return list.stream()
                .filter(user -> user.id().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    private static void saveUsersToFile(String filename, List<User> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (User user : list) {
                writer.write(user.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving to " + filename + ": " + e.getMessage());
        }
    }

    private static void loadUsersFromFile(String filename, List<User> list) {
        list.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    list.add(new User(parts[0], parts[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing file found: " + filename);
        } catch (IOException e) {
            System.err.println("Error loading from " + filename + ": " + e.getMessage());
        }
    }
}
