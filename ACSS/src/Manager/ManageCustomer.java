package Manager;

import Customer.Customer;
import Customer.CustomerDataIO;
import Customer.DeletedCustomer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManageCustomer extends JFrame {
    private List<Customer> customerList = new ArrayList<>();
    private static final String FILE_NAME = "data/CustomersList.txt";
    private Object manager; // Store the manager object to pass back

    // GUI Components
    private JTextArea outputArea;
    private JTextField idField, nameField, emailField, passwordField, searchIdField;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton listButton;
    private JCheckBox approvedCheckBox;

    public ManageCustomer(Object manager) {
        super("Customer Management System");
        this.manager = manager;
        initialize(); // Initialize GUI first
        loadCustomersFromFile(); // Then load data
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
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Customer Information"));

        // Search ID Field
        panel.add(new JLabel("Search by ID:"));
        searchIdField = new JTextField();
        searchIdField.setToolTipText("Enter Customer ID to search, update, delete, or approve");
        panel.add(searchIdField);

        // ID Field (Display Only)
        panel.add(new JLabel("Customer ID:"));
        idField = new JTextField();
        idField.setToolTipText("Customer ID (Display Only)");
        idField.setEditable(false);
        idField.setBackground(Color.LIGHT_GRAY);
        panel.add(idField);

        // Name Field
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        // Email Field
        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        // Password Field
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // Approved Checkbox
        panel.add(new JLabel("Approved:"));
        approvedCheckBox = new JCheckBox();
        approvedCheckBox.setSelected(false);
        panel.add(approvedCheckBox);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Delete Button
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this::deleteCustomer);
        panel.add(deleteButton);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this::searchCustomer);
        panel.add(searchButton);

        // Update Button
        updateButton = new JButton("Update");
        updateButton.addActionListener(this::updateCustomer);
        panel.add(updateButton);

        // Approve Button
        JButton approveCustomerButton = new JButton("Approve");
        approveCustomerButton.addActionListener(this::approveCustomer);
        panel.add(approveCustomerButton);

        // List All Button
        listButton = new JButton("List All");
        listButton.addActionListener(this::listAllCustomers);
        panel.add(listButton);

        // Back Button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> returnToManagerDashboard());
        panel.add(backButton);

        return panel;
    }

    private void approveCustomer(ActionEvent e) {
        String id = searchIdField.getText().trim();
        if (id.isEmpty()) {
            showMessage("Please enter Customer ID to approve");
            return;
        }

        // Use CustomerDataIO to find and approve customer
        Customer found = CustomerDataIO.searchId(id);
        if (found != null) {
            found.setApproved(true);
            CustomerDataIO.writeCustomer(); // Save using CustomerDataIO
            showMessage("Customer approved successfully.");
            approvedCheckBox.setSelected(true);
            // Refresh local list
            loadCustomersFromFile();
        } else {
            showMessage("Customer not found.");
        }
    }

    private void deleteCustomer(ActionEvent e) {
        String id = searchIdField.getText().trim();
        if (id.isEmpty()) {
            showMessage("Please enter Customer ID");
            return;
        }

        // Use CustomerDataIO to properly delete customer (moves to deleted list)
        boolean success = CustomerDataIO.deleteCustomer(id);
        if (success) {
            showMessage("Customer moved to deleted customers file successfully.");
            clearFields();
            // Refresh local list
            loadCustomersFromFile();
        } else {
            showMessage("Customer not found.");
        }
    }

    private void restoreCustomer(ActionEvent e) {
        String id = searchIdField.getText().trim();
        if (id.isEmpty()) {
            showMessage("Please enter Customer ID to restore");
            return;
        }

        // Confirm restoration
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to restore this deleted customer?",
                "Confirm Restoration",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Use CustomerDataIO to restore customer
            boolean success = CustomerDataIO.restoreCustomer(id);
            if (success) {
                showMessage("Customer restored successfully.");
                clearFields();
                // Refresh local list
                loadCustomersFromFile();
            } else {
                showMessage("Deleted customer not found or restoration failed.");
            }
        }
    }

    private void listDeletedCustomers(ActionEvent e) {
        // Load deleted customers data first
        CustomerDataIO.readDeletedCustomer();

        if (CustomerDataIO.allDeletedCustomers.isEmpty()) {
            showMessage("No deleted customers to show.");
        } else {
            StringBuilder sb = new StringBuilder("--- List of All Deleted Customers ---\n");
            for (DeletedCustomer dc : CustomerDataIO.allDeletedCustomers) {
                sb.append(formatDeletedCustomerInfo(dc)).append("\n\n");
            }
            outputArea.setText(sb.toString());
        }
    }

    private void searchCustomer(ActionEvent e) {
        String id = searchIdField.getText().trim();
        if (id.isEmpty()) {
            showMessage("Please enter Customer ID");
            return;
        }

        Customer found = findCustomerById(id);
        if (found != null) {
            outputArea.setText("Customer Found:\n" + formatCustomerInfo(found));
            idField.setText(found.getCustomerId());
            nameField.setText(found.getUsername());
            emailField.setText(found.getEmail());
            passwordField.setText(found.getPassword());
            approvedCheckBox.setSelected(found.isApproved());
        } else {
            showMessage("Customer not found.");
        }
    }

    private void updateCustomer(ActionEvent e) {
        String id = searchIdField.getText().trim();
        if (id.isEmpty()) {
            showMessage("Please enter Customer ID");
            return;
        }

        Customer found = CustomerDataIO.searchId(id);
        if (found != null) {
            String newName = nameField.getText().trim();
            if (!newName.isEmpty()) {
                found.setUsername(newName);
            }

            String newEmail = emailField.getText().trim();
            if (!newEmail.isEmpty()) {
                found.setEmail(newEmail);
            }

            String newPassword = passwordField.getText().trim();
            if (!newPassword.isEmpty()) {
                found.setPassword(newPassword);
            }

            found.setApproved(approvedCheckBox.isSelected());
            CustomerDataIO.writeCustomer(); // Save using CustomerDataIO
            showMessage("Customer information updated.");
            // Refresh local list
            loadCustomersFromFile();
        } else {
            showMessage("Customer not found.");
        }
    }

    private void listAllCustomers(ActionEvent e) {
        if (customerList.isEmpty()) {
            showMessage("No customers to show.");
        } else {
            StringBuilder sb = new StringBuilder("--- List of All Customers ---\n");
            customerList.forEach(c -> sb.append(formatCustomerInfo(c)).append("\n\n"));
            outputArea.setText(sb.toString());
        }
    }

    private Customer findCustomerById(String id) {
        return customerList.stream()
                .filter(c -> c.getCustomerId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    private Customer findCustomerByEmail(String email) {
        return customerList.stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    private String formatCustomerInfo(Customer customer) {
        return String.format("ID: %s\nName: %s\nEmail: %s\nApproved: %s\nUser Type: %s",
                customer.getCustomerId(),
                customer.getUsername(),
                customer.getEmail(),
                customer.isApproved() ? "Yes" : "No",
                customer.getUserType());
    }

    private String formatDeletedCustomerInfo(DeletedCustomer customer) {
        return String.format("ID: %s\nName: %s\nEmail: %s\nUser Type: %s\nStatus: DELETED",
                customer.getUserId(),
                customer.getUsername(),
                customer.getEmail(),
                customer.getUserType());
    }

    private void loadCustomersFromFile() {
        // Use CustomerDataIO to load data
        CustomerDataIO.readCustomer();

        // Copy to local list for compatibility with existing code
        customerList.clear();
        customerList.addAll(CustomerDataIO.allCustomers);

        showMessage("Loaded " + customerList.size() + " customers.");
    }

    private Customer parseCustomerFromLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid customer data format. Expected 5 fields, got " + parts.length);
        }

        String customerId = parts[0].trim();
        String name = parts[1].trim();
        String email = parts[2].trim();
        String password = parts[3].trim();
        boolean approved = Boolean.parseBoolean(parts[4].trim());

        return new Customer(customerId, name, email, password, approved);
    }

    private void saveCustomersToFile() {
        // Use CustomerDataIO instead of manual file writing
        CustomerDataIO.writeCustomer();
        CustomerDataIO.writeDeletedCustomer();
    }

    private void clearFields() {
        searchIdField.setText("");
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        approvedCheckBox.setSelected(false);
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

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new ManageCustomer(null));
//    }

    JPanel getPanel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}