package Manager;

import Car.CarList;
import Car.Car;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManageCarInventory extends JFrame {
    private List<Car> carList = new ArrayList<>();
    private static final String FILE_NAME = "data/CarList.txt";
    private Object manager; // Store the manager object to pass back

    // GUI Components
    private JTextArea outputArea;
    private JTextField carIdField, brandField, priceField, statusField, salesmanIdField, searchCarIdField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton searchButton;
    private JButton listButton;

    public ManageCarInventory(Object manager) {
        super("Car Inventory Management System");
        this.manager = manager;
        initialize(); // Initialize GUI first
        carList = CarList.loadCarDataFromFile();// Then load data
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // Add window listener to handle closing event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                returnToManagerDashboard();
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Car Information"));

        // Search Car ID Field
        panel.add(new JLabel("Search by Car ID:"));
        searchCarIdField = new JTextField();
        searchCarIdField.setToolTipText("Enter Car ID to search, update, or delete");
        panel.add(searchCarIdField);

        // Car ID Field
        panel.add(new JLabel("Car ID:"));
        carIdField = new JTextField();
        carIdField.setToolTipText("Enter unique Car ID");
        panel.add(carIdField);

        // Brand Field
        panel.add(new JLabel("Brand:"));
        brandField = new JTextField();
        panel.add(brandField);

        // Price Field
        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        // Status Field
        panel.add(new JLabel("Status:"));
        statusField = new JTextField();
        statusField.setToolTipText("e.g., Available, Sold, Reserved");
        panel.add(statusField);

        // Salesman ID Field
        panel.add(new JLabel("Assign Salesman:"));
        salesmanIdField = new JTextField();
        salesmanIdField.setToolTipText("Enter Salesman ID to assign");
        panel.add(salesmanIdField);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add Button
        addButton = new JButton("Add Car");
        addButton.addActionListener(this::addCar);
        panel.add(addButton);

        // Delete Button
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this::deleteCar);
        panel.add(deleteButton);

        // Search Button
        searchButton = new JButton("Search");
        searchButton.addActionListener(this::searchCar);
        panel.add(searchButton);

        // Update Button
        updateButton = new JButton("Update");
        updateButton.addActionListener(this::updateCar);
        panel.add(updateButton);

        // List All Button
        listButton = new JButton("List All");
        listButton.addActionListener(this::listAllCars);
        panel.add(listButton);

        // Back Button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> returnToManagerDashboard());
        panel.add(backButton);

        return panel;
    }

    private void addCar(ActionEvent e) {
        String carId = carIdField.getText().trim();
        String brand = brandField.getText().trim();
        String priceStr = priceField.getText().trim();
        String status = statusField.getText().trim();
        String salesmanId = salesmanIdField.getText().trim();

        // Validation
        if (carId.isEmpty() || brand.isEmpty() || priceStr.isEmpty() || status.isEmpty()) {
            showMessage("Please fill in all required fields (Car ID, Brand, Price, Status)");
            return;
        }

        // Check if car ID already exists
        if (findCarById(carId) != null) {
            showMessage("Car ID already exists. Please use a different ID.");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);

            // Create new car object
            Car newCar = new Car(carId, brand, price, status, salesmanId);
            carList.add(newCar);

            // Save to file
            saveCarsToFile();

            showMessage("Car added successfully.");
            clearFields();
        } catch (NumberFormatException ex) {
            showMessage("Invalid price format. Please enter a valid number.");
        }
    }

    private void deleteCar(ActionEvent e) {
        String carId = searchCarIdField.getText().trim();
        if (carId.isEmpty()) {
            showMessage("Please enter Car ID");
            return;
        }

        Car found = findCarById(carId);
        if (found != null) {
            carList.remove(found);
            saveCarsToFile();
            showMessage("Car deleted successfully.");
            clearFields();
        } else {
            showMessage("Car not found.");
        }
    }

    private void searchCar(ActionEvent e) {
        String carId = searchCarIdField.getText().trim();
        if (carId.isEmpty()) {
            showMessage("Please enter Car ID");
            return;
        }

        Car found = findCarById(carId);
        if (found != null) {
            outputArea.setText("Car Found:\n" + formatCarInfo(found));
            carIdField.setText(found.getCarId());
            brandField.setText(found.getBrand());
            priceField.setText(String.valueOf(found.getPrice()));
            statusField.setText(found.getStatus());
            salesmanIdField.setText(found.getSalesmanId() != null ? found.getSalesmanId() : "");
        } else {
            showMessage("Car not found.");
        }
    }

    private void updateCar(ActionEvent e) {
        String carId = searchCarIdField.getText().trim();
        if (carId.isEmpty()) {
            showMessage("Please enter Car ID");
            return;
        }

        Car found = findCarById(carId);
        if (found != null) {
            String newBrand = brandField.getText().trim();
            if (!newBrand.isEmpty()) {
                found.setBrand(newBrand);
            }

            String newPriceStr = priceField.getText().trim();
            if (!newPriceStr.isEmpty()) {
                try {
                    double newPrice = Double.parseDouble(newPriceStr);
                    found.setPrice(newPrice);
                } catch (NumberFormatException ex) {
                    showMessage("Invalid price format. Price not updated.");
                    return;
                }
            }

            String newStatus = statusField.getText().trim();
            if (!newStatus.isEmpty()) {
                found.setStatus(newStatus);
            }

            String newSalesmanId = salesmanIdField.getText().trim();
            found.setSalesmanId(newSalesmanId);

            saveCarsToFile();
            showMessage("Car information updated.");
        } else {
            showMessage("Car not found.");
        }
    }

    private void listAllCars(ActionEvent e) {
        if (carList.isEmpty()) {
            showMessage("No cars to show.");
        } else {
            StringBuilder sb = new StringBuilder("--- List of All Cars ---\n");
            carList.forEach(c -> sb.append(formatCarInfo(c)).append("\n\n"));
            outputArea.setText(sb.toString());
        }
    }

    private Car findCarById(String carId) {
        return carList.stream()
                .filter(c -> c.getCarId().equalsIgnoreCase(carId))
                .findFirst()
                .orElse(null);
    }

    private String formatCarInfo(Car car) {
        double price = car.getPrice();
        return String.format("Car ID: %s\nBrand: %s\nPrice: $%.2f\nStatus: %s\nAssigned Salesman: %s",
                car.getCarId(),
                car.getBrand(),
                (double) car.getPrice(),
                car.getStatus(),
                (car.getSalesmanId() != null && !car.getSalesmanId().isEmpty()) ? car.getSalesmanId() : "Not Assigned");
    }

    private void loadCarsFromFile() {
        carList.clear();
        File file = new File(FILE_NAME);

        // Create directory if it doesn't exist
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            showMessage("No existing car data found. Starting with empty inventory.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int loadedCount = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    try {
                        Car car = parseCarFromLine(line);
                        carList.add(car);
                        loadedCount++;
                    } catch (Exception ex) {
                        System.err.println("Error parsing line: " + line + " - " + ex.getMessage());
                    }
                }
            }
            showMessage("Loaded " + loadedCount + " cars.");
        } catch (IOException e) {
            showMessage("Error loading cars from file: " + e.getMessage());
        }
    }

    private Car parseCarFromLine(String line) {
        String[] parts = line.split(",");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid car data format. Expected at least 4 fields, got " + parts.length);
        }

        String carId = parts[0].trim();
        String brand = parts[1].trim();
        double price = Double.parseDouble(parts[2].trim());
        String status = parts[3].trim();
        String salesmanId = parts.length > 4 ? parts[4].trim() : "";

        return new Car(carId, brand, price, status, salesmanId);
    }

    private void saveCarsToFile() {
        File file = new File(FILE_NAME);

        // Create directory if it doesn't exist
        file.getParentFile().mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Car car : carList) {
                writer.println(car.toString());
            }
        } catch (IOException e) {
            showMessage("Error saving cars to file: " + e.getMessage());
        }
    }

    private void clearFields() {
        searchCarIdField.setText("");
        carIdField.setText("");
        brandField.setText("");
        priceField.setText("");
        statusField.setText("");
        salesmanIdField.setText("");
    }

    private void showMessage(String message) {
        if (outputArea != null) {
            outputArea.setText(message);
        } else {
            System.out.println(message); // Fallback for early initialization messages
        }
    }

    private void returnToManagerDashboard() {
        try {
            // Close current window
            this.dispose();

            // Create and show ManagerDashboard using reflection
            Class<?> managerDashboardClass = Class.forName("Manager.ManagerDashboard");

            // Try different constructor signatures
            java.lang.reflect.Constructor<?> constructor = null;
            try {
                // Try with the actual manager class type
                constructor = managerDashboardClass.getConstructor(manager.getClass());
            } catch (NoSuchMethodException e1) {
                try {
                    // Try with Object parameter
                    constructor = managerDashboardClass.getConstructor(Object.class);
                } catch (NoSuchMethodException e2) {
                    try {
                        // Try looking for any single-parameter constructor
                        java.lang.reflect.Constructor<?>[] constructors = managerDashboardClass.getConstructors();
                        for (java.lang.reflect.Constructor<?> c : constructors) {
                            if (c.getParameterCount() == 1) {
                                constructor = c;
                                break;
                            }
                        }
                    } catch (Exception e3) {
                        throw new Exception("No suitable constructor found");
                    }
                }
            }

            if (constructor != null) {
                constructor.newInstance(manager);
            } else {
                throw new Exception("No suitable constructor found");
            }

        } catch (Exception e) {
            // Fallback - try simpler approach or just close
            System.err.println("Could not return to ManagerDashboard: " + e.getMessage());
            e.printStackTrace();

            // Alternative: Try to call it directly if possible
            try {
                SwingUtilities.invokeLater(() -> {
                    // This will work if ManagerDashboard is in the classpath
                    // You might need to replace this with the actual constructor call
                    System.out.println("Attempting to create ManagerDashboard with manager: " + manager);
                    // new Manager.ManagerDashboard(manager); // Uncomment and fix type if needed
                });
            } catch (Exception fallbackError) {
                System.err.println("Fallback also failed: " + fallbackError.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("data/carList.txt");
        if (!file.exists()) {
            CarList.initializeCars();
            CarList.saveInitializedCarListToFile();
        }

        SwingUtilities.invokeLater(() -> new ManageCarInventory(null));
    }

    JPanel getPanel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}