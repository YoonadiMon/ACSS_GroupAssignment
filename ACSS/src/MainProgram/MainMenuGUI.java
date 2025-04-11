package MainProgram;

import Customer.CustomerGUI;
import Manager.Manager;
import Salesman.Salesman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI {
    JFrame x;
    private JLabel dynamicLabel;
    private JTextField inputField;

    public MainMenuGUI() {
        x = new JFrame();
        x.setSize(600, 300);
        x.setLocationRelativeTo(null);// Center the frame
        x.setLayout(null); // Use absolute positioning

        // Content Alignment Variables
        int ContentH = 30;
        int GapX = 25;
        int leftPad = 50;
        int titleWidth = 200;

        // Create labels for menu options
        JLabel title = new JLabel("--- Main Program ---");
        title.setBounds((x.getWidth() - titleWidth) / 2, ContentH, titleWidth, 20);

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
        x.add(title);
        x.add(option0);
        x.add(option1);
        x.add(option2);
        x.add(option3);
        x.add(prompt);
        x.add(inputField);
        x.add(okButton);

        dynamicLabel = new JLabel();
        dynamicLabel.setBounds(50, ContentH += GapX, 300, 20);
        dynamicLabel.setForeground(Color.RED);
        dynamicLabel.setVisible(false);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String userInput = inputField.getText();

                    switch (userInput) {
                        case "0" -> {
                            String input = JOptionPane.showInputDialog("Enter admin password: ");
                            System.out.println("Exiting...");
                            x.dispose(); // Close the frame
                            System.exit(0);
                        }
                        case "1" -> {
                            x.dispose();
                            CustomerGUI customer = new CustomerGUI(x.getWidth(), x.getHeight());
                        }
                        case "2" -> {
                            x.dispose();
                            Manager manager = new Manager();
                        }
                        case "3" -> {
                            x.dispose();
                            Salesman salesman = new Salesman();
                        }
                        default -> {
                            // Add dynamic label
                            dynamicLabel.setText("Your choice (" + userInput + ") is not a valid option!");
                            dynamicLabel.setVisible(true);
                            x.add(dynamicLabel);
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(x, "Invalid Input");
                }


            }
        });

        x.add(dynamicLabel);
        x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on window close
        x.setVisible(true);
    }
}
