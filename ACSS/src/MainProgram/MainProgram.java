package MainProgram;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Customer.Customer;
import Manager.Manager;
import Salesman.Salesman;

public class MainProgram extends Frame {
    private Label dynamicLabel;
    private TextField inputField;
    
    public static void main(String[] args) {
        MainProgram mainProgram = new MainProgram();
    }
    
    public MainProgram() {
        super("Main Program");
        
        setSize(300, 250);
        setLocation(100, 100);
        setLayout(null);

        // Create labels for menu options
        Label title = new Label("--- Main Program ---");
        title.setBounds(100, 20, 150, 20);

        Label option0 = new Label("0. Exit");
        option0.setBounds(50, 50, 100, 20);

        Label option1 = new Label("1. Customer");
        option1.setBounds(50, 70, 150, 20);

        Label option2 = new Label("2. Manager");
        option2.setBounds(50, 90, 150, 20);

        Label option3 = new Label("3. Salesman");
        option3.setBounds(50, 110, 150, 20);

        Label prompt = new Label("Enter your choice (0-3):");
        prompt.setBounds(50, 140, 150, 20);

        // Create input field and button
        inputField = new TextField(5);
        inputField.setBounds(200, 140, 50, 20);

        Button okButton = new Button("OK");
        okButton.setBounds(260, 140, 50, 20);

        // Add components to frame
        add(title);
        add(option0);
        add(option1);
        add(option2);
        add(option3);
        add(prompt);
        add(inputField);
        add(okButton);
        
        dynamicLabel = new Label();
        dynamicLabel.setBounds(50, 170, 300, 20);
        dynamicLabel.setVisible(false);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                
                // Add dynamic label
                dynamicLabel.setText("Your choice: "+userInput);
                dynamicLabel.setVisible(true);
                repaint();
                
                if (userInput.equals("0")) {
                    System.out.println("Exiting...");
                    dispose(); // Close the frame
                    System.exit(0); // Exit the application
                } else if (userInput.equals("1")) {
                    dispose(); // Close the current frame
                    Customer customer = new Customer(); // Open Customer frame
                } else if (userInput.equals("2")) {
                    dispose(); // Close the current frame
                    Manager manager = new Manager(); // Open Manager frame
                } else if (userInput.equals("3")) {
                    dispose(); // Close the current frame
                    Salesman salesman = new Salesman(); // Open Salesman frame
                }
            }
        });
        
        setVisible(true);
    }
    
}
