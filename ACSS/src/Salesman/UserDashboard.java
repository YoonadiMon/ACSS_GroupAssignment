/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Salesman;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author hp
 */


public abstract class UserDashboard {

    // Encapsulation: fields are protected to limit access to subclasses only
    protected JFrame frame;
    protected JPanel mainPanel;
    protected JLabel welcomeLabel;

    // Constructor sets up common UI components (frame, panel, label)
    public UserDashboard(String title, String welcomeMessage) {
        frame = new JFrame(title);
        GradientPanel background = new GradientPanel();
        background.setLayout(new BorderLayout());
        frame.setContentPane(background);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        welcomeLabel = new JLabel(welcomeMessage);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        background.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Abstraction: abstract method forces subclasses to define their own buttons
    protected abstract void setupButtons();

    // Code reuse: shared method for creating buttons with consistent style
    protected JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }
}
