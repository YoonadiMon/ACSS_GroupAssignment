package Customer;

import MainProgram.MainMenuGUI;
import Utils.ButtonStyler;
import Utils.WindowNav;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerGUI implements ActionListener {
    private JFrame frame;
    private JButton registerButton, loginButton, exitButton;
    private int windowWidth, windowHeight;

    public static void main(String[] Args) {
        SwingUtilities.invokeLater(() -> new CustomerGUI(400, 250));
    }

    public CustomerGUI(int width, int height) {
        windowWidth = width;
        windowHeight = height;

        // Create the frame
        frame = new JFrame("Customer's Features");
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        exitButton = new JButton("\u2190");
        exitButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 16));
        ButtonStyler.styleExitButton(exitButton);

        registerButton = new JButton("Register an account");
        ButtonStyler.styleButton(registerButton);

        loginButton = new JButton("Log In");
        ButtonStyler.styleButton(loginButton);

        // Button actions
        exitButton.addActionListener(this);
        registerButton.addActionListener(this);
        loginButton.addActionListener(this);

        frame.add(registerButton);
        frame.add(loginButton);
        frame.add(exitButton);

        WindowNav.setCloseOperation(frame, () -> new MainMenuGUI());

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            frame.setVisible(false);
            new MainMenuGUI();
        } else if (e.getSource() == registerButton) {
            frame.setVisible(false);
            new RegisterAccount(windowWidth, windowHeight);
        } else if (e.getSource() == loginButton) {
            System.out.println("Login functionality to be implemented");
            // Add your login logic here
        }
    }
}