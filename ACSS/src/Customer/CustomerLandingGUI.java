package Customer;

import MainProgram.MainMenuGUI;
import Utils.ButtonStyler;
import Utils.WindowNav;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author YOON
 */
public class CustomerLandingGUI implements ActionListener, KeyListener {
    private JFrame frame;
    private JPanel cards;
    private CardLayout cardLayout;
    //private int windowWidth, windowHeight; i dun think this is needed

    // Login and Register components
    private JPanel loginPanel, registerPanel;
    private JTextField loginUserOrEmailTF, registerEmailTF, registerUsernameTF;
    private JPasswordField loginPasswordTF, registerPasswordTF, confirmPasswordTF;
    private JButton loginBtn, switchToRegisterBtn, backToMainBtn, registerBtn, switchToLoginBtn, backToMainFromRegisterBtn;
    private JLabel forgotPasswordLbl;
    // Guest login button
    private JButton guestLoginBtn;

    // Colors
    private final Color PRIMARY_COLOR = new Color(0, 84, 159);
    private final Color LIGHT_TEXT_COLOR = new Color(111, 143, 175);

    public CustomerLandingGUI() {
        CustomerDataIO.loadAllData();
        frame = new JFrame("Customer Account");
        frame.setSize(400, 550);
        frame.setLocationRelativeTo(null);

        // Card layout to switch between login and register pages
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Create login and register panels
        createLoginPanel();
        createRegisterPanel();

        // Add panels to the card layout
        cards.add(loginPanel, "login");
        cards.add(registerPanel, "register");

        frame.add(cards);

        // Set close operation to go back to main menu
        WindowNav.setCloseOperation(frame, () -> new MainMenuGUI());

        frame.setVisible(true);
    }

    private void createLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        loginPanel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Login Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tab buttons panel
        JPanel tabPanel = new JPanel(new GridLayout(1, 2));
        switchToLoginBtn = createTabButton("Login", true);
        switchToRegisterBtn = createTabButton("Register", false);

        tabPanel.add(switchToLoginBtn);
        tabPanel.add(switchToRegisterBtn);
        tabPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Username or Email field
        JLabel userOrEmailLabel = new JLabel("Username or Email Address: ");
        userOrEmailLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userOrEmailLabel.setForeground(Color.GRAY);
        userOrEmailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginUserOrEmailTF = new JTextField();
        loginUserOrEmailTF.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        loginUserOrEmailTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        loginUserOrEmailTF.addKeyListener(this);

        // Password field
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordLabel.setForeground(Color.GRAY);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginPasswordTF = new JPasswordField();
        loginPasswordTF.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        loginPasswordTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        loginPasswordTF.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginPasswordTF.addKeyListener(this);

        // Forgot password link
        forgotPasswordLbl = new JLabel("Forgot password?");
        forgotPasswordLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        forgotPasswordLbl.setForeground(LIGHT_TEXT_COLOR);
        forgotPasswordLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordLbl.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Not a member text
        JPanel registerTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerTextPanel.setBackground(Color.WHITE);
        registerTextPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel notMemberLabel = new JLabel("Not a member?");
        notMemberLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel registerNowLabel = new JLabel("Register now");
        registerNowLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        registerNowLabel.setForeground(LIGHT_TEXT_COLOR);
        registerNowLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        registerTextPanel.add(notMemberLabel);
        registerTextPanel.add(registerNowLabel);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        // Login button
        loginBtn = new JButton("Login");
        ButtonStyler.stylePrimaryButton(loginBtn);
        loginBtn.addActionListener(this);

        // Back button
        backToMainBtn = new JButton("BACK");
        backToMainBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        ButtonStyler.styleExitButton(backToMainBtn);
        backToMainBtn.addActionListener(this);

