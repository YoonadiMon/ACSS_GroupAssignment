/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manager;

import java.util.ArrayList;
import java.io.*;
import Salesman.Salesman;

/**
 *
 * @author hp
 */
public class SalesmanList {

    public static ArrayList<Salesman> salesmanList = new ArrayList<>();

    public static void initializeSalesman() {
        // Create alreay exits salesmen and add them to the list
        salesmanList.add(new Salesman("S001", "Ali", "S001"));
        salesmanList.add(new Salesman("S002", "Abu", "S002"));
        salesmanList.add(new Salesman("S003", "Kelvin", "S003"));
        salesmanList.add(new Salesman("S004", "Ben", "S004"));

    }

    public static void saveInitializedSalesmanDataToFile() {
        // Save to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/salesmenList.txt"))) {
            for (Salesman salesman : salesmanList) {
                writer.write(salesman.getID() + "," + salesman.getName() + "," + salesman.getPassword());
                writer.newLine(); // Move to next line
            }
            System.out.println("Salesmen saved to file.");
        } catch (IOException e) {
            System.out.println("Problem with file output.");
        }
    }
    
     public static void saveSalesmanDataToFile(ArrayList<Salesman> List) {
        // Save to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/salesmenList.txt"))) {
            for (Salesman salesman : salesmanList) {
                writer.write(salesman.getID() + "," + salesman.getName() + "," + salesman.getPassword());
                writer.newLine(); // Move to next line
            }
            System.out.println("Salesmen saved to file.");
        } catch (IOException e) {
            System.out.println("Problem with file output.");
        }
    }

    public static ArrayList<Salesman> loadSalesmanDataFromFile() {
        ArrayList<Salesman> salesmanList = new ArrayList<>();

        try (java.util.Scanner scanner = new java.util.Scanner(new java.io.File("data/salesmenList.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String id = parts[0];
                    String name = parts[1];
                    String password = parts[2];
                    salesmanList.add(new Salesman(id, name, password)); 
                }
            }
        } catch (IOException e) {
            System.out.println("Problem with file input.");
        }

        return salesmanList;
    }

}
