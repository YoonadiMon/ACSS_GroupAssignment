/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// SalesmanLoginDialog.java - Extends AbstractWindow for login functionality
package Salesman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SalesmanLoginDialog extends AbstractWindow {

    private LoginComponents loginComponents;
    private LoginHandler loginHandler;

    public SalesmanLoginDialog(LoginHandler handler) {
        super(450, 600, "Salesman Login");
        this.loginHandler = handler;
        initializeUI();
    }

    private void initializeUI() {
        GradientPanel background = new GradientPanel();
        background.setLayout(null);
        frame.setContentPane(background);

        JPanel formPanel = createFormPanel();
        background.add(formPanel);

        createBrandLabel(formPanel);
        createLabels(formPanel);

        loginComponents = new LoginComponents(formPanel);
        setupEventHandlers();

        showFrame();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(255, 255, 255, 220));
        panel.setBounds(50, 50, 350, 500);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        return panel;
    }

    private void createBrandLabel(JPanel panel) {
        JLabel brandLabel = new JLabel("Salesman Login", SwingConstants.CENTER);
        brandLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        brandLabel.setForeground(Color.BLACK);
        brandLabel.setBounds(0, 20, 350, 30);
        panel.add(brandLabel);
    }

    private void createLabels(JPanel panel) {
        // ID Label
        JLabel IDLabel = new JLabel("Salesman ID");
        IDLabel.setBounds(40, 160, 300, 20);
        IDLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(IDLabel);

        // Password Label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(40, 230, 300, 20);
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(passwordLabel);

        // Forgot Password Button
        JButton forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setBounds(200, 295, 150, 20);
        forgotPasswordButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setContentAreaFilled(false);
        forgotPasswordButton.setForeground(new Color(0, 100, 200));
        forgotPasswordButton.addActionListener(e -> loginHandler.handleForgotPassword());
        panel.add(forgotPasswordButton);
    }

    private void setupEventHandlers() {
        // Show password toggle
        loginComponents.getShowPasswordCheckbox().addActionListener(e -> {
            loginComponents.getPasswordField().setEchoChar(
                    loginComponents.getShowPasswordCheckbox().isSelected() ? (char) 0 : '*'
            );
        });

        // Login button
        loginComponents.getLoginButton().addActionListener(e -> {
            String id = loginComponents.getIdField().getText();
            String password = new String(loginComponents.getPasswordField().getPassword());
            loginHandler.handleLogin(id, password);
        });

        // Back button
        loginComponents.getBackButton().addActionListener(e -> loginHandler.handleBack());
    }
}
