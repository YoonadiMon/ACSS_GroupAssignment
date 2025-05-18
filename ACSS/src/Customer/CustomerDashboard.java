package Customer;

import Utils.ButtonStyler;
import Utils.WindowNav;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class CustomerDashboard implements ActionListener   {
    
    private JFrame frame;
    private JPanel cards;
    private CardLayout cardLayout;
    private Customer customer;
    
    // Pages
    private JPanel mainPage, page2Panel, page3Panel, page4Panel, page5Panel;
    
    // Navigation buttons
    private JButton[] navButtons;

    // Colors
    private final Color PRIMARY_COLOR = new Color(0, 84, 159);
    private final Color LIGHT_TEXT_COLOR = new Color(111, 143, 175);
    private final Color BACKGROUND_COLOR = Color.WHITE;

    public CustomerDashboard(Customer customer) {
        this.customer = customer;

        frame = new JFrame("Customer Dashboard");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        //WindowNav.setCloseOperation(frame, () -> new CustomerLandingGUI());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Card layout to switch between login and register pages
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Create the pages
        createMainPage();
        createPage2();
        createPage3();
        createPage4();
        createPage5();
        
        // Add panels to the card layout
        cards.add(mainPage, "page1");
        cards.add(page2Panel, "page2");
        cards.add(page3Panel, "page3");
        cards.add(page4Panel, "page4");
        cards.add(page5Panel, "page5");
        
        // Create navigation panel
        JPanel navigationPanel = createNavigationPanel();
        
        // Add components to the main frame
        frame.setLayout(new BorderLayout());
        frame.add(navigationPanel, BorderLayout.NORTH);
        frame.add(cards, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }
    
    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel(new GridLayout(1, 5, 5, 0));
        navPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        navPanel.setBackground(BACKGROUND_COLOR);
        
        navButtons = new JButton[5];
        String[] buttonLabels = {"Page 1", "Page 2", "Page 3", "Page 4", "Page 5"};
        String[] cardNames = {"page1", "page2", "page3", "page4", "page5"};
        
        for (int i = 0; i < 5; i++) {
            navButtons[i] = createNavButton(buttonLabels[i], cardNames[i]);
            navPanel.add(navButtons[i]);
        }
        
        // Initially highlight the first button
        updateNavButtonsState(0);
        
        return navPanel;
    }
    
    private JButton createNavButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setActionCommand(cardName);
        button.addActionListener(this);
        return button;
    }
    
    private void updateNavButtonsState(int activeIndex) {
        for (int i = 0; i < navButtons.length; i++) {
            if (i == activeIndex) {
                navButtons[i].setBackground(PRIMARY_COLOR);
                navButtons[i].setForeground(Color.WHITE);
                navButtons[i].setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            } else {
                navButtons[i].setBackground(BACKGROUND_COLOR);
                navButtons[i].setForeground(Color.BLACK);
                navButtons[i].setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(10, 0, 10, 0)));
            }
        }
    }
    
    private void createMainPage() {
        mainPage = createBasicPagePanel("Welcome To ACSS, " + customer.getUsername() + "!");

        // Get the content panel (which is at index 1 in BorderLayout.CENTER)
        JPanel contentPanel = (JPanel) ((BorderLayout) mainPage.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Page-specific content 
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new BorderLayout(10, 10));
        accountPanel.setMaximumSize(new Dimension(600, 400));
        accountPanel.setPreferredSize(new Dimension(500, 330));
        accountPanel.setBackground(new Color(240, 240, 240));
        accountPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header -> Title + Button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(accountPanel.getBackground());
        JLabel accountLabel = new JLabel("Account Information");
        accountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(accountLabel, BorderLayout.WEST);

        JButton editButton = new JButton("Edit Profile");
        editButton.setBackground(new Color(0, 84, 159)); 
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setFont(new Font("Arial", Font.BOLD, 14));
        editButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        headerPanel.add(editButton, BorderLayout.EAST);     

        // Field Section with fixed height rows
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(5, 2, 10, 20)); // Added an extra row for consistent spacing
        fieldsPanel.setBackground(accountPanel.getBackground());

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField usernameField = new JTextField();
        usernameField.setText(customer.getUsername());
        usernameField.setEditable(false);

        // Email field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField emailField = new JTextField();
        emailField.setText(customer.getEmail());
        emailField.setEditable(false);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setText("***");
        passwordField.setEditable(false);

        // Account status
        JLabel statusLabel = new JLabel("Account Status:");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        String status = customer.isApproved() ? "Approved" : "Pending Approval";
        JLabel statusValueLabel = new JLabel(status);
        statusValueLabel.setForeground(customer.isApproved() ? new Color(0, 128, 0) : new Color(255, 140, 0));
        statusValueLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Save button - Always in the layout but initially hidden
        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(0, 84, 159));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        saveButton.setVisible(false);

        // Empty placeholder panel for the 5th row's first column
        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);

        // Add all components to the fields panel
        fieldsPanel.add(usernameLabel);
        fieldsPanel.add(usernameField);
        fieldsPanel.add(emailLabel);
        fieldsPanel.add(emailField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);
        fieldsPanel.add(statusLabel);
        fieldsPanel.add(statusValueLabel);
        fieldsPanel.add(emptyPanel);
        fieldsPanel.add(saveButton);

        accountPanel.add(headerPanel, BorderLayout.NORTH);
        accountPanel.add(fieldsPanel, BorderLayout.CENTER);

        // Center the account panel in the content panel + Hide extra space 
        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(accountPanel);

        contentPanel.add(wrapperPanel);

        // Add action listeners
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle edit mode
                boolean editMode = !usernameField.isEditable();

                // Update UI based on edit mode
                usernameField.setEditable(editMode);
                emailField.setEditable(editMode);
                passwordField.setEditable(editMode);

                // If switching to edit mode, clear password field and show actual values
                if (editMode) {
                    usernameField.setText(customer.getUsername());
                    emailField.setText(customer.getEmail());
                    passwordField.setText("");

                    // Change edit button appearance
                    editButton.setBackground(new Color(100, 100, 100)); // Gray color

                    // Show the save button
                    saveButton.setVisible(true);
                } else {
                    // Revert to display mode
                    usernameField.setText(customer.getUsername());
                    emailField.setText(customer.getEmail());
                    passwordField.setText("***");

                    editButton.setBackground(new Color(0, 84, 159)); 
                    saveButton.setVisible(false);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the updated information
                customer.setUsername(usernameField.getText());
                customer.setEmail(emailField.getText());

                String password = new String(passwordField.getPassword());
                if (!password.isEmpty()) {
                    // Update password if changed and valid
                    if (!CustomerDataValidator.isValidPassword(password)) {
                        JOptionPane.showMessageDialog(frame, "Email is not valid!", "Invalid Email Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        customer.setPassword(password);
                    }
                }
                CustomerDataIO.writeCustomer();
                JOptionPane.showMessageDialog(frame,
                            "Account Information has been edited!",
                            "Account edit Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                
                // Return to display mode
                usernameField.setEditable(false);
                emailField.setEditable(false);
                passwordField.setEditable(false);

                usernameField.setText(customer.getUsername());
                emailField.setText(customer.getEmail());
                passwordField.setText("***");

                editButton.setBackground(new Color(0, 84, 159)); // Blue color
                saveButton.setVisible(false);

                // Update page title with new username
                JLabel titleLabel = (JLabel) ((JPanel) mainPage.getComponent(0)).getComponent(0);
                titleLabel.setText("Welcome To ACSS, " + customer.getUsername() + "!");
            }
        });
    }
    
    
    private void createPage2() {
        page2Panel = createBasicPagePanel("Page 2");
        
        // Get the content panel (which is at index 1 in BorderLayout.CENTER)
        JPanel contentPanel = (JPanel) ((BorderLayout) page2Panel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        
        // Page-specific content 
        JLabel contentLabel = new JLabel("This is Page 2 content");
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        contentPanel.add(contentLabel);
    }
    
    private void createPage3() {
        page3Panel = createBasicPagePanel("Page 3");
        
        // Get the content panel (which is at index 1 in BorderLayout.CENTER)
        JPanel contentPanel = (JPanel) ((BorderLayout) page3Panel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        
        // Page-specific content 
        JLabel contentLabel = new JLabel("This is Page 3 content");
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        contentPanel.add(contentLabel);
    }
    
    private void createPage4() {
        page4Panel = createBasicPagePanel("Page 4");
        
        // Get the content panel (which is at index 1 in BorderLayout.CENTER)
        JPanel contentPanel = (JPanel) ((BorderLayout) page4Panel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        
        // Page-specific content 
        JLabel contentLabel = new JLabel("This is Page 4 content");
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        contentPanel.add(contentLabel);
    }
    
    private void createPage5() {
        page5Panel = createBasicPagePanel("Page 5");
        
        // Get the content panel (which is at index 1 in BorderLayout.CENTER)
        JPanel contentPanel = (JPanel) ((BorderLayout) page5Panel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        
        // Page-specific content 
        JLabel contentLabel = new JLabel("This is Page 5 content");
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        contentPanel.add(contentLabel);
    }
    
    private JPanel createBasicPagePanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        // Top panel for the title
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(new EmptyBorder(30, 40, 10, 40));
        topPanel.setBackground(BACKGROUND_COLOR);
        
        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(titleLabel);
        
        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(0, 40, 0, 40));
        contentPanel.setBackground(BACKGROUND_COLOR);
        
        // Create a logout button panel (fixed at bottom right)
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(BACKGROUND_COLOR);
        JButton logoutButton = new JButton("Logout");
        ButtonStyler.styleExitButton(logoutButton);
        logoutButton.addActionListener(e->{
            frame.dispose();
            new CustomerLandingGUI();      
        });
        logoutPanel.add(logoutButton);
        
        // Add components to the panel
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(logoutPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        // Show the selected page
        cardLayout.show(cards, command);
        
        // Update navigation buttons style
        for (int i = 0; i < navButtons.length; i++) {
            if (navButtons[i].getActionCommand().equals(command)) {
                updateNavButtonsState(i);
                break;
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerDataIO.readCustomer();
            Customer customer = CustomerDataIO.searchName("Yoon");
            //Customer.Customer@1e9b5f31 14cb8731,Yoon,yoon@email.com,1234567A,true
            if (customer != null) {
                new CustomerDashboard(customer);
            }
            else {
                System.out.println("error: "+customer);
            }
        });
    }
    
}