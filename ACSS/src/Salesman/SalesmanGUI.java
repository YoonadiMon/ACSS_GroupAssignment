/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Salesman;

import MainProgram.MainMenuGUI;
import Salesman.SalesmanList;
import Salesman.SalesmanDashboard;
import Utils.ButtonStyler;
import Utils.WindowNav;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//nothing here
/**
 *
 * @author hp
 */
public class SalesmanGUI implements ActionListener {

    private JFrame frame;
    private JButton loginButton, exitButton;
    private int windowWidth, windowHeight;

    public static void main(String[] args) {
//        SalesmanList.initializeSalesman();
//        SalesmanList.saveInitializedSalesmanDataToFile();
        
        
        
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
        formPanel.setBackground(new Color(255, 255, 255, 220));
        formPanel.setBounds(50, 50, 350, 500); // Increased height
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        background.add(formPanel);

        // Brand Title
        JLabel brandLabel = new JLabel("Salesman Login", SwingConstants.CENTER);
        brandLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        brandLabel.setForeground(Color.BLACK);
        brandLabel.setBounds(0, 20, 350, 30);
        formPanel.add(brandLabel);

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
        showPasswordCheckbox.setOpaque(false);
        showPasswordCheckbox.addActionListener(e -> {
            passwordField.setEchoChar(showPasswordCheckbox.isSelected() ? (char) 0 : '*');
        });
        formPanel.add(showPasswordCheckbox);

        // Forgot Password Button
        JButton forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setBounds(200, 295, 150, 20);
        forgotPasswordButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setContentAreaFilled(false);
        forgotPasswordButton.setForeground(new Color(0, 100, 200));
        forgotPasswordButton.addActionListener(e -> showForgotPasswordDialog(loginPageFrame));
        formPanel.add(forgotPasswordButton);

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

        loginPageFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        loginPageFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Point location = loginPageFrame.getLocation();
                loginPageFrame.dispose();
                new MainMenuGUI();
            }
        });

        loginPageFrame.setVisible(true);
    }

    private void showForgotPasswordDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Forgot Password", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Password Recovery", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(titleLabel, gbc);

        JLabel idLabel = new JLabel("Enter your Salesman ID:");
        panel.add(idLabel, gbc);

        JTextField idField = new JTextField(20);
        panel.add(idField, gbc);

        JButton nextButton = new JButton("Next");
        nextButton.setBackground(new Color(70, 130, 180));
        nextButton.setForeground(Color.WHITE);
        panel.add(nextButton, gbc);

        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        panel.add(statusLabel, gbc);

        nextButton.addActionListener(e -> {
            String id = idField.getText().trim();
            Salesman salesman = SalesmanList.findSalesmanById(id);

            if (salesman != null) {
                showSecurityQuestionDialog(dialog, salesman);
            } else {
                statusLabel.setText("Invalid Salesman ID");
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());
        panel.add(cancelButton, gbc);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void showSecurityQuestionDialog(JDialog parentDialog, Salesman salesman) {
        parentDialog.dispose();

        JDialog dialog = new JDialog((JFrame) null, "Security Question", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel questionLabel = new JLabel(salesman.getSecurityQuestion());
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(questionLabel, gbc);

        JPasswordField answerField = new JPasswordField(20);
        panel.add(answerField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);
        panel.add(submitButton, gbc);

        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        panel.add(statusLabel, gbc);

        submitButton.addActionListener(e -> {
            String answer = new String(answerField.getPassword()).trim();

            if (SalesmanList.verifySecurityAnswer(salesman.getID(), answer)) {
                JOptionPane.showMessageDialog(dialog,
                        "Your password is: " + salesman.getPassword(),
                        "Password Recovery",
                        JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                statusLabel.setText("Incorrect answer. Please contact admin.");
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dialog.dispose();
            showForgotPasswordDialog(null);
        });
        panel.add(backButton, gbc);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
}
