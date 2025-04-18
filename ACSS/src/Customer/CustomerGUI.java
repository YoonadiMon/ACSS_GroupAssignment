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

        exitButton = new JButton("Go Back");
        registerButton = new JButton("Register an account");
        loginButton = new JButton("Log In");

        exitButton.addActionListener(this);
        ButtonStyler.styleExitButton(exitButton);
        registerButton.addActionListener(this);
        ButtonStyler.styleButton(registerButton);
        loginButton.addActionListener(this);
        ButtonStyler.styleButton(loginButton);

        frame.add(registerButton);
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
        } else if (e.getSource() == registerButton) {
            frame.dispose();
            new RegisterAccount(windowWidth, windowHeight);
        } else if (e.getSource() == loginButton) {
            System.out.println("Login functionality to be implemented");
            // Add your login logic here
        }
    }
}