/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Customer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Yoon
 */
public class CustomerCarWishlist {
    private static final String FILE_NAME = "data/CustomerCarWishlist.txt";
    
    private String customerId;
    private String carId;
    
    // Constructor
    public CustomerCarWishlist(String customerId, String carId) {
        this.customerId = customerId;
        this.carId = carId;
    }
    
    // Getters
    public String getCustomerId() { return customerId; }
    public String getCarId() { return carId; }
    
    // Setters
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setCarId(String carId) { this.carId = carId; }
    
    public static ArrayList<CustomerCarWishlist> loadWishlistDataFromFile() {
        ArrayList<CustomerCarWishlist> wishlistEntries = new ArrayList<>();
        
        try {
            File file = new File(FILE_NAME);
            
            // Create file if it doesn't exist
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // Create directories if needed
                file.createNewFile();
                return wishlistEntries; // Return empty list for new file
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String customerId = parts[0].trim();
                        String carId = parts[1].trim();
                        wishlistEntries.add(new CustomerCarWishlist(customerId, carId));
                    }
                }
            }
            reader.close();
            
        } catch (IOException e) {
            System.err.println("Error reading wishlist file: " + e.getMessage());
        }
        
        return wishlistEntries;
    }

    public static void saveWishlistDataToFile(ArrayList<CustomerCarWishlist> wishlistEntries) {
        try {
            File file = new File(FILE_NAME);
            
            // Create directories if they don't exist
            file.getParentFile().mkdirs();
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            
            for (CustomerCarWishlist entry : wishlistEntries) {
                writer.write(entry.getCustomerId() + "," + entry.getCarId());
                writer.newLine();
            }
            
            writer.close();
            
        } catch (IOException e) {
            System.err.println("Error writing to wishlist file: " + e.getMessage());
            throw new RuntimeException("Failed to save wishlist data: " + e.getMessage());
        }
    }
    
    public static ArrayList<String> getCustomerWishlistCarIds(String customerId) {
        ArrayList<String> customerCarIds = new ArrayList<>();
        ArrayList<CustomerCarWishlist> allEntries = loadWishlistDataFromFile();
        
        for (CustomerCarWishlist entry : allEntries) {
            if (entry.getCustomerId().equals(customerId)) {
                customerCarIds.add(entry.getCarId());
            }
        }
        
        return customerCarIds;
    }
    
    public static boolean addToWishlist(String customerId, String carId) {
        ArrayList<CustomerCarWishlist> allEntries = loadWishlistDataFromFile();
        
        // Check if entry already exists
        for (CustomerCarWishlist entry : allEntries) {
            if (entry.getCustomerId().equals(customerId) && entry.getCarId().equals(carId)) {
                return false; // Already exists
            }
        }
        
        // Add new entry
        allEntries.add(new CustomerCarWishlist(customerId, carId));
        saveWishlistDataToFile(allEntries);
        return true;
    }
    
    public static boolean removeFromWishlist(String customerId, String carId) {
        ArrayList<CustomerCarWishlist> allEntries = loadWishlistDataFromFile();
        boolean removed = false;
        
        // Remove matching entries
        for (int i = allEntries.size() - 1; i >= 0; i--) {
            CustomerCarWishlist entry = allEntries.get(i);
            if (entry.getCustomerId().equals(customerId) && entry.getCarId().equals(carId)) {
                allEntries.remove(i);
                removed = true;
            }
        }
        
        if (removed) {
            saveWishlistDataToFile(allEntries);
        }
        
        return removed;
    }
    
    public static boolean isCarInWishlist(String customerId, String carId) {
        ArrayList<String> customerWishlist = getCustomerWishlistCarIds(customerId);
        return customerWishlist.contains(carId);
    }
    
    public static int clearCustomerWishlist(String customerId) {
        ArrayList<CustomerCarWishlist> allEntries = loadWishlistDataFromFile();
        int removedCount = 0;
        
        for (int i = allEntries.size() - 1; i >= 0; i--) {
            if (allEntries.get(i).getCustomerId().equals(customerId)) {
                allEntries.remove(i);
                removedCount++;
            }
        }
        
        if (removedCount > 0) {
            saveWishlistDataToFile(allEntries);
        }
        
        return removedCount;
    }
    
    public static int removeCarFromAllWishlists(String carId) {
        ArrayList<CustomerCarWishlist> allEntries = loadWishlistDataFromFile();
        int removedCount = 0;
        
        for (int i = allEntries.size() - 1; i >= 0; i--) {
            if (allEntries.get(i).getCarId().equals(carId)) {
                allEntries.remove(i);
                removedCount++;
            }
        }
        
        if (removedCount > 0) {
            saveWishlistDataToFile(allEntries);
        }
        
        return removedCount;
    }
    
    @Override
    public String toString() {
        return "CustomerCarWishlist{" +
                "customerId='" + customerId + '\'' +
                ", carId='" + carId + '\'' +
                '}';
    }
}
