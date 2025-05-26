/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Salesman;

import MainProgram.MainMenuGUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import java.awt.Image;
import java.awt.RenderingHints;

/**
 *
 * @author hp
 */
class GradientPanel extends JPanel {

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//        Color color1 = Color.WHITE;
//        Color color2 = new Color(173, 216, 230); // Baby blue
//        int width = getWidth();
//        int height = getHeight();
//        GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
//        g2d.setPaint(gp);
//        g2d.fillRect(0, 0, width, height);
//    }
//
//    private void loginPage() {
//        JFrame loginPageFrame = new JFrame("Salesman Login");
//        loginPageFrame.setSize(450, 600);
//        loginPageFrame.setLocationRelativeTo(null);
//        loginPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        GradientPanel background = new GradientPanel();
//        background.setLayout(null);
//        loginPageFrame.setContentPane(background);
//
//        // Glass panel for content
//        JPanel formPanel = new JPanel();
//        formPanel.setLayout(null);
//        formPanel.setBackground(new Color(255, 255, 255, 220)); // semi-transparent white
//        formPanel.setBounds(50, 50, 350, 470);
//        formPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
//        background.add(formPanel);
//
//        // Brand Title
//        JLabel brandLabel = new JLabel("Salesman Login", SwingConstants.CENTER);
//        brandLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
//        brandLabel.setForeground(new Color(138, 58, 185));
//        brandLabel.setBounds(0, 20, 350, 30);
//        formPanel.add(brandLabel);
//
//        // Avatar icon
//        ImageIcon avatarIcon = new ImageIcon("avatar.png"); // Make sure avatar.png exists
//        Image avatarImage = avatarIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
//        JLabel avatarLabel = new JLabel(new ImageIcon(avatarImage));
//        avatarLabel.setBounds(135, 60, 80, 80);
//        formPanel.add(avatarLabel);
//
//        // ID Label
//        JLabel IDLabel = new JLabel("Salesman ID");
//        IDLabel.setBounds(40, 160, 300, 20);
//        IDLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
//        formPanel.add(IDLabel);
//
//        // ID Field
//        JTextField IDField = new JTextField();
//        IDField.setBounds(40, 185, 270, 35);
//        IDField.setFont(new Font("SansSerif", Font.PLAIN, 14));
//        IDField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
//        formPanel.add(IDField);
//
//        // Password Label
//        JLabel passwordLabel = new JLabel("Password");
//        passwordLabel.setBounds(40, 230, 300, 20);
//        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
//        formPanel.add(passwordLabel);
//
//        // Password Field
//        JPasswordField passwordField = new JPasswordField();
//        passwordField.setBounds(40, 255, 270, 35);
//        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
//        passwordField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
//        formPanel.add(passwordField);
//
//        // Show password checkbox
//        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
//        showPasswordCheckbox.setBounds(40, 295, 150, 20);
//        showPasswordCheckbox.setFont(new Font("SansSerif", Font.PLAIN, 12));
//        showPasswordCheckbox.setBackground(new Color(255, 255, 255, 0));
//        showPasswordCheckbox.addActionListener(e -> {
//            passwordField.setEchoChar(showPasswordCheckbox.isSelected() ? (char) 0 : '*');
//        });
//        formPanel.add(showPasswordCheckbox);
//
//        // Login Button
//        JButton loginButton = new JButton("Login");
//        loginButton.setBounds(40, 330, 120, 40);
//        loginButton.setBackground(new Color(255, 90, 95));
//        loginButton.setForeground(Color.WHITE);
//        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
//        loginButton.setFocusPainted(false);
//        loginButton.setBorder(BorderFactory.createEmptyBorder());
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
//                new SalesmanDashboard(loggedInSalesman);
//            } else {
//                JOptionPane.showMessageDialog(loginPageFrame, "Invalid ID or Password. Please try again.");
//            }
//        });
//        formPanel.add(loginButton);
//
//        // Go Back Button
//        JButton closeButton = new JButton("Go Back");
//        closeButton.setBounds(190, 330, 120, 40);
//        closeButton.setBackground(new Color(140, 140, 255));
//        closeButton.setForeground(Color.WHITE);
//        closeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
//        closeButton.setFocusPainted(false);
//        closeButton.setBorder(BorderFactory.createEmptyBorder());
//        closeButton.addActionListener(e -> {
//            loginPageFrame.dispose();
//            new MainMenuGUI();
//        });
//        formPanel.add(closeButton);
//
//        loginPageFrame.setVisible(true);
//    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        Color color1 = new Color(255, 255, 255);
        Color color2 = new Color(200, 220, 255);

        GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
