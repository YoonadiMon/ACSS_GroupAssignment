package Manager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ManagerDataIO {
    private static final String FILE_NAME = "data/managerList.txt";
    private static final Map<String, Manager> managerMap = new HashMap<>();

    // Read managers from the file and populate managerMap
    public static void readManager() {
        managerMap.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    String securityPhrase = parts[2];
                    Manager manager = new Manager(username, password, securityPhrase);
                    managerMap.put(username, manager);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading manager data: " + e.getMessage());
        }
    }

    // Search for a manager by username
    public static Manager searchName(String username) {
        return managerMap.get(username);
    }

    // Save all manager data back to file
    public static void saveManagerData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Manager manager : managerMap.values()) {
                writer.write(manager.getUsername() + "," +
                        manager.getPassword() + "," +
                        manager.getSecurityPhrase());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing manager data: " + e.getMessage());
        }
    }

    // Update a specific manager's password
    public static void updatePassword(String username, String newPassword) {
        Manager manager = managerMap.get(username);
        if (manager != null) {
            manager.setPassword(newPassword);
            saveManagerData();
        }
    }
}
