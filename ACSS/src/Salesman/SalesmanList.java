/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Salesman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class SalesmanList {
    private static ArrayList<Salesman> salesmanList = new ArrayList<>();
    private static final String SALESMAN_FILE = "data/salesmenList.txt"; 
    
    public static ArrayList<Salesman> loadSalesmanDataFromFile() {
        salesmanList.clear();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(SALESMAN_FILE));
            String line;
            
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] data = line.split(",");
                    if (data.length >= 3) {
                        Salesman salesman = new Salesman(data[0], data[1], data[2]);
                        salesmanList.add(salesman);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error loading salesman data: " + e.getMessage());
        }
        
        return salesmanList;
    }
    
    public static Salesman findSalesmanById(String salesmanId) {
        for (Salesman salesman : salesmanList) {
            if (salesman.getID().equals(salesmanId)) {
                return salesman;
            }
        }
        return null; // Salesman not found
    }

    public static String getSalesmanNameById(String salesmanId) {
        Salesman salesman = findSalesmanById(salesmanId);
        return (salesman != null) ? salesman.getName() : "Unknown";
    }
}
