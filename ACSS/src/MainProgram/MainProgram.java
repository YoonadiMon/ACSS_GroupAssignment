package MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Customer.Customer;
import Manager.Manager;
import Salesman.Salesman;

public class MainProgram extends JFrame {
    private JLabel dynamicLabel;
    private JTextField inputField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainProgram mainProgram = new MainProgram();
            mainProgram.setVisible(true);
        });
    }

    public MainProgram() {
        super("Main Program");

        // Content Alignment Variables
        int ContentH = 30;
        int GapX = 25;
        int leftPad = 50;
        int titleWidth = 200;

        setSize(600, 300);
        setLocationRelativeTo(null); // Center the frame
        setLayout(null); // Use absolute positioning

        // Create labels for menu options
        JLabel title = new JLabel("--- Main Program ---");
        title.setBounds((getWidth() - titleWidth) / 2, ContentH, titleWidth, 20);

        JLabel option0 = new JLabel("0. Exit");
        option0.setBounds(leftPad, ContentH += GapX, 100, 20);

        JLabel option1 = new JLabel("1. Customer");
        option1.setBounds(leftPad, ContentH += GapX, 150, 20);

        JLabel option2 = new JLabel("2. Manager");
        option2.setBounds(leftPad, ContentH += GapX, 150, 20);

        JLabel option3 = new JLabel("3. Salesman");
        option3.setBounds(leftPad, ContentH += GapX, 150, 20);

        JLabel prompt = new JLabel("Enter your choice (0-3):");
        prompt.setBounds(leftPad, ContentH += GapX, 150, 20);

        // Create input field and button
        inputField = new JTextField(5);
        inputField.setBounds(200, ContentH, 50, 20);

        JButton okButton = new JButton("OK");
        okButton.setBounds(260, ContentH, 50, 20);

        // Add components to frame
        add(title);
        add(option0);
        add(option1);
        add(option2);
        add(option3);
        add(prompt);
        add(inputField);
        add(okButton);

        dynamicLabel = new JLabel();
        dynamicLabel.setBounds(50, ContentH += GapX, 300, 20);
        dynamicLabel.setForeground(Color.RED);
        dynamicLabel.setVisible(false);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();

                switch (userInput) {
                    case "0" -> {
                        System.out.println("Exiting...");
                        dispose(); // Close the frame
                        System.exit(0);
                    }
                    case "1" -> {
                        dispose();
                        Customer customer = new Customer(getWidth(), getHeight());
                    }
                    case "2" -> {
                        dispose();
                        Manager manager = new Manager();
                    }
                    case "3" -> {
                        dispose();
                        Salesman salesman = new Salesman();
                    }
                    default -> {
                        // Add dynamic label
                        dynamicLabel.setText("Your choice (" + userInput + ") is not a valid option!");
                        dynamicLabel.setVisible(true);
                        add(dynamicLabel);
                    }
                }

            }
        });

        add(dynamicLabel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on window close
    }
}