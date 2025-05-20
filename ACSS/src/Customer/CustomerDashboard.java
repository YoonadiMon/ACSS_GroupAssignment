package Customer;

import Car.Car;
import Car.CarList;
import Car.CarRequest;
import Car.SoldCarRecord;
import Salesman.Salesman;
import Salesman.SalesmanList;
import Utils.ButtonStyler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;



/// DELETE THE ON CLOSE FUNC 

public class CustomerDashboard implements ActionListener   {
    
    private JFrame frame;
    private JPanel cards;
    private CardLayout cardLayout;
    private Customer customer;
    
    // Pages
    private JPanel mainPage, CarPage, feedbackPanel, carHistoryPage, page5Panel;
    
    // Navigation buttons
    private JButton[] navButtons;

    // Colors
    private final Color PRIMARY_COLOR = new Color(0, 84, 159);
    private final Color LIGHT_TEXT_COLOR = new Color(111, 143, 175);
    private final Color BACKGROUND_COLOR = Color.WHITE;

    public CustomerDashboard(Customer customer) {
        this.customer = customer;

        frame = new JFrame("Customer Dashboard");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        //WindowNav.setCloseOperation(frame, () -> new CustomerLandingGUI());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Card layout to switch between login and register pages
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Create the pages
        createMainPage();
        createCarPage();
        createFeedbackPage();
        createCarHistoryPage();
        createPage5();
        
        // Add panels to the card layout
        cards.add(mainPage, "Main");
        cards.add(CarPage, "Cars");
        cards.add(feedbackPanel, "Feedbacks");
        cards.add(carHistoryPage, "CarHistory");
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
        String[] buttonLabels = {"Main", "Cars", "Feedbacks", "Cars History", "Page 5"};
        String[] cardNames = {"Main", "Cars", "Feedbacks", "CarHistory", "page5"};
        
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
        JPanel accountPanel, headerPanel, contentPanel, fieldsPanel, emptyPanel, wrapperPanel;
        JLabel accountLabel, usernameLabel, emailLabel, passwordLabel, statusLabel, forgotPasswordLbl;
        JButton editButton, saveButton;
        JTextField usernameField, emailField;
        
        mainPage = createBasicPagePanel("Welcome To ACSS, " + customer.getUsername() + "!");

        // Get the content panel (which is at index 1 in BorderLayout.CENTER)
        contentPanel = (JPanel) ((BorderLayout) mainPage.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Page-specific content 
        accountPanel = new JPanel();
        accountPanel.setLayout(new BorderLayout(10, 10));
        accountPanel.setMaximumSize(new Dimension(600, 400));
        accountPanel.setPreferredSize(new Dimension(500, 330));
        accountPanel.setBackground(new Color(240, 240, 240));
        accountPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header -> Title + Button
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(accountPanel.getBackground());
        accountLabel = new JLabel("Account Information");
        accountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(accountLabel, BorderLayout.WEST);

        editButton = new JButton("Edit Profile");
        editButton.setBackground(new Color(0, 84, 159)); 
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setFont(new Font("Arial", Font.BOLD, 14));
        editButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        headerPanel.add(editButton, BorderLayout.EAST);     

        // Field Section with fixed height rows
        fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(5, 2, 10, 20)); // Added an extra row for consistent spacing
        fieldsPanel.setBackground(accountPanel.getBackground());

        // Username field
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameField = new JTextField();
        usernameField.setText(customer.getUsername());
        usernameField.setEditable(false);

        // Email field
        emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailField = new JTextField();
        emailField.setText(customer.getEmail());
        emailField.setEditable(false);

        // Password field
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setText("***");
        passwordField.setEditable(false);
        
        // Account status
        statusLabel = new JLabel("Account Status:");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        String status = customer.isApproved() ? "Approved" : "Pending Approval";
        JLabel statusValueLabel = new JLabel(status);
        statusValueLabel.setForeground(customer.isApproved() ? new Color(0, 128, 0) : new Color(255, 140, 0));
        statusValueLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Save button - Always in the layout but initially hidden
        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(0, 84, 159));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        saveButton.setVisible(false);

