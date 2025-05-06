/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Manager;

import Salesman.Salesman;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FilterOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 *
 * @author hp
 */
public class SalesmenList {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ArrayList<Salesman> salesmanList = new ArrayList<>();

        // Create alreay exits salesmen and add them to the list
        salesmanList.add(new Salesman("S001", "Ali", "S001"));
        salesmanList.add(new Salesman("S002", "Abu", "S002"));
        salesmanList.add(new Salesman("S003", "Kelvin", "S003"));
        salesmanList.add(new Salesman("S004", "Ben", "S004"));

        saveSalesmanDataToFile(salesmanList);

    }

    public static void saveSalesmanDataToFile(ArrayList<Salesman> salesmanList) {
        // Save to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("salesmenList.txt"))) {
            for (Salesman salesman : salesmanList) {
                writer.write(salesman.getID() + "," + salesman.getPassword());
                writer.newLine(); // Move to next line
            }
            System.out.println("Salesmen saved to file.");
        } catch (IOException e) {
            System.out.println("Problem with file output.");
        }
    }

    public static ArrayList<Salesman> loadSalesmanDataFromFile() {
        ArrayList<Salesman> salesmanList = new ArrayList<>();

        try (java.util.Scanner scanner = new java.util.Scanner(new java.io.File("salesmenList.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String id = parts[0];
                    String password = parts[1];
                    salesmanList.add(new Salesman(id, "", password)); // Name not saved in file, so keep it empty or load differently
                }
            }
        } catch (IOException e) {
            System.out.println("Problem with file input.");
        }

        return salesmanList;
    }

}
