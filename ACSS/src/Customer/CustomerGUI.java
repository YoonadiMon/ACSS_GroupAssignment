package Customer;

import MainProgram.MainMenuGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CustomerGUI {
    private JLabel dynamicLabel;
    private JTextField inputField;
    private JFrame frame;

    public static void main(String[] Args) {
        new CustomerGUI(600, 300);
    }

    public CustomerGUI(int width, int height) {
        // Create the frame
        frame = new JFrame("Customer's Features");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // Content Alignment Variables
        int ContentH = 10;
        int GapX = 25;
        int leftPad = 50;
        int titleWidth = 200;

        // Create labels for menu options
        JLabel title = new JLabel("--- Select a Customer Features ---");
        title.setBounds((width - titleWidth) / 2, ContentH, titleWidth, 20);

        JLabel option0 = new JLabel("0. EXIT");
        option0.setBounds(leftPad, ContentH += GapX, 100, 20);

        JLabel option1 = new JLabel("1. Register an account");
        option1.setBounds(leftPad, ContentH += GapX, 200, 20);

        JLabel option2 = new JLabel("2. Log In");
        option2.setBounds(leftPad, ContentH += GapX, 200, 20);

        JLabel option3 = new JLabel("3. Edit your profile");
        option3.setBounds(leftPad, ContentH += GapX, 250, 20);

        JLabel option4 = new JLabel("4. View details of available cars");
        option4.setBounds(leftPad, ContentH += GapX, 300, 20);

        JLabel option5 = new JLabel("5. Give feedback regarding your purchases");
        option5.setBounds(leftPad, ContentH += GapX, 350, 20);

        JLabel option6 = new JLabel("6. View history");
        option6.setBounds(leftPad, ContentH += GapX, 150, 20);

        JLabel prompt = new JLabel("Enter your choice (0-6):");
        prompt.setBounds(leftPad, ContentH += GapX, 150, 20);

        // Create input field and button
        inputField = new JTextField(5);
        inputField.setBounds(200, ContentH, 50, 20);

        JButton okButton = new JButton("OK");
        okButton.setBounds(260, ContentH, 80, 20);

        // Add components to frame
        frame.add(title);
        frame.add(option0);
        frame.add(option1);
        frame.add(option2);
        frame.add(option3);
        frame.add(option4);
        frame.add(option5);
        frame.add(option6);
        frame.add(prompt);
        frame.add(inputField);
        frame.add(okButton);

        dynamicLabel = new JLabel();
        dynamicLabel.setBounds(leftPad, ContentH += GapX, 300, 20);
        dynamicLabel.setForeground(Color.RED);
        dynamicLabel.setVisible(false);
        frame.add(dynamicLabel);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();

                switch (userInput) {
                    case "0" -> {
                        frame.dispose();
                        new MainMenuGUI();
                    }
                    case "1" -> {
                        frame.dispose(); // Close the current frame
                        new RegisterAccount(width, height);
                    }
                    case "2" -> System.out.println("2");
                    case "3" -> System.out.println("3");
                    case "4" -> System.out.println("4");
                    case "5" -> System.out.println("5");
                    case "6" -> System.out.println("6");
                    default -> {
                        dynamicLabel.setText("Your choice (" + userInput + ") is not a valid option!");
                        dynamicLabel.setVisible(true);
                    }
                }
            }
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                new MainMenuGUI();
            }
        });

        frame.setVisible(true);
    }
}