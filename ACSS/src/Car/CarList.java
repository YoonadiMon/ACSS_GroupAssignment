/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Car;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FilterOutputStream;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author hp
 */
public class CarList {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ArrayList<Car> carList = new ArrayList<>();

        carList.add(new Car("C001", "Toyota", 10000, "available", "S001"));
        carList.add(new Car("C002", "Honda", 20000, "available", "S001"));
        carList.add(new Car("C003", "Myvi", 15000, "available", "S002"));
        carList.add(new Car("C004", "BMW", 100000, "available", "S002"));

        saveCarListToFile(carList);

    }

    public static void saveCarListToFile(ArrayList<Car> carList) {
        // Save to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("carList.txt"))) {
            for (int i = 0; i < carList.size(); i++) {
                Car car = carList.get(i);
                writer.write(car.getCarId() + "," + car.getBrand() + "," + car.getPrice() + "," + car.getStatus() + "," + car.getSalesmanId());
                writer.newLine(); // Move to next line
            }
            System.out.println("Car list saved to file.");
        } catch (IOException e) {
            System.out.println("Problem with file output.");
        }
    }

    public static ArrayList<Car> loadCarDataFromFile() {
        ArrayList<Car> carList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("carList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    String carId = data[0];
                    String brand = data[1];
                    double price = Double.parseDouble(data[2]);
                    String status = data[3];
                    String salesmanId = data[4];
                    carList.add(new Car(carId, brand, price, status, salesmanId));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading car data.");
        }
        return carList;
    }

}
