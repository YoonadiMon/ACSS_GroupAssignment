package Customer;

import Utils.WindowNav;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RegisterAccount {
    private JFrame frame;
    public RegisterAccount(int width, int height) {
        frame = new JFrame("Customer's Features");
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Registering Customer Account");
        frame.setLayout(null);

        // Content Alignment Variables
        int ContentH = 30;
        int GapX = 30;
        int titleWidth = 250;



        JLabel title = new JLabel("--- Register a new Customer Account ---");
        title.setBounds((width-titleWidth)/2, ContentH, titleWidth, 20);

        ContentH += GapX;
        JLabel username = new JLabel("Username");
        username.setBounds(50, ContentH, 80, 20);

        JTextField usernameTF = new JTextField();
        usernameTF.setBounds(150, ContentH, 150, 20);

        ContentH += GapX;
        JLabel email = new JLabel("Email");
        email.setBounds(50, ContentH, 80, 20);

        JTextField emailTF = new JTextField();
        emailTF.setBounds(150, ContentH, 150, 20);

        ContentH += GapX;
        JLabel password = new JLabel("Password");
        password.setBounds(50, ContentH, 80, 20);

        JPasswordField passwordTF = new JPasswordField();
        passwordTF.setBounds(150, ContentH, 150, 20);

        ContentH += GapX;
        JButton sbmt = new JButton("Submit");
        sbmt.setBounds(50, ContentH, 100, 30);

        JButton reset = new JButton("Reset");
        reset.setBounds(170,ContentH,100,30);

        frame.add(title);
        frame.add(username);
        frame.add(email);
        frame.add(password);
        frame.add(usernameTF);
        frame.add(emailTF);
        frame.add(passwordTF);
        frame.add(sbmt);
        frame.add(reset);

        // Button actions
        sbmt.addActionListener(e -> {
            System.out.println("Username: " + usernameTF.getText());
            System.out.println("Email: " + emailTF.getText());
            System.out.println("Password: " + new String(passwordTF.getPassword()));
        });

        reset.addActionListener(e -> {
            usernameTF.setText("");
            emailTF.setText("");
            passwordTF.setText("");
        });

        WindowNav.setCloseOperation(frame, () -> new CustomerGUI(width, height));
        frame.setVisible(true);
    }
}