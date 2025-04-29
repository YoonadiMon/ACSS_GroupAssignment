/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Salesman;

import MainProgram.MainMenuGUI;
import Salesman.SalesmanDashboard;
import Utils.ButtonStyler;
import Utils.WindowNav;
import Manager.SalesmenList;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author hp
 */
public class SalesmanGUI implements ActionListener {

    private JFrame frame;
    private JButton loginButton, exitButton;
    private int windowWidth, windowHeight;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SalesmanGUI(400, 250));
    }

    public SalesmanGUI(int width, int height) {
        windowWidth = width;
        windowHeight = height;

        // Create the frame
        frame = new JFrame("Salesman's Features");
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        exitButton = new JButton("Go Back");
        loginButton = new JButton("Log In");

        exitButton.addActionListener(this);
        ButtonStyler.styleExitButton(exitButton);

        loginButton.addActionListener(this);
        ButtonStyler.styleButton(loginButton);

        frame.add(loginButton);
        frame.add(exitButton);

        WindowNav.setCloseOperation(frame, () -> new MainMenuGUI());

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            frame.dispose();
            new MainMenuGUI();
        } else if (e.getSource() == loginButton) {
            frame.dispose();
            loginPage(); // Open the login page instead of directly opening dashboard
//            new SalesmanDashboard();  // Open Dashboard after login
        }

    }

    private void loginPage() {
        JFrame loginPageFrame = new JFrame("Edit Profile");
        loginPageFrame.setSize(400, 350); // Adjusted size to fit the extra fields
        loginPageFrame.setLocationRelativeTo(null);
        loginPageFrame.setLayout(null); // Use absolute positioning

        // Title label centered at the top
        JLabel title = new JLabel("--- Login Salesman Profile ---");
        title.setBounds(100, 20, 200, 30);  // Manually set the bounds
        loginPageFrame.add(title);

        // ID label and input field
        JLabel IDLabel = new JLabel("ID:");
        IDLabel.setBounds(50, 70, 80, 20);
        JTextField IDField = new JTextField(20);
        IDField.setBounds(150, 70, 150, 20);

        // Password label and input field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 80, 20);
        JPasswordField passwordField = new JPasswordField(20); // Use JPasswordField for password input
        passwordField.setBounds(150, 100, 150, 20);

        // Show password checkbox
        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setBounds(150, 160, 150, 20);  // Position the checkbox below password fields

        // Action listener for the checkbox to toggle password visibility
        showPasswordCheckbox.addActionListener(e -> {
            if (showPasswordCheckbox.isSelected()) {
                // Set password field visibility to true
                passwordField.setEchoChar((char) 0);  // Shows the password

            } else {
                // Set password field visibility to false
                passwordField.setEchoChar('*');  // Hides the password (default)

            }
        });

        loginPageFrame.add(IDLabel);
        loginPageFrame.add(IDField);
        loginPageFrame.add(passwordLabel);
        loginPageFrame.add(passwordField);
        loginPageFrame.add(showPasswordCheckbox);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 180, 120, 30); // Set position of the button

        // Show password checkbox
        
        showPasswordCheckbox.setBounds(150, 160, 150, 20);  // Position the checkbox below password fields

        loginButton.addActionListener(e -> {
            String enteredID = IDField.getText();
            String enteredPassword = String.valueOf(passwordField.getPassword());

            boolean loginSuccess = false;
            ArrayList<Salesman> salesmanList = SalesmenList.loadSalesmanDataFromFile();

            for (Salesman salesman : salesmanList) {
                if (salesman.getID().equals(enteredID) && salesman.getPassword().equals(enteredPassword)) {
                    loginSuccess = true;
                    break;
                }
            }

            if (loginSuccess) {
                JOptionPane.showMessageDialog(loginPageFrame, "Login Successful!");
                loginPageFrame.dispose();
                new SalesmanDashboard(); // You can open the dashboard after successful login
            } else {
                JOptionPane.showMessageDialog(loginPageFrame, "Invalid ID or Password. Please try again.");
            }
        });

        // Add the login button
        loginPageFrame.add(loginButton);

        // Make the frame visible
        loginPageFrame.setVisible(true);

    }
}
