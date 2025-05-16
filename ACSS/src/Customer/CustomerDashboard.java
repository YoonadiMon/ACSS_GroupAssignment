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
        WindowNav.setCloseOperation(frame, () -> new CustomerLandingGUI());
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
        mainPage = createBasicPagePanel("Home Page");
        
        // Get the content panel (which is at index 1 in BorderLayout.CENTER)
        JPanel contentPanel = (JPanel) ((BorderLayout) mainPage.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        
        // Page-specific content 
        JLabel contentLabel = new JLabel("Welcome, " + customer.getUsername() + "!");
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Account Information"));
        infoPanel.add(new JLabel("Username:"));
        infoPanel.add(new JLabel(customer.getUsername()));
        infoPanel.add(new JLabel("Email:"));
        infoPanel.add(new JLabel(customer.getEmail()));
        infoPanel.add(new JLabel("Account Status:"));
        String status = customer.isApproved() ? "Approved" : "Pending Approval";
        JLabel statusLabel = new JLabel(status);
        statusLabel.setForeground(customer.isApproved() ? new Color(0, 128, 0) : new Color(255, 140, 0));
        infoPanel.add(statusLabel);
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        contentPanel.add(contentLabel);
        contentPanel.add(infoPanel);   
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
        
        // Create a top panel for the title
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        topPanel.setBackground(BACKGROUND_COLOR);
        
        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(titleLabel);
        
        // Create a content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(0, 40, 30, 40));
        contentPanel.setBackground(BACKGROUND_COLOR);
        
        // Create a logout button panel (fixed at bottom right)
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(BACKGROUND_COLOR);
        JButton logoutButton = new JButton("Logout");
        ButtonStyler.styleExitButton(logoutButton);
        logoutButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Logout functionality will be implemented"));
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