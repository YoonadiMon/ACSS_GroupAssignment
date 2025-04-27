/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Salesman;

import MainProgram.MainMenuGUI;
import Salesman.SalesmanDashboard;
import Utils.ButtonStyler;
import Utils.WindowNav;

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
            System.out.println("Login functionality to be implemented");
            frame.dispose();
            new SalesmanDashboard();  // Open Dashboard after login
        } 

    }
}

