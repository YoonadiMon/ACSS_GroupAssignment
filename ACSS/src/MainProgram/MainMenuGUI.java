package MainProgram;

import Customer.CustomerGUI;
import Manager.Manager;
import Salesman.Salesman;
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
    private static final String ADMIN_PASSWORD = "admin123"; // Change this to your desired password

    public MainMenuGUI() {
        x = new JFrame("--- Main Program ---");
        x.setSize(400, 250);
        x.setLocationRelativeTo(null); // Center the frame
        x.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Use FlowLayout for button arrangement

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
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        topPanel.add(customerButton);
        topPanel.add(managerButton);
        topPanel.add(salesmanButton);

        // Add the top panel to the CENTER of BorderLayout
        x.add(topPanel, BorderLayout.CENTER);

        // Add the exit button to the SOUTH of BorderLayout
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
        if (e.getSource() == exitButton) {
            verifyAdminPassword();
        } else if (e.getSource() == customerButton) {
            x.dispose();
            new CustomerGUI(x.getWidth(), x.getHeight());
        } else if (e.getSource() == managerButton) {
            x.dispose();
            new Manager();
        } else if (e.getSource() == salesmanButton) {
            x.dispose();
            new Salesman();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenuGUI::new);
    }
}