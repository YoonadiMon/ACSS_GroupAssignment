package Manager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private final String regNo;
    private String model;
    private String brand;
    private double price;

    public Car(String regNo, String model, String brand, double price) {
        this.regNo = regNo;
        this.model = model;
        this.brand = brand;
        this.price = price;
    }

    // Getters
    public String getRegNo() { return regNo; }
    public String getModel() { return model; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }

    // Setters (except for regNo which is final)
    public void setModel(String model) { this.model = model; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return String.format("Reg No: %s, Model: %s, Brand: %s, Price: RM %.2f",
                regNo, model, brand, price);
    }

    public String toFileString() {
        return String.join(",", regNo, model, brand, String.valueOf(price));
    }

    public static Car fromFileString(String line) throws IllegalArgumentException {
        String[] parts = line.split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid car data format");
        }
        try {
            return new Car(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid price format");
        }
    }
}

public class CarManagement {
    private static final List<Car> carList = new ArrayList<>();
    private static final String FILE_NAME = "carList.txt";

    public static void main(String[] args) {
        loadCarsFromFile();

        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            do {
                displayMenu();
                while (!scanner.hasNextInt()) {
                    System.out.println("Please enter a number!");
                    scanner.next();
                }
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> addCar(scanner);
                    case 2 -> deleteCar(scanner);
                    case 3 -> searchCar(scanner);
                    case 4 -> updateCar(scanner);
                    case 5 -> listAllCars();
                    case 0 -> System.out.println("Exiting and saving data...");
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } while (choice != 0);
        }
        saveCarsToFile();
    }

    private static void displayMenu() {
        System.out.println("\n--- Car Management Menu ---");
        System.out.println("1. Add Car");
        System.out.println("2. Delete Car");
        System.out.println("3. Search Car");
        System.out.println("4. Update Car");
        System.out.println("5. List All Cars");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addCar(Scanner scanner) {
        System.out.print("Enter Registration Number: ");
        String regNo = scanner.nextLine().trim();

        if (findCarByRegNo(regNo) != null) {
            System.out.println("Car with this registration number already exists!");
            return;
        }

        System.out.print("Enter Model: ");
        String model = scanner.nextLine().trim();

        System.out.print("Enter Brand: ");
        String brand = scanner.nextLine().trim();

        System.out.print("Enter Price: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Please enter a valid price!");
            scanner.next();
        }
        double price = scanner.nextDouble();
        scanner.nextLine();

        carList.add(new Car(regNo, model, brand, price));
        System.out.println("Car added successfully.");
    }

    private static void deleteCar(Scanner scanner) {
        System.out.print("Enter Registration Number to delete: ");
        String regNo = scanner.nextLine().trim();

        Car car = findCarByRegNo(regNo);
        if (car != null) {
            carList.remove(car);
            System.out.println("Car deleted successfully.");
        } else {
            System.out.println("Car not found.");
        }
    }

    private static void searchCar(Scanner scanner) {
        System.out.print("Enter Registration Number to search: ");
        String regNo = scanner.nextLine().trim();

        Car car = findCarByRegNo(regNo);
        if (car != null) {
            System.out.println("Car Found: " + car);
        } else {
            System.out.println("Car not found.");
        }
    }

    private static void updateCar(Scanner scanner) {
        System.out.print("Enter Registration Number to update: ");
        String regNo = scanner.nextLine().trim();

        Car car = findCarByRegNo(regNo);
        if (car != null) {
            System.out.print("Enter new Model (leave blank to keep current): ");
            String newModel = scanner.nextLine().trim();
            if (!newModel.isEmpty()) {
                car.setModel(newModel);
            }

            System.out.print("Enter new Brand (leave blank to keep current): ");
            String newBrand = scanner.nextLine().trim();
            if (!newBrand.isEmpty()) {
                car.setBrand(newBrand);
            }

            System.out.print("Enter new Price (leave blank to keep current): ");
            String priceInput = scanner.nextLine().trim();
            if (!priceInput.isEmpty()) {
                try {
                    car.setPrice(Double.parseDouble(priceInput));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format. Price not updated.");
                }
            }

            System.out.println("Car updated successfully.");
        } else {
            System.out.println("Car not found.");
        }
    }

    private static void listAllCars() {
        if (carList.isEmpty()) {
            System.out.println("No cars in inventory.");
        } else {
            System.out.println("\n--- Car Inventory ---");
            carList.forEach(System.out::println);
        }
    }

    private static Car findCarByRegNo(String regNo) {
        return carList.stream()
                .filter(c -> c.getRegNo().equalsIgnoreCase(regNo))
                .findFirst()
                .orElse(null);
    }

    private static void saveCarsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Car car : carList) {
                writer.write(car.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving car data: " + e.getMessage());
        }
    }

    private static void loadCarsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    carList.add(Car.fromFileString(line));
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping invalid car data: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing car file found. Starting fresh.");
        } catch (IOException e) {
            System.err.println("Error loading car data: " + e.getMessage());
        }
    }
}
