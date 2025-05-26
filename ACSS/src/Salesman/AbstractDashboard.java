/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// AbstractDashboard.java
package Salesman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class AbstractDashboard extends JFrame implements ActionListener {

    protected JPanel mainPanel;
    protected Salesman currentUser;

    public AbstractDashboard(Salesman salesman, String title, int width, int height) {
        super(title);
        this.currentUser = salesman;
        initializeWindow(width, height);
        setupUI();
    }

    private void initializeWindow(int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set gradient background
        GradientPanel background = new GradientPanel();
        background.setLayout(new BorderLayout());
        setContentPane(background);
    }

    protected abstract void setupUI();

    protected JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        return button;
    }
}
