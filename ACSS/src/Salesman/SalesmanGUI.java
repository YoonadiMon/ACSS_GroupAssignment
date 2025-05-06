/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Salesman;

import MainProgram.MainMenuGUI;
import Salesman.SalesmanDashboard;
import Utils.ButtonStyler;
import Utils.WindowNav;
import Manager.SalesmanList;
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
        loginPageFrame.setSize(400, 350);
        loginPageFrame.setLocationRelativeTo(null);
        loginPageFrame.setLayout(null);

        // Title label
        JLabel title = new JLabel("--- Login Salesman Profile ---");
        title.setBounds(100, 20, 200, 30);
        loginPageFrame.add(title);

        // ID field
        JLabel IDLabel = new JLabel("ID:");
        IDLabel.setBounds(50, 70, 80, 20);
        JTextField IDField = new JTextField(20);
        IDField.setBounds(150, 70, 150, 20);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 80, 20);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(150, 100, 150, 20);

        // Show password checkbox
        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setBounds(150, 130, 150, 20);
        showPasswordCheckbox.addActionListener(e -> {
            if (showPasswordCheckbox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 160, 120, 30);
        loginButton.addActionListener(e -> {
            String enteredID = IDField.getText();
            String enteredPassword = String.valueOf(passwordField.getPassword());

            Salesman loggedInSalesman = null;
            ArrayList<Salesman> salesmanList = SalesmanList.loadSalesmanDataFromFile();

            for (Salesman salesman : salesmanList) {
                if (salesman.getID().equals(enteredID) && salesman.getPassword().equals(enteredPassword)) {
                    loggedInSalesman = salesman;
                    break;
                }
            }

            if (loggedInSalesman != null) {
                JOptionPane.showMessageDialog(loginPageFrame, "Login Successful!");
                loginPageFrame.dispose();
                new SalesmanDashboard(loggedInSalesman); // Pass salesman to dashboard
            } else {
                JOptionPane.showMessageDialog(loginPageFrame, "Invalid ID or Password. Please try again.");
            }
        });

        // Add components
        loginPageFrame.add(IDLabel);
        loginPageFrame.add(IDField);
        loginPageFrame.add(passwordLabel);
        loginPageFrame.add(passwordField);
        loginPageFrame.add(showPasswordCheckbox);
        loginPageFrame.add(loginButton);

        loginPageFrame.setVisible(true);
    }
}
