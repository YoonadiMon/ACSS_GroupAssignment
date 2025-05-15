
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

/**
 *
 * @author hp
 */
public class SalesRecords {

    private String customerID;
    private String carID;
    private String salesmanID;
    private double price;
    private String status; 
    private String comment;

    public SalesRecords(String customerID, String carID, String salesmanID, double price, String status, String comment) {
        this.customerID = customerID;
        this.carID = carID;
        this.salesmanID = salesmanID;
        this.price = price;
        this.status = status;
        this.comment = comment;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCarID() {
        return carID;
    }

    public String getSalesmanID() {
        return salesmanID;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public String toFileString() {
        return customerID + "," + carID + "," + salesmanID + "," + price + "," + status + "," + comment;
    }

    public static SalesRecords fromFileString(String line) {
        String[] data = line.split(",");
        return new SalesRecords(
                data[0], data[1], data[2],
                Double.parseDouble(data[3]),
                data[4], data.length > 5 ? data[5] : ""
        );
    }

    public static void saveSalesRecord(SalesRecords record) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/salesList.txt", true))) {
            writer.write(record.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving sales record: " + e.getMessage());
        }
    }

    public static ArrayList<SalesRecords> loadSalesRecords() {
        ArrayList<SalesRecords> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/salesList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(SalesRecords.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading sales records.");
        }
        return records;
    }
}
