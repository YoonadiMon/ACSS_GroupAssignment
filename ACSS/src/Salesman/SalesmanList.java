/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Salesman;

import java.util.ArrayList;
import java.io.*;
import Salesman.Salesman;
import java.util.Scanner;

/**
 *
 * @author hp
 */
public class SalesmanList {

    public static ArrayList<Salesman> salesmanList = new ArrayList<>();

    public static void initializeSalesman() {
        // Create default salesmen with security questions and answers
        salesmanList.add(new Salesman("S001", "Ali", "S001",
                "What is your mother's maiden name?", "Smith"));
        salesmanList.add(new Salesman("S002", "Abu", "S002",
                "What was your first pet's name?", "Fluffy"));
        salesmanList.add(new Salesman("S003", "Kelvin", "S003",
                "What city were you born in?", "Kuala Lumpur"));
        salesmanList.add(new Salesman("S004", "Ben", "S004",
                "What is your favorite book?", "Harry Potter"));

        saveInitializedSalesmanDataToFile();
    }

    public static void saveInitializedSalesmanDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/salesmenList.txt"))) {
            for (Salesman salesman : salesmanList) {
                writer.write(String.join(",",
                        salesman.getID(),
                        salesman.getName(),
                        salesman.getPassword(),
                        salesman.getSecurityQuestion(),
                        salesman.getSecurityAnswer()
                ));
                writer.newLine();
            }
            System.out.println("Salesmen saved to file.");
        } catch (IOException e) {
            System.out.println("Problem with file output.");
        }
    }

    public static void saveSalesmanDataToFile(ArrayList<Salesman> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/salesmenList.txt"))) {
            for (Salesman salesman : list) {
                writer.write(String.join(",",
                        salesman.getID(),
                        salesman.getName(),
                        salesman.getPassword(),
                        salesman.getSecurityQuestion(),
                        salesman.getSecurityAnswer()
                ));
                writer.newLine();
            }
            System.out.println("Salesmen saved to file.");
        } catch (IOException e) {
            System.out.println("Problem with file output.");
        }
    }

    public static ArrayList<Salesman> loadSalesmanDataFromFile() {
        salesmanList.clear();

        try (Scanner scanner = new Scanner(new File("data/salesmenList.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String password = parts[2].trim();
                    String question = parts[3].trim();
                    String answer = parts[4].trim();

                    salesmanList.add(new Salesman(id, name, password, question, answer));
                } else {
                    System.out.println("Invalid line skipped: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Problem with file input. Initializing default salesmen.");
            initializeSalesman();
        }

        return salesmanList;
    }

    public static Salesman findSalesmanById(String salesmanId) {
        for (Salesman salesman : salesmanList) {
            if (salesman.getID().equals(salesmanId)) {
                return salesman;
            }
        }
        return null;
    }

    public static String getSalesmanNameById(String salesmanId) {
        Salesman salesman = findSalesmanById(salesmanId);
        return (salesman != null) ? salesman.getName() : "Unknown";
    }

    public static boolean verifySecurityAnswer(String salesmanId, String answer) {
        Salesman salesman = findSalesmanById(salesmanId);
        return salesman != null && salesman.getSecurityAnswer().equalsIgnoreCase(answer);
    }

    public static boolean updateSalesmanPassword(String salesmanId, String newPassword) {
        Salesman salesman = findSalesmanById(salesmanId);
        if (salesman != null) {
            salesman.setPassword(newPassword);
            saveSalesmanDataToFile(salesmanList);
            return true;
        }
        return false;
    }
}
