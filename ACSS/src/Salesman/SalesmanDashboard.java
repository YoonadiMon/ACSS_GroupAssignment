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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

// Inheritance: implements ActionListener (interface)
public class SalesmanDashboard extends UserDashboard implements ActionListener {

    // Encapsulation: private fields for buttons and current salesman info
    private JPanel buttonPanel;
    private JButton editProfileButton, viewCarsButton, viewCarRequestButton,
            updateCarStatusButton, recordSalesHistory, markCarAsPaidButton,
            logoutButton;

    private Salesman currentSalesman;

    public SalesmanDashboard(Salesman salesman) {
        // Inheritance: calling superclass constructor to setup frame and welcome label
        super("Salesman Dashboard!", "Welcome, " + salesman.getName() + " (ID: " + salesman.getID() + ")");
        this.currentSalesman = salesman;

        // Setup buttons specific to SalesmanDashboard
        setupButtons();

        // Add action listeners for buttons
        editProfileButton.addActionListener(this);
        viewCarsButton.addActionListener(this);
        viewCarRequestButton.addActionListener(this);
        updateCarStatusButton.addActionListener(this);
        recordSalesHistory.addActionListener(this);
        markCarAsPaidButton.addActionListener(this);
        logoutButton.addActionListener(this);

        frame.setVisible(true);
    }

    @Override
    protected void setupButtons() {
        // Polymorphism: overriding abstract method from UserDashboard to define buttons

        buttonPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create buttons using inherited method for consistent styling
        editProfileButton = createStyledButton("Edit Profile");
        viewCarsButton = createStyledButton("View Car Status");
        viewCarRequestButton = createStyledButton("View Car Requests");
        updateCarStatusButton = createStyledButton("Update Car Status");
        recordSalesHistory = createStyledButton("View Sales History");
        markCarAsPaidButton = createStyledButton("Mark Car as Paid");
        logoutButton = createStyledButton("Logout");
        logoutButton.setBackground(new Color(255, 100, 100)); // red for logout

        // Add buttons to panel
        buttonPanel.add(editProfileButton);
        buttonPanel.add(viewCarsButton);
        buttonPanel.add(viewCarRequestButton);
        buttonPanel.add(updateCarStatusButton);
        buttonPanel.add(recordSalesHistory);
        buttonPanel.add(markCarAsPaidButton);

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutButton);

