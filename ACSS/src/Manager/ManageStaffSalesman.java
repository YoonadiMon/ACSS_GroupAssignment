package Manager;

import Car.Car;
import Car.CarList;
import Car.CarRequest;
import Salesman.Salesman;
import Salesman.SalesmanList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManageStaffSalesman extends JFrame {

    private List<Salesman> salesmanList;
    private static final String FILE_NAME = "data/salesmanList.txt";
    private Object manager;

    private static final String DELETED_SALESMAN_FILE = "data/deletedSalesman.txt";

    // GUI Components
    private JTextField idField, nameField, searchIdField, assignedCarField;
    private JTextArea outputArea;
    private JButton addButton, deleteButton, updateButton, searchButton, listButton;

    public ManageStaffSalesman(Object manager) {
        super("Staff & Salesman Management");
        this.manager = manager;
        initialize();
        loadFromFile();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                returnToManagerDashboard();
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();
        outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Salesman Info"));

        panel.add(new JLabel("Search by ID:"));
        searchIdField = new JTextField();
        panel.add(searchIdField);

        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Assigned Car(s):"));
        assignedCarField = new JTextField();
        assignedCarField.setEditable(false);
        panel.add(assignedCarField);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        addButton = new JButton("Add");
        addButton.addActionListener(this::addSalesman);
        panel.add(addButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this::deleteSalesman);
        panel.add(deleteButton);

        searchButton = new JButton("Search");
        searchButton.addActionListener(this::searchSalesman);
        panel.add(searchButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(this::updateSalesman);
        panel.add(updateButton);

        listButton = new JButton("List All");
        listButton.addActionListener(this::listAll);
        panel.add(listButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> returnToManagerDashboard());
        panel.add(backButton);

        return panel;
    }

    private void addSalesman(ActionEvent e) {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            showMessage("Please fill in both ID and Name.");
            return;
        }

        if (findById(id) != null) {
            showMessage("ID already exists.");
            return;
        }

        salesmanList.add(new Salesman(id, name, "password123", "Default question", "Default answer"));
        saveToFile();
        showMessage("Salesman added successfully.");
        clearFields();
    }

    private void deleteSalesman(ActionEvent e) {
        String id = searchIdField.getText().trim();
        Salesman salesman = findById(id);
        if (salesman != null) {
            // Log the deleted salesman
            saveDeletedSalesmanToFile(salesman);

            // Remove and save
            salesmanList.remove(salesman);
            saveToFile();
            showMessage("Deleted successfully and logged to deletedSalesman.txt.");
            clearFields();
        } else {
            showMessage("ID not found.");
        }
    }

    private void saveDeletedSalesmanToFile(Salesman s) {
        File file = new File(DELETED_SALESMAN_FILE);
        file.getParentFile().mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) { // append mode
            writer.println(s.getID() + "," + s.getName() + "," + s.getPassword());
        } catch (IOException e) {
            System.out.println("Failed to log deleted salesman: " + e.getMessage());
        }
    }

    private void searchSalesman(ActionEvent e) {
        String id = searchIdField.getText().trim();
        Salesman salesman = findById(id);
        if (salesman != null) {
            ArrayList<Car> cars = CarList.loadCarDataFromFile();
            List<String> assignedCars = new ArrayList<>();

            for (Car car : cars) {
                if (car.getSalesmanId().equalsIgnoreCase(salesman.getID())) {
                    assignedCars.add(car.getCarId());
                }
            }

            String carIds = assignedCars.isEmpty() ? "None" : String.join(", ", assignedCars);

            outputArea.setText("Salesman Found:\nID: " + salesman.getID() + " | Name: " + salesman.getName()
                    + "\nAssigned Car(s): " + carIds);
            idField.setText(salesman.getID());
            nameField.setText(salesman.getName());
            assignedCarField.setText(carIds);
        } else {
            showMessage("ID not found.");
        }
    }

    private void updateSalesman(ActionEvent e) {
        String id = searchIdField.getText().trim();
        Salesman salesman = findById(id);
        if (salesman != null) {
            String newName = nameField.getText().trim();
            if (!newName.isEmpty()) {
                salesman.setName(newName);
                saveToFile();
                showMessage("Updated successfully.");
            }
        } else {
            showMessage("ID not found.");
        }
    }

    private void listAll(ActionEvent e) {
        if (salesmanList == null || salesmanList.isEmpty()) {
            showMessage("No salesmen to show.");
        } else {
            StringBuilder sb = new StringBuilder("--- Salesman List ---\n");
            ArrayList<Car> cars = CarList.loadCarDataFromFile();
            for (Salesman s : salesmanList) {
                List<String> carIds = new ArrayList<>();
                for (Car c : cars) {
                    if (c.getSalesmanId().equalsIgnoreCase(s.getID())) {
                        carIds.add(c.getCarId());
                    }
                }
                sb.append("ID: ").append(s.getID())
                        .append(" | Name: ").append(s.getName())
                        .append(" | Assigned Car(s): ")
                        .append(carIds.isEmpty() ? "None" : String.join(", ", carIds))
                        .append("\n");
            }
            outputArea.setText(sb.toString());
        }
    }

    private void showMessage(String msg) {
        outputArea.setText(msg);
    }

    private void clearFields() {
        searchIdField.setText("");
        idField.setText("");
        nameField.setText("");
        assignedCarField.setText("");
    }

    private Salesman findById(String id) {
        return salesmanList.stream()
                .filter(s -> s.getID().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    private void loadFromFile() {
        salesmanList = SalesmanList.loadSalesmanDataFromFile();
        showMessage("Loaded " + salesmanList.size() + " records.");
    }

    private void saveToFile() {
        SalesmanList.saveSalesmanDataToFile(new ArrayList<>(salesmanList));
    }

    private void returnToManagerDashboard() {
        dispose();
        try {
            Class<?> managerDashboardClass = Class.forName("Manager.ManagerDashboard");
            java.lang.reflect.Constructor<?> constructor = managerDashboardClass.getConstructor(manager.getClass());
            constructor.newInstance(manager);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateRelatedCarDataAfterDeletion(String salesmanId) {
        // Update car requests
        ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();
        ArrayList<CarRequest> updatedRequests = new ArrayList<>();

        for (CarRequest request : requests) {
            if (request.getSalesmanID().equalsIgnoreCase(salesmanId)) {
                String currentStatus = request.getRequestStatus().toLowerCase();
                String newStatus = request.getRequestStatus();
                String comment = request.getComment();

                // Skip if already paid or sold
                if (!currentStatus.equals("paid") && !currentStatus.equals("sold")) {
                    if ("pending".equalsIgnoreCase(currentStatus)) {
                        newStatus = "rejected";
                        comment = "Request rejected - salesman " + salesmanId + " has been deleted";
                    } else if ("booked".equalsIgnoreCase(currentStatus)) {
                        newStatus = "cancelled";
                        comment = "Booking cancelled - salesman " + salesmanId + " has been deleted";
                    }
                }

                updatedRequests.add(new CarRequest(
                        request.getCustomerID(),
                        request.getCarID(),
                        request.getSalesmanID(),
                        newStatus,
                        comment
                ));
            } else {
                updatedRequests.add(request);
            }
        }

        // Save updated requests
        CarRequest.writeCarRequests(updatedRequests);

        // Update car list - set status to available for cars assigned to this salesman
        ArrayList<Car> cars = CarList.loadCarDataFromFile();
        ArrayList<Car> updatedCars = new ArrayList<>();

        for (Car car : cars) {
            if (car.getSalesmanId().equalsIgnoreCase(salesmanId)) {
                String currentStatus = car.getStatus().toLowerCase();
                String newStatus = car.getStatus();

                // Only update if not paid or sold
                if (!currentStatus.equals("paid") && !currentStatus.equals("sold")) {
                    newStatus = "available";
                }

                updatedCars.add(new Car(
                        car.getCarId(),
                        car.getBrand(),
                        car.getPrice(),
                        newStatus,
                        car.getSalesmanId() // You might want to clear this too or set to "unassigned"
                ));
            } else {
                updatedCars.add(car);
            }
        }

        // Save updated cars
        CarList.saveUpdatedCarToFile(updatedCars);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new ManageStaffSalesman(null));
//    }
}
