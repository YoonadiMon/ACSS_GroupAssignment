package Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ManagerGUI {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Manager Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null); // Center the window

        // Create the main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);

        // Add title label
        JLabel titleLabel = new JLabel("Manager Features Menu", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, gbc);

        // Create menu buttons
        JButton staffButton = createMenuButton("Manage Staff and Salesman");
        JButton customerButton = createMenuButton("Manage Customers");
        JButton inventoryButton = createMenuButton("Manage Car Inventory");
        JButton paymentButton = createMenuButton("Payment and Feedback");
        JButton reportsButton = createMenuButton("Generate Reports");
        JButton logoutButton = createMenuButton("Logout");

        // Add buttons to panel
        mainPanel.add(staffButton, gbc);
        mainPanel.add(customerButton, gbc);
        mainPanel.add(inventoryButton, gbc);
        mainPanel.add(paymentButton, gbc);
        mainPanel.add(reportsButton, gbc);
        mainPanel.add(logoutButton, gbc);

        // Add action listeners (using method references where possible)
        staffButton.addActionListener(e-> showFeatureMessage("Staff and Salesman Management"));
        customerButton.addActionListener(e -> showFeatureMessage("Customer Management"));
        inventoryButton.addActionListener(e -> showFeatureMessage("Car Inventory Management"));
        paymentButton.addActionListener(e -> showFeatureMessage("Payment and Feedback"));
        reportsButton.addActionListener(e -> showFeatureMessage("Report Generation"));
        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Logged out successfully");
            frame.dispose();
        });

        // Add panel to frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(300, 50));
        return button;
    }

    private static void showFeatureMessage(String featureName) {
        JOptionPane.showMessageDialog(null,
                "Opening: " + featureName + "\n\n" +
                        "This would open the " + featureName + " panel in a full implementation.",
                "Feature Preview",
                JOptionPane.INFORMATION_MESSAGE);
    }
}