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
import Car.SalesRecords;
import Car.SoldCarRecord;
import Salesman.SalesmanList;
//import static Salesman.SalesmanList.loadSalesmanDataFromFile;
//import static Salesman.SalesmanList.salesmanList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

//public class SalesmanDashboard implements ActionListener {
//
//    private Salesman currentSalesman;
//
//    private JFrame frame;
//    private JButton editProfileButton, viewCarsButton, viewCarRequestButton, updateCarStatusButton, recordSalesHistory, logoutButton, markCarAsPaidButton;
//
//    public SalesmanDashboard(Salesman salesman) {
//        this.currentSalesman = salesman;
//
//        System.out.println("Logged in as: " + currentSalesman.getID());
//
//        frame = new JFrame("Salesman Dashboard");
//        frame.setSize(500, 300);
//        frame.setLocationRelativeTo(null);
//        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
//
//        editProfileButton = new JButton("Edit Profile");
//        viewCarsButton = new JButton("View Car Status");
//        viewCarRequestButton = new JButton("View Car Request");
//        updateCarStatusButton = new JButton("Update Car Status");
//        recordSalesHistory = new JButton("View Sales History");
//        markCarAsPaidButton = new JButton("markCarAsPaidWindow");
//        logoutButton = new JButton("Logout");
//
//        editProfileButton.addActionListener(this);
//        viewCarsButton.addActionListener(this);
//        updateCarStatusButton.addActionListener(this);
//        recordSalesHistory.addActionListener(this);
//        logoutButton.addActionListener(this);
//        viewCarRequestButton.addActionListener(this);
//        markCarAsPaidButton.addActionListener(this);
//
//        frame.add(editProfileButton);
//        frame.add(viewCarsButton);
//        frame.add(viewCarRequestButton);
//        frame.add(updateCarStatusButton);
//        frame.add(recordSalesHistory);
//        frame.add(logoutButton);
//        frame.add(markCarAsPaidButton);
//
//        WindowNav.setCloseOperation(frame, () -> new MainMenuGUI());
//
//        frame.setVisible(true);
//
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//        if (e.getSource() == editProfileButton) {
//            frame.dispose();
//            openEditProfileWindow();
//        } else if (e.getSource() == viewCarsButton) {
//            frame.dispose();
//            viewCarStatusWindow();
//        } else if (e.getSource() == viewCarRequestButton) {
//            frame.dispose();
//            viewCarRequestWindow();
//        } else if (e.getSource() == updateCarStatusButton) {
//            frame.dispose();
//            updateCarStatusWindow();
//        } else if (e.getSource() == markCarAsPaidButton) {
//            frame.dispose();
//            markCarAsPaidWindow();
//
//        } else if (e.getSource() == recordSalesHistory) {
//            frame.dispose();
//            viewSalesHistoryWindow();
//        } else if (e.getSource() == logoutButton) {
//            frame.dispose();
//            new SalesmanGUI(400, 250);  // Back to login
//        }
//    }
public class SalesmanDashboard implements ActionListener {

    private JFrame frame;
    private JPanel mainPanel, buttonPanel;
    private JLabel welcomeLabel;
    private JButton editProfileButton, viewCarsButton, viewCarRequestButton,
            updateCarStatusButton, recordSalesHistory, markCarAsPaidButton,
            logoutButton;

    private Salesman currentSalesman;

    public SalesmanDashboard(Salesman salesman) {
        this.currentSalesman = salesman;

        // Initialize frame
        frame = new JFrame("Salesman Dashboard!");
        GradientPanel background = new GradientPanel();
        background.setLayout(new BorderLayout()); // Important fix!
        frame.setContentPane(background);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with border layout
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setOpaque(false); // Allow background to show through
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Welcome label at top
        welcomeLabel = new JLabel("Welcome, " + currentSalesman.getName() + " (ID: " + currentSalesman.getID() + ")");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Button panel in center
        buttonPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create buttons with consistent styling
        editProfileButton = createStyledButton("Edit Profile");
        viewCarsButton = createStyledButton("View Car Status");
        viewCarRequestButton = createStyledButton("View Car Requests");
        updateCarStatusButton = createStyledButton("Update Car Status");
        recordSalesHistory = createStyledButton("View Sales History");
        markCarAsPaidButton = createStyledButton("Mark Car as Paid");
        logoutButton = createStyledButton("Logout");
        logoutButton.setBackground(new Color(255, 100, 100)); // Red for logout

        // Add buttons to panel
        buttonPanel.add(editProfileButton);
        buttonPanel.add(viewCarsButton);
        buttonPanel.add(viewCarRequestButton);
        buttonPanel.add(updateCarStatusButton);
        buttonPanel.add(recordSalesHistory);
        buttonPanel.add(markCarAsPaidButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Logout button at bottom
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutButton);
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);

        // Add action listeners
        editProfileButton.addActionListener(this);
        viewCarsButton.addActionListener(this);
        viewCarRequestButton.addActionListener(this);
        updateCarStatusButton.addActionListener(this);
        recordSalesHistory.addActionListener(this);
        markCarAsPaidButton.addActionListener(this);
        logoutButton.addActionListener(this);

        background.add(mainPanel, BorderLayout.CENTER);
        WindowNav.setCloseOperation(frame, () -> new MainMenuGUI());
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose(); // Dispose current frame before opening new window

        if (e.getSource() == editProfileButton) {
            openEditProfileWindow();
        } else if (e.getSource() == viewCarsButton) {
            viewCarStatusWindow();
        } else if (e.getSource() == viewCarRequestButton) {
            viewCarRequestWindow();
        } else if (e.getSource() == updateCarStatusButton) {
            updateCarStatusWindow();
        } else if (e.getSource() == markCarAsPaidButton) {
            markCarAsPaidWindow();
        } else if (e.getSource() == recordSalesHistory) {
            viewSalesHistoryWindow();
        } else if (e.getSource() == logoutButton) {
            new SalesmanGUI(400, 250); // Back to login
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
        saveButton.setBounds(60, 190, 150, 30); // Set position of the button

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
            new SalesmanDashboard(currentSalesman);

        });

        // Add the save button
        editProfileFrame.add(saveButton);

        JButton closeButton = new JButton("Go Back");
        closeButton.setBounds(230, 190, 100, 30);
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/salesmenList.txt"))) {
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

        // Editable only for Status column
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Car ID", "Brand", "Price", "Status"}, 0) {

//            public boolean isCellEditable(int row, int column) {
//                return column == 3; // Only 'Status' column editable
//            }
        };

        JTable carTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(carTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        carListFrame.add(scrollPane, BorderLayout.CENTER);

        // Function to load all assigned cars
        // Reload the cars after status update
        Runnable loadAllCars = () -> {
            tableModel.setRowCount(0);  // Clear existing rows
            ArrayList<Car> allCars = CarList.loadCarDataFromFile();  // Load fresh data from file
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

        carListFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                loadAllCars.run(); // Refresh when window is opened
            }

            @Override
            public void windowActivated(WindowEvent e) {
                loadAllCars.run(); // Optional: Refresh when window regains focus
            }
        });
        // Load all cars initially
//        loadAllCars.run();