        bottomPanel.add(backToMainBtn);
        bottomPanel.add(loginBtn);
        bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        bottomPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));

        // Show password checkbox
        JCheckBox showPasswordCheckBox = new JCheckBox(" Show Password");
        showPasswordCheckBox.setBackground(Color.WHITE);
        showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
        showPasswordCheckBox.setFocusable(false);
        showPasswordCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                loginPasswordTF.setEchoChar((char) 0);
            } else {
                loginPasswordTF.setEchoChar('\u2022');
            }
        });

        // Guest login button
        guestLoginBtn = new JButton("Continue as Guest");
        guestLoginBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        guestLoginBtn.setForeground(new Color(0, 84, 159));
        guestLoginBtn.setBackground(Color.WHITE);
        guestLoginBtn.setBorder(BorderFactory.createLineBorder(new Color(0, 84, 159), 1));
        guestLoginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        guestLoginBtn.setFocusPainted(false);
        guestLoginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        guestLoginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        guestLoginBtn.addActionListener(this);

        // Add action listeners
        switchToRegisterBtn.addActionListener(this);
        
        
        registerNowLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(cards, "register");
            }
        });
        
        
        forgotPasswordLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String userOrEmail = loginUserOrEmailTF.getText().trim();

                if (userOrEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter your username or email first.");
                    return;
                }

                Customer customer = CustomerDataIO.getIDfromUsernameorEmail(userOrEmail);

                if (customer == null) {
                    JOptionPane.showMessageDialog(frame, "User not found. Please check your username or email.");
                    return;
                }

                String customerId = customer.getCustomerId();

                if (customerId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Customer ID is empty. Please contact admin.");
                    return;
                }

                if (!CustomersForgetPwd.customerExists(customerId)) {
                    JOptionPane.showMessageDialog(frame, "No security question set for this Customer ID. Please contact admin.");
                    return;
                }

                // Load saved question and answer
                String savedQuestion = null;
                String savedAnswer = null;

                try (BufferedReader reader = new BufferedReader(new FileReader("data/CustomersForgetPwd.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith(customerId + ",")) {
                            String[] parts = line.split(",", 3);
                            if (parts.length == 3) {
                                savedQuestion = parts[1];
                                savedAnswer = parts[2];
                            }
                            break;
                        }
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(frame, "Error reading security questions. Please contact admin.");
                    return;
                }

                if (savedQuestion == null) {
                    JOptionPane.showMessageDialog(frame, "Security question not found. Please contact admin.");
                    return;
                }

                String userAnswer = JOptionPane.showInputDialog(frame, "Security Question:\n" + savedQuestion);

                if (userAnswer == null) {
                    // User cancelled
                    return;
                }

                if (userAnswer.trim().equalsIgnoreCase(savedAnswer.trim())) {
                    JOptionPane.showMessageDialog(frame, "Security answer verified! You may now reset your password.");
                    frame.dispose();
                    new CustomerDashboard(customer);
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect answer. Please contact admin for assistance.");
                }
            }
        });



        // Add components to panel with spacing
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(tabPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(userOrEmailLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loginPanel.add(loginUserOrEmailTF);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        loginPanel.add(passwordLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loginPanel.add(loginPasswordTF);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(forgotPasswordLbl);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(registerTextPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 0)));
        loginPanel.add(showPasswordCheckBox);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(bottomPanel);
        // Add guest login button with spacing
        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        loginPanel.add(guestLoginBtn);
    }

    private void createRegisterPanel() {
        registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));
        registerPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        registerPanel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Register Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tab buttons panel
        JPanel tabPanel = new JPanel(new GridLayout(1, 2));
        JButton loginTabBtn = createTabButton("Login", false);
        JButton registerTabBtn = createTabButton("Register", true);

        tabPanel.add(loginTabBtn);
        tabPanel.add(registerTabBtn);
        tabPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Username field
        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        usernameLabel.setForeground(Color.GRAY);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerUsernameTF = new JTextField();
        registerUsernameTF.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        registerUsernameTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        registerUsernameTF.addKeyListener(this);

        // Email field
        JLabel emailLabel = new JLabel("Email Address: ");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        emailLabel.setForeground(Color.GRAY);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerEmailTF = new JTextField();
        registerEmailTF.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        registerEmailTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        registerEmailTF.addKeyListener(this);

        // Password field
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordLabel.setForeground(Color.GRAY);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerPasswordTF = new JPasswordField();
        registerPasswordTF.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        registerPasswordTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        registerPasswordTF.addKeyListener(this);

        // Confirm Password field
        JLabel confirmPasswordLabel = new JLabel("Confirm password: ");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        confirmPasswordLabel.setForeground(Color.GRAY);
        confirmPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        confirmPasswordTF = new JPasswordField();
        confirmPasswordTF.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        confirmPasswordTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        confirmPasswordTF.addKeyListener(this);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        // Register button
        registerBtn = new JButton("Register");
        ButtonStyler.stylePrimaryButton(registerBtn);
        registerBtn.addActionListener(this);

        // Back button
        backToMainFromRegisterBtn = new JButton("BACK");
        ButtonStyler.styleExitButton(backToMainFromRegisterBtn);
        backToMainFromRegisterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToMainFromRegisterBtn.addActionListener(this);


        bottomPanel.add(backToMainFromRegisterBtn);
        bottomPanel.add(registerBtn);
        bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        bottomPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));

        // Add action listeners
        loginTabBtn.addActionListener(e -> cardLayout.show(cards, "login"));

        // Add components to panel with spacing
        registerPanel.add(titleLabel);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        registerPanel.add(tabPanel);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        registerPanel.add(usernameLabel);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        registerPanel.add(registerUsernameTF);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        registerPanel.add(emailLabel);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        registerPanel.add(registerEmailTF);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        registerPanel.add(passwordLabel);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        registerPanel.add(registerPasswordTF);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        registerPanel.add(confirmPasswordLabel);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        registerPanel.add(confirmPasswordTF);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        registerPanel.add(bottomPanel);
    }

    private JButton createTabButton(String text, boolean isActive) {
        JButton button = new JButton(text);
        if (isActive) {
            button.setBackground(PRIMARY_COLOR);
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(10, 0, 10, 0)));
        }
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            try {
                String userOrEmail = loginUserOrEmailTF.getText().trim();
                String password = new String(loginPasswordTF.getPassword()).trim();

                if (userOrEmail.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields", "Login Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // First check if the account has been deleted
                DeletedCustomer deletedCustomer = CustomerDataIO.searchDeletedName(userOrEmail);
                if (deletedCustomer == null) {
                    deletedCustomer = CustomerDataIO.searchDeletedEmail(userOrEmail);
                }

                if (deletedCustomer != null && deletedCustomer.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(frame,
                            "Your account has been deleted by the administrator.\n" +
                            "Please contact support if you believe this was done in error.\n" +
                            "You may create a new account if you wish to continue using our services.",
                            "Account Deleted",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if customer exists with these credentials (by username or email)
                Customer customer = CustomerDataIO.validateLogin(userOrEmail, password);
                if (customer != null) {
                    // Check if customer is approved
                    if (customer.isApproved()) {
                        JOptionPane.showMessageDialog(frame, "Login successful!");
                        frame.dispose();
                        new CustomerDashboard(customer);
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "Your account is pending approval by admin. Please try again later.",
                                "Account Not Approved",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username/email or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Login error: " + ex.getMessage());
            }
        }else if (e.getSource() == guestLoginBtn) {
            // Create a guest customer with limited access
            try {
                GuestCustomer guestCustomer = new GuestCustomer();
                JOptionPane.showMessageDialog(frame, "Continuing as guest...");
                frame.dispose();
                new CustomerDashboardGuest(guestCustomer);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error accessing guest mode: " + ex.getMessage());
            }
        }else if (e.getSource() == registerBtn) {
            try {
                String username = registerUsernameTF.getText().trim();
                String email = registerEmailTF.getText().trim();
                String password = new String(registerPasswordTF.getPassword()).trim();
                String confirmPassword = new String(confirmPasswordTF.getPassword()).trim();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields", "Registering Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (CustomerDataIO.searchEmail(email) != null) {
                    JOptionPane.showMessageDialog(frame, "Email already registered!", "Register Error", JOptionPane.ERROR_MESSAGE);
                } else if (CustomerDataIO.searchName(username) != null) {
                    JOptionPane.showMessageDialog(frame, "Username already taken!", "Register Error", JOptionPane.ERROR_MESSAGE);
                } else if (CustomerDataValidator.isEmailBanned(email)) {
                    JOptionPane.showMessageDialog(frame, 
                            "This email address has been banned by the administrator.\n" +
                            "Please contact support if you believe this was done in error\n" +
                            "or use a different email address.", 
                            "Email Banned", 
                            JOptionPane.ERROR_MESSAGE);
                } else if (CustomerDataValidator.isUsernameBanned(username)) {
                    JOptionPane.showMessageDialog(frame, 
                            "This username has been banned by the administrator.\n" +
                            "Please contact support if you believe this was done in error\n" +
                            "or choose a different username.", 
                            "Username Banned", 
                            JOptionPane.ERROR_MESSAGE);
                } else if (!CustomerDataValidator.isValidUsername(username)) {
                    JOptionPane.showMessageDialog(frame, "Username is not valid! (At least 3 characters. Must not contain any special characters such as commas, quotes, newlines except underscore)", "Invalid Username Error", JOptionPane.ERROR_MESSAGE);
                } else if (!CustomerDataValidator.isValidEmail(email)) {
                    JOptionPane.showMessageDialog(frame, "Email is not valid! Must not contain any special characters such as commas, quotes, newlines.", "Invalid Email Error", JOptionPane.ERROR_MESSAGE);
                } else if (!CustomerDataValidator.isValidPassword(password)) {
                    JOptionPane.showMessageDialog(frame, "Passwords is not valid! Must not contain any special characters such as commas, quotes, newlines. (At least 8 characters containing one uppercase letter)", "Invalid Password Error", JOptionPane.ERROR_MESSAGE);
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match!", "Password Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Register the new customer with approval status set to false by default
                    Customer newCustomer = new Customer(username, email, password);
                    CustomerDataIO.allCustomers.add(newCustomer);
                    CustomerDataIO.writeCustomer(); // Save to file with approval status
                    JOptionPane.showMessageDialog(frame,
                            "Account has been created! Your account needs approval by admin before you can login.\nTip: All values have been trimed to remove extra spaces at front and end!",
                            "Registration Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                    // Clear fields and switch to login
                    registerUsernameTF.setText("");
                    registerEmailTF.setText("");
                    registerPasswordTF.setText("");
                    confirmPasswordTF.setText("");
                    cardLayout.show(cards, "login");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Registering error: " + ex.getMessage());
            }
        }else if (e.getSource() == switchToRegisterBtn) {
            cardLayout.show(cards, "register");
        } else if (e.getSource() == backToMainBtn || e.getSource() == backToMainFromRegisterBtn) {
            frame.dispose();
            new MainMenuGUI();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Nothing's here
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getSource() == loginUserOrEmailTF) {
                loginPasswordTF.requestFocusInWindow();
            } else if (e.getSource() == loginPasswordTF) {
                loginBtn.doClick();
            } else if (e.getSource() == registerUsernameTF) {
                registerEmailTF.requestFocusInWindow();
            } else if (e.getSource() == registerEmailTF) {
                registerPasswordTF.requestFocusInWindow();
            } else if (e.getSource() == registerPasswordTF) {
                confirmPasswordTF.requestFocusInWindow();
            } else if (e.getSource() == confirmPasswordTF) {
                registerBtn.doClick();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Nothing's here
    }
}