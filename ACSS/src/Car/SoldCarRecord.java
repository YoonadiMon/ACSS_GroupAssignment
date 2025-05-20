
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Car;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoldCarRecord {
    private String carID;
    private String carPrice;
    private String customerID;
    private String salesmanID;
    private String buyingDate;
    private String comment;
    
    // Constructor
    public SoldCarRecord(String carID, String carPrice, String customerID, 
            String salesmanID, String buyingDate, String comment) {
        this.carID = carID;
        this.carPrice = carPrice;
        this.customerID = customerID;
        this.salesmanID = salesmanID;
        this.buyingDate = buyingDate;
        this.comment = comment;
    }
    
    // Getters and setters
    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(String carPrice) {
        this.carPrice = carPrice;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(String salesmanID) {
        this.salesmanID = salesmanID;
    }

    public String getBuyingDate() {
        return buyingDate;
    }

    public void setBuyingDate(String buyingDate) {
        this.buyingDate = buyingDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    // Method to save a sold car record to the file
    public static void saveSoldCarRecord(String carID, String carPrice, String customerID,
            String salesmanID, String buyingDate, String comment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/soldCars.txt", true))) {
            String record = String.join(",",
                    carID,
                    carPrice,
                    customerID,
                    salesmanID,
                    buyingDate,
                    comment.replace(",", ";") // Replace commas in comment to avoid CSV issues
            );
            writer.write(record);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to sold cars file: " + e.getMessage());
        }
    }
    
    // Method to read all sold car records from the file
    public static List<SoldCarRecord> getAllSoldCarRecords() {
        List<SoldCarRecord> soldCarsList = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader("data/soldCars.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",", 6); // Use 6 to handle commas in comment if any escaped
                    
                    if (parts.length >= 6) {
                        SoldCarRecord record = new SoldCarRecord(
                                parts[0], // carID
                                parts[1], // carPrice
                                parts[2], // customerID
                                parts[3], // salesmanID
                                parts[4], // buyingDate
                                parts[5]  // comment (may contain semicolons instead of commas)
                        );
                        soldCarsList.add(record);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from sold cars file: " + e.getMessage());
        }
        
        return soldCarsList;
    }
    
    // Method to find sold car records by car ID
    public static SoldCarRecord findByCarID(String searchCarID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/soldCars.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",", 6);
                    if (parts.length >= 6 && parts[0].equals(searchCarID)) {
                        return new SoldCarRecord(
                                parts[0], // carID
                                parts[1], // carPrice
                                parts[2], // customerID
                                parts[3], // salesmanID
                                parts[4], // buyingDate
                                parts[5]  // comment
                        );
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from sold cars file: " + e.getMessage());
        }
        
        return null; // Return null if no matching record is found
    }
    
    // Method to find sold car records by customer ID
    public static List<SoldCarRecord> findByCustomerID(String searchCustomerID) {
        List<SoldCarRecord> results = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader("data/soldCars.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",", 6);
                    if (parts.length >= 6 && parts[2].equals(searchCustomerID)) {
                        SoldCarRecord record = new SoldCarRecord(
                                parts[0], // carID
                                parts[1], // carPrice
                                parts[2], // customerID
                                parts[3], // salesmanID
                                parts[4], // buyingDate
                                parts[5]  // comment
                        );
                        results.add(record);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from sold cars file: " + e.getMessage());
        }
        
        return results;
    }
    
    // Method to find sold car records by salesman ID
    public static List<SoldCarRecord> findBySalesmanID(String searchSalesmanID) {
        List<SoldCarRecord> results = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader("data/soldCars.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",", 6);
                    if (parts.length >= 6 && parts[3].equals(searchSalesmanID)) {
                        SoldCarRecord record = new SoldCarRecord(
                                parts[0], // carID
                                parts[1], // carPrice
                                parts[2], // customerID
                                parts[3], // salesmanID
                                parts[4], // buyingDate
                                parts[5]  // comment
                        );
                        results.add(record);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from sold cars file: " + e.getMessage());
        }
        
        return results;
    }
    
    @Override
    public String toString() {
        return "Car ID: " + carID +
               ", Price: " + carPrice +
               ", Customer ID: " + customerID +
               ", Salesman ID: " + salesmanID +
               ", Date: " + buyingDate +
               ", Comment: " + comment;
    }
}