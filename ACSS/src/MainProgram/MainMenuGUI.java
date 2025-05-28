package MainProgram;

import Customer.CustomerLandingGUI;
import Manager.ManagerLogin;
import Salesman.SalesmanGUI;
import Utils.ButtonStyler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenuGUI implements ActionListener {
    JFrame x;
    JButton customerButton, managerButton, salesmanButton, exitButton;
    JLabel welcomeTxt, roleTxt;
    // Change this to the password needed to turn off system
    private static final String ADMIN_PASSWORD = "admin123"; 

    public MainMenuGUI() {
        x = new JFrame("Main Program");
        x.setSize(500, 300);
        x.setLocationRelativeTo(null); // Center the frame
        x.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15)); // Use FlowLayout for button arrangement
        x.setResizable(false);
        x.setMinimumSize(new Dimension(450, 300));

        // Welcome Message
        welcomeTxt = new JLabel("Welcome to APU Car Sales System");
        welcomeTxt.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeTxt.setHorizontalAlignment(JLabel.CENTER);

        // Select Role Message
        roleTxt = new JLabel("Please select your role.");
        roleTxt.setFont(new Font("Arial", Font.PLAIN, 14));
        roleTxt.setHorizontalAlignment(JLabel.CENTER);
        roleTxt.setForeground(new Color(150, 150, 150));

        // Create buttons for menu options
        exitButton = new JButton("Exit");
        customerButton = new JButton("Customer");
        managerButton = new JButton("Manager");
        salesmanButton = new JButton("Salesman");

        // Add action listeners to the buttons
        customerButton.addActionListener(this);
        ButtonStyler.styleButton(customerButton);
        managerButton.addActionListener(this);
        ButtonStyler.styleButton(managerButton);
        salesmanButton.addActionListener(this);
        ButtonStyler.styleButton(salesmanButton);
        exitButton.addActionListener(this);
        ButtonStyler.styleExitButton(exitButton);

        // Create a JPanel for the top buttons using FlowLayout
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));
        centerPanel.add(customerButton);
        centerPanel.add(salesmanButton);
        centerPanel.add(managerButton);


        x.add(welcomeTxt);
        x.add(roleTxt);
        x.add(centerPanel, BorderLayout.CENTER);
        x.add(exitButton, BorderLayout.SOUTH);

        // Add window listener for the close button (X)
        x.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                verifyAdminPassword();
            }
        });

        x.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        x.setVisible(true);
    }

    private void verifyAdminPassword() {
        String input = JOptionPane.showInputDialog(x, "Enter admin password to exit:");
        if (input != null && input.equals(ADMIN_PASSWORD)) {
            System.out.println("Exiting...");
            x.dispose();
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(x, "Incorrect password. Exit denied.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == exitButton) {
                verifyAdminPassword();
            } else if (e.getSource() == customerButton) {
                x.setVisible(false);
                new CustomerLandingGUI();
            } else if (e.getSource() == managerButton) {
                x.setVisible(false);
                new ManagerLogin();
            } else if (e.getSource() == salesmanButton) {
                x.setVisible(false);
                new SalesmanGUI(x.getWidth(), x.getHeight());
            }
        } catch (Exception ex) {
            // Show the original window again if new window creation fails
            x.setVisible(true);

            // Log the error
            ex.printStackTrace();

            // Show error message dialog
            JOptionPane.showMessageDialog(x, 
                "Unable to open the requested window. Please try again.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}