        // Add button panel and logout button to main panel from superclass
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);

        editProfileButton.addActionListener(this);
        viewCarsButton.addActionListener(this);
        viewCarRequestButton.addActionListener(this);
        updateCarStatusButton.addActionListener(this);
        recordSalesHistory.addActionListener(this);
        markCarAsPaidButton.addActionListener(this);
        logoutButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose(); // Close current frame before navigating

        if (e.getSource() == logoutButton) {
            new SalesmanGUI(400, 250);
        } else if (e.getSource() == editProfileButton) {
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
        }
    }
    
    
    private void openEditProfileWindow() {
        
        JFrame editProfileFrame = new JFrame("Edit Profile");
        try {
            
            SwingUtilities.updateComponentTreeUI(editProfileFrame);
        } catch (Exception e) {
            e.printStackTrace();
        }

        editProfileFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        editProfileFrame.setMinimumSize(new Dimension(450, 550));
        editProfileFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        editProfileFrame.add(mainPanel);

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Edit Salesman Profile");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        titlePanel.add(title);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        JPanel currentInfoPanel = createCurrentInfoPanel();
        tabbedPane.addTab("Current Information", currentInfoPanel);

        JPanel editInfoPanel = createEditInfoPanel(editProfileFrame);
        tabbedPane.addTab("Edit Information", editInfoPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton saveButton = new JButton("Save Changes");

        saveButton.addActionListener(e -> saveChanges(editProfileFrame));

        JButton closeButton = new JButton("Go Back");

        closeButton.addActionListener(e -> {
            editProfileFrame.dispose();
            new SalesmanDashboard(currentSalesman);
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        WindowNav.setCloseOperation(editProfileFrame, () -> new SalesmanDashboard(currentSalesman));

        editProfileFrame.pack();
        editProfileFrame.setVisible(true);
    }

    private JPanel createCurrentInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel currentInfoLabel = new JLabel("Current Information:");
        currentInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(currentInfoLabel, gbc);

        String[][] infoData = {
            {"Name:", currentSalesman.getName()},
            {"Password:", "********"},
            {"Security Question:", currentSalesman.getSecurityQuestion()},
            {"Security Answer:", "********"}
        };

        for (String[] data : infoData) {
            gbc.gridy++;
            JLabel label = new JLabel(data[0]);
            label.setFont(new Font("SansSerif", Font.BOLD, 12));
            panel.add(label, gbc);

            gbc.gridx = 1;
            JLabel value = new JLabel(data[1]);
            panel.add(value, gbc);
            gbc.gridx = 0;
        }

        return panel;
    }

    private JPanel createEditInfoPanel(JFrame parentFrame) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel editInfoLabel = new JLabel("Edit Information:");
        editInfoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(editInfoLabel, gbc);

        JTextField nameField = new JTextField(currentSalesman.getName(), 20);
        nameField.setName("nameField");

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setName("passwordField");

        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setName("confirmPasswordField");

        JTextField questionField = new JTextField(currentSalesman.getSecurityQuestion(), 20);
        questionField.setName("questionField");

        JTextField answerField = new JTextField(currentSalesman.getSecurityAnswer(), 20);
        answerField.setName("answerField");

        JProgressBar strengthBar = new JProgressBar(0, 4);
        strengthBar.setStringPainted(true);
        strengthBar.setString("Password Strength");
        strengthBar.setForeground(Color.RED);

        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                update();
            }

            public void removeUpdate(DocumentEvent e) {
                update();
            }

            public void insertUpdate(DocumentEvent e) {
                update();
            }

            private void update() {
                String password = new String(passwordField.getPassword());
                int strength = calculatePasswordStrength(password);
                strengthBar.setValue(strength);
                if (strength < 2) {
                    strengthBar.setForeground(Color.RED);
                } else if (strength < 4) {
                    strengthBar.setForeground(Color.ORANGE);
                } else {
                    strengthBar.setForeground(Color.GREEN);
                }
            }

            private int calculatePasswordStrength(String password) {
                int strength = 0;
                if (password.length() >= 8) {
                    strength++;
                }
                if (password.matches(".*[A-Z].*")) {
                    strength++;
                }
                if (password.matches(".*[0-9].*")) {
                    strength++;
                }
                if (password.matches(".*[!@#$%^&*].*")) {
                    strength++;
                }
                return strength;
            }
        });

        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");

        showPasswordCheckbox.addActionListener(e -> {
            char echo = showPasswordCheckbox.isSelected() ? (char) 0 : '*';
            passwordField.setEchoChar(echo);
            confirmPasswordField.setEchoChar(echo);
        });

        addFormField(panel, gbc, "New Name:", 'N', "Enter your full name", nameField);
        addFormField(panel, gbc, "New Password:", 'P', "Minimum 8 characters with mix of letters and numbers", passwordField);
        addFormField(panel, gbc, "Confirm Password:", 'C', "Re-enter your password", confirmPasswordField);

        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(strengthBar, gbc);
        gbc.gridwidth = 1;

        addFormField(panel, gbc, "Security Question:", 'Q', "What was your first pet's name?", questionField);
        addFormField(panel, gbc, "Security Answer:", 'A', "Answer to your security question", answerField);

        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(showPasswordCheckbox, gbc);

        return panel;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText,
            char mnemonic, String tooltip, JComponent field) {
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel label = new JLabel(labelText);

        label.setLabelFor(field);
        panel.add(label, gbc);

        gbc.gridx = 1;
        field.setToolTipText(tooltip);
        panel.add(field, gbc);
    }

    private void saveChanges(JFrame editProfileFrame) {
        Component editTab = ((JTabbedPane) ((JPanel) editProfileFrame.getContentPane().getComponent(0)).getComponent(1)).getComponent(1);

        JTextField nameField = (JTextField) findComponentByName((Container) editTab, "nameField");
        JPasswordField passwordField = (JPasswordField) findComponentByName((Container) editTab, "passwordField");
        JPasswordField confirmPasswordField = (JPasswordField) findComponentByName((Container) editTab, "confirmPasswordField");
        JTextField questionField = (JTextField) findComponentByName((Container) editTab, "questionField");
        JTextField answerField = (JTextField) findComponentByName((Container) editTab, "answerField");

        String newName = nameField.getText().trim();
        String newPassword = String.valueOf(passwordField.getPassword()).trim();
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword()).trim();
        String newQuestion = questionField.getText().trim();
        String newAnswer = answerField.getText().trim();

        if (newName.isEmpty() || newQuestion.isEmpty() || newAnswer.isEmpty()) {
            showErrorDialog(editProfileFrame, "All fields except password are required.\nPlease fill in all fields.");
            return;
        }

        if (!newPassword.isEmpty() && !newPassword.equals(confirmPassword)) {
            showErrorDialog(editProfileFrame, "Password fields do not match.\nPlease enter the same password in both fields.");
            return;
        }

        if (!newPassword.isEmpty() && newPassword.length() < 8) {
            showErrorDialog(editProfileFrame, "Password must be at least 8 characters long.");
            return;
        }

        ArrayList<Salesman> updatedList = SalesmanList.loadSalesmanDataFromFile();
        for (int i = 0; i < updatedList.size(); i++) {
            if (updatedList.get(i).getID().equals(currentSalesman.getID())) {
                currentSalesman.setName(newName);
                if (!newPassword.isEmpty()) {
                    currentSalesman.setPassword(newPassword);
                }
                currentSalesman.setSecurityQuestion(newQuestion);
                currentSalesman.getSecurityAnswer();
                updatedList.set(i, currentSalesman);

                SalesmanList.saveSalesmanDataToFile(updatedList);

                JOptionPane.showMessageDialog(editProfileFrame,
                        "Profile updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                editProfileFrame.dispose();
                new SalesmanDashboard(currentSalesman);
                return;
            }
        }
    }

    private void showErrorDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent,
                "<html><div style='width: 200px;'>" + message.replace("\n", "<br>") + "</div></html>",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private Component findComponentByName(Container container, String name) {
        for (Component comp : container.getComponents()) {
            if (name.equals(comp.getName())) {
                return comp;
            }
            if (comp instanceof Container) {
                Component child = findComponentByName((Container) comp, name);
                if (child != null) {
                    return child;
                }
            }
        }
        return null;
    }

