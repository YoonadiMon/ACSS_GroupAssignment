/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Salesman;

import MainProgram.MainMenuGUI;
import Salesman.SalesmanDashboard;
import Utils.ButtonStyler;
import Utils.WindowNav;
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

        }

    }

//    private void loginPage() {
//        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
//        JFrame loginPageFrame = new JFrame("Edit Profile");
//        loginPageFrame.setSize(400, 350);
//        loginPageFrame.setLocationRelativeTo(null);
//        loginPageFrame.setLayout(null);
//
//        // Title label
//        JLabel title = new JLabel("--- Login Salesman Profile ---");
//        title.setBounds(100, 20, 200, 30);
//        loginPageFrame.add(title);
//
//        // ID field
//        JLabel IDLabel = new JLabel("ID:");
//        IDLabel.setBounds(50, 70, 80, 20);
//        JTextField IDField = new JTextField(20);
//        IDField.setBounds(150, 70, 150, 20);
//
//        // Password field
//        JLabel passwordLabel = new JLabel("Password:");
//        passwordLabel.setBounds(50, 100, 80, 20);
//        JPasswordField passwordField = new JPasswordField(20);
//        passwordField.setBounds(150, 100, 150, 20);
//
//        // Show password checkbox
//        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
//        showPasswordCheckbox.setBounds(150, 130, 150, 20);
//        showPasswordCheckbox.addActionListener(e -> {
//            if (showPasswordCheckbox.isSelected()) {
//                passwordField.setEchoChar((char) 0);
//            } else {
//                passwordField.setEchoChar('*');
//            }
//        });
//
//        // Login button
//        
//        JButton loginButton = new JButton("Login");
//        loginButton.setBounds(110, 160, 100, 30);
//        loginButton.addActionListener(e -> {
//            String enteredID = IDField.getText();
//            String enteredPassword = String.valueOf(passwordField.getPassword());
//
//            Salesman loggedInSalesman = null;
//            ArrayList<Salesman> salesmanList = SalesmanList.loadSalesmanDataFromFile();
//
//            for (Salesman salesman : salesmanList) {
//                if (salesman.getID().equals(enteredID) && salesman.getPassword().equals(enteredPassword)) {
//                    loggedInSalesman = salesman;
//                    break;
//                }
//            }
//
//            if (loggedInSalesman != null) {
//                JOptionPane.showMessageDialog(loginPageFrame, "Login Successful!");
//                loginPageFrame.dispose();
//                new SalesmanDashboard(loggedInSalesman); // Pass salesman to dashboard
//            } else {
//                JOptionPane.showMessageDialog(loginPageFrame, "Invalid ID or Password. Please try again.");
//            }
//        });
//
//        // Add components
//        loginPageFrame.add(IDLabel);
//        loginPageFrame.add(IDField);
//        loginPageFrame.add(passwordLabel);
//        loginPageFrame.add(passwordField);
//        loginPageFrame.add(showPasswordCheckbox);
//        loginPageFrame.add(loginButton);
//
////       close button
//        
//        JButton closeButton = new JButton("Go Back");
//        closeButton.setBounds(230, 160, 100, 30);
//        closeButton.addActionListener(e ->{ loginPageFrame.dispose(); new MainMenuGUI();});
//        loginPageFrame.add(closeButton);
//
//        loginPageFrame.setVisible(true);
//    }
    private void loginPage() {
        JFrame loginPageFrame = new JFrame("Salesman Login");
        loginPageFrame.setSize(450, 600);
        loginPageFrame.setLocationRelativeTo(null);
        loginPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GradientPanel background = new GradientPanel();
        background.setLayout(null);
        loginPageFrame.setContentPane(background);

        // Glass panel for content
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(255, 255, 255, 220)); // semi-transparent white
        formPanel.setBounds(50, 50, 350, 470);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        background.add(formPanel);

        // Brand Title
        JLabel brandLabel = new JLabel("Salesman Login", SwingConstants.CENTER);
        brandLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        brandLabel.setForeground(Color.BLACK); // Changed to black
        brandLabel.setBounds(0, 20, 350, 30);
        formPanel.add(brandLabel);

//        // Avatar icon
//        ImageIcon avatarIcon = new ImageIcon("avatar.png"); // Make sure avatar.png exists
//        Image avatarImage = avatarIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
//        JLabel avatarLabel = new JLabel(new ImageIcon(avatarImage));
//        avatarLabel.setBounds(135, 60, 80, 80);
//        formPanel.add(avatarLabel);
        // ID Label
        JLabel IDLabel = new JLabel("Salesman ID");
        IDLabel.setBounds(40, 160, 300, 20);
        IDLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(IDLabel);

        // ID Field
        JTextField IDField = new JTextField();
        IDField.setBounds(40, 185, 270, 35);
        IDField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        IDField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        formPanel.add(IDField);

        // Password Label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(40, 230, 300, 20);
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(passwordLabel);

        // Password Field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(40, 255, 270, 35);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        formPanel.add(passwordField);

        // Show password checkbox
        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setBounds(40, 295, 150, 20);
        showPasswordCheckbox.setFont(new Font("SansSerif", Font.PLAIN, 12));
        showPasswordCheckbox.setBackground(new Color(255, 255, 255, 0));
        showPasswordCheckbox.addActionListener(e -> {
            passwordField.setEchoChar(showPasswordCheckbox.isSelected() ? (char) 0 : '*');
        });
        formPanel.add(showPasswordCheckbox);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(40, 330, 120, 40);
        loginButton.setBackground(new Color(255, 90, 95));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
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
                new SalesmanDashboard(loggedInSalesman);
            } else {
                JOptionPane.showMessageDialog(loginPageFrame, "Invalid ID or Password. Please try again.");
            }
        });
        formPanel.add(loginButton);

        // Go Back Button
        JButton closeButton = new JButton("Go Back");
        closeButton.setBounds(190, 330, 120, 40);
        closeButton.setBackground(new Color(140, 140, 255));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.addActionListener(e -> {
            loginPageFrame.dispose();
            new MainMenuGUI();
        });
        formPanel.add(closeButton);

        loginPageFrame.setVisible(true);
    }
}
