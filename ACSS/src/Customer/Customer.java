package Customer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class for the main customer features
public class Customer extends Frame {
    private Label dynamicLabel;
    private TextField inputField;

    public static void main(String[] Args) {
        Customer customer = new Customer();
    }

    public Customer() {
        super("Customer Features");
        
        setSize(400,300);
        setLocation(100,100);
        setTitle("Customer's Features");
        setLayout(null);
        
        // Create labels for menu options
        Label title = new Label("--- Select a Customer Features ---");
        title.setBounds(130, 30, 200, 20);

        Label option0 = new Label("0. EXIT");
        option0.setBounds(50, 50, 100, 20);

        Label option1 = new Label("1. Register an account");
        option1.setBounds(50, 70, 200, 20);

        Label option2 = new Label("2. Edit your profile");
        option2.setBounds(50, 90, 200, 20);

        Label option3 = new Label("3. View details of available cars");
        option3.setBounds(50, 110, 250, 20);

        Label option4 = new Label("4. Give feedback regarding your purchases");
        option4.setBounds(50, 130, 300, 20);

        Label option5 = new Label("5. View history");
        option5.setBounds(50, 150, 150, 20);

        Label prompt = new Label("Enter your choice (0-5):");
        prompt.setBounds(50, 180, 150, 20);

        // Create input field and button
        inputField = new TextField(5);
        inputField.setBounds(200, 180, 50, 20);

        Button okButton = new Button("OK");
        okButton.setBounds(260, 180, 50, 20);

        // Add components to frame
        add(title);
        add(option0);
        add(option1);
        add(option2);
        add(option3);
        add(option4);
        add(option5);
        add(prompt);
        add(inputField);
        add(okButton);
        
        dynamicLabel = new Label();
        dynamicLabel.setBounds(50, 210, 300, 20);
        dynamicLabel.setVisible(false);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                
                // Add dynamic label
                dynamicLabel.setText("Your choice: "+userInput);
                dynamicLabel.setVisible(true);
                repaint();
                
                if (userInput.equals("1")) {
                    dispose(); // Close the current frame
                    RegisterAccount registerAccount = new RegisterAccount();
                }
            }
        });
        
        setVisible(true);
    }
}

