/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Car;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class CarRequest {

    private static final String REQUEST_FILE = "CarRequest.txt";
    public static ArrayList<CarRequest> carRequestsList = new ArrayList<>();

    private String customerID;
    private String carID;
    private String salesmanID;
    private String requestStatus;
    private String comment;

    public CarRequest(String customerID, String carID, String salesmanID, String requestStatus, String comment) {
        this.customerID = customerID;
        this.carID = carID;
        this.salesmanID = salesmanID;
        this.requestStatus = requestStatus;
        this.comment = comment;
    }

    // Getters
    public String getCustomerID() {
        return customerID;
    }

    public String getCarID() {
        return carID;
    }

    public String getSalesmanID() {
        return salesmanID;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public String getComment() {
        return comment;
    }

    // Write all car requests to the file
    public static void writeCarRequests(ArrayList<CarRequest> carRequestsList) {
        try (PrintWriter writer = new PrintWriter(REQUEST_FILE)) {
            for (CarRequest request : carRequestsList) {
                writer.print(request.getCustomerID() + ",");
                writer.print(request.getCarID() + ",");
                writer.print(request.getSalesmanID() + ",");
                writer.print(request.getRequestStatus() + ",");
                writer.println(request.getComment());
            }
        } catch (Exception e) {
            System.out.printf("Error writing to %s: %s%n", REQUEST_FILE, e.getMessage());
        }
    }

    public static void initializeCarRequests() {
        carRequestsList.add(new CarRequest("14cb8731", "C001", "S001", "pending", "."));
        carRequestsList.add(new CarRequest("f1363ffa", "C002", "S001", "pending", "."));
        carRequestsList.add(new CarRequest("e292d371", "C003", "S002", "pending", "."));
    }

    public static void saveInitialCarRequestsToFile() {
        initializeCarRequests();
        writeCarRequests(carRequestsList);

    }

    public static ArrayList<CarRequest> loadCarRequestDataFromFile() {
        ArrayList<CarRequest> loadedRequestList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("CarRequest.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    String customerID = data[0];
                    String carID = data[1];
                    String salesmanID = data[2];
                    String status = data[3];
                    String comment = data[4];
                    loadedRequestList.add(new CarRequest(customerID, carID, salesmanID, status, comment));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading car request data.");
        }
        return loadedRequestList;
    }

    public static boolean updateRequestStatusWithComment(String carID, String salesmanID, String newStatus, String comment) {
        ArrayList<CarRequest> requests = loadCarRequestDataFromFile();
        boolean found = false;

        for (int i = 0; i < requests.size(); i++) {
            CarRequest req = requests.get(i);
            if (req.getCarID().equalsIgnoreCase(carID) && req.getSalesmanID().equals(salesmanID)) {
                CarRequest updatedRequest = new CarRequest(
                        req.getCustomerID(),
                        req.getCarID(),
                        req.getSalesmanID(),
                        newStatus,
                        (comment == null || comment.trim().isEmpty() ? "." : comment)
                );
                requests.set(i, updatedRequest);
                found = true;
                break;
            }
        }

        if (found) {
            writeCarRequests(requests);
            return true;
        }
        return false;
    }
}