        // Empty placeholder panel for the 5th row's first column
        emptyPanel = new JPanel();
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
        wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(accountPanel);
        
        
        //
        forgotPasswordLbl = new JLabel("Add secuity question in case of forgotten password?");
        forgotPasswordLbl.setFont(new Font("Arial", Font.BOLD, 14));
        forgotPasswordLbl.setForeground(LIGHT_TEXT_COLOR);
        forgotPasswordLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPasswordLbl.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        
        wrapperPanel.add(forgotPasswordLbl);

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
        
        forgotPasswordLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String customerId = customer.getCustomerId(); 

                String question = JOptionPane.showInputDialog(frame, "Enter your security question:");
                if (question == null || question.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Security question cannot be empty.");
                    return;
                }

                String answer = JOptionPane.showInputDialog(frame, "Enter your answer:");
                if (answer == null || answer.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Answer cannot be empty.");
                    return;
                }

                CustomersForgetPwd customerForgetPwd = new CustomersForgetPwd(customerId, question, answer);
                if (CustomersForgetPwd.customerExists(customerId)) {
                    int option = JOptionPane.showConfirmDialog(frame,
                        "You already have a saved security question. Do you want to override it?",
                        "Confirm Override",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                    if (option == JOptionPane.YES_OPTION) {
                        // User chose to override
                        boolean success = customerForgetPwd.overrideSecurityQuestion();
                        if (success) {
                            JOptionPane.showMessageDialog(frame, "Security question updated successfully.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Error updating security question.");
                        }
                    } else {
                        // User chose NO or closed the dialog
                        JOptionPane.showMessageDialog(frame, "Security question not changed.");
                    }
                } else {
                    // Customer ID does not exist, save new question
                    boolean success = customerForgetPwd.saveSecurityQuestion();
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Security question saved.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error saving security question.");
                    }
                }
            }
        });
    }
    
    private void createCarPage() {
        CarPage = createBasicPagePanel("Available Cars at ACSS");

        // Load salesman data
        ArrayList<Salesman> salesmanList = SalesmanList.loadSalesmanDataFromFile();

        // Get the content panel (which is at index 1 in BorderLayout.CENTER)
        JPanel contentPanel = (JPanel) ((BorderLayout) CarPage.getLayout()).getLayoutComponent(BorderLayout.CENTER);

        // Set layout for the content panel
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Page-specific content 
        ArrayList<Car> allCarsList = CarList.loadCarDataFromFile();
        ArrayList<Car> showCarsList = new ArrayList<>();
        for (Car car : allCarsList) {
            if (car.getStatus().equals("available")) {
                showCarsList.add(car);
            }
        }

        // Add some spacing at the top
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create a panel to hold car boxes with some margin on the sides
        JPanel carListContainer = new JPanel();
        carListContainer.setLayout(new BoxLayout(carListContainer, BoxLayout.Y_AXIS));
        carListContainer.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
        carListContainer.setBackground(Color.WHITE);

        // Create a box for each car
        for (Car car : showCarsList) {
            JPanel carBox = createCarBox(car);
            carListContainer.add(carBox);
            carListContainer.add(Box.createRigidArea(new Dimension(0, 15))); // Space between car boxes
        }

        // Add the car container to a scroll pane in case there are many cars
        JScrollPane scrollPane = new JScrollPane(carListContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(scrollPane);
    }

    private JPanel createCarBox(Car car) {
        // Main container for car info
        JPanel carBox = new JPanel();
        carBox.setLayout(new BorderLayout());
        carBox.setBackground(new Color(240, 240, 240)); 
        carBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        carBox.setMaximumSize(new Dimension(800, 150)); 

        // Left panel for car details
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 1));
        infoPanel.setBackground(new Color(240, 240, 240));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // Create and style the information labels
        JLabel carIdLabel = new JLabel("Car ID: " + car.getCarId());
        carIdLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel brandLabel = new JLabel("Brand: " + car.getBrand());
        brandLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel priceLabel = new JLabel("Price: $" + String.format("%,.2f", (double) car.getPrice()));
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        
        String salesmanId = car.getSalesmanId();
        String salesmanName = SalesmanList.getSalesmanNameById(salesmanId);
        JLabel salesmanLabel = new JLabel("Salesman: " + salesmanName);
        salesmanLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Add labels to the info panel
        infoPanel.add(carIdLabel);
        infoPanel.add(brandLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(salesmanLabel);

        // Right panel for the Book button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 240, 240));

        // Create and style the Book button
        JButton bookButton = new JButton("Book");
        ButtonStyler.stylePrimaryButton(bookButton);
        bookButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        // Add action listener to the button
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerId = customer.getCustomerId(); // Get current logged in customer ID

                int confirm = JOptionPane.showConfirmDialog(
                    CarPage,  // parent component (your panel or frame)
                    "Confirm booking for " + car.getBrand() + " (ID: " + car.getCarId() + ")",  // message
                    "Confirm Booking",  // dialog title
                    JOptionPane.OK_CANCEL_OPTION,  // options: OK and Cancel buttons
                    JOptionPane.QUESTION_MESSAGE  // icon type
                );

                if (confirm == JOptionPane.OK_OPTION) {
                    try {
                        // Load existing requests
                        ArrayList<CarRequest> requests = CarRequest.loadCarRequestDataFromFile();
                        
                         // Check if this car is already requested
                        boolean alreadyRequested = false;
                        boolean rejected = false;
                        String reason = "";
                        for (CarRequest req : requests) {
                            if (req.getCarID().equals(car.getCarId()) && req.getCustomerID().equals(customerId) ) {
                                alreadyRequested = true;
                                if ((req.getRequestStatus().equals("rejected"))) {
                                    rejected = true;
                                    reason = req.getComment();
                                }
                                break;
                            }
                        }
                        if (reason.equals(".") || reason.trim().isEmpty()) {
                            reason = "Not provided.";
                        }
                        if (rejected) {
                            JOptionPane.showMessageDialog(CarPage,
                                    "Your previous booking has been rejected by saleman.\nReason: " + reason,
                                    "Car Not Available",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (alreadyRequested) {
                            JOptionPane.showMessageDialog(CarPage,
                                    "You have already requested for a booking for this car. Please wait for approval.",
                                    "Car Not Available",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    
                        // Add new request
                        CarRequest newRequest = new CarRequest(
                                customerId,
                                car.getCarId(),
                                car.getSalesmanId(),
                                "pending", // Initial status is pending
                                "."
                        );

                        requests.add(newRequest);
                        CarRequest.writeCarRequests(requests);

                        // Show success message
                        JOptionPane.showMessageDialog(CarPage,
                                "Car booking request submitted successfully!\n" +
                                "Car: " + car.getBrand() + " (ID: " + car.getCarId() + ")\n" +
                                "Status: Pending approval by salesman",
                                "Booking Submitted",
                                JOptionPane.INFORMATION_MESSAGE);

                        // Refresh the car list to update availability
                        //refreshCarPage();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(CarPage,
                                "Error creating booking: " + ex.getMessage(),
                                "Booking Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        buttonPanel.add(bookButton);

        // Add components to the main car box
        carBox.add(infoPanel, BorderLayout.CENTER);
        carBox.add(buttonPanel, BorderLayout.EAST);

        return carBox;
    }
    
    private void createFeedbackPage() {
        
        ArrayList<CarRequest> bookings = CarRequest.getRequestsByCustomerID(customer.getCustomerId());
        List<SoldCarRecord> purchases = SoldCarRecord.findByCustomerID(customer.getCustomerId());
        
        JPanel contentPanel, bookingPanel, purchasePanel, headerPanel;
        JLabel title1, title2, noBookingLabel, purchasePlaceholder, headerLabel;
        JScrollPane scrollPane;
        JTable bookingTable;
        DefaultTableModel tableModel;
        
        feedbackPanel = createBasicPagePanel("Page 3");
        
        // Get the content panel (which is at index 1 in BorderLayout.CENTER)
        contentPanel = (JPanel) ((BorderLayout) feedbackPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(248, 249, 250));
        
        // Page-specific content 
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainContainer.setBackground(new Color(248, 249, 250));

        // --- Booking Panel ---
        bookingPanel = new JPanel();
        bookingPanel.setLayout(new BorderLayout());
        bookingPanel.setBackground(Color.WHITE);
        bookingPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Booking header
        JPanel bookingHeaderPanel = new JPanel(new BorderLayout());
        bookingHeaderPanel.setBackground(Color.WHITE);
        title1 = new JLabel("Booking History");
        title1.setFont(new Font("Arial", Font.BOLD, 18));
        title1.setForeground(new Color(52, 58, 64));
        title1.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        bookingHeaderPanel.add(title1, BorderLayout.WEST);

        // Create a panel for count and refresh button
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightPanel.setBackground(Color.WHITE);

        // Booking count badge
        JLabel bookingCountLabel = new JLabel(bookings.size() + " booking(s)");
        bookingCountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bookingCountLabel.setForeground(new Color(108, 117, 125));
        bookingCountLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        bookingCountLabel.setBackground(new Color(248, 249, 250));
        bookingCountLabel.setOpaque(true);

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 12));
        refreshButton.setBackground(PRIMARY_COLOR);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add action listener for refresh
        refreshButton.addActionListener(e -> refreshCarBookingHistoryPage());

        rightPanel.add(bookingCountLabel);
        rightPanel.add(refreshButton);
        bookingHeaderPanel.add(rightPanel, BorderLayout.EAST);

        // Booking content
        JPanel bookingContentPanel = new JPanel(new BorderLayout());
        bookingContentPanel.setBackground(Color.WHITE);

        if (bookings.isEmpty()) {
            JPanel emptyStatePanel = createEmptyStatePanel(
                "No Booking History", 
                "You haven't made any car booking requests yet.",
                "📋"
            );
            bookingContentPanel.add(emptyStatePanel, BorderLayout.CENTER);
        } else {
            String[] columns = {"Car ID", "Salesman ID", "Status", "Comment"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table non-editable
                }
            };

            for (CarRequest booking : bookings) {
                Object[] rowData = {
                    booking.getCarID(),
                    booking.getSalesmanID(),
                    booking.getRequestStatus(),
                    booking.getComment() != null ? booking.getComment() : "No comment"
                };
                tableModel.addRow(rowData);
            }

            bookingTable = new JTable(tableModel);
            styleTable(bookingTable);

            scrollPane = new JScrollPane(bookingTable);
            scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
            scrollPane.setBackground(Color.WHITE);
            scrollPane.getViewport().setBackground(Color.WHITE);

            // Set preferred height based on content
            int rowHeight = bookingTable.getRowHeight();
            int headerHeight = bookingTable.getTableHeader().getPreferredSize().height;
            int maxVisibleRows = Math.min(bookings.size(), 6); // Show max 6 rows
            int preferredHeight = headerHeight + (rowHeight * maxVisibleRows) + 10;
            scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, preferredHeight));

            bookingContentPanel.add(scrollPane, BorderLayout.CENTER);
        }

        bookingPanel.add(bookingHeaderPanel, BorderLayout.NORTH);
        bookingPanel.add(bookingContentPanel, BorderLayout.SOUTH);

        // --- Purchase Panel ---
        purchasePanel = new JPanel();
        purchasePanel.setLayout(new BorderLayout());
        purchasePanel.setBackground(Color.WHITE);
        purchasePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Purchase header
        JPanel purchaseHeaderPanel = new JPanel(new BorderLayout());
        purchaseHeaderPanel.setBackground(Color.WHITE);
        title2 = new JLabel("Purchase History");
        title2.setFont(new Font("Arial", Font.BOLD, 18));
        title2.setForeground(new Color(52, 58, 64));
        title2.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        purchaseHeaderPanel.add(title2, BorderLayout.WEST);

        // Create a panel for count and refresh button
        JPanel purchaseRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        purchaseRightPanel.setBackground(Color.WHITE);

        // Purchase count badge
        JLabel purchaseCountLabel = new JLabel(purchases.size() + " purchase(s)");
        purchaseCountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        purchaseCountLabel.setForeground(new Color(108, 117, 125));
        purchaseCountLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        purchaseCountLabel.setBackground(new Color(248, 249, 250));
        purchaseCountLabel.setOpaque(true);

        // Refresh button for purchases
        JButton purchaseRefreshButton = new JButton("Refresh");
        purchaseRefreshButton.setFont(new Font("Arial", Font.PLAIN, 12));
        purchaseRefreshButton.setBackground(PRIMARY_COLOR);
        purchaseRefreshButton.setForeground(Color.WHITE);
        purchaseRefreshButton.setFocusPainted(false);
        purchaseRefreshButton.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        purchaseRefreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add action listener for purchase refresh
        purchaseRefreshButton.addActionListener(e -> refreshCarPurchaseHistoryPage());

        purchaseRightPanel.add(purchaseCountLabel);
        purchaseRightPanel.add(purchaseRefreshButton);
        purchaseHeaderPanel.add(purchaseRightPanel, BorderLayout.EAST);

        // Purchase content
        JPanel purchaseContentPanel = new JPanel(new BorderLayout());
        purchaseContentPanel.setBackground(Color.WHITE);

        if (purchases.isEmpty()) {
            JPanel emptyStatePanel = createEmptyStatePanel(
                "No Purchase History", 
                "You haven't made any car purchases yet.",
                "🛒"
            );
            purchaseContentPanel.add(emptyStatePanel, BorderLayout.CENTER);
        } else {
            // Define columns for purchase table
            String[] purchaseColumns = {"Car ID", "Purchase Date", "Amount", "Salesman ID", "Comment"};
            DefaultTableModel purchaseTableModel = new DefaultTableModel(purchaseColumns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table non-editable
                }
            };

            // Populate table with purchase data
            for (SoldCarRecord purchase : purchases) {
                Object[] rowData = {
                    purchase.getCarID(),
                    purchase.getBuyingDate(),
                    purchase.getCarPrice(),
                    purchase.getSalesmanID(),
                    purchase.getComment() != null ? purchase.getComment() : "No comment"
                };
                purchaseTableModel.addRow(rowData);
            }

            // Create table and add to panel
            JTable purchaseTable = new JTable(purchaseTableModel);
            purchaseTable.setRowHeight(30);

            // Add table to scroll pane and add to content panel
            JScrollPane scrollPane2 = new JScrollPane(purchaseTable);
            purchaseContentPanel.add(scrollPane2, BorderLayout.CENTER);
        }

        purchasePanel.add(purchaseHeaderPanel, BorderLayout.NORTH);
        purchasePanel.add(purchaseContentPanel, BorderLayout.CENTER); 

        // Add panels to main container
        mainContainer.add(bookingPanel);
        mainContainer.add(Box.createVerticalStrut(20));
        mainContainer.add(purchasePanel);

        // Add main container to content panel with scrolling capability
        JScrollPane mainScrollPane = new JScrollPane(mainContainer);
        mainScrollPane.setBorder(null);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        contentPanel.add(mainScrollPane, BorderLayout.CENTER);
    }
    
    private void createCarHistoryPage() {
        carHistoryPage = createBasicPagePanel("Booking & Purchase History");

        JPanel contentPanel, bookingPanel, purchasePanel, headerPanel;
        JLabel title1, title2, noBookingLabel, purchasePlaceholder, headerLabel;
        JScrollPane scrollPane;
        JTable bookingTable;
        DefaultTableModel tableModel;

        // Get the content panel
        contentPanel = (JPanel) ((BorderLayout) carHistoryPage.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(248, 249, 250));

        // Create main container with proper margins
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainContainer.setBackground(new Color(248, 249, 250));

        // --- Booking Panel ---
        ArrayList<CarRequest> bookings = CarRequest.getRequestsByCustomerID(customer.getCustomerId());
        bookingPanel = new JPanel();
        bookingPanel.setLayout(new BorderLayout());
        bookingPanel.setBackground(Color.WHITE);
        bookingPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Booking header
        JPanel bookingHeaderPanel = new JPanel(new BorderLayout());
        bookingHeaderPanel.setBackground(Color.WHITE);
        title1 = new JLabel("Booking History");
        title1.setFont(new Font("Arial", Font.BOLD, 18));
        title1.setForeground(new Color(52, 58, 64));
        title1.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        bookingHeaderPanel.add(title1, BorderLayout.WEST);

        // Create a panel for count and refresh button
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightPanel.setBackground(Color.WHITE);

        // Booking count badge
        JLabel bookingCountLabel = new JLabel(bookings.size() + " booking(s)");
        bookingCountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bookingCountLabel.setForeground(new Color(108, 117, 125));
        bookingCountLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        bookingCountLabel.setBackground(new Color(248, 249, 250));
        bookingCountLabel.setOpaque(true);

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 12));
        refreshButton.setBackground(PRIMARY_COLOR);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add action listener for refresh
        refreshButton.addActionListener(e -> refreshCarBookingHistoryPage());

        rightPanel.add(bookingCountLabel);
        rightPanel.add(refreshButton);
        bookingHeaderPanel.add(rightPanel, BorderLayout.EAST);

        // Booking content
        JPanel bookingContentPanel = new JPanel(new BorderLayout());
        bookingContentPanel.setBackground(Color.WHITE);

        if (bookings.isEmpty()) {
            JPanel emptyStatePanel = createEmptyStatePanel(
                "No Booking History", 
                "You haven't made any car booking requests yet.",
                "📋"
            );
            bookingContentPanel.add(emptyStatePanel, BorderLayout.CENTER);
        } else {
            String[] columns = {"Car ID", "Salesman ID", "Status", "Comment"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table non-editable
                }
            };

            for (CarRequest booking : bookings) {
                Object[] rowData = {
                    booking.getCarID(),
                    booking.getSalesmanID(),
                    booking.getRequestStatus(),
                    booking.getComment() != null ? booking.getComment() : "No comment"
                };
                tableModel.addRow(rowData);
            }

            bookingTable = new JTable(tableModel);
            styleTable(bookingTable);

            scrollPane = new JScrollPane(bookingTable);
            scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
            scrollPane.setBackground(Color.WHITE);
            scrollPane.getViewport().setBackground(Color.WHITE);

            // Set preferred height based on content
            int rowHeight = bookingTable.getRowHeight();
            int headerHeight = bookingTable.getTableHeader().getPreferredSize().height;
            int maxVisibleRows = Math.min(bookings.size(), 6); // Show max 6 rows
            int preferredHeight = headerHeight + (rowHeight * maxVisibleRows) + 10;
            scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, preferredHeight));

            bookingContentPanel.add(scrollPane, BorderLayout.CENTER);
        }

        bookingPanel.add(bookingHeaderPanel, BorderLayout.NORTH);
        bookingPanel.add(bookingContentPanel, BorderLayout.SOUTH);

        // --- Purchase Panel ---
        List<SoldCarRecord> purchases = SoldCarRecord.findByCustomerID(customer.getCustomerId());
        purchasePanel = new JPanel();
        purchasePanel.setLayout(new BorderLayout());
        purchasePanel.setBackground(Color.WHITE);
        purchasePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Purchase header
        JPanel purchaseHeaderPanel = new JPanel(new BorderLayout());
        purchaseHeaderPanel.setBackground(Color.WHITE);
        title2 = new JLabel("Purchase History");
        title2.setFont(new Font("Arial", Font.BOLD, 18));
        title2.setForeground(new Color(52, 58, 64));
        title2.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        purchaseHeaderPanel.add(title2, BorderLayout.WEST);

        // Create a panel for count and refresh button
        JPanel purchaseRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        purchaseRightPanel.setBackground(Color.WHITE);

        // Purchase count badge
        JLabel purchaseCountLabel = new JLabel(purchases.size() + " purchase(s)");
        purchaseCountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        purchaseCountLabel.setForeground(new Color(108, 117, 125));
        purchaseCountLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        purchaseCountLabel.setBackground(new Color(248, 249, 250));
        purchaseCountLabel.setOpaque(true);

        // Refresh button for purchases
        JButton purchaseRefreshButton = new JButton("Refresh");
        purchaseRefreshButton.setFont(new Font("Arial", Font.PLAIN, 12));
        purchaseRefreshButton.setBackground(PRIMARY_COLOR);
        purchaseRefreshButton.setForeground(Color.WHITE);
        purchaseRefreshButton.setFocusPainted(false);
        purchaseRefreshButton.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        purchaseRefreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add action listener for purchase refresh
        purchaseRefreshButton.addActionListener(e -> refreshCarPurchaseHistoryPage());

        purchaseRightPanel.add(purchaseCountLabel);
        purchaseRightPanel.add(purchaseRefreshButton);
        purchaseHeaderPanel.add(purchaseRightPanel, BorderLayout.EAST);

        // Purchase content
        JPanel purchaseContentPanel = new JPanel(new BorderLayout());
        purchaseContentPanel.setBackground(Color.WHITE);

        if (purchases.isEmpty()) {
            JPanel emptyStatePanel = createEmptyStatePanel(
                "No Purchase History", 
                "You haven't made any car purchases yet.",
                "🛒"
            );
            purchaseContentPanel.add(emptyStatePanel, BorderLayout.CENTER);
        } else {
            // Define columns for purchase table
            String[] purchaseColumns = {"Car ID", "Purchase Date", "Amount", "Salesman ID", "Comment"};
            DefaultTableModel purchaseTableModel = new DefaultTableModel(purchaseColumns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table non-editable
                }
            };

            // Populate table with purchase data
            for (SoldCarRecord purchase : purchases) {
                Object[] rowData = {
                    purchase.getCarID(),
                    purchase.getBuyingDate(),
                    purchase.getCarPrice(),
                    purchase.getSalesmanID(),
                    purchase.getComment() != null ? purchase.getComment() : "No comment"
                };
                purchaseTableModel.addRow(rowData);
            }

            // Create table and add to panel
            JTable purchaseTable = new JTable(purchaseTableModel);
            styleTable(purchaseTable);

            scrollPane = new JScrollPane(purchaseTable);
            scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1));
            scrollPane.setBackground(Color.WHITE);
            scrollPane.getViewport().setBackground(Color.WHITE);

            // Set preferred height based on content
            int rowHeight = purchaseTable.getRowHeight();
            int headerHeight = purchaseTable.getTableHeader().getPreferredSize().height;
            int maxVisibleRows = Math.min(bookings.size(), 6); // Show max 6 rows
            int preferredHeight = headerHeight + (rowHeight * maxVisibleRows) + 10;
            scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, preferredHeight));

            // Add table to scroll pane and add to content panel
            //JScrollPane scrollPane2 = new JScrollPane(purchaseTable);
            purchaseContentPanel.add(scrollPane, BorderLayout.CENTER);
        }

        purchasePanel.add(purchaseHeaderPanel, BorderLayout.NORTH);
        purchasePanel.add(purchaseContentPanel, BorderLayout.CENTER); 

        // Add panels to main container
        mainContainer.add(bookingPanel);
        mainContainer.add(Box.createVerticalStrut(20));
        mainContainer.add(purchasePanel);

        // Add main container to content panel with scrolling capability
        JScrollPane mainScrollPane = new JScrollPane(mainContainer);
        mainScrollPane.setBorder(null);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        contentPanel.add(mainScrollPane, BorderLayout.CENTER);
    }
    
    private void refreshCarBookingHistoryPage() {
        frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        try {
            cards.remove(carHistoryPage);
            createCarHistoryPage();
            cards.add(carHistoryPage, "CarHistory");
            cardLayout.show(cards, "CarHistory");

            for (int i = 0; i < navButtons.length; i++) {
                if (navButtons[i].getActionCommand().equals("CarHistory")) {
                    updateNavButtonsState(i);
                    break;
                }
            }

            // Revalidate and repaint to ensure UI updates
            cards.revalidate();
            cards.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, 
                "Error refreshing data: " + ex.getMessage(), 
                "Refresh Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // For debugging
        } finally {
            // Reset cursor
            frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private void refreshCarPurchaseHistoryPage() {
        frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        try {
            // Remove and recreate the entire car history page to refresh both booking and purchase data
            cards.remove(carHistoryPage);
            createCarHistoryPage();
            cards.add(carHistoryPage, "CarHistory");
            cardLayout.show(cards, "CarHistory");

            for (int i = 0; i < navButtons.length; i++) {
                if (navButtons[i].getActionCommand().equals("CarHistory")) {
                    updateNavButtonsState(i);
                    break;
                }
            }

            // Revalidate and repaint to ensure UI updates
            cards.revalidate();
            cards.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, 
                "Error refreshing purchase data: " + ex.getMessage(), 
                "Refresh Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // For debugging
        } finally {
            // Reset cursor
            frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    private void styleTable(JTable table) {
        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(new Color(248, 249, 250));
        header.setForeground(new Color(52, 58, 64));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(222, 226, 230)));
        header.setReorderingAllowed(false);

        // Table styling
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(240, 248, 255));
        table.setSelectionForeground(new Color(52, 58, 64));
        table.setFillsViewportHeight(true);

        // Set column widths
        if (table.getColumnCount() == 4) {
            table.getColumnModel().getColumn(0).setPreferredWidth(80);   // Car ID
            table.getColumnModel().getColumn(1).setPreferredWidth(100);  // Salesman ID
            table.getColumnModel().getColumn(2).setPreferredWidth(80);   // Status
            table.getColumnModel().getColumn(3).setPreferredWidth(200);  // Comment
        }

        // Custom cell renderer for better visual appearance
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(248, 249, 250));
                    }
                }

                // Status column styling
                if (column == 2 && value != null) {
                    String status = value.toString().toLowerCase();
                    switch (status) {
                        case "pending":
                            setForeground(new Color(255, 140, 0)); // Orange
                            break;
                        case "approved":
                            setForeground(new Color(40, 167, 69)); // Green
                            break;
                        case "rejected":
                            setForeground(new Color(220, 53, 69)); // Red
                            break;
                        default:
                            setForeground(new Color(52, 58, 64)); // Default
                    }
                } else {
                    setForeground(new Color(52, 58, 64));
                }

                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        });
    }

    private JPanel createEmptyStatePanel(String title, String message, String emoji) {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
        emptyPanel.setBackground(Color.WHITE);
        emptyPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));

        // Emoji
        JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        emojiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(52, 58, 64));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Message
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(108, 117, 125));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        emptyPanel.add(emojiLabel);
        emptyPanel.add(Box.createVerticalStrut(15));
        emptyPanel.add(titleLabel);
        emptyPanel.add(Box.createVerticalStrut(8));
        emptyPanel.add(messageLabel);

        return emptyPanel;
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
        topPanel.setBorder(new EmptyBorder(10, 40, 20, 40));
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