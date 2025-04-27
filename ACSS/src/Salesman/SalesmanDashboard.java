/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Salesman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SalesmanDashboard implements ActionListener {

    private JFrame frame;
    private JButton editProfileButton, viewCarsButton, logoutButton;

    public SalesmanDashboard() {
        frame = new JFrame("Salesman Dashboard");
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));

        editProfileButton = new JButton("Edit Profile");
        viewCarsButton = new JButton("View Car Status");
        logoutButton = new JButton("Logout");

        editProfileButton.addActionListener(this);
        viewCarsButton.addActionListener(this);
        logoutButton.addActionListener(this);

        frame.add(editProfileButton);
        frame.add(viewCarsButton);
        frame.add(logoutButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editProfileButton) {
            openEditProfileWindow();
        } else if (e.getSource() == viewCarsButton) {
            JOptionPane.showMessageDialog(frame, "Car status functionality to be implemented!");
        } else if (e.getSource() == logoutButton) {
            frame.dispose();
            new SalesmanGUI(400, 250);  // Back to login
        }
    }

    private void openEditProfileWindow() {
        JFrame editProfileFrame = new JFrame("Edit Profile");
        editProfileFrame.setSize(400, 350); // Adjusted size to fit the extra fields
        editProfileFrame.setLocationRelativeTo(null);
        editProfileFrame.setLayout(null); // Use absolute positioning

        // Title label centered at the top
        JLabel title = new JLabel("--- Edit Salesman Profile ---");
        title.setBounds(100, 20, 200, 30);  // Manually set the bounds
        editProfileFrame.add(title);

        // Name label and input field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 70, 80, 20);
        JTextField nameField = new JTextField(20);
        nameField.setBounds(150, 70, 150, 20);

        // Password label and input field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 80, 20);
        JPasswordField passwordField = new JPasswordField(20); // Use JPasswordField for password input
        passwordField.setBounds(150, 100, 150, 20);

        // Confirm Password label and input field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(50, 130, 120, 20);
        JPasswordField confirmPasswordField = new JPasswordField(20); // Use JPasswordField for confirmation
        confirmPasswordField.setBounds(150, 130, 150, 20);

        // Show password checkbox
        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setBounds(150, 160, 150, 20);  // Position the checkbox below password fields

        // Action listener for the checkbox to toggle password visibility
        showPasswordCheckbox.addActionListener(e -> {
            if (showPasswordCheckbox.isSelected()) {
                // Set password field visibility to true
                passwordField.setEchoChar((char) 0);  // Shows the password
                confirmPasswordField.setEchoChar((char) 0);  // Shows the confirm password
            } else {
                // Set password field visibility to false
                passwordField.setEchoChar('*');  // Hides the password (default)
                confirmPasswordField.setEchoChar('*');  // Hides the confirm password
            }
        });

        // Add the labels, text fields, and checkbox
        editProfileFrame.add(nameLabel);
        editProfileFrame.add(nameField);
        editProfileFrame.add(passwordLabel);
        editProfileFrame.add(passwordField);
        editProfileFrame.add(confirmPasswordLabel);
        editProfileFrame.add(confirmPasswordField);
        editProfileFrame.add(showPasswordCheckbox);

        // Save changes button
        JButton saveButton = new JButton("Save Changes");
        saveButton.setBounds(150, 180, 120, 30); // Set position of the button
        saveButton.addActionListener(e -> {
            // Check if the passwords match
            if (String.valueOf(passwordField.getPassword()).equals(String.valueOf(confirmPasswordField.getPassword()))) {
                JOptionPane.showMessageDialog(editProfileFrame, "Changes Saved!");
                editProfileFrame.dispose();  // Close the window after saving
            } else {
                JOptionPane.showMessageDialog(editProfileFrame, "Passwords do not match. Please try again.");
            }
        });

        // Add the save button
        editProfileFrame.add(saveButton);

        // Make the frame visible
        editProfileFrame.setVisible(true);
    }
}
