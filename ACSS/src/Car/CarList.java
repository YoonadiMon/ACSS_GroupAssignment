/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Car;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class CarList {

    public static ArrayList<Car> carList = new ArrayList<>();

    public static void initializeCars() {

        carList.add(new Car("C001", "Toyota", 10000, "available", "S001"));
        carList.add(new Car("C002", "Honda", 20000, "available", "S001"));
        carList.add(new Car("C003", "Myvi", 15000, "available", "S002"));
        carList.add(new Car("C004", "BMW", 100000, "available", "S002"));
        carList.add(new Car("C005", "Mercedes", 120000, "paid", "S001"));
        carList.add(new Car("C007", "Hyundai", 22000, "available", "S001"));
        carList.add(new Car("C008", "Proton", 13000, "paid", "S002"));
        carList.add(new Car("C010", "Mazda", 27000, "available", "S002"));
        carList.add(new Car("C012", "Ford", 24000, "paid", "S001"));
        carList.add(new Car("C014", "Tesla", 150000, "available", "S002"));

    }

    public static void saveInitializedCarListToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("carList.txt"))) {
            for (Car car : carList) {
                writer.write(car.getCarId() + "," + car.getBrand() + "," + car.getPrice() + ","
                        + car.getStatus() + "," + car.getSalesmanId());
                writer.newLine();
            }
            System.out.println("Car list saved to file.");
        } catch (IOException e) {
            System.out.println("Problem with file output.");
        }
    }

    public static void saveUpdatedCarToFile(ArrayList<Car> updatedCars) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("CarList.txt"))) {
            for (Car car : updatedCars) {
                writer.write(car.getCarId() + "," + car.getBrand() + "," + car.getPrice() + ","
                        + car.getStatus() + "," + car.getSalesmanId());
                writer.newLine();

            }
        } catch (IOException e) {
            System.out.println("Error writing to CarList.txt: " + e.getMessage());
        }
    }

    public static ArrayList<Car> loadCarDataFromFile() {
        ArrayList<Car> loadedList = new ArrayList<>();
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
                    loadedList.add(new Car(carId, brand, price, status, salesmanId));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading car data.");
        }
        return loadedList;
    }

    //  getter if you need the current list elsewhere
    public static ArrayList<Car> getCarList() {
        return carList;
    }

    public static void filterCars(String searchInput, ArrayList<Car> carList) {
        boolean found = false;

        for (Car car : carList) {
            if (car.getCarId().equalsIgnoreCase(searchInput) || car.getBrand().equalsIgnoreCase(searchInput) || car.getStatus().equalsIgnoreCase(searchInput)) {
                System.out.println("Car ID: " + car.getCarId());
                System.out.println("Brand: " + car.getBrand());
                System.out.println("Price: " + car.getPrice());
                System.out.println("Status: " + car.getStatus());
                System.out.println("Salesman ID: " + car.getSalesmanId());
                System.out.println("----------------------------------");
                found = true;

            }
        }

        if (!found) {
            System.out.println("No car found matching input: " + searchInput);
        }

    }
}
