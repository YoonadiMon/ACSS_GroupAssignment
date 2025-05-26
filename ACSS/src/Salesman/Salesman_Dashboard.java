/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// SalesmanDashboard.java
package Salesman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Salesman_Dashboard extends AbstractDashboard {
    private DashboardController controller;

    public Salesman_Dashboard(Salesman salesman) {
        super(salesman, "Salesman Dashboard", 600, 400);
        this.controller = new DashboardController(this, salesman);
    }

    @Override
    protected void setupUI() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName() + " (ID: " + currentUser.getID() + ")");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Main buttons
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Logout button
        JButton logoutButton = createStyledButton("Logout");
        logoutButton.setBackground(new Color(255, 100, 100));
        logoutButton.addActionListener(this);
        
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutButton);
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 15, 15));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] buttonLabels = {
            "Edit Profile", "View Car Status", 
            "View Car Requests", "Update Car Status",
            "View Sales History", "Mark Car as Paid"
        };

        for (String label : buttonLabels) {
            JButton button = createStyledButton(label);
            button.addActionListener(this);
            panel.add(button);
        }

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.handleAction(e.getActionCommand());
    }
}