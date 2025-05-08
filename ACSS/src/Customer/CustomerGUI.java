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

public class CustomerGUI implements ActionListener, KeyListener {
    private JFrame frame;
    private JPanel cards;
    private CardLayout cardLayout;
    private int windowWidth, windowHeight;

    // Login and Register components
    private JPanel loginPanel, registerPanel;
    private JTextField loginUserOrEmailTF, registerEmailTF, registerUsernameTF;
    private JPasswordField loginPasswordTF, registerPasswordTF, confirmPasswordTF;
    private JButton loginBtn, switchToRegisterBtn, backToMainBtn, registerBtn, switchToLoginBtn, backToMainFromRegisterBtn;
    private JLabel forgotPasswordLbl;

    // Colors
    private final Color PRIMARY_COLOR = new Color(0, 84, 159); // Dark blue color from the image
    private final Color LIGHT_TEXT_COLOR = new Color(111, 143, 175); // Light blue for links

    public static void main(String[] args) {
        // Load customer data
        CustomerDataIO.readCustomer();

        SwingUtilities.invokeLater(() -> {
            new CustomerGUI();
        });
    }

    public CustomerGUI() {
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
        JLabel userOrEmailLabel = new JLabel("Username or Email Address");
        userOrEmailLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userOrEmailLabel.setForeground(Color.GRAY);
        loginUserOrEmailTF = new JTextField();
        loginUserOrEmailTF.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        loginUserOrEmailTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        loginUserOrEmailTF.addKeyListener(this);

        // Password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordLabel.setForeground(Color.GRAY);
        loginPasswordTF = new JPasswordField();
        loginPasswordTF.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        loginPasswordTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        loginPasswordTF.addKeyListener(this);

        // Forgot password link
        forgotPasswordLbl = new JLabel("Forgot password?");
        forgotPasswordLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        forgotPasswordLbl.setForeground(LIGHT_TEXT_COLOR);
        forgotPasswordLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Login button
        loginBtn = new JButton("Login");
        loginBtn.setBackground(PRIMARY_COLOR);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        loginBtn.addActionListener(this);

        // Not a member text
        JPanel registerTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerTextPanel.setBackground(Color.WHITE);
        JLabel notMemberLabel = new JLabel("Not a member?");
        notMemberLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        JLabel registerNowLabel = new JLabel("Register now");
        registerNowLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        registerNowLabel.setForeground(LIGHT_TEXT_COLOR);
        registerNowLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerTextPanel.add(notMemberLabel);
        registerTextPanel.add(registerNowLabel);

        // Back button
        backToMainBtn = new JButton("BACK");
        backToMainBtn.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        ButtonStyler.styleExitButton(backToMainBtn);
        backToMainBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToMainBtn.setMaximumSize(new Dimension(100, 30));
        backToMainBtn.addActionListener(this);

        // Add action listeners
        switchToRegisterBtn.addActionListener(this);
        registerNowLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(cards, "register");
            }
        });
        forgotPasswordLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Implement forgot password functionality
                JOptionPane.showMessageDialog(frame, "Forgot password functionality will be implemented soon.");
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
        loginPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loginPanel.add(forgotPasswordLbl);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(loginBtn);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        loginPanel.add(registerTextPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(backToMainBtn);
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
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        usernameLabel.setForeground(Color.GRAY);
        registerUsernameTF = new JTextField();
        registerUsernameTF.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        registerUsernameTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        registerUsernameTF.addKeyListener(this);

        // Email field
        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        emailLabel.setForeground(Color.GRAY);
        registerEmailTF = new JTextField();
        registerEmailTF.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        registerEmailTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        registerEmailTF.addKeyListener(this);

        // Password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordLabel.setForeground(Color.GRAY);
        registerPasswordTF = new JPasswordField();
        registerPasswordTF.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        registerPasswordTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        registerPasswordTF.addKeyListener(this);

        // Confirm Password field
        JLabel confirmPasswordLabel = new JLabel("Confirm password");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        confirmPasswordLabel.setForeground(Color.GRAY);
        confirmPasswordTF = new JPasswordField();
        confirmPasswordTF.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        confirmPasswordTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        confirmPasswordTF.addKeyListener(this);

        // Register button
        registerBtn = new JButton("Register");
        registerBtn.setBackground(PRIMARY_COLOR);
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        registerBtn.setFocusPainted(false);
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        registerBtn.addActionListener(this);

        // Back button
        backToMainFromRegisterBtn = new JButton("BACK");
        backToMainFromRegisterBtn.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        ButtonStyler.styleExitButton(backToMainFromRegisterBtn);
        backToMainFromRegisterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToMainFromRegisterBtn.setMaximumSize(new Dimension(100, 30));
        backToMainFromRegisterBtn.addActionListener(this);

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
        registerPanel.add(registerBtn);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        registerPanel.add(backToMainFromRegisterBtn);
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
                String userOrEmail = loginUserOrEmailTF.getText();
                String password = new String(loginPasswordTF.getPassword());

                if (userOrEmail.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields", "Login Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if customer exists with these credentials (by username or email)
                Customer customer = CustomerDataIO.validateLogin(userOrEmail, password);

                if (customer != null) {
                    // Check if customer is approved
                    if (customer.isApproved()) {
                        JOptionPane.showMessageDialog(frame, "Login successful!");
                        // Navigate to customer dashboard
                        frame.dispose();
                        new CustomerDashboard(customer, windowWidth, windowHeight);
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
        } else if (e.getSource() == registerBtn) {
            try {
                String username = registerUsernameTF.getText();
                String email = registerEmailTF.getText();
                String password = new String(registerPasswordTF.getPassword());
                String confirmPassword = new String(confirmPasswordTF.getPassword());

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields", "Registering Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (CustomerDataIO.searchEmail(email) != null) {
                    JOptionPane.showMessageDialog(frame, "Email already registered!", "Register Error", JOptionPane.ERROR_MESSAGE);
                } else if (CustomerDataIO.searchName(username) != null) {
                    JOptionPane.showMessageDialog(frame, "Username already taken!", "Register Error", JOptionPane.ERROR_MESSAGE);
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match!", "Password Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Register the new customer with approval status set to false by default
                    Customer newCustomer = new Customer(username, email, password);
                    CustomerDataIO.allCustomers.add(newCustomer);
                    CustomerDataIO.writeCustomer(); // Save to file with approval status

                    JOptionPane.showMessageDialog(frame,
                            "Account has been created! Your account needs approval by admin before you can login.",
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
        } else if (e.getSource() == switchToRegisterBtn) {
            cardLayout.show(cards, "register");
        } else if (e.getSource() == backToMainBtn || e.getSource() == backToMainFromRegisterBtn) {
            frame.dispose();
            new MainMenuGUI();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed
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
        // Not needed
    }
}