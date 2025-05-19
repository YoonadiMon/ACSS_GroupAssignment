/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Car;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author hp
 */
public class SoldCarRecord {

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
}


