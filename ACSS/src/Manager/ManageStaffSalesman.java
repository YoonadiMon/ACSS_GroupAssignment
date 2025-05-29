package Manager;

import Salesman.Salesman;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManageStaffSalesman extends JFrame {
    private static final String FILE_NAME = "data/salesmanList.txt";
    private final List<User> staffList = new ArrayList<>();
    private Object manager;

    // GUI Components
    private JTextField idField, nameField, searchIdField;
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
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Staff/Salesman Info"));

        panel.add(new JLabel("Search by ID:"));
        searchIdField = new JTextField();
        panel.add(searchIdField);

        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        addButton = new JButton("Add");
        addButton.addActionListener(this::addStaff);
        panel.add(addButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this::deleteStaff);
        panel.add(deleteButton);

        searchButton = new JButton("Search");
        searchButton.addActionListener(this::searchStaff);
        panel.add(searchButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(this::updateStaff);
        panel.add(updateButton);

        listButton = new JButton("List All");
        listButton.addActionListener(this::listAll);
        panel.add(listButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> returnToManagerDashboard());
        panel.add(backButton);

        return panel;
    }

    private void addStaff(ActionEvent e) {
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

        staffList.add(new User(id, name));
        saveToFile();
        showMessage("Staff/Salesman added successfully.");
        clearFields();
    }

    private void deleteStaff(ActionEvent e) {
        String id = searchIdField.getText().trim();
        User user = findById(id);
        if (user != null) {
            staffList.remove(user);
            saveToFile();
            showMessage("Deleted successfully.");
            clearFields();
        } else {
            showMessage("ID not found.");
        }
    }

    private void searchStaff(ActionEvent e) {
        String id = searchIdField.getText().trim();
        User user = findById(id);
        if (user != null) {
            outputArea.setText("Staff Found:\n" + user.toString());
            idField.setText(user.id);
            nameField.setText(user.name);
        } else {
            showMessage("ID not found.");
        }
    }

    private void updateStaff(ActionEvent e) {
        String id = searchIdField.getText().trim();
        User user = findById(id);
        if (user != null) {
            String newName = nameField.getText().trim();
            if (!newName.isEmpty()) {
                user.name = newName;
                saveToFile();
                showMessage("Updated successfully.");
            }
        } else {
            showMessage("ID not found.");
        }
    }

    private void listAll(ActionEvent e) {
        if (staffList.isEmpty()) {
            showMessage("No staff/salesman to show.");
        } else {
            StringBuilder sb = new StringBuilder("--- Staff & Salesman List ---\n");
            staffList.forEach(u -> sb.append(u.toString()).append("\n"));
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
    }

    private User findById(String id) {
        return staffList.stream()
                .filter(u -> u.id.equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    private void loadFromFile() {
        staffList.clear();
        File file = new File(FILE_NAME);
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            showMessage("No existing staff data found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    staffList.add(new User(parts[0].trim(), parts[1].trim()));
                }
            }
            showMessage("Loaded " + staffList.size() + " records.");
        } catch (IOException e) {
            showMessage("Failed to load data: " + e.getMessage());
        }
    }

    private void saveToFile() {
        File file = new File(FILE_NAME);
        file.getParentFile().mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (User u : staffList) {
                writer.println(u.id + "," + u.name);
            }
        } catch (IOException e) {
            showMessage("Failed to save data: " + e.getMessage());
        }
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

    static class User {
        String id;
        String name;

        User(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String toString() {
            return "ID: " + id + " | Name: " + name;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageStaffSalesman(null));
    }
}
