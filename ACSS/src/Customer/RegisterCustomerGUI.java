package Customer;

import MainProgram.MainMenuGUI;
import Utils.ButtonStyler;
import Utils.WindowNav;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class RegisterAccount implements ActionListener, KeyListener {
    private JFrame frame;
    private JButton sbmtBtn, resetBtn;
    private JLabel titleLbl, usernameLbl, emailLbl, passwordLbl, confirmPasswordLbl;
    private JTextField usernameTF, emailTF;
    private JPasswordField passwordTF, confirmPasswordTF;

    public RegisterAccount(int width, int height) {
        frame = new JFrame("Register a new account");
        frame.setSize(width, height+20);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));

        titleLbl = new JLabel("Register a new Customer Account", SwingConstants.CENTER);
        titleLbl.setFont(new Font("Arial", Font.BOLD, 16));

        usernameLbl = new JLabel("Username:");
        usernameTF = new JTextField();
        usernameTF.addKeyListener(this);

        emailLbl = new JLabel("Email:");
        emailTF = new JTextField();
        emailTF.addKeyListener(this);

        passwordLbl = new JLabel("Password:");
        passwordTF = new JPasswordField();
        passwordTF.addKeyListener(this);

        confirmPasswordLbl = new JLabel("Confirm Password:");
        confirmPasswordTF = new JPasswordField();
        confirmPasswordTF.addKeyListener(this);

        contentPanel.add(usernameLbl);
        contentPanel.add(usernameTF);
        contentPanel.add(emailLbl);
        contentPanel.add(emailTF);
        contentPanel.add(passwordLbl);
        contentPanel.add(passwordTF);
        contentPanel.add(confirmPasswordLbl);
        contentPanel.add(confirmPasswordTF);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        sbmtBtn = new JButton("Submit");
        ButtonStyler.stylePrimaryButton(sbmtBtn);
        resetBtn = new JButton("Reset");
        ButtonStyler.styleButton(resetBtn);
        buttonPanel.add(sbmtBtn);
        buttonPanel.add(resetBtn);

        // Button actions
        sbmtBtn.addActionListener(this);
        resetBtn.addActionListener(this);

        frame.add(titleLbl, BorderLayout.NORTH);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        WindowNav.setCloseOperation(frame, () -> new CustomerGUI(width, height));
        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == sbmtBtn) {
                String username = usernameTF.getText();
                String email = emailTF.getText();
                String password = new String(passwordTF.getPassword());
                String confirmPassword = new String(confirmPasswordTF.getPassword());
                if(CustomerDataIO.searchName(username) != null){
                    JOptionPane.showMessageDialog(frame, "Username already exists!");
                }
                else {
                    if (password.equals(confirmPassword)) {
                        CustomerDataIO.allCustomers.add(new Customer(username, email, password));
                        CustomerDataIO.writeCustomer();
                        JOptionPane.showMessageDialog(frame, "Account has been added! Waiting for approval.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Passwords do not match!", "Password Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } else if (e.getSource() == resetBtn) {
                usernameTF.setText("");
                emailTF.setText("");
                passwordTF.setText("");
                confirmPasswordTF.setText("");
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,"Invalid input!");
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed for this functionality
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getSource() == usernameTF) {
                emailTF.requestFocusInWindow();
            } else if (e.getSource() == emailTF) {
                passwordTF.requestFocusInWindow();
            } else if (e.getSource() == passwordTF) {
                confirmPasswordTF.requestFocusInWindow();
            } else if (e.getSource() == confirmPasswordLbl) {
                sbmtBtn.requestFocusInWindow();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed for this functionality
    }
}