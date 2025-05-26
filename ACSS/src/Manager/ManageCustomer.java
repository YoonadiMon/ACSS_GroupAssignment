package Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManageCustomer extends JFrame {
    private List<Customer> customerList = new ArrayList<>();
    private static final String FILE_NAME = "customerList.txt";

    // GUI Components
    private JTextArea outputArea;
    private JTextField idField, nameField, contactField;
    private JButton approveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton listButton;
    private JCheckBox approvedCheckBox;

    public ManageCustomer() {
        super("Customer Management System");
        loadCustomersFromFile();
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

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
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
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

        // Approved Checkbox
        panel.add(new JLabel("Approved:"));
        approvedCheckBox = new JCheckBox();
        approvedCheckBox.setSelected(true); // Default to approved
        panel.add(approvedCheckBox);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Approve/Add Button
        approveButton = new JButton("Approve/Add");
        approveButton.addActionListener(this::approveCustomer);
        panel.add(approveButton);

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

        // List All Button
        listButton = new JButton("List All");
        listButton.addActionListener(this::listAllCustomers);
        panel.add(listButton);

        return panel;
    }

    private void approveCustomer(ActionEvent e) {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();
        boolean approved = approvedCheckBox.isSelected();

        if (id.isEmpty() || name.isEmpty() || contact.isEmpty()) {
            showMessage("Please fill in all fields");
            return;
        }

        Customer existing = findCustomerById(id);
        if (existing != null) {
            existing.setName(name);
            existing.setContact(contact);
            existing.setApproved(approved);
            showMessage("Customer information updated.");
        } else {
            Customer newCustomer = new Customer(id, name, contact, approved);
            customerList.add(newCustomer);
            showMessage("Customer added successfully.");
        }

        saveCustomersToFile();
        clearFields();
    }

    private void deleteCustomer(ActionEvent e) {
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

    private void searchCustomer(ActionEvent e) {
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
            approvedCheckBox.setSelected(found.isApproved());
        } else {
            showMessage("Customer not found.");
        }
    }

    private void updateCustomer(ActionEvent e) {
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

            found.setApproved(approvedCheckBox.isSelected());
            saveCustomersToFile();
            showMessage("Customer information updated.");
        } else {
            showMessage("Customer not found.");
        }
    }

    private void listAllCustomers(ActionEvent e) {
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
        customerList.clear();
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
        approvedCheckBox.setSelected(true);
    }

    private void showMessage(String message) {
        outputArea.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageCustomer());
    }

    JPanel getPanel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Inner Customer class
    private static class Customer {
        private String id;
        private String name;
        private String contact;
        private boolean approved;

        public Customer(String id, String name, String contact, boolean approved) {
            this.id = id;
            this.name = name;
            this.contact = contact;
            this.approved = approved;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getContact() { return contact; }
        public boolean isApproved() { return approved; }
        public void setName(String name) { this.name = name; }
        public void setContact(String contact) { this.contact = contact; }
        public void setApproved(boolean approved) { this.approved = approved; }

        @Override
        public String toString() {
            return String.format("ID: %s, Name: %s, Contact: %s, Approved: %s",
                    id, name, contact, approved ? "Yes" : "No");
        }

        public String toFileString() {
            return String.join(",", id, name, contact, String.valueOf(approved));
        }

        public static Customer fromFileString(String line) {
            String[] parts = line.split(",");
            if (parts.length != 4) {
                throw new IllegalArgumentException("Invalid customer data format");
            }
            return new Customer(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3]));
        }
    }
}