//        // Search bar and button
//        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
//        JLabel searchLabel = new JLabel("Search (CarID/Brand/Status):");
//        JTextField searchField = new JTextField(20);
//        JButton searchButton = new JButton("Search");
//        searchPanel.add(searchLabel);
//        searchPanel.add(searchField);
//        searchPanel.add(searchButton);
//        carListFrame.add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);
//
//        // Search button action
//        searchButton.addActionListener(e -> {
//            String searchInput = searchField.getText().trim();
//            if (!searchInput.isEmpty()) {
//                ArrayList<Car> carList = CarList.loadCarDataFromFile();
//                ArrayList<Car> filteredCars = new ArrayList<>();
//
//                for (Car car : carList) {
//                    if (car.getSalesmanId().equals(currentSalesman.ID)
//                            && (car.getCarId().equalsIgnoreCase(searchInput)
//                            || car.getBrand().equalsIgnoreCase(searchInput)
//                            || car.getStatus().equalsIgnoreCase(searchInput))) {
//                        filteredCars.add(car);
//                    }
//                }
//
//                tableModel.setRowCount(0);
//
//                if (!filteredCars.isEmpty()) {
//                    for (Car car : filteredCars) {
//                        tableModel.addRow(new Object[]{
//                            car.getCarId(), car.getBrand(), car.getPrice(), car.getStatus()
//                        });
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(carListFrame,
//                            "No matching car found for input: " + searchInput,
//                            "Search Result", JOptionPane.INFORMATION_MESSAGE);
//                    searchField.setText("");
//                    loadAllCars.run();
//                }
//
//            } else {
//                JOptionPane.showMessageDialog(carListFrame,
//                        "Please enter a Car ID, Brand, or Status to search.",
//                        "Search Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        // Dropdown filter panel
//        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
//
//        JLabel filterLabel = new JLabel("Filter by:");
//        String[] filterOptions = {"All", "Brand: Toyota", "Brand: Honda", "Status: Available", "Status: Sold"};
//        JComboBox<String> filterComboBox = new JComboBox<>(filterOptions);
//        JButton filterButton = new JButton("Apply Filter");
//
//        filterPanel.add(filterLabel);
//        filterPanel.add(filterComboBox);
//        filterPanel.add(filterButton);
//        carListFrame.add(filterPanel, BorderLayout.BEFORE_FIRST_LINE);
//
//// Filter button action
//        filterButton.addActionListener(e -> {
//            String selected = (String) filterComboBox.getSelectedItem();
//            ArrayList<Car> carList = CarList.loadCarDataFromFile();
//            tableModel.setRowCount(0); // Clear table first
//
//            for (Car car : carList) {
//                if (!car.getSalesmanId().equals(currentSalesman.ID)) {
//                    continue;
//                }
//
//                boolean matches = false;
//
//                switch (selected) {
//                    case "All":
//                        matches = true;
//                        break;
//                    case "Brand: Toyota":
//                        matches = car.getBrand().equalsIgnoreCase("Toyota");
//                        break;
//                    case "Brand: Honda":
//                        matches = car.getBrand().equalsIgnoreCase("Honda");
//                        break;
//                    case "Status: Available":
//                        matches = car.getStatus().equalsIgnoreCase("Available");
//                        break;
//                    case "Status: Paid":
//                        matches = car.getStatus().equalsIgnoreCase("Paid");
//                        break;
//                }
//
//                if (matches) {
//                    tableModel.addRow(new Object[]{car.getCarId(), car.getBrand(), car.getPrice(), car.getStatus()});
//                }
//            }
//        });
// Top panel to hold search and filter components
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // optional padding

// --- Search Panel ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel searchLabel = new JLabel("Search (CarID/Brand/Status):");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

// --- Filter Panel ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel filterLabel = new JLabel("Filter by:");
        String[] filterOptions = {"All", "Brand: Toyota", "Brand: Honda", "Status: Available", "Status: Sold"};
        JComboBox<String> filterComboBox = new JComboBox<>(filterOptions);
        JButton filterButton = new JButton("Apply Filter");

        filterPanel.add(filterLabel);
        filterPanel.add(filterComboBox);
        filterPanel.add(filterButton);

// Add panels to topPanel
        topPanel.add(searchPanel);
        topPanel.add(filterPanel);

// Add topPanel to frame
        carListFrame.add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

// --- Search button action ---
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
                    loadAllCars.run();
                }

            } else {
                JOptionPane.showMessageDialog(carListFrame,
                        "Please enter a Car ID, Brand, or Status to search.",
                        "Search Error", JOptionPane.ERROR_MESSAGE);
            }
        });

