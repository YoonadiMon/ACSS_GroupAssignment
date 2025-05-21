package Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManageCustomerGUI extends JFrame {
    private List<Customer> customerList = new ArrayList<>();
    private static final String FILE_NAME = "customerList.txt";

    // GUI Components
    private JTextArea outputArea;
    private JTextField idField, nameField, contactField;
    private JButton approveButton, deleteButton, searchButton, updateButton, listButton;

    public ManageCustomerGUI() {
        super("Customer Management System");
        loadCustomersFromFile();
        initializeGUI();
    }

    private void initializeGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create form panel
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Create output area
        outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Customer Information"));

        // ID Field
        panel.add(new JLabel("Customer ID:"));
        idField = new JTextField();
        panel.add(idField);

        // Name Field
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        // Contact Field
        panel.add(new JLabel("Contact:"));
        contactField = new JTextField();
        panel.add(contactField);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Approve/Add Button
        approveButton = new JButton("Approve/Add");
        approveButton.addActionListener(e -> approveCustomer());
        panel.add(approveButton);

        // Delete Button
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteCustomer());
        panel.add(deleteButton);

        // Search Button
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchCustomer());
        panel.add(searchButton);

        // Update Button
        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateCustomer());
        panel.add(updateButton);

        // List All Button
        listButton = new JButton("List All");
        listButton.addActionListener(e -> listAllCustomers());
        panel.add(listButton);

        return panel;
    }

    private void approveCustomer() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || contact.isEmpty()) {
            showMessage("Please fill in all fields");
            return;
        }

        if (findCustomerById(id) != null) {
            showMessage("Customer with this ID already exists!");
            return;
        }

        Customer newCustomer = new Customer(id, name, contact, true);
        customerList.add(newCustomer);
        saveCustomersToFile();
        showMessage("Customer approved and added successfully.");
        clearFields();
    }

    private void deleteCustomer() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            showMessage("Please enter Customer ID");
            return;
        }

        Customer found = findCustomerById(id);
        if (found != null) {
            customerList.remove(found);
            saveCustomersToFile();
            showMessage("Customer deleted successfully.");
            clearFields();
        } else {
            showMessage("Customer not found.");
        }
    }

    private void searchCustomer() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            showMessage("Please enter Customer ID");
            return;
        }

        Customer found = findCustomerById(id);
        if (found != null) {
            outputArea.setText("Customer Found:\n" + found);
            nameField.setText(found.getName());
            contactField.setText(found.getContact());
        } else {
            showMessage("Customer not found.");
        }
    }

    private void updateCustomer() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            showMessage("Please enter Customer ID");
            return;
        }

        Customer found = findCustomerById(id);
        if (found != null) {
            String newName = nameField.getText().trim();
            if (!newName.isEmpty()) {
                found.setName(newName);
            }

            String newContact = contactField.getText().trim();
            if (!newContact.isEmpty()) {
                found.setContact(newContact);
            }

            saveCustomersToFile();
            showMessage("Customer information updated.");
        } else {
            showMessage("Customer not found.");
        }
    }

    private void listAllCustomers() {
        if (customerList.isEmpty()) {
            showMessage("No customers to show.");
        } else {
            StringBuilder sb = new StringBuilder("--- List of All Customers ---\n");
            customerList.forEach(c -> sb.append(c).append("\n"));
            outputArea.setText(sb.toString());
        }
    }

    private Customer findCustomerById(String id) {
        return customerList.stream()
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    private void loadCustomersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    customerList.add(Customer.fromFileString(line));
                } catch (IllegalArgumentException e) {
                    showMessage("Skipping invalid customer data: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            showMessage("No existing customer file found. Starting fresh.");
        } catch (IOException e) {
            showMessage("Error reading customer file: " + e.getMessage());
        }
    }

    private void saveCustomersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Customer c : customerList) {
                writer.write(c.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            showMessage("Error saving customers to file: " + e.getMessage());
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        contactField.setText("");
    }

    private void showMessage(String message) {
        outputArea.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageCustomerGUI());
    }
}