//    private JPanel createSummaryBox(String title, String value, Color bgColor) {
//        JPanel box = new JPanel(new BorderLayout());
//        box.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(Color.GRAY, 1),
//                BorderFactory.createEmptyBorder(10, 10, 10, 10)
//        ));
//        box.setBackground(bgColor);
//
//        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
//
//        JLabel valueLabel = new JLabel(value, JLabel.CENTER);
//        valueLabel.setFont(new Font("Arial", Font.BOLD, 18));
//
//        box.add(titleLabel, BorderLayout.NORTH);
//        box.add(valueLabel, BorderLayout.CENTER);
//
//        return box;
//    }
//
//    private void updateSummaryBoxes(ArrayList<Car> cars, JPanel totalBox, JPanel paidBox, JPanel availableBox) {
//        int totalCount = cars.size();
//        int paidCount = 0;
//        int availableCount = 0;
//
//        for (Car car : cars) {
//            if (car.getStatus().equalsIgnoreCase("Paid")) {
//                paidCount++;
//            } else if (car.getStatus().equalsIgnoreCase("Available")) {
//                availableCount++;
//            }
//        }
//
//        ((JLabel) totalBox.getComponent(1)).setText(String.valueOf(totalCount));
//        ((JLabel) paidBox.getComponent(1)).setText(String.valueOf(paidCount));
//        ((JLabel) availableBox.getComponent(1)).setText(String.valueOf(availableCount));
//    }
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

        // Table model
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Car ID", "Brand", "Price", "Status"}, 0);

        // Create the table with custom styling
        JTable carTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                // Alternate row coloring
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(250, 250, 250) : Color.WHITE);
                }

                // Status column styling
                if (column == 3) {
                    String status = getValueAt(row, column).toString();
                    if (status.equalsIgnoreCase("Paid")) {
                        c.setForeground(Color.RED);
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else if (status.equalsIgnoreCase("Available")) {
                        c.setForeground(new Color(0, 150, 0)); // Dark green
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                } else {
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        };

        // Table styling
        carTable.setRowHeight(25);
        carTable.setShowGrid(false);
        carTable.setIntercellSpacing(new Dimension(0, 1));
        carTable.setFont(new Font("Arial", Font.PLAIN, 12));

        // Header styling
        JTableHeader header = carTable.getTableHeader();
        header.setBackground(new Color(230, 240, 255)); // Light blue
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 13));

        // Selection styling
        carTable.setSelectionBackground(new Color(200, 220, 255));
        carTable.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(carTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        scrollPane.getViewport().setBackground(Color.WHITE);
        carListFrame.add(scrollPane, BorderLayout.CENTER);

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

        carListFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                loadAllCars.run();
            }

            @Override
            public void windowActivated(WindowEvent e) {
                loadAllCars.run();
            }
        });

        // Top panel for search and filter
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel searchLabel = new JLabel("Search (CarID/Brand/Status):");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel filterLabel = new JLabel("Filter by:");
        String[] filterOptions = {"All", "Brand: Toyota", "Brand: Honda", "Status: Available", "Status: Paid"};
        JComboBox<String> filterComboBox = new JComboBox<>(filterOptions);
        JButton filterButton = new JButton("Apply Filter");

        filterPanel.add(filterLabel);
        filterPanel.add(filterComboBox);
        filterPanel.add(filterButton);

        topPanel.add(searchPanel);
        topPanel.add(filterPanel);
        carListFrame.add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

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
                    loadAllCars.run();
                }
            } else {
                JOptionPane.showMessageDialog(carListFrame,
                        "Please enter a Car ID, Brand, or Status to search.",
                        "Search Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Filter button action
        filterButton.addActionListener(e -> {
            String selected = (String) filterComboBox.getSelectedItem();
            ArrayList<Car> carList = CarList.loadCarDataFromFile();
            tableModel.setRowCount(0);

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
                    case "Status: Paid":
                        matches = car.getStatus().equalsIgnoreCase("Paid");
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

        carListFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        WindowNav.setCloseOperation(carListFrame, () -> new SalesmanDashboard(currentSalesman));

        // Display the window
        carListFrame.setVisible(true);
    }

    public void viewCarRequestWindow() {
        // OOP - Inheritance: Salesman inherits from a general User class
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

        // Create the table with custom styling
        JTable requestTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                // Alternate row coloring
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(250, 250, 250) : Color.WHITE);
                }

                // Status column styling
                if (column == 2) { // Status is in column 2
                    String status = getValueAt(row, column).toString();
                    if (status.equalsIgnoreCase("Pending")) {
                        c.setForeground(new Color(255, 140, 0)); // Orange
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else if (status.equalsIgnoreCase("Booked")) {
                        c.setForeground(new Color(0, 100, 0)); // Dark green
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else if (status.equalsIgnoreCase("Cancelled")) {
                        c.setForeground(Color.RED);
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                } else {
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        };

        // Table styling
        requestTable.setRowHeight(25);
        requestTable.setShowGrid(false);
        requestTable.setIntercellSpacing(new Dimension(0, 1));
        requestTable.setFont(new Font("Arial", Font.PLAIN, 12));
        requestTable.setEnabled(false);

        // Header styling
        JTableHeader header = requestTable.getTableHeader();
        header.setBackground(new Color(230, 240, 255)); // Light blue
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 13));

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

        WindowNav.setCloseOperation(requestFrame, () -> new SalesmanDashboard(currentSalesman));
        requestFrame.setVisible(true);
    }

    // Helper method to create form rows
    private JPanel createFormRow(String labelText, JComponent field, int labelWidth) {
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(labelWidth, label.getPreferredSize().height));
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.add(label);
        row.add(field);
        return row;
    }

    public void updateCarStatusWindow() {
        JFrame updateFrame = new JFrame("Update Car Status");
        updateFrame.setSize(800, 500);
        updateFrame.setLocationRelativeTo(null);
        updateFrame.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("All Car Requests", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        updateFrame.add(titleLabel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Customer ID", "Car ID", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        // Create the table with custom styling
        JTable requestTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                // Alternate row coloring
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(250, 250, 250) : Color.WHITE);
                }

                // Status column styling
                if (column == 2) {
                    String status = getValueAt(row, column).toString();
                    if (status.equalsIgnoreCase("Pending")) {
                        c.setForeground(new Color(255, 140, 0)); // Orange
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else if (status.equalsIgnoreCase("Booked")) {
                        c.setForeground(new Color(0, 100, 0)); // Dark green
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else if (status.equalsIgnoreCase("Paid")) {
                        c.setForeground(Color.RED);
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else if (status.equalsIgnoreCase("Rejected")) {
                        c.setForeground(new Color(139, 0, 0)); // Dark red
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else if (status.equalsIgnoreCase("Cancelled")) {
                        c.setForeground(Color.DARK_GRAY);
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                } else {
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        };

        // Table styling
        requestTable.setRowHeight(25);
        requestTable.setShowGrid(false);
        requestTable.setIntercellSpacing(new Dimension(0, 1));
        requestTable.setFont(new Font("Arial", Font.PLAIN, 12));
        requestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Header styling
        JTableHeader header = requestTable.getTableHeader();
        header.setBackground(new Color(230, 240, 255)); // Light blue
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(requestTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        updateFrame.add(scrollPane, BorderLayout.CENTER);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JComboBox<String> statusFilter = new JComboBox<>(new String[]{"All", "Pending", "Booked", "Paid", "Rejected", "Cancelled"});
        statusFilter.setSelectedItem("All");

        searchPanel.add(new JLabel("Search (Customer ID / Car ID):"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(new JLabel("Status Filter:"));
        searchPanel.add(statusFilter);
        updateFrame.add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField customerIDField = new JTextField(15);
        JTextField carIDField = new JTextField(15);
        JTextField commentField = new JTextField(20);

        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");
        JButton cancelBtn = new JButton("Cancel");
        JButton closeButton = new JButton("Go Back");

        // Set consistent button size
        Dimension buttonSize = new Dimension(100, 30);
        approveBtn.setPreferredSize(buttonSize);
        rejectBtn.setPreferredSize(buttonSize);
        cancelBtn.setPreferredSize(buttonSize);
        closeButton.setPreferredSize(buttonSize);

        // Form rows
        JPanel row0 = createFormRow("Customer ID:", customerIDField, 120);
        JPanel row1 = createFormRow("Car ID:", carIDField, 120);
        JPanel row2 = createFormRow("Comment (optional):", commentField, 120);

        // Button row
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonRow.add(approveBtn);
        buttonRow.add(rejectBtn);
        buttonRow.add(cancelBtn);
        buttonRow.add(closeButton);

        inputPanel.add(row0);
        inputPanel.add(row1);
        inputPanel.add(row2);
        inputPanel.add(buttonRow);
        updateFrame.add(inputPanel, BorderLayout.SOUTH);

        // Selection listener
        requestTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = requestTable.getSelectedRow();
                if (selectedRow >= 0) {
                    customerIDField.setText(requestTable.getValueAt(selectedRow, 0).toString());
                    carIDField.setText(requestTable.getValueAt(selectedRow, 1).toString());
                }
            }
        });

        // Load data with filtering
        Runnable loadRequests = () -> {
            tableModel.setRowCount(0);
            ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();
            String selectedStatus = statusFilter.getSelectedItem().toString();

            for (CarRequest req : requests) {
                if (req.getSalesmanID().equals(currentSalesman.ID)) {
                    if (selectedStatus.equals("All") || req.getRequestStatus().equalsIgnoreCase(selectedStatus)) {
                        tableModel.addRow(new Object[]{
                            req.getCustomerID(),
                            req.getCarID(),
                            req.getRequestStatus()
                        });
                    }
                }
            }
        };

        // Initial load
        loadRequests.run();

        // Search button action
        searchButton.addActionListener(e -> {
            String searchInput = searchField.getText().trim();
            String selectedStatus = statusFilter.getSelectedItem().toString();

            if (!searchInput.isEmpty()) {
                ArrayList<CarRequest> requestList = CarRequest.loadCarRequestDataFromFile();
                tableModel.setRowCount(0);
                boolean found = false;

                for (CarRequest req : requestList) {
                    if (req.getSalesmanID().equals(currentSalesman.ID)
                            && (req.getCustomerID().equalsIgnoreCase(searchInput)
                            || req.getCarID().equalsIgnoreCase(searchInput))
                            && (selectedStatus.equals("All")
                            || req.getRequestStatus().equalsIgnoreCase(selectedStatus))) {

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
                }
            }
            loadRequests.run();
        });

        // Status filter action
        statusFilter.addActionListener(e -> loadRequests.run());

        // Close button action
        closeButton.addActionListener(e -> {
            updateFrame.dispose();
            new SalesmanDashboard(currentSalesman);
        });

        // Window close handler
        updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new SalesmanDashboard(currentSalesman);
            }
        });

        approveBtn.addActionListener(e -> {
            String customerID = customerIDField.getText().trim();
            String carID = carIDField.getText().trim();
            String comment = commentField.getText().trim();
            //String finalComment = comment.isEmpty() ? "." : comment;
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
                loadRequests.run();
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
        rejectBtn.addActionListener(e -> {
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

                        // Check if status is cancelled
                        if (req.getRequestStatus().equalsIgnoreCase("cancelled")) {
                            JOptionPane.showMessageDialog(updateFrame,
                                    "This request has been cancelled and cannot be rejected.",
                                    "Invalid Operation", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // Check if status is booked
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
                    loadRequests.run();
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
        });

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
                            loadRequests.run();
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

//        updateFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        updateFrame.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                Point location = updateFrame.getLocation();
//                updateFrame.dispose();
//                SalesmanDashboard dashboard = new SalesmanDashboard(currentSalesman);
//
//            }
//        });
        WindowNav.setCloseOperation(updateFrame, () -> new SalesmanDashboard(currentSalesman));

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
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Title label
        JLabel titleLabel = new JLabel("Cars Marked as 'Booked'", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        filterPanel.add(new JLabel("Filter by Brand:"));
        JTextField brandFilterField = new JTextField(15);
        filterPanel.add(brandFilterField);
        frame.add(filterPanel, BorderLayout.PAGE_START);

        // Table with all text in black
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"Car ID", "Brand", "Price", "Status", "Customer ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable carTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                // Alternate row coloring
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(250, 250, 250) : Color.WHITE);
                }

                // All text in black
                c.setForeground(Color.BLACK);

                return c;
            }
        };

        // Table styling
        carTable.setRowHeight(25);
        carTable.setFont(new Font("Arial", Font.PLAIN, 12));
        carTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Header styling
        JTableHeader header = carTable.getTableHeader();
        header.setBackground(new Color(230, 240, 255));
        header.setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(carTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField carIDField = new JTextField(15);
        JTextField customerIDField = new JTextField(15);
        JTextField commentField = new JTextField(15);
        JTextField dateField = new JTextField(15);
        dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        JButton paidButton = new JButton("Mark as Paid");
        JButton backButton = new JButton("Go Back");

        // Form rows
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(new JLabel("Car ID:"));
        row1.add(carIDField);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(new JLabel("Customer ID:"));
        row2.add(customerIDField);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.add(new JLabel("Date (YYYY-MM-DD):"));
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

        // Selection listener
        carTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && carTable.getSelectedRow() >= 0) {
                carIDField.setText(carTable.getValueAt(carTable.getSelectedRow(), 0).toString());
                customerIDField.setText(carTable.getValueAt(carTable.getSelectedRow(), 4).toString());
            }
        });

        // Load booked cars
        Runnable loadBookedCars = () -> {
            String brandFilter = brandFilterField.getText().trim().toLowerCase();
            tableModel.setRowCount(0);
            ArrayList<Car> cars = CarList.loadCarDataFromFile();
            ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();

            for (Car car : cars) {
                if (car.getSalesmanId().equals(currentSalesman.ID)
                        && car.getStatus().equalsIgnoreCase("booked")
                        && (brandFilter.isEmpty() || car.getBrand().toLowerCase().contains(brandFilter))) {

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

        // Filter listener
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

        // Paid button action (keep your existing business logic)
        paidButton.addActionListener(e -> {

            String carID = carIDField.getText().trim();
            String customerID = customerIDField.getText().trim();
            String comment = commentField.getText().trim();
            String date = dateField.getText().trim();

            // 1. Validate inputs
            if (carID.isEmpty() || customerID.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter both Car ID and Customer ID.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Validate date format
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

            // 3. Check if car exists and is booked
            ArrayList<Car> cars = CarList.loadCarDataFromFile();
            ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();

            boolean carFound = false;
            boolean validBooking = false;

            for (Car car : cars) {
                if (car.getCarId().equalsIgnoreCase(carID)
                        && car.getSalesmanId().equals(currentSalesman.ID)) {

                    carFound = true;

                    // 4. Check if already paid
                    if (car.getStatus().equalsIgnoreCase("paid")) {
                        JOptionPane.showMessageDialog(frame,
                                "This car has already been marked as paid.",
                                "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 5. Verify booking exists
                    for (CarRequest req : requests) {
                        if (req.getCarID().equalsIgnoreCase(carID)
                                && req.getCustomerID().equalsIgnoreCase(customerID)
                                && req.getRequestStatus().equalsIgnoreCase("booked")) {

                            validBooking = true;
                            break;
                        }
                    }

                    if (!validBooking) {
                        JOptionPane.showMessageDialog(frame,
                                "No valid booking found for this car and customer.",
                                "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 6. Update car status
                    car.setStatus("paid");

                    // 7. Update request status
                    ArrayList<CarRequest> updatedRequests = new ArrayList<>();
                    for (CarRequest req : requests) {
                        if (req.getCarID().equalsIgnoreCase(carID)) {
                            if (req.getCustomerID().equalsIgnoreCase(customerID)) {
                                // Update the booked request to paid
                                updatedRequests.add(new CarRequest(
                                        req.getCustomerID(),
                                        req.getCarID(),
                                        req.getSalesmanID(),
                                        "paid",
                                        comment.isEmpty() ? "Payment completed" : comment
                                ));
                            } else {
                                // Reject other requests for this car
                                updatedRequests.add(new CarRequest(
                                        req.getCustomerID(),
                                        req.getCarID(),
                                        req.getSalesmanID(),
                                        "rejected",
                                        "Car has been sold"
                                ));
                            }
                        } else {
                            updatedRequests.add(req);
                        }
                    }

                    // 8. Save changes
                    CarList.saveUpdatedCarToFile(cars);
                    CarRequest.writeCarRequests(updatedRequests);

                    // 9. Create sales record
                    SalesRecords sale = new SalesRecords(
                            customerID,
                            carID,
                            currentSalesman.ID,
                            car.getPrice(),
                            "paid",
                            comment.isEmpty() ? "No comments" : comment,
                            date
                    );
                    SalesRecords.saveSalesRecord(sale);

                    // 10. Update UI
                    JOptionPane.showMessageDialog(frame,
                            "Car successfully marked as paid!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadBookedCars.run();
                    carIDField.setText("");
                    customerIDField.setText("");
                    commentField.setText("");
                    break;
                }
            }

            if (!carFound) {
                JOptionPane.showMessageDialog(frame,
                        "No booked car found with ID: " + carID,
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            new SalesmanDashboard(currentSalesman);
        });

        // Initial load
        loadBookedCars.run();

//        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        frame.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                Point location = frame.getLocation();
//                frame.dispose();
//                SalesmanDashboard dashboard = new SalesmanDashboard(currentSalesman);
//
//            }
//        });
        WindowNav.setCloseOperation(frame, () -> new SalesmanDashboard(currentSalesman));

        frame.setVisible(true);
    }

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

    public void viewSalesHistoryWindow() {
        JFrame frame = new JFrame("Sales History");
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Top panel with title and filters
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Your Sales History", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Search field
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(230, 240, 255));
        searchButton.setFocusPainted(false);

        // Sort options
        JComboBox<String> sortCombo = new JComboBox<>(new String[]{"Newest First", "Oldest First"});
        JButton applySortButton = new JButton("Apply Sort");
        applySortButton.setBackground(new Color(230, 240, 255));
        applySortButton.setFocusPainted(false);

        filterPanel.add(new JLabel("Search:"));
        filterPanel.add(searchField);
        filterPanel.add(searchButton);
        filterPanel.add(new JLabel("Sort by:"));
        filterPanel.add(sortCombo);
        filterPanel.add(applySortButton);

        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(filterPanel, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);

        // Main table with custom styling
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"Date", "Customer ID", "Car ID", "Price", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                // Alternate row coloring
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(250, 250, 250) : Color.WHITE);
                }

                // Price column styling (column 3)
                if (column == 3) {
                    c.setForeground(new Color(0, 100, 0)); // Dark green for prices
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                }

                // Status column styling (column 4)
                if (column == 4) {
                    String status = getValueAt(row, column).toString();
                    if (status.equalsIgnoreCase("Completed")) {
                        c.setForeground(new Color(0, 100, 0)); // Dark green
                    } else if (status.equalsIgnoreCase("Cancelled")) {
                        c.setForeground(Color.RED);
                    } else if (status.equalsIgnoreCase("Pending")) {
                        c.setForeground(new Color(255, 140, 0)); // Orange
                    }
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        };

        // Table styling
        table.setRowHeight(25);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setAutoCreateRowSorter(true);

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(230, 240, 255)); // Light blue
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Summary panel at bottom
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        summaryPanel.setBackground(new Color(240, 240, 240));

        // Total sales count
        JPanel salesCountPanel = createSummaryPanel("Total Sales", "0", new Color(230, 240, 255));

        // Total earnings
        JPanel earningsPanel = createSummaryPanel("Total Earnings", "RM 0.00", new Color(230, 240, 255));

        // Back button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(230, 240, 255));
        backButton.setFocusPainted(false);
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
            ((JLabel) salesCountPanel.getComponent(0)).setText(String.valueOf(totalSales));
            ((JLabel) earningsPanel.getComponent(0)).setText(String.format("RM %.2f", totalEarnings));
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

            ((JLabel) salesCountPanel.getComponent(0)).setText(String.valueOf(totalSales));
            ((JLabel) earningsPanel.getComponent(0)).setText(String.format("RM %.2f", totalEarnings));
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

        WindowNav.setCloseOperation(frame, () -> new SalesmanDashboard(currentSalesman));
        frame.setVisible(true);
    }

// Helper method to create summary panels
    private JPanel createSummaryPanel(String title, String value, Color bgColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setBackground(bgColor);

        JLabel label = new JLabel(value, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(label, BorderLayout.CENTER);

        return panel;
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