// --- Filter button action ---
        filterButton.addActionListener(e -> {
            String selected = (String) filterComboBox.getSelectedItem();
            ArrayList<Car> carList = CarList.loadCarDataFromFile();
            tableModel.setRowCount(0); // Clear table first

            for (Car car : carList) {
                if (!car.getSalesmanId().equals(currentSalesman.ID)) {
                    continue;
                }

                boolean matches = false;

                switch (selected) {
                    case "All":
                        matches = true;
                        break;
                    case "Brand: Toyota":
                        matches = car.getBrand().equalsIgnoreCase("Toyota");
                        break;
                    case "Brand: Honda":
                        matches = car.getBrand().equalsIgnoreCase("Honda");
                        break;
                    case "Status: Available":
                        matches = car.getStatus().equalsIgnoreCase("Available");
                        break;
                    case "Status: Sold":
                        matches = car.getStatus().equalsIgnoreCase("Sold");
                        break;
                }

                if (matches) {
                    tableModel.addRow(new Object[]{
                        car.getCarId(), car.getBrand(), car.getPrice(), car.getStatus()
                    });
                }
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

//    public void viewCarRequestWindow() {
//        String salesmanID = currentSalesman.getID();
//
//        JFrame requestFrame = new JFrame("Car Requests");
//        requestFrame.setSize(600, 400);
//        requestFrame.setLocationRelativeTo(null);
//        requestFrame.setLayout(new BorderLayout(10, 10));
//
//        JLabel titleLabel = new JLabel("All Car Requests", JLabel.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
//        requestFrame.add(titleLabel, BorderLayout.NORTH);
//
//        DefaultTableModel tableModel = new DefaultTableModel(
//                new Object[]{"Customer ID", "Car ID", "Status", "Comment"}, 0
//        );
//        JTable requestTable = new JTable(tableModel);
//        requestTable.setEnabled(false);
//
//        JScrollPane scrollPane = new JScrollPane(requestTable);
//        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        requestFrame.add(scrollPane, BorderLayout.CENTER);
//
//        // Search bar and button
//        JTextField searchField = new JTextField(20);
//        JButton searchButton = new JButton("Search");
//
//        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
//        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
//        searchPanel.add(new JLabel("Search (CustomerID/CarID/Status):"));
//        searchPanel.add(searchField);
//        searchPanel.add(searchButton);
//        requestFrame.add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);
//
//        // Function to load all data
//        Runnable loadAllRequests = () -> {
//            tableModel.setRowCount(0); // Clear table
//            ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();
//            for (CarRequest req : requests) {
//                if (req.getSalesmanID().equals(currentSalesman.ID)) {
//                    tableModel.addRow(new Object[]{
//                        req.getCustomerID(),
//                        req.getCarID(),
//                        req.getRequestStatus(),
//                        req.getComment()
//                    });
//                }
//            }
//        };
//
//        // Load all data initially
//        loadAllRequests.run();
//
//        // Search button action
//        searchButton.addActionListener(e -> {
//            String searchInput = searchField.getText().trim();
//            if (!searchInput.isEmpty()) {
//                ArrayList<CarRequest> requestList = CarRequest.loadCarRequestDataFromFile();
//                ArrayList<CarRequest> filteredRequests = new ArrayList<>();
//
//                for (CarRequest req : requestList) {
//                    if (req.getSalesmanID().equals(currentSalesman.ID) && (req.getCustomerID().equalsIgnoreCase(searchInput)
//                            || req.getCarID().equalsIgnoreCase(searchInput)
//                            || req.getRequestStatus().equalsIgnoreCase(searchInput)
//                            || req.getComment().toLowerCase().contains(searchInput.toLowerCase()))) {
//                        filteredRequests.add(req);
//                    }
//                }
//
//                tableModel.setRowCount(0);
//
//                if (!filteredRequests.isEmpty()) {
//                    for (CarRequest req : filteredRequests) {
//                        tableModel.addRow(new Object[]{
//                            req.getCustomerID(),
//                            req.getCarID(),
//                            req.getRequestStatus(),
//                            req.getComment()
//                        });
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(requestFrame,
//                            "No matching request found for input: " + searchInput,
//                            "Search Result", JOptionPane.INFORMATION_MESSAGE);
//                    searchField.setText("");
//                    loadAllRequests.run();
//                }
//
//            } else {
//                JOptionPane.showMessageDialog(requestFrame,
//                        "Please enter a keyword to search.",
//                        "Search Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        // Close button
//        JButton closeButton = new JButton("Go Back");
//        closeButton.addActionListener(e -> {
//            requestFrame.dispose();
//            new SalesmanDashboard(currentSalesman);
//
//        });
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
//        buttonPanel.add(closeButton);
//        requestFrame.add(buttonPanel, BorderLayout.SOUTH);
//
//        // Display the window
//        requestFrame.setVisible(true);
//    }
    public void viewCarRequestWindow() {
        String salesmanID = currentSalesman.getID();

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

        // --- Search Panel ---
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.add(new JLabel("Search (CustomerID/CarID/Status/Comment):"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // --- Filter Panel ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel filterLabel = new JLabel("Filter by Status:");
        String[] filterOptions = {"All", "Pending", "Booked", "Cancelled"};
        JComboBox<String> filterComboBox = new JComboBox<>(filterOptions);
        JButton filterButton = new JButton("Apply Filter");
        filterPanel.add(filterLabel);
        filterPanel.add(filterComboBox);
        filterPanel.add(filterButton);

        // --- Combined Top Panel ---
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        topPanel.add(searchPanel);
        topPanel.add(filterPanel);
        requestFrame.add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

        // --- Load All Requests Function ---
        Runnable loadAllRequests = () -> {
            tableModel.setRowCount(0);
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

        loadAllRequests.run(); // Load initially

        // --- Search Button Action ---
        searchButton.addActionListener(e -> {
            String searchInput = searchField.getText().trim();
            if (!searchInput.isEmpty()) {
                ArrayList<CarRequest> requestList = CarRequest.loadCarRequestDataFromFile();
                ArrayList<CarRequest> filteredRequests = new ArrayList<>();

                for (CarRequest req : requestList) {
                    if (req.getSalesmanID().equals(currentSalesman.ID)
                            && (req.getCustomerID().equalsIgnoreCase(searchInput)
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

        // --- Filter Button Action ---
        filterButton.addActionListener(e -> {
            String selected = (String) filterComboBox.getSelectedItem();
            ArrayList<CarRequest> requestList = CarRequest.loadCarRequestDataFromFile();
            tableModel.setRowCount(0);

            for (CarRequest req : requestList) {
                if (!req.getSalesmanID().equals(currentSalesman.ID)) {
                    continue;
                }

                boolean matches = selected.equals("All") || req.getRequestStatus().equalsIgnoreCase(selected);
                if (matches) {
                    tableModel.addRow(new Object[]{
                        req.getCustomerID(),
                        req.getCarID(),
                        req.getRequestStatus(),
                        req.getComment()
                    });
                }
            }
        });

        // --- Close Button ---
        JButton closeButton = new JButton("Go Back");
        closeButton.addActionListener(e -> {
            requestFrame.dispose();
            new SalesmanDashboard(currentSalesman);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(closeButton);
        requestFrame.add(buttonPanel, BorderLayout.SOUTH);

        requestFrame.setVisible(true);
    }

//    public void updateCarStatusWindow() {
//        JFrame updateFrame = new JFrame("Update Car Status");
//        updateFrame.setSize(600, 500);
//        updateFrame.setLocationRelativeTo(null);
//        updateFrame.setLayout(new BorderLayout(10, 10));
//
//        JLabel titleLabel = new JLabel("All Car Requests", JLabel.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
//        updateFrame.add(titleLabel, BorderLayout.NORTH);
//
//        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Customer ID", "Car ID", "Status"}, 0);
//        JTable requestTable = new JTable(tableModel);
//        requestTable.setEnabled(false);
//        JScrollPane scrollPane = new JScrollPane(requestTable);
//        updateFrame.add(scrollPane, BorderLayout.CENTER);
//
//        JTextField searchField = new JTextField(20);
//        JButton searchButton = new JButton("Search");
//        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
//        searchPanel.add(new JLabel("Search (Customer ID / Car ID):"));
//        searchPanel.add(searchField);
//        searchPanel.add(searchButton);
//        updateFrame.add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);
//
//        JTextField carIDField = new JTextField(10);
//        JTextField commentField = new JTextField(15);
//        JButton approveBtn = new JButton("Approve");
//        JButton rejectBtn = new JButton("Reject");
//        JButton cancelBtn = new JButton("Cancel");
//        JButton closeButton = new JButton("Go Back");
//
//        Dimension buttonSize = new Dimension(100, 30);
//        approveBtn.setPreferredSize(buttonSize);
//        rejectBtn.setPreferredSize(buttonSize);
//        cancelBtn.setPreferredSize(buttonSize);
//        closeButton.setPreferredSize(buttonSize);
//
//        JPanel inputPanel = new JPanel();
//        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
//        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        int labelWidth = 120;
//
//        JLabel carIDLabel = new JLabel("Car ID:");
//        carIDLabel.setPreferredSize(new Dimension(labelWidth, carIDLabel.getPreferredSize().height));
//        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        row1.add(carIDLabel);
//        row1.add(carIDField);
//
//        JLabel commentLabel = new JLabel("Comment (optional):");
//        commentLabel.setPreferredSize(new Dimension(labelWidth, commentLabel.getPreferredSize().height));
//        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        row2.add(commentLabel);
//        row2.add(commentField);
//
//        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        row3.add(approveBtn);
//        row3.add(rejectBtn);
//        row3.add(cancelBtn);
//        row3.add(closeButton);
//
//        inputPanel.add(row1);
//        inputPanel.add(row2);
//        inputPanel.add(row3);
//        updateFrame.add(inputPanel, BorderLayout.SOUTH);
//
//        Runnable loadAllRequests = () -> {
//            tableModel.setRowCount(0);
//            ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();
//            for (CarRequest req : requests) {
//                if (req.getSalesmanID().equals(currentSalesman.ID)) {
//                    tableModel.addRow(new Object[]{
//                        req.getCustomerID(),
//                        req.getCarID(),
//                        req.getRequestStatus()
//                    });
//                }
//            }
//        };
//        loadAllRequests.run();
//
//        searchButton.addActionListener(e -> {
//            String searchInput = searchField.getText().trim();
//            if (!searchInput.isEmpty()) {
//                ArrayList<CarRequest> requestList = CarRequest.loadCarRequestDataFromFile();
//                tableModel.setRowCount(0);
//                boolean found = false;
//                for (CarRequest req : requestList) {
//                    if (req.getSalesmanID().equals(currentSalesman.ID)
//                            && (req.getCustomerID().equalsIgnoreCase(searchInput)
//                            || req.getCarID().equalsIgnoreCase(searchInput))) {
//                        tableModel.addRow(new Object[]{
//                            req.getCustomerID(),
//                            req.getCarID(),
//                            req.getRequestStatus()
//                        });
//                        found = true;
//                    }
//                }
//                if (!found) {
//                    JOptionPane.showMessageDialog(updateFrame,
//                            "No matching request found for: " + searchInput,
//                            "Search Result", JOptionPane.INFORMATION_MESSAGE);
//                    loadAllRequests.run();
//                }
//            } else {
//                JOptionPane.showMessageDialog(updateFrame,
//                        "Please enter search keyword",
//                        "Search Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        approveBtn.addActionListener(e -> {
//            String carID = carIDField.getText().trim();
//            String comment = commentField.getText().trim();
//            String finalComment = comment.isEmpty() ? "." : comment;
//
//            if (!carID.isEmpty()) {
//                ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();
//                boolean isRejected = false;
//
//                for (CarRequest req : requests) {
//                    if (req.getCarID().equalsIgnoreCase(carID)
//                            && req.getSalesmanID().equals(currentSalesman.ID)) {
//                        if (req.getRequestStatus().equalsIgnoreCase("rejected")) {
//                            isRejected = true;
//                            break;
//                        }
//                    }
//                }
//
//                if (isRejected) {
//                    JOptionPane.showMessageDialog(updateFrame,
//                            "This request has already been rejected. You cannot approve it.",
//                            "Invalid Operation", JOptionPane.WARNING_MESSAGE);
//                    loadAllRequests.run();
//                    carIDField.setText("");
//                    commentField.setText("");
//                    return;
//                }
//
//                boolean requestUpdated = CarRequest.updateRequestStatusWithComment(
//                        carID, currentSalesman.ID, "booked", finalComment);
//
//                if (requestUpdated) {
//                    ArrayList<Car> allCars = CarList.loadCarDataFromFile();
//                    boolean carUpdated = false;
//
//                    for (Car car : allCars) {
//                        if (car.getCarId().equalsIgnoreCase(carID)) {
//                            car.setStatus("booked");
//                            carUpdated = true;
//                            break;
//                        }
//                    }
//
//                    if (carUpdated) {
//                        CarList.saveUpdatedCarToFile(allCars);
//                        JOptionPane.showMessageDialog(updateFrame,
//                                "Request approved and car status updated");
//                        loadAllRequests.run();
//                        carIDField.setText("");
//                        commentField.setText("");
//                    } else {
//                        JOptionPane.showMessageDialog(updateFrame,
//                                "Car not found in inventory",
//                                "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(updateFrame,
//                            "Failed to update request",
//                            "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            } else {
//                JOptionPane.showMessageDialog(updateFrame,
//                        "Please enter Car ID",
//                        "Input Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        rejectBtn.addActionListener(e -> {
//            String carID = carIDField.getText().trim();
//            String comment = commentField.getText().trim();
//            String finalComment = comment.isEmpty() ? "." : comment;
//
//            if (!carID.isEmpty()) {
//                ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();
//                for (CarRequest req : requests) {
//                    if (req.getCarID().equalsIgnoreCase(carID)
//                            && req.getSalesmanID().equals(currentSalesman.ID)) {
//                        if (req.getRequestStatus().equalsIgnoreCase("booked")) {
//                            JOptionPane.showMessageDialog(updateFrame,
//                                    "This request has already been approved (booked). You cannot reject it.",
//                                    "Invalid Operation", JOptionPane.WARNING_MESSAGE);
//                            return;
//                        }
//                    }
//                }
//
//                boolean requestUpdated = CarRequest.updateRequestStatusWithComment(
//                        carID, currentSalesman.ID, "rejected", finalComment);
//
//                if (requestUpdated) {
//                    ArrayList<Car> allCars = CarList.loadCarDataFromFile();
//                    for (Car car : allCars) {
//                        if (car.getCarId().equalsIgnoreCase(carID)) {
//                            car.setStatus("available");
//                            break;
//                        }
//                    }
//                    CarList.saveUpdatedCarToFile(allCars);
//                    JOptionPane.showMessageDialog(updateFrame,
//                            "Request rejected and car status updated");
//                    loadAllRequests.run();
//                    carIDField.setText("");
//                    commentField.setText("");
//                } else {
//                    JOptionPane.showMessageDialog(updateFrame,
//                            "Failed to update request",
//                            "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            } else {
//                JOptionPane.showMessageDialog(updateFrame,
//                        "Please enter Car ID",
//                        "Input Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        cancelBtn.addActionListener(e -> {
//            String carID = carIDField.getText().trim();
//            String comment = commentField.getText().trim();
//            String finalComment = comment.isEmpty() ? "." : comment;
//
//            if (!carID.isEmpty()) {
//                ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();
//                for (CarRequest req : requests) {
//                    if (req.getCarID().equalsIgnoreCase(carID)
//                            && req.getSalesmanID().equals(currentSalesman.ID)) {
//                        if (!req.getRequestStatus().equalsIgnoreCase("booked")) {
//                            JOptionPane.showMessageDialog(updateFrame,
//                                    "Only 'booked' requests can be cancelled.",
//                                    "Invalid Operation", JOptionPane.WARNING_MESSAGE);
//                            return;
//                        }
//
//                        boolean requestUpdated = CarRequest.updateRequestStatusWithComment(
//                                carID, currentSalesman.ID, "cancelled", finalComment);
//
//                        if (requestUpdated) {
//                            ArrayList<Car> allCars = CarList.loadCarDataFromFile();
//                            for (Car car : allCars) {
//                                if (car.getCarId().equalsIgnoreCase(carID)) {
//                                    car.setStatus("available");
//                                    break;
//                                }
//                            }
//                            CarList.saveUpdatedCarToFile(allCars);
//                            JOptionPane.showMessageDialog(updateFrame,
//                                    "Request cancelled and car status updated");
//                            loadAllRequests.run();
//                            carIDField.setText("");
//                            commentField.setText("");
//                            return;
//                        } else {
//                            JOptionPane.showMessageDialog(updateFrame,
//                                    "Failed to update request",
//                                    "Error", JOptionPane.ERROR_MESSAGE);
//                            return;
//                        }
//                    }
//                }
//
//                JOptionPane.showMessageDialog(updateFrame,
//                        "Request not found for the given Car ID.",
//                        "Error", JOptionPane.ERROR_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(updateFrame,
//                        "Please enter Car ID",
//                        "Input Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        closeButton.addActionListener(e -> {
//            updateFrame.dispose();
//            new SalesmanDashboard(currentSalesman);
//        });
//
//        updateFrame.setVisible(true);
//    }
    public void updateCarStatusWindow() {
        JFrame updateFrame = new JFrame("Update Car Status");
        updateFrame.setSize(600, 500);
        updateFrame.setLocationRelativeTo(null);
        updateFrame.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("All Car Requests", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        updateFrame.add(titleLabel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Customer ID", "Car ID", "Status"}, 0);
        JTable requestTable = new JTable(tableModel);
        requestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Make table selectable
        JScrollPane scrollPane = new JScrollPane(requestTable);
        updateFrame.add(scrollPane, BorderLayout.CENTER);

        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        searchPanel.add(new JLabel("Search (Customer ID / Car ID):"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        updateFrame.add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);

        JTextField customerIDField = new JTextField(10); // Added Customer ID field
        JTextField carIDField = new JTextField(10);
        JTextField commentField = new JTextField(15);
        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");
        JButton cancelBtn = new JButton("Cancel");
        JButton closeButton = new JButton("Go Back");

        Dimension buttonSize = new Dimension(100, 30);
        approveBtn.setPreferredSize(buttonSize);
        rejectBtn.setPreferredSize(buttonSize);
        cancelBtn.setPreferredSize(buttonSize);
        closeButton.setPreferredSize(buttonSize);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        int labelWidth = 120;

        // Added Customer ID row
        JLabel customerIDLabel = new JLabel("Customer ID:");
        customerIDLabel.setPreferredSize(new Dimension(labelWidth, customerIDLabel.getPreferredSize().height));
        JPanel row0 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row0.add(customerIDLabel);
        row0.add(customerIDField);

        JLabel carIDLabel = new JLabel("Car ID:");
        carIDLabel.setPreferredSize(new Dimension(labelWidth, carIDLabel.getPreferredSize().height));
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(carIDLabel);
        row1.add(carIDField);

        JLabel commentLabel = new JLabel("Comment (optional):");
        commentLabel.setPreferredSize(new Dimension(labelWidth, commentLabel.getPreferredSize().height));
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(commentLabel);
        row2.add(commentField);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        row3.add(approveBtn);
        row3.add(rejectBtn);
        row3.add(cancelBtn);
        row3.add(closeButton);

        inputPanel.add(row0); // Added Customer ID row
        inputPanel.add(row1);
        inputPanel.add(row2);
        inputPanel.add(row3);
        updateFrame.add(inputPanel, BorderLayout.SOUTH);

        // Add selection listener to auto-fill fields
        requestTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = requestTable.getSelectedRow();
                if (selectedRow >= 0) {
                    customerIDField.setText(requestTable.getValueAt(selectedRow, 0).toString());
                    carIDField.setText(requestTable.getValueAt(selectedRow, 1).toString());
                }
            }
        });

        Runnable loadAllRequests = () -> {
            tableModel.setRowCount(0);
            ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();
            for (CarRequest req : requests) {
                if (req.getSalesmanID().equals(currentSalesman.ID)) {
                    tableModel.addRow(new Object[]{
                        req.getCustomerID(),
                        req.getCarID(),
                        req.getRequestStatus()
                    });
                }
            }
        };
        loadAllRequests.run();

        searchButton.addActionListener(e -> {
            String searchInput = searchField.getText().trim();
            if (!searchInput.isEmpty()) {
                ArrayList<CarRequest> requestList = CarRequest.loadCarRequestDataFromFile();
                tableModel.setRowCount(0);
                boolean found = false;
                for (CarRequest req : requestList) {
                    if (req.getSalesmanID().equals(currentSalesman.ID)
                            && (req.getCustomerID().equalsIgnoreCase(searchInput)
                            || req.getCarID().equalsIgnoreCase(searchInput))) {
                        tableModel.addRow(new Object[]{
                            req.getCustomerID(),
                            req.getCarID(),
                            req.getRequestStatus()
                        });
                        found = true;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(updateFrame,
                            "No matching request found for: " + searchInput,
                            "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    loadAllRequests.run();
                }
            } else {
                JOptionPane.showMessageDialog(updateFrame,
                        "Please enter search keyword",
                        "Search Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        approveBtn.addActionListener(e -> {
            String customerID = customerIDField.getText().trim();
            String carID = carIDField.getText().trim();
            String comment = commentField.getText().trim();
            String finalComment = comment.isEmpty() ? "Approved by salesman" : comment;

            // Validate inputs
            if (carID.isEmpty() || customerID.isEmpty()) {
                JOptionPane.showMessageDialog(updateFrame,
                        "Please enter both Customer ID and Car ID",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Load all data
            ArrayList<Car> allCars = CarList.loadCarDataFromFile();
            ArrayList<CarRequest> allRequests = CarRequest.loadCarRequestDataFromFile();

            // Find the car
            Car targetCar = null;
            for (Car car : allCars) {
                if (car.getCarId().equalsIgnoreCase(carID)) {
                    targetCar = car;
                    break;
                }
            }

            if (targetCar == null) {
                JOptionPane.showMessageDialog(updateFrame,
                        "Car not found in inventory",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check for paid status
            if (targetCar.getStatus().equalsIgnoreCase("paid")) {
                JOptionPane.showMessageDialog(updateFrame,
                        "This car has already been paid for and cannot be modified.",
                        "Action Blocked", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Find all requests for this car+customer
            ArrayList<CarRequest> customerRequests = new ArrayList<>();
            for (CarRequest req : allRequests) {
                if (req.getCarID().equalsIgnoreCase(carID)
                        && req.getCustomerID().equalsIgnoreCase(customerID)
                        && req.getSalesmanID().equals(currentSalesman.ID)) {
                    customerRequests.add(req);
                }
            }

            // Check if there's any cancelled request
            boolean hasCancelledRequest = false;
            for (CarRequest req : customerRequests) {
                if (req.getRequestStatus().equalsIgnoreCase("cancelled")) {
                    hasCancelledRequest = true;
                    break;
                }
            }

            // Check if there's a pending request
            boolean hasPendingRequest = false;
            for (CarRequest req : customerRequests) {
                if (req.getRequestStatus().equalsIgnoreCase("pending")) {
                    hasPendingRequest = true;
                    break;
                }
            }

            // Rule: If cancelled exists but no pending, block action
            if (hasCancelledRequest && !hasPendingRequest) {
                JOptionPane.showMessageDialog(updateFrame,
                        "This booking was previously cancelled.\n"
                        + "Cannot approve/reject unless a new request is made.",
                        "Cancelled Booking", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Find the most recent pending request (if exists)
            CarRequest targetRequest = null;
            for (CarRequest req : customerRequests) {
                if (req.getRequestStatus().equalsIgnoreCase("pending")) {
                    targetRequest = req;
                    break;
                }
            }

            if (targetRequest == null) {
                // Check if request already exists in any status
                if (!customerRequests.isEmpty()) {
                    CarRequest existingRequest = customerRequests.get(0);
                    if (existingRequest.getRequestStatus().equalsIgnoreCase("rejected")) {
                        JOptionPane.showMessageDialog(updateFrame,
                                "This request has already been rejected and cannot be approved.",
                                "Invalid Operation", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (existingRequest.getRequestStatus().equalsIgnoreCase("booked")) {
                        JOptionPane.showMessageDialog(updateFrame,
                                "This request is already approved.",
                                "Information", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }

                JOptionPane.showMessageDialog(updateFrame,
                        "No pending request found for this customer and car",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if car is already booked (by others)
            if (targetCar.getStatus().equalsIgnoreCase("booked")) {
                boolean bookedByThisCustomer = false;
                boolean otherPendingRequestsExist = false;

                // Check all requests for this car (not just from this customer)
                for (CarRequest req : allRequests) {
                    if (req.getCarID().equalsIgnoreCase(carID)) {
                        if (req.getCustomerID().equalsIgnoreCase(customerID)
                                && req.getRequestStatus().equalsIgnoreCase("booked")) {
                            bookedByThisCustomer = true;
                        } else if (!req.getCustomerID().equalsIgnoreCase(customerID)
                                && req.getRequestStatus().equalsIgnoreCase("pending")) {
                            otherPendingRequestsExist = true;
                        }
                    }
                }

                if (!bookedByThisCustomer) {
                    int option = JOptionPane.showConfirmDialog(updateFrame,
                            "This car is already booked by another customer.\n"
                            + "Do you want to cancel the existing booking and approve this request?",
                            "Car Already Booked", JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.YES_OPTION) {
                        // Cancel the existing booking first
                        for (CarRequest req : allRequests) {
                            if (req.getCarID().equalsIgnoreCase(carID)
                                    && req.getRequestStatus().equalsIgnoreCase("booked")) {
                                // Update the existing booked request to cancelled
                                CarRequest.updateRequestStatusWithComment(
                                        req.getCarID(), req.getCustomerID(), req.getSalesmanID(),
                                        "cancelled", "Cancelled to approve new request for customer " + customerID);

                                // Set car status back to available temporarily
                                targetCar.setStatus("available");
                                CarList.saveUpdatedCarToFile(allCars);
                                break;
                            }
                        }
                    } else {
                        return; // User chose not to proceed
                    }
                }
            }

            // Process approval
            boolean requestUpdated = CarRequest.updateRequestStatusWithComment(
                    carID, customerID, currentSalesman.ID, "booked", finalComment);

            if (requestUpdated) {
                // Update car status
                targetCar.setStatus("booked");
                CarList.saveUpdatedCarToFile(allCars);

                JOptionPane.showMessageDialog(updateFrame,
                        "Request approved successfully!\n"
                        + "Car: " + carID + "\n"
                        + "Customer: " + customerID + "\n"
                        + "Status: Booked",
                        "Approval Successful", JOptionPane.INFORMATION_MESSAGE);

                // Refresh UI
                loadAllRequests.run();
                customerIDField.setText("");
                carIDField.setText("");
                commentField.setText("");
            } else {
                JOptionPane.showMessageDialog(updateFrame,
                        "Failed to update request status",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Similar changes for rejectBtn and cancelBtn action listeners
        // (Add customer ID validation and field clearing)
        rejectBtn.addActionListener(e
                -> {
            String customerID = customerIDField.getText().trim();
            String carID = carIDField.getText().trim();
            String comment = commentField.getText().trim();
            String finalComment = comment.isEmpty() ? "." : comment;

            if (!carID.isEmpty() && !customerID.isEmpty()) {

                if (isCarPaid(carID)) {
                    JOptionPane.showMessageDialog(updateFrame,
                            "This car has already been paid for and cannot be modified.",
                            "Action Blocked", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();
                boolean requestFound = false;

                for (CarRequest req : requests) {
                    if (req.getCarID().equalsIgnoreCase(carID)
                            && req.getCustomerID().equalsIgnoreCase(customerID)
                            && req.getSalesmanID().equals(currentSalesman.ID)) {
                        requestFound = true;
                        if (req.getRequestStatus().equalsIgnoreCase("booked")) {
                            JOptionPane.showMessageDialog(updateFrame,
                                    "This request has already been approved (booked). You cannot reject it.",
                                    "Invalid Operation", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }

                if (!requestFound) {
                    JOptionPane.showMessageDialog(updateFrame,
                            "No matching request found for the given Customer ID and Car ID",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean requestUpdated = CarRequest.updateRequestStatusWithComment(
                        carID, customerID, currentSalesman.ID, "rejected", finalComment);

                if (requestUpdated) {
                    ArrayList<Car> allCars = CarList.loadCarDataFromFile();
                    for (Car car : allCars) {
                        if (car.getCarId().equalsIgnoreCase(carID)) {
                            car.setStatus("available");
                            break;
                        }
                    }
                    CarList.saveUpdatedCarToFile(allCars);
                    JOptionPane.showMessageDialog(updateFrame,
                            "Request rejected and car status updated");
                    loadAllRequests.run();
                    customerIDField.setText("");
                    carIDField.setText("");
                    commentField.setText("");
                } else {
                    JOptionPane.showMessageDialog(updateFrame,
                            "Failed to update request",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(updateFrame,
                        "Please enter both Customer ID and Car ID",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        );

        cancelBtn.addActionListener(e
                -> {
            String customerID = customerIDField.getText().trim();
            String carID = carIDField.getText().trim();
            String comment = commentField.getText().trim();
            String finalComment = comment.isEmpty() ? "." : comment;

            if (!carID.isEmpty() && !customerID.isEmpty()) {

                // Block if car is paid
                if (isCarPaid(carID)) {
                    JOptionPane.showMessageDialog(updateFrame,
                            "This car has already been paid for and cannot be cancelled.",
                            "Action Blocked", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();
                ArrayList<Car> allCars = CarList.loadCarDataFromFile();
                boolean requestFound = false;

                for (CarRequest req : requests) {
                    if (req.getCarID().equalsIgnoreCase(carID)
                            && req.getCustomerID().equalsIgnoreCase(customerID)
                            && req.getSalesmanID().equals(currentSalesman.ID)) {
                        requestFound = true;
                        if (!req.getRequestStatus().equalsIgnoreCase("booked")) {
                            JOptionPane.showMessageDialog(updateFrame,
                                    "Only 'booked' requests can be cancelled.",
                                    "Invalid Operation", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        boolean requestUpdated = CarRequest.updateRequestStatusWithComment(
                                carID, customerID, currentSalesman.ID, "cancelled", finalComment);

                        if (requestUpdated) {
                            for (Car car : allCars) {
                                if (car.getCarId().equalsIgnoreCase(carID)) {
                                    car.setStatus("available");
                                    break;
                                }
                            }
                            CarList.saveUpdatedCarToFile(allCars);
                            JOptionPane.showMessageDialog(updateFrame,
                                    "Request cancelled and car status updated to 'available'\n"
                                    + "The car is now available for other customers to book",
                                    "Cancellation Successful", JOptionPane.INFORMATION_MESSAGE);
                            loadAllRequests.run();
                            customerIDField.setText("");
                            carIDField.setText("");
                            commentField.setText("");
                            return;
                        } else {
                            JOptionPane.showMessageDialog(updateFrame,
                                    "Failed to update request",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                if (!requestFound) {
                    JOptionPane.showMessageDialog(updateFrame,
                            "No matching request found for the given Customer ID and Car ID",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(updateFrame,
                        "Please enter both Customer ID and Car ID",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        );

        closeButton.addActionListener(e
                -> {
            updateFrame.dispose();
            new SalesmanDashboard(currentSalesman);
        }
        );

        updateFrame.setVisible(
                true);
    }

    private boolean isCarPaid(String carID) {
        ArrayList<Car> allCars = CarList.loadCarDataFromFile();
        for (Car car : allCars) {
            if (car.getCarId().equalsIgnoreCase(carID) && car.getStatus().equalsIgnoreCase("paid")) {
                return true;
            }
        }
        return false;
    }

    public void markCarAsPaidWindow() {
        JFrame frame = new JFrame("Mark Car as Paid");
        frame.setSize(600, 650);  // Increased height to accommodate date field
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Cars Marked as 'Booked'", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Panel for brand filter
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        filterPanel.add(new JLabel("Filter by Brand:"));
        JTextField brandFilterField = new JTextField(15);
        filterPanel.add(brandFilterField);
        frame.add(filterPanel, BorderLayout.PAGE_START);

        // Table with customer ID column
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Car ID", "Brand", "Price", "Status", "Customer ID"}, 0);
        JTable carTable = new JTable(tableModel);
        carTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(carTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField carIDField = new JTextField(15);
        JTextField customerIDField = new JTextField(15);
        JTextField commentField = new JTextField(15);
        JTextField dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 15);
        JButton paidButton = new JButton("Paid");
        JButton backButton = new JButton("Go Back");

        // Add selection listener to auto-fill fields
        carTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = carTable.getSelectedRow();
                if (selectedRow >= 0) {
                    carIDField.setText(carTable.getValueAt(selectedRow, 0).toString());
                    customerIDField.setText(carTable.getValueAt(selectedRow, 4).toString());
                    dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    commentField.setText("");
                }
            }
        });

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(new JLabel("Car ID:"));
        row1.add(carIDField);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(new JLabel("Customer ID:"));
        row2.add(customerIDField);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.add(new JLabel("Date:"));
        row3.add(dateField);

        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row4.add(new JLabel("Comment (optional):"));
        row4.add(commentField);

        JPanel row5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        row5.add(paidButton);
        row5.add(backButton);

        inputPanel.add(row1);
        inputPanel.add(row2);
        inputPanel.add(row3);
        inputPanel.add(row4);
        inputPanel.add(row5);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Load booked cars by this salesman with customer ID and optional brand filter
        Runnable loadBookedCars = () -> {
            String brandFilter = brandFilterField.getText().trim().toLowerCase();
            tableModel.setRowCount(0);
            ArrayList<Car> cars = CarList.loadCarDataFromFile();
            ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();

            for (Car car : cars) {
                boolean matchesBrand = brandFilter.isEmpty() || car.getBrand().toLowerCase().contains(brandFilter);
                if (car.getSalesmanId().equals(currentSalesman.ID)
                        && car.getStatus().equalsIgnoreCase("booked")
                        && matchesBrand) {

                    // Find the customer who booked this car
                    String customerID = "";
                    for (CarRequest req : requests) {
                        if (req.getCarID().equalsIgnoreCase(car.getCarId())
                                && req.getRequestStatus().equalsIgnoreCase("booked")) {
                            customerID = req.getCustomerID();
                            break;
                        }
                    }

                    tableModel.addRow(new Object[]{
                        car.getCarId(),
                        car.getBrand(),
                        car.getPrice(),
                        car.getStatus(),
                        customerID
                    });
                }
            }
        };
        loadBookedCars.run();

        // Update table when brand filter changes
        brandFilterField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                loadBookedCars.run();
            }

            public void removeUpdate(DocumentEvent e) {
                loadBookedCars.run();
            }

            public void changedUpdate(DocumentEvent e) {
                loadBookedCars.run();
            }
        });

        paidButton.addActionListener(e -> {
            String carID = carIDField.getText().trim();
            String customerID = customerIDField.getText().trim();
            String comment = commentField.getText().trim();
            String date = dateField.getText().trim();

            if (carID.isEmpty() || customerID.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter both Car ID and Customer ID.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate date format
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                sdf.parse(date);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter a valid date in YYYY-MM-DD format.",
                        "Invalid Date", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ArrayList<Car> carList = CarList.loadCarDataFromFile();
            ArrayList<CarRequest> carRequests = CarRequest.loadCarRequestDataFromFile();
            boolean carFound = false;

            for (Car car : carList) {
                if (car.getCarId().equalsIgnoreCase(carID)
                        && car.getSalesmanId().equals(currentSalesman.ID)) {

                    // Check if already paid
                    if (car.getStatus().equalsIgnoreCase("paid")) {
                        JOptionPane.showMessageDialog(frame,
                                "This car has already been marked as paid.",
                                "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Verify the customer ID matches the booking
                    boolean validCustomer = false;
                    for (CarRequest req : carRequests) {
                        if (req.getCarID().equalsIgnoreCase(carID)
                                && req.getCustomerID().equalsIgnoreCase(customerID)
                                && req.getRequestStatus().equalsIgnoreCase("booked")) {
                            validCustomer = true;
                            break;
                        }
                    }

                    if (!validCustomer) {
                        JOptionPane.showMessageDialog(frame,
                                "No matching booked request found for this Car ID and Customer ID.",
                                "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Only proceed if status is booked
                    if (!car.getStatus().equalsIgnoreCase("booked")) {
                        JOptionPane.showMessageDialog(frame,
                                "Only 'booked' cars can be marked as paid.",
                                "Invalid Operation", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    car.setStatus("paid");
                    carFound = true;

                    // Update all requests for this car
                    ArrayList<CarRequest> updatedRequests = new ArrayList<>();

                    for (CarRequest req : carRequests) {
                        if (req.getCarID().equalsIgnoreCase(carID)) {
                            if (req.getCustomerID().equalsIgnoreCase(customerID)
                                    && req.getRequestStatus().equalsIgnoreCase("booked")) {
                                // Update the booked request to paid
                                CarRequest paidRequest = new CarRequest(
                                        req.getCustomerID(),
                                        req.getCarID(),
                                        req.getSalesmanID(),
                                        "paid",
                                        comment.isEmpty() ? "Payment completed" : comment
                                );
                                updatedRequests.add(paidRequest);
                            } else if (req.getRequestStatus().equalsIgnoreCase("pending")) {
                                // Reject other pending requests
                                CarRequest rejectedRequest = new CarRequest(
                                        req.getCustomerID(),
                                        req.getCarID(),
                                        req.getSalesmanID(),
                                        "rejected",
                                        "This car has been sold - payment completed"
                                );
                                updatedRequests.add(rejectedRequest);
                            }
                        } else {
                            // Keep other requests unchanged
                            updatedRequests.add(req);
                        }
                    }

                    // Save changes
                    CarList.saveUpdatedCarToFile(carList);
                    CarRequest.writeCarRequests(updatedRequests);

                    // Create sales record
                    SalesRecords sale = new SalesRecords(
                            customerID,
                            car.getCarId(),
                            currentSalesman.ID,
                            car.getPrice(),
                            "paid",
                            comment.isEmpty() ? "No comments" : comment,
                            date // Add date to the sales record
                    );
                    SalesRecords.saveSalesRecord(sale);

                    // Add to sold cars file
                    SoldCarRecord.saveSoldCarRecord(
                            car.getCarId(),
                            String.valueOf(car.getPrice()),
                            customerID,
                            currentSalesman.ID,
                            date, // Use the selected date
                            comment.isEmpty() ? "No comments" : comment
                    );

                    JOptionPane.showMessageDialog(frame,
                            "Payment processed successfully!\n"
                            + "• Car status updated to 'paid'\n"
                            + "• Booking request marked as 'paid'\n"
                            + "• All other requests for this car rejected\n"
                            + "• Sales record created",
                            "Payment Completed", JOptionPane.INFORMATION_MESSAGE);

                    loadBookedCars.run();
                    carIDField.setText("");
                    customerIDField.setText("");
                    commentField.setText("");
                    dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    break;
                }
            }

            if (!carFound) {
                JOptionPane.showMessageDialog(frame,
                        "No booked car found with ID: " + carID,
                        "Not Found", JOptionPane.WARNING_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            new SalesmanDashboard(currentSalesman);
        });

        frame.setVisible(true);
    }

//    public String getCustomerIDFromRequest(String carID) {
//        try (BufferedReader reader = new BufferedReader(new FileReader("data/CarRequest.txt"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] data = line.split(",");
//                if (data.length >= 2 && data[1].equalsIgnoreCase(carID)) {
//                    return data[0]; // assuming format is: customerID,carID,...
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Error reading request file: " + e.getMessage());
//        }
//        return null;
//    }
    // Improved getCustomerIDFromRequest method:
    public String getCustomerIDFromRequest(String carID) {
        ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();
        for (CarRequest req : requests) {
            if (req.getCarID().equalsIgnoreCase(carID)
                    && req.getRequestStatus().equalsIgnoreCase("booked")) {
                return req.getCustomerID();
            }
        }
        return null;
    }

//    public void viewSalesHistoryWindow() {
//        JFrame frame = new JFrame("Sales History");
//        frame.setSize(700, 500);
//        frame.setLocationRelativeTo(null);
//        frame.setLayout(new BorderLayout(10, 10));
//
//        // Top panel with title and back button
//        JPanel topPanel = new JPanel(new BorderLayout());
//        JLabel titleLabel = new JLabel("Your Sales History", JLabel.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
//        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
//
//        JButton backButton = new JButton("Back");
//        backButton.addActionListener(e -> {
//            frame.dispose();
//            new SalesmanDashboard(currentSalesman);
//        });
//
//        topPanel.add(backButton, BorderLayout.WEST);
//        topPanel.add(titleLabel, BorderLayout.CENTER);
//        frame.add(topPanel, BorderLayout.NORTH);
//
//        // Table setup
//        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Customer ID", "Car ID", "Price", "Status", "Comment"}, 0);
//        JTable table = new JTable(tableModel);
//        JScrollPane scrollPane = new JScrollPane(table);
//        frame.add(scrollPane, BorderLayout.CENTER);
//
//        // Search panel
//        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        JTextField searchField = new JTextField(20);
//        JButton searchButton = new JButton("Search");
//        JButton resetButton = new JButton("Reset");
//
//        searchPanel.add(new JLabel("Search by Car ID or Customer ID:"));
//        searchPanel.add(searchField);
//        searchPanel.add(searchButton);
//        searchPanel.add(resetButton);
//        frame.add(searchPanel, BorderLayout.SOUTH);
//
//        // Method to load all sales for current salesman
//        Runnable loadSalesData = () -> {
//            tableModel.setRowCount(0);
//            ArrayList<SalesRecords> records = SalesRecords.loadSalesRecords();
//            for (SalesRecords record : records) {
//                if (record.getSalesmanID().equals(currentSalesman.ID)) {
//                    tableModel.addRow(new Object[]{
//                        record.getCustomerID(),
//                        record.getCarID(),
//                        record.getPrice(),
//                        record.getStatus(),
//                        record.getComment()
//                    });
//                }
//            }
//        };
//
//        // Search action
//        searchButton.addActionListener(e -> {
//            String keyword = searchField.getText().trim().toLowerCase();
//            if (keyword.isEmpty()) {
//                JOptionPane.showMessageDialog(frame, "Please enter a Car ID or Customer ID to search.", "Input Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            ArrayList<SalesRecords> records = SalesRecords.loadSalesRecords();
//            tableModel.setRowCount(0);
//
//            boolean found = false;
//            for (SalesRecords record : records) {
//                if (record.getSalesmanID().equals(currentSalesman.ID)
//                        && (record.getCarID().toLowerCase().contains(keyword) || record.getCustomerID().toLowerCase().contains(keyword))) {
//                    tableModel.addRow(new Object[]{
//                        record.getCustomerID(),
//                        record.getCarID(),
//                        record.getPrice(),
//                        record.getStatus(),
//                        record.getComment()
//                    });
//                    found = true;
//                }
//            }
//
//            if (!found) {
//                JOptionPane.showMessageDialog(frame, "No records found for '" + keyword + "'. Showing all sales again.");
//                loadSalesData.run();
//            }
//        });
//
//        // Reset action
//        resetButton.addActionListener(e -> {
//            searchField.setText("");
//            loadSalesData.run();
//        });
//
//        loadSalesData.run();
//        frame.setVisible(true);
//    }
    public void viewSalesHistoryWindow() {
        JFrame frame = new JFrame("Sales History");
        frame.setSize(900, 600);  // Reduced height since we removed some components
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Top panel with title and filters
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Your Sales History", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        // Search field
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        // Sort options
        JComboBox<String> sortCombo = new JComboBox<>(new String[]{"Newest First", "Oldest First"});
        JButton applySortButton = new JButton("Apply Sort");

        filterPanel.add(new JLabel("Search:"));
        filterPanel.add(searchField);
        filterPanel.add(searchButton);
        filterPanel.add(new JLabel("Sort by:"));
        filterPanel.add(sortCombo);
        filterPanel.add(applySortButton);

        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(filterPanel, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);

        // Main table
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"Date", "Customer ID", "Car ID", "Price", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        JTable table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Summary panel at bottom
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Total sales count
        JPanel salesCountPanel = new JPanel(new BorderLayout());
        salesCountPanel.setBorder(BorderFactory.createTitledBorder("Total Sales"));
        JLabel salesCountLabel = new JLabel("0", JLabel.CENTER);
        salesCountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        salesCountPanel.add(salesCountLabel, BorderLayout.CENTER);

        // Total earnings
        JPanel earningsPanel = new JPanel(new BorderLayout());
        earningsPanel.setBorder(BorderFactory.createTitledBorder("Total Earnings"));
        JLabel earningsLabel = new JLabel("RM 0.00", JLabel.CENTER);
        earningsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        earningsPanel.add(earningsLabel, BorderLayout.CENTER);

        // Back button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back");
        buttonPanel.add(backButton);

        summaryPanel.add(salesCountPanel);
        summaryPanel.add(earningsPanel);
        summaryPanel.add(buttonPanel);
        frame.add(summaryPanel, BorderLayout.SOUTH);

        // Load data method
        Runnable loadData = () -> {
            ArrayList<SalesRecords> records = SalesRecords.loadSalesRecords();
            tableModel.setRowCount(0);

            double totalEarnings = 0;
            int totalSales = 0;

            // Filter for current salesman and sort
            records.removeIf(record -> !record.getSalesmanID().equals(currentSalesman.ID));

            // Apply sorting
            if (sortCombo.getSelectedItem().equals("Newest First")) {
                records.sort((r1, r2) -> r2.getDate().compareTo(r1.getDate()));
            } else {
                records.sort((r1, r2) -> r1.getDate().compareTo(r2.getDate()));
            }

            // Add to table and calculate totals
            for (SalesRecords record : records) {
                tableModel.addRow(new Object[]{
                    record.getDate(),
                    record.getCustomerID(),
                    record.getCarID(),
                    String.format("RM %.2f", record.getPrice()),
                    record.getStatus()
                });

                totalEarnings += record.getPrice();
                totalSales++;
            }

            // Update summary
            salesCountLabel.setText(String.valueOf(totalSales));
            earningsLabel.setText(String.format("RM %.2f", totalEarnings));
        };

        // Search function
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim().toLowerCase();
            if (keyword.isEmpty()) {
                loadData.run();
                return;
            }

            ArrayList<SalesRecords> allRecords = SalesRecords.loadSalesRecords();
            tableModel.setRowCount(0);

            double totalEarnings = 0;
            int totalSales = 0;

            for (SalesRecords record : allRecords) {
                if (record.getSalesmanID().equals(currentSalesman.ID)
                        && (record.getCarID().toLowerCase().contains(keyword)
                        || record.getCustomerID().toLowerCase().contains(keyword))) {

                    tableModel.addRow(new Object[]{
                        record.getDate(),
                        record.getCustomerID(),
                        record.getCarID(),
                        String.format("RM %.2f", record.getPrice()),
                        record.getStatus()
                    });

                    totalEarnings += record.getPrice();
                    totalSales++;
                }
            }

            salesCountLabel.setText(String.valueOf(totalSales));
            earningsLabel.setText(String.format("RM %.2f", totalEarnings));
        });

        // Sort button
        applySortButton.addActionListener(e -> loadData.run());

        // Back button
        backButton.addActionListener(e -> {
            frame.dispose();
            new SalesmanDashboard(currentSalesman);
        });

        // Initial load
        loadData.run();
        frame.setVisible(true);
    }

// Create stats panel with title and scrollable text area
    private JPanel createStatsPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));

        JTextArea statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        statsArea.setBackground(new Color(240, 240, 240));
        statsArea.setText("No data available");
        statsArea.setForeground(Color.GRAY);

        JScrollPane scrollPane = new JScrollPane(statsArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadSalesData(DefaultTableModel tableModel, JPanel popularCarsPanel, JPanel earningsPanel) {
        tableModel.setRowCount(0);

        ArrayList<SalesRecords> allRecords = SalesRecords.loadSalesRecords();

        Map<String, Integer> carSalesCount = new HashMap<>();
        double totalEarnings = 0;

        for (SalesRecords record : allRecords) {
            if (!record.getSalesmanID().equals(currentSalesman.ID)) {
                continue;
            }

            tableModel.addRow(new Object[]{
                record.getCustomerID(),
                record.getCarID(),
                record.getPrice(),
                record.getStatus(),
                record.getComment()
            });

//            // Use Map.merge to update counts more cleanly
//            carSalesCount.merge(record.getCarID(), 1, Integer::sum);
//
//            try {
//                String priceText = record.getPrice().replaceAll("[^\\d.]", "");
//                totalEarnings += Integer.parseInt(priceText);
//            } catch (NumberFormatException e) {
//                System.err.println("Invalid price: " + record.getPrice());
//            }
        }

        updatePopularCarsPanel(popularCarsPanel, carSalesCount);
        updateEarningsPanel(earningsPanel, totalEarnings);
    }

    void updatePopularCarsPanel(JPanel panel, Map<String, Integer> carSalesCount) {
        JTextArea textArea = (JTextArea) ((JScrollPane) panel.getComponent(0)).getViewport().getView();

        if (carSalesCount.isEmpty()) {
            textArea.setText("No cars sold");
            textArea.setForeground(Color.GRAY);
            return;
        }

        // Sort and collect to a formatted string using streams
        String text = carSalesCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(entry -> entry.getKey() + ": " + entry.getValue() + " sales")
                .collect(Collectors.joining("\n"));

        textArea.setText(text);
        textArea.setForeground(Color.BLACK);
    }
// Update the earnings panel text area with total earnings

    private void updateEarningsPanel(JPanel panel, double totalEarnings) {
        JTextArea textArea = (JTextArea) ((JScrollPane) panel.getComponent(0)).getViewport().getView();

        if (totalEarnings == 0) {
            textArea.setText("No earnings yet");
            textArea.setForeground(Color.GRAY);
        } else {
            textArea.setText(String.format("Total earnings: $%,.2f", totalEarnings));
            textArea.setForeground(Color.BLACK);
        }
    }

}
