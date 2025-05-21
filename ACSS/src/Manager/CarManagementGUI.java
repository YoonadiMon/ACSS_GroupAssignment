package Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public String getRegNo() { return regNo; }
    public String getModel() { return model; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }

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

public class CarManagementGUI {
    private static final List<Car> carList = new ArrayList<>();
    private static final String FILE_NAME = "carList.txt";
    private JFrame frame;
    private JTable carTable;
    private CarTableModel tableModel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new CarManagementGUI().initialize();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error initializing application: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initialize() {
        loadCarsFromFile();

        frame = new JFrame("Car Dealership Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        createMenuBar();
        createMainPanel();

        frame.setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save Data");
        saveItem.addActionListener(_ -> saveCarsToFile());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(_ -> System.exit(0));
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu carMenu = new JMenu("Car Management");
        JMenuItem addItem = new JMenuItem("Add Car");
        addItem.addActionListener(_ -> showAddCarDialog());
        JMenuItem deleteItem = new JMenuItem("Delete Car");
        deleteItem.addActionListener(_ -> deleteCar());
        JMenuItem searchItem = new JMenuItem("Search Car");
        searchItem.addActionListener(_ -> searchCar());
        JMenuItem updateItem = new JMenuItem("Update Car");
        updateItem.addActionListener(_ -> updateCar());
        JMenuItem listItem = new JMenuItem("List All Cars");
        listItem.addActionListener(_ -> listAllCars());

        carMenu.add(addItem);
        carMenu.add(deleteItem);
        carMenu.add(searchItem);
        carMenu.add(updateItem);
        carMenu.addSeparator();
        carMenu.add(listItem);

        menuBar.add(fileMenu);
        menuBar.add(carMenu);

        frame.setJMenuBar(menuBar);
    }

    private void createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tableModel = new CarTableModel(carList);
        carTable = new JTable(tableModel);
        carTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carTable.getColumnModel().getColumn(3).setCellRenderer(new CurrencyRenderer());

        JScrollPane scrollPane = new JScrollPane(carTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addButton = new JButton("Add Car");
        addButton.addActionListener(_ -> showAddCarDialog());
        buttonPanel.add(addButton);

        JButton deleteButton = new JButton("Delete Car");
        deleteButton.addActionListener(_ -> deleteCar());
        buttonPanel.add(deleteButton);

        JButton searchButton = new JButton("Search Car");
        searchButton.addActionListener(_ -> searchCar());
        buttonPanel.add(searchButton);

        JButton updateButton = new JButton("Update Car");
        updateButton.addActionListener(_ -> updateCar());
        buttonPanel.add(updateButton);

        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(_ -> listAllCars());
        buttonPanel.add(refreshButton);

        return buttonPanel;
    }

    private void showAddCarDialog() {
        JDialog dialog = new JDialog(frame, "Add New Car", true);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(frame);

        JTextField regNoField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField brandField = new JTextField();
        JTextField priceField = new JTextField();

        dialog.add(new JLabel("Registration Number:"));
        dialog.add(regNoField);
        dialog.add(new JLabel("Model:"));
        dialog.add(modelField);
        dialog.add(new JLabel("Brand:"));
        dialog.add(brandField);
        dialog.add(new JLabel("Price:"));
        dialog.add(priceField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(_ -> {
            try {
                String regNo = regNoField.getText().trim();
                if (regNo.isEmpty()) {
                    throw new IllegalArgumentException("Registration number cannot be empty");
                }

                if (findCarByRegNo(regNo) != null) {
                    throw new IllegalArgumentException("Car with this registration number already exists");
                }

                String model = modelField.getText().trim();
                String brand = brandField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());

                carList.add(new Car(regNo, model, brand, price));
                tableModel.fireTableDataChanged();
                dialog.dispose();
                JOptionPane.showMessageDialog(frame, "Car added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid price",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(_ -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        dialog.add(new JLabel());
        dialog.add(buttonPanel);

        dialog.setVisible(true);
    }

    private void deleteCar() {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a car to delete",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to delete this car?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            carList.remove(selectedRow);
            tableModel.fireTableDataChanged();
            JOptionPane.showMessageDialog(frame, "Car deleted successfully",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void searchCar() {
        String regNo = JOptionPane.showInputDialog(frame, "Enter Registration Number to search:");
        if (regNo == null || regNo.trim().isEmpty()) return;

        Car car = findCarByRegNo(regNo.trim());
        if (car != null) {
            int index = carList.indexOf(car);
            carTable.setRowSelectionInterval(index, index);
            carTable.scrollRectToVisible(carTable.getCellRect(index, 0, true));
        } else {
            JOptionPane.showMessageDialog(frame, "Car not found",
                    "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateCar() {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a car to update",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Car car = carList.get(selectedRow);

        JDialog dialog = new JDialog(frame, "Update Car", true);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(frame);

        JLabel regNoLabel = new JLabel("Registration Number:");
        JTextField regNoField = new JTextField(car.getRegNo());
        regNoField.setEditable(false);
        JTextField modelField = new JTextField(car.getModel());
        JTextField brandField = new JTextField(car.getBrand());
        JTextField priceField = new JTextField(String.valueOf(car.getPrice()));

        dialog.add(regNoLabel);
        dialog.add(regNoField);
        dialog.add(new JLabel("Model:"));
        dialog.add(modelField);
        dialog.add(new JLabel("Brand:"));
        dialog.add(brandField);
        dialog.add(new JLabel("Price:"));
        dialog.add(priceField);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(_ -> {
            try {
                String model = modelField.getText().trim();
                String brand = brandField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());

                car.setModel(model);
                car.setBrand(brand);
                car.setPrice(price);

                tableModel.fireTableDataChanged();
                dialog.dispose();
                JOptionPane.showMessageDialog(frame, "Car updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid price",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(_ -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        dialog.add(new JLabel());
        dialog.add(buttonPanel);

        dialog.setVisible(true);
    }

    private void listAllCars() {
        tableModel.fireTableDataChanged();
    }

    private Car findCarByRegNo(String regNo) {
        return carList.stream()
                .filter(c -> c.getRegNo().equalsIgnoreCase(regNo))
                .findFirst()
                .orElse(null);
    }

    private void saveCarsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Car car : carList) {
                writer.write(car.toFileString());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(frame, "Data saved successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving car data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCarsFromFile() {
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
            JOptionPane.showMessageDialog(frame, "Error loading car data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class CarTableModel extends javax.swing.table.AbstractTableModel {
        private final List<Car> carList;
        private final String[] columnNames = {"Reg No", "Model", "Brand", "Price"};

        public CarTableModel(List<Car> carList) {
            this.carList = carList;
        }

        @Override
        public int getRowCount() {
            return carList.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Car car = carList.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> car.getRegNo();
                case 1 -> car.getModel();
                case 2 -> car.getBrand();
                case 3 -> car.getPrice();
                default -> null;
            };
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 3 ? Double.class : String.class;
        }
    }

    private static class CurrencyRenderer extends javax.swing.table.DefaultTableCellRenderer {
        private final java.text.NumberFormat currencyFormat = java.text.NumberFormat.getCurrencyInstance();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Double) {
                value = currencyFormat.format(value);
            }
            return super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
        }
    }
}