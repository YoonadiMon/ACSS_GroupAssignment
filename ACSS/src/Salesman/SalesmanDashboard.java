/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Salesman;

import Car.Car;
import MainProgram.MainMenuGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Utils.WindowNav;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import Car.CarList;
import Car.CarRequest;
import static Car.CarRequest.carRequestsList;
import Manager.SalesmanList;
import static Manager.SalesmanList.loadSalesmanDataFromFile;
import static Manager.SalesmanList.salesmanList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class SalesmanDashboard implements ActionListener {

    private Salesman currentSalesman;

    private JFrame frame;
    private JButton editProfileButton, viewCarsButton, viewCarRequestButton, updateCarStatusButton, recordSalesHistory, logoutButton;

    public SalesmanDashboard(Salesman salesman) {
        this.currentSalesman = salesman;

        System.out.println("Logged in as: " + currentSalesman.getID());

        frame = new JFrame("Salesman Dashboard");
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));

        editProfileButton = new JButton("Edit Profile");
        viewCarsButton = new JButton("View Car Status");
        viewCarRequestButton = new JButton("View Car Request");
        updateCarStatusButton = new JButton("Update Car Status");
        recordSalesHistory = new JButton("View Sales History");
        logoutButton = new JButton("Logout");

        editProfileButton.addActionListener(this);
        viewCarsButton.addActionListener(this);
        updateCarStatusButton.addActionListener(this);
        recordSalesHistory.addActionListener(this);
        logoutButton.addActionListener(this);
        viewCarRequestButton.addActionListener(this);

        frame.add(editProfileButton);
        frame.add(viewCarsButton);
        frame.add(viewCarRequestButton);
        frame.add(updateCarStatusButton);
        frame.add(recordSalesHistory);
        frame.add(logoutButton);

        WindowNav.setCloseOperation(frame, () -> new MainMenuGUI());

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == editProfileButton) {
            frame.dispose();
            openEditProfileWindow();
        } else if (e.getSource() == viewCarsButton) {
            frame.dispose();
            viewCarStatusWindow();
        } else if (e.getSource() == viewCarRequestButton) {
            frame.dispose();
            viewCarRequestWindow();
        } else if (e.getSource() == updateCarStatusButton) {
            frame.dispose();
            updateCarStatusWindow();
        } else if (e.getSource() == recordSalesHistory) {
            JOptionPane.showMessageDialog(frame, "Car status functionality to be implemented!");
        } else if (e.getSource() == logoutButton) {
            frame.dispose();
            new SalesmanGUI(400, 250);  // Back to login
        }
    }

    private void openEditProfileWindow() {
        JFrame editProfileFrame = new JFrame("Edit Profile");
        editProfileFrame.setSize(400, 350); // Adjusted size to fit the extra fields
        editProfileFrame.setLocationRelativeTo(null);
        editProfileFrame.setLayout(null); // Use absolute positioning

        // Title label centered at the top
        JLabel title = new JLabel("--- Edit Salesman Profile ---");
        title.setBounds(100, 20, 200, 30);  // Manually set the bounds
        editProfileFrame.add(title);

        // Name label and input field
        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 70, 120, 20);  // Width 120 for alignment
        JTextField nameField = new JTextField(20);
        nameField.setBounds(180, 70, 150, 20); // Same Y as label

// Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 120, 20);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(180, 100, 150, 20);

// Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(50, 130, 120, 20);
        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setBounds(180, 130, 150, 20);

        // Show password checkbox
        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setBounds(150, 160, 150, 20);  // Position the checkbox below password fields

        // Action listener for the checkbox to toggle password visibility
        showPasswordCheckbox.addActionListener(e -> {
            if (showPasswordCheckbox.isSelected()) {
                // Set password field visibility to true
                passwordField.setEchoChar((char) 0);  // Shows the password
                confirmPasswordField.setEchoChar((char) 0);  // Shows the confirm password
            } else {
                // Set password field visibility to false
                passwordField.setEchoChar('*');  // Hides the password (default)
                confirmPasswordField.setEchoChar('*');  // Hides the confirm password
            }
        });

        // Add the labels, text fields, and checkbox
        editProfileFrame.add(nameLabel);
        editProfileFrame.add(nameField);
        editProfileFrame.add(passwordLabel);
        editProfileFrame.add(passwordField);
        editProfileFrame.add(confirmPasswordLabel);
        editProfileFrame.add(confirmPasswordField);
        editProfileFrame.add(showPasswordCheckbox);

        // Save changes button
        JButton saveButton = new JButton("Save Changes");
        saveButton.setBounds(80, 200, 110, 30); // Set position of the button

        saveButton.addActionListener(e -> {
            String newName = nameField.getText().trim();
            String newPassword = String.valueOf(passwordField.getPassword()).trim();
            String confirmPassword = String.valueOf(confirmPasswordField.getPassword()).trim();
            if (newName.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(editProfileFrame, "Name and password cannot be empty.");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(editProfileFrame, "Passwords do not match. Please try again.");
                return;
            }

//            ArrayList<Salesman> salesmanList = loadSalesmanDataFromFile(); // Load existing data
            ArrayList<Salesman> updatedList = SalesmanList.loadSalesmanDataFromFile();

            // Find and update current salesman
            for (int i = 0; i < updatedList.size(); i++) {
                if (updatedList.get(i).getID().equals(currentSalesman.ID)) {
                    updatedList.set(i, new Salesman(currentSalesman.ID, newName, newPassword));
                    System.out.println("Saved Edited " + currentSalesman.ID + " to file");
                    break;
                }
            }

            // Save updated list back to file
//            saveEditedSalesmanDataToFile(salesmanList);
            saveEditedSalesmanDataToFile(updatedList);

            JOptionPane.showMessageDialog(editProfileFrame, "Changes Saved!");
            editProfileFrame.dispose();
        });

        // Add the save button
        editProfileFrame.add(saveButton);

        JButton closeButton = new JButton("Go Back");
        closeButton.setBounds(210, 200, 90, 30);
        closeButton.addActionListener(e -> {
            editProfileFrame.dispose();
            new SalesmanDashboard(currentSalesman);
        });
        editProfileFrame.add(closeButton);

        // Make the frame visible
        editProfileFrame.setVisible(true);
    }

    public static void saveEditedSalesmanDataToFile(ArrayList<Salesman> updatedList) {
        // Save to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("salesmenList.txt"))) {
            for (Salesman salesman : updatedList) {
                writer.write(salesman.getID() + "," + salesman.getName() + "," + salesman.getPassword());
                writer.newLine(); // Move to next line
            }
            System.out.println("Changed Salesmen data saved to file.");
        } catch (IOException e) {
            System.out.println("Problem with file output.");
        }
    }

    private void viewCarStatusWindow() {
        // Create new frame
        JFrame carListFrame = new JFrame("Cars Assigned to Salesman " + currentSalesman.ID);
        carListFrame.setSize(600, 400);
        carListFrame.setLocationRelativeTo(null);
        carListFrame.setLayout(new BorderLayout(10, 10));

        // Title label
        JLabel titleLabel = new JLabel("Cars Assigned to Salesman " + currentSalesman.ID, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        carListFrame.add(titleLabel, BorderLayout.NORTH);

        // Table setup
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Car ID", "Brand", "Price", "Status"}, 0);
        JTable carTable = new JTable(tableModel);
        carTable.setEnabled(false); // Read-only

        // Function to load all assigned cars
        Runnable loadAllCars = () -> {
            tableModel.setRowCount(0);
            ArrayList<Car> allCars = CarList.loadCarDataFromFile();
            boolean found = false;
            for (Car car : allCars) {
                if (car.getSalesmanId().equals(currentSalesman.ID)) {
                    tableModel.addRow(new Object[]{car.getCarId(), car.getBrand(), car.getPrice(), car.getStatus()});
                    found = true;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(carListFrame,
                        "No cars assigned to Salesman ID: " + currentSalesman.ID,
                        "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        // Load all cars initially
        loadAllCars.run();

        // Add table in scroll pane
        JScrollPane scrollPane = new JScrollPane(carTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        carListFrame.add(scrollPane, BorderLayout.CENTER);

        // Search bar and button
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JLabel searchLabel = new JLabel("Search:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        carListFrame.add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);

        // Search button action
        searchButton.addActionListener(e -> {
            String searchInput = searchField.getText().trim();
            if (!searchInput.isEmpty()) {
                ArrayList<Car> carList = CarList.loadCarDataFromFile();
                ArrayList<Car> filteredCars = new ArrayList<>();

                for (Car car : carList) {
                    if (car.getSalesmanId().equals(currentSalesman.ID)
                            && (car.getCarId().equalsIgnoreCase(searchInput)
                            || car.getBrand().equalsIgnoreCase(searchInput)
                            || car.getStatus().equalsIgnoreCase(searchInput))) {
                        filteredCars.add(car);
                    }
                }

                tableModel.setRowCount(0);

                if (!filteredCars.isEmpty()) {
                    for (Car car : filteredCars) {
                        tableModel.addRow(new Object[]{
                            car.getCarId(), car.getBrand(), car.getPrice(), car.getStatus()
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(carListFrame,
                            "No matching car found for input: " + searchInput,
                            "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    searchField.setText("");
                    loadAllCars.run(); // Reset table to show all data
                }

            } else {
                JOptionPane.showMessageDialog(carListFrame,
                        "Please enter a Car ID, Brand, or Status to search.",
                        "Search Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Close button
        JButton closeButton = new JButton("Go Back");
        closeButton.addActionListener(e -> {
            carListFrame.dispose();
            new SalesmanDashboard(currentSalesman);
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(closeButton);
        carListFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Display the window
        carListFrame.setVisible(true);
    }

    public void viewCarRequestWindow() {
        JFrame requestFrame = new JFrame("Car Requests");
        requestFrame.setSize(600, 400);
        requestFrame.setLocationRelativeTo(null);
        requestFrame.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("All Car Requests", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        requestFrame.add(titleLabel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"Customer ID", "Car ID", "Status", "Comment"}, 0
        );
        JTable requestTable = new JTable(tableModel);
        requestTable.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(requestTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        requestFrame.add(scrollPane, BorderLayout.CENTER);

        // Search bar and button
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        requestFrame.add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);

        // Function to load all data
        Runnable loadAllRequests = () -> {
            tableModel.setRowCount(0); // Clear table
            ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();
            for (CarRequest req : requests) {
                if (req.getSalesmanID().equals(currentSalesman.ID)) {
                    tableModel.addRow(new Object[]{
                        req.getCustomerID(),
                        req.getCarID(),
                        req.getRequestStatus(),
                        req.getComment()
                    });
                }
            }
        };

        // Load all data initially
        loadAllRequests.run();

        // Search button action
        searchButton.addActionListener(e -> {
            String searchInput = searchField.getText().trim();
            if (!searchInput.isEmpty()) {
                ArrayList<CarRequest> requestList = CarRequest.loadCarRequestDataFromFile();
                ArrayList<CarRequest> filteredRequests = new ArrayList<>();

                for (CarRequest req : requestList) {
                    if (req.getSalesmanID().equals(currentSalesman.ID) && (req.getCustomerID().equalsIgnoreCase(searchInput)
                            || req.getCarID().equalsIgnoreCase(searchInput)
                            || req.getRequestStatus().equalsIgnoreCase(searchInput)
                            || req.getComment().toLowerCase().contains(searchInput.toLowerCase()))) {
                        filteredRequests.add(req);
                    }
                }

                tableModel.setRowCount(0);

                if (!filteredRequests.isEmpty()) {
                    for (CarRequest req : filteredRequests) {
                        tableModel.addRow(new Object[]{
                            req.getCustomerID(),
                            req.getCarID(),
                            req.getRequestStatus(),
                            req.getComment()
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(requestFrame,
                            "No matching request found for input: " + searchInput,
                            "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    searchField.setText("");
                    loadAllRequests.run();
                }

            } else {
                JOptionPane.showMessageDialog(requestFrame,
                        "Please enter a keyword to search.",
                        "Search Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Close button
        JButton closeButton = new JButton("Go Back");
        closeButton.addActionListener(e -> {
            requestFrame.dispose();
            new SalesmanDashboard(currentSalesman);
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(closeButton);
        requestFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Display the window
        requestFrame.setVisible(true);
    }

    public void updateCarStatusWindow() {
        JFrame updateFrame = new JFrame("Update Car Status");
        updateFrame.setSize(600, 400);
        updateFrame.setLocationRelativeTo(null);
        updateFrame.setLayout(new BorderLayout(10, 10));

        // Title label
        JLabel titleLabel = new JLabel("All Car Requests", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        updateFrame.add(titleLabel, BorderLayout.NORTH);

        // Table with only Customer ID and Car ID
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"Customer ID", "Car ID"}, 0
        );
        JTable requestTable = new JTable(tableModel);
        requestTable.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(requestTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        updateFrame.add(scrollPane, BorderLayout.CENTER);

        // Search bar and button
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        searchPanel.add(new JLabel("Search (Customer ID / Car ID):"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        updateFrame.add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);

        // Input field and approve/reject buttons
        JTextField carIDField = new JTextField(15);
        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        inputPanel.add(new JLabel("Enter Car ID:"));
        inputPanel.add(carIDField);
        inputPanel.add(approveBtn);
        inputPanel.add(rejectBtn);
        updateFrame.add(inputPanel, BorderLayout.SOUTH);

        // Load all requests
        Runnable loadAllRequests = () -> {
            tableModel.setRowCount(0); // Clear table
            ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();
            for (CarRequest req : requests) {
                if (req.getSalesmanID().equals(currentSalesman.ID)) {
                    tableModel.addRow(new Object[]{
                        req.getCustomerID(),
                        req.getCarID()
                    });
                }
            }
        };
        loadAllRequests.run();

        // Search logic
        searchButton.addActionListener(e -> {
            String searchInput = searchField.getText().trim();
            if (!searchInput.isEmpty()) {
                ArrayList<CarRequest> requestList = CarRequest.loadCarRequestDataFromFile();
                ArrayList<CarRequest> filteredRequests = new ArrayList<>();

                for (CarRequest req : requestList) {
                    if (req.getSalesmanID().equals(currentSalesman.ID)
                            && (req.getCustomerID().equalsIgnoreCase(searchInput)
                            || req.getCarID().equalsIgnoreCase(searchInput))) {
                        filteredRequests.add(req);
                    }
                }

                tableModel.setRowCount(0);
                if (!filteredRequests.isEmpty()) {
                    for (CarRequest req : filteredRequests) {
                        tableModel.addRow(new Object[]{
                            req.getCustomerID(),
                            req.getCarID()
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(updateFrame,
                            "No matching request found for input: " + searchInput,
                            "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    searchField.setText("");
                    loadAllRequests.run();
                }
            } else {
                JOptionPane.showMessageDialog(updateFrame,
                        "Please enter a keyword to search.",
                        "Search Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Approve/Reject button logic
//        approveBtn.addActionListener(e -> {
//            String carID = carIDField.getText().trim();
//            if (!carID.isEmpty()) {
//                handleStatusUpdate(carID, "approved", updateFrame);
//                loadAllRequests.run(); // Refresh table
//            } else {
//                JOptionPane.showMessageDialog(updateFrame,
//                        "Please enter a Car ID.",
//                        "Input Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        rejectBtn.addActionListener(e -> {
//            String carID = carIDField.getText().trim();
//            if (!carID.isEmpty()) {
//                handleStatusUpdate(carID, "rejected", updateFrame);
//                loadAllRequests.run(); // Refresh table
//            } else {
//                JOptionPane.showMessageDialog(updateFrame,
//                        "Please enter a Car ID.",
//                        "Input Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });

        // Close button
        JButton closeButton = new JButton("Go Back");
        closeButton.addActionListener(e -> {
            updateFrame.dispose();
            new SalesmanDashboard(currentSalesman);
        });
        inputPanel.add(closeButton);

        updateFrame.setVisible(true);
    }

}
