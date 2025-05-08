package Customer;

import javax.swing.*;
import java.awt.*;

public class CustomerDashboard {
    private JFrame frame;
    private Customer customer;

    public CustomerDashboard(Customer customer) {
        this.customer = customer;

        frame = new JFrame("Customer Dashboard");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + customer.getUsername() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Account info panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Account Information"));

        infoPanel.add(new JLabel("Username:"));
        infoPanel.add(new JLabel(customer.getUsername()));

        infoPanel.add(new JLabel("Email:"));
        infoPanel.add(new JLabel(customer.getEmail()));

        infoPanel.add(new JLabel("Account Status:"));
        String status = customer.isApproved() ? "Approved" : "Pending Approval";
        JLabel statusLabel = new JLabel(status);
        statusLabel.setForeground(customer.isApproved() ? new Color(0, 128, 0) : new Color(255, 140, 0));
        infoPanel.add(statusLabel);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);

        logoutButton.addActionListener(e -> {
            frame.dispose();
            new CustomerLandingGUI();
        });

        // Add components to main panel
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(logoutButton, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}