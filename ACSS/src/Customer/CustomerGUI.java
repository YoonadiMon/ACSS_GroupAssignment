package Customer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class for the main customer features
public class CustomerGUI extends Frame {
    private Label dynamicLabel;
    private TextField inputField;

    public static void main(String[] Args) {
        CustomerGUI customer = new CustomerGUI(600,300);
    }

    public CustomerGUI(int width, int height) {
        super("Customer Features");

        // Content Alignment Variables
        int ContentH = 30;
        int GapX = 25;
        int leftPad = 50;
        int titleWidth = 200;
        
        setSize(width, height);
        setLocation(100,100);
        setTitle("Customer's Features");
        setLayout(null);
        
        // Create labels for menu options
        Label title = new Label("--- Select a Customer Features ---");
        title.setBounds((width-titleWidth)/2, ContentH, titleWidth, 20);

        Label option0 = new Label("0. EXIT");
        option0.setBounds(leftPad, ContentH += GapX, 100, 20);

        Label option1 = new Label("1. Register an account");
        option1.setBounds(leftPad, ContentH += GapX, 200, 20);

        Label option2 = new Label("2. Log In");
        option2.setBounds(leftPad, ContentH += GapX, 200, 20);

        Label option3 = new Label("3. Edit your profile");
        option3.setBounds(leftPad, ContentH += GapX, 250, 20);

        Label option4 = new Label("4. View details of available cars");
        option4.setBounds(leftPad, ContentH += GapX, 300, 20);

        Label option5 = new Label("5. Give feedback regarding your purchases");
        option5.setBounds(leftPad, ContentH += GapX, 150, 20);

        Label option6 = new Label("6. View history");
        option6.setBounds(leftPad, ContentH += GapX, 150, 20);

        Label prompt = new Label("Enter your choice (0-6):");
        prompt.setBounds(leftPad, ContentH += GapX, 150, 20);

        // Create input field and button
        inputField = new TextField(5);
        inputField.setBounds(200, ContentH, 50, 20);

        Button okButton = new Button("OK");
        okButton.setBounds(260, ContentH, 50, 20);

        // Add components to frame
        add(title);
        add(option0);
        add(option1);
        add(option2);
        add(option3);
        add(option4);
        add(option5);
        add(option6);
        add(prompt);
        add(inputField);
        add(okButton);
        
        dynamicLabel = new Label();
        dynamicLabel.setBounds(leftPad, ContentH += GapX, 300, 20);
        dynamicLabel.setForeground(Color.RED);
        dynamicLabel.setVisible(false);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();

                switch(userInput){
                    case "0" -> {
                        System.out.println("0");
                    }
                    case "1" -> {
                        dispose(); // Close the current frame
                        RegisterAccount registerAccount = new RegisterAccount(getWidth(), getHeight());
                    }
                    case "2" -> System.out.println("2");
                    case "3" -> System.out.println("3");
                    case "4" -> System.out.println("4");
                    case "5" -> System.out.println("5");
                    case "6" -> System.out.println("6");
                    default -> {
                        // Add dynamic label
                        dynamicLabel.setText("Your choice ("+userInput+") is not a valid option!");
                        dynamicLabel.setVisible(true);
                        add(dynamicLabel);
                    }
                }
            }
        });
        
        setVisible(true);
    }
}

