package Manager;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SalesmanStaffGUI extends JFrame {
    public SalesmanStaffGUI() {
        setTitle("Staff and Salesman Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize models and lists as local variables in the constructor
        DefaultListModel<String> staffModel = new DefaultListModel<>();
        DefaultListModel<String> salesmanModel = new DefaultListModel<>();
        List<User> staffList = new ArrayList<>();
        List<User> salesmanList = new ArrayList<>();

        loadUsersFromFile("staffList.txt", staffList, staffModel);
        loadUsersFromFile("salesmanList.txt", salesmanList, salesmanModel);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add("Staff", createPanel(staffList, staffModel, "staffList.txt"));
        tabbedPane.add("Salesman", createPanel(salesmanList, salesmanModel, "salesmanList.txt"));

        add(tabbedPane);
    }

    private JPanel createPanel(List<User> list, DefaultListModel<String> model, String filename) {
        JPanel panel = new JPanel(new BorderLayout());

        JList<String> listUI = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(listUI);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        addButton.addActionListener(event -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            if (!id.isEmpty() && !name.isEmpty()) {
                User user = new User(id, name);
                list.add(user);
                model.addElement(user.toString());
                saveUsersToFile(filename, list);
                idField.setText("");
                nameField.setText("");
            }
        });

        deleteButton.addActionListener(event -> {
            int selected = listUI.getSelectedIndex();
            if (selected != -1) {
                list.remove(selected);
                model.remove(selected);
                saveUsersToFile(filename, list);
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void saveUsersToFile(String filename, List<User> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (User user : list) {
                writer.write(user.getId() + "," + user.getName());
                writer.newLine();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error saving to file: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadUsersFromFile(String filename, List<User> list, DefaultListModel<String> model) {
        list.clear();
        model.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    User user = new User(parts[0], parts[1]);
                    list.add(user);
                    model.addElement(user.toString());
                }
            }
        } catch (IOException ex) {
            System.out.println("Error loading from " + filename + ": " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SalesmanStaffGUI().setVisible(true));
    }
}