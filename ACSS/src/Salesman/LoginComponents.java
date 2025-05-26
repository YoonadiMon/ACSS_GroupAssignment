/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// LoginComponents.java - Encapsulates login-related UI components
package Salesman;

import javax.swing.*;
import java.awt.*;

public class LoginComponents {

    private JTextField idField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckbox;
    private JButton loginButton;
    private JButton backButton;

    public LoginComponents(JPanel parentPanel) {
        createComponents(parentPanel);
    }

    private void createComponents(JPanel parentPanel) {
        // ID Field
        idField = new JTextField();
        idField.setBounds(40, 185, 270, 35);
        idField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        idField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        parentPanel.add(idField);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(40, 255, 270, 35);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        parentPanel.add(passwordField);

        // Show Password Checkbox
        showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setBounds(40, 295, 150, 20);
        showPasswordCheckbox.setFont(new Font("SansSerif", Font.PLAIN, 12));
        showPasswordCheckbox.setOpaque(false);
        parentPanel.add(showPasswordCheckbox);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(40, 330, 120, 40);
        loginButton.setBackground(new Color(255, 90, 95));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        parentPanel.add(loginButton);

        // Back Button
        backButton = new JButton("Go Back");
        backButton.setBounds(190, 330, 120, 40);
        backButton.setBackground(new Color(140, 140, 255));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        parentPanel.add(backButton);
    }

    // Getters for the components
    public JTextField getIdField() {
        return idField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JCheckBox getShowPasswordCheckbox() {
        return showPasswordCheckbox;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
