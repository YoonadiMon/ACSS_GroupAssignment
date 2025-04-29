/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Car;

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

        saveSalesmanDataToFile(carList);

    }

    public static void saveSalesmanDataToFile(ArrayList<Car> carList) {
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

}
