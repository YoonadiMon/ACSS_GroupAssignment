package Customer;

import Car.CarRequest;
import Car.SoldCarRecord;
import Salesman.SalesmanList;
import Utils.ButtonStyler;
import Utils.WindowNav;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author YOON
 */
public class CustomerDashboard implements ActionListener   {
    
    private JFrame frame;
    private JPanel cards;
    private CardLayout cardLayout;
    private Customer customer;
    
    // Pages
    private JPanel mainPage, carPage, feedbackPage, carHistoryPage, feedbackHistoryPanel;
    
    // Navigation buttons
    private JButton[] navButtons;

    // Colors
    private final Color PRIMARY_COLOR = new Color(0, 84, 159);
    private final Color LIGHT_TEXT_COLOR = new Color(111, 143, 175);
    private final Color BACKGROUND_COLOR = Color.WHITE;

    public CustomerDashboard(Customer customer) {
        this.customer = customer;

        frame = new JFrame("Customer Dashboard");
        frame.setSize(860, 620);
        frame.setLocationRelativeTo(null);
        WindowNav.setCloseOperation(frame, () -> new CustomerLandingGUI());
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Card layout to switch between login and register pages
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        
        createPages();
        
        createFeedbackPage();
        createCarHistoryPage();
        createFeedbackHistoryPage();
        
        // Add panels to the card layout
        cards.add(mainPage, "Main");
        cards.add(carPage, "Cars");
        cards.add(feedbackPage, "Feedbacks");
        cards.add(carHistoryPage, "CarHistory");
        cards.add(feedbackHistoryPanel, "FeedbacksHistory");
        
        // Create navigation panel
        JPanel navigationPanel = createNavigationPanel();
        
        // Add components to the main frame
        frame.setLayout(new BorderLayout());
        frame.add(navigationPanel, BorderLayout.NORTH);
        frame.add(cards, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }
    
    private void createPages() {
        // Create instances of page classes
        MainPage mainPageCreator = new MainPage();
        CarPage carPageCreator = new CarPage();
        //FeedbackPage feedbackPageCreator = new FeedbackPage();
        
        // Create pages using the page creator instances
        
        mainPage = mainPageCreator.createPage(customer, frame);
        carPage = carPageCreator.createPage(customer, frame);
        //feedbackPage = feedbackPageCreator.createPage(customer, frame);
        
    }
    
    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel(new GridLayout(1, 5, 5, 0));
        navPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        navPanel.setBackground(BACKGROUND_COLOR);
        
        navButtons = new JButton[5];
        String[] buttonLabels = {"Main", "Cars", "Feedbacks", "Cars History", "Feedbacks History"};
        String[] cardNames = {"Main", "Cars", "Feedbacks", "CarHistory", "FeedbacksHistory"};
        
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
    
    private void createFeedbackPage() {
        // Get customer's bookings and purchases
        ArrayList<CarRequest> bookings = CarRequest.getRequestsByCustomerID(customer.getCustomerId());
        List<SoldCarRecord> purchases = SoldCarRecord.findByCustomerID(customer.getCustomerId());

        // Extract booking information (cars they looked at but haven't purchased)
        List<String> bookedCarIDs = new ArrayList<>();
        Map<String, String> bookedCarSalesmanMap = new HashMap<>(); // Maps carID to salesmanID

        //for (CarRequest booking : bookings) {
        //    String carID = booking.getCarID();
        //    String salesmanID = booking.getSalesmanID();
        //    bookedCarIDs.add(carID);
        //    bookedCarSalesmanMap.put(carID, salesmanID);
        //}
        
        for (CarRequest booking : bookings) {
            // Only include requests that were pending -> approved -> cancelled (meaning customer viewed the car but didn't buy)
            if ("cancelled".equals(booking.getRequestStatus())) {
                String carID = booking.getCarID();
                String salesmanID = booking.getSalesmanID();

                bookedCarIDs.add(carID);
                bookedCarSalesmanMap.put(carID, salesmanID);
            }
        }

        // Extract purchase information (cars they bought)
        List<String> purchasedCarIDs = new ArrayList<>();
        Map<String, String> purchasedCarSalesmanMap = new HashMap<>(); // Maps carID to salesmanID

        for (SoldCarRecord purchase : purchases) {
            String carID = purchase.getCarID();
            String salesmanID = purchase.getSalesmanID();

            purchasedCarIDs.add(carID);
            purchasedCarSalesmanMap.put(carID, salesmanID);
        }

        List<String> onlyBookedCarIDs = new ArrayList<>(bookedCarIDs);
        onlyBookedCarIDs.removeAll(purchasedCarIDs);

        feedbackPage = createBasicPagePanel("Thank You for Your Feedback");

        // Create main content panel with consistent padding
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create salesmen panel with consistent alignment
        JPanel salesmanPanel = new JPanel();
        salesmanPanel.setLayout(new BoxLayout(salesmanPanel, BoxLayout.Y_AXIS));
        salesmanPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 128), 1), "Salesmen"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Get all unique salesmen from both bookings and purchases
        Set<String> allSalesmenIDs = new HashSet<>();
        allSalesmenIDs.addAll(bookedCarSalesmanMap.values());
        allSalesmenIDs.addAll(purchasedCarSalesmanMap.values());

        // Add salesmen to panel with consistent style
        for (String salesmanID : allSalesmenIDs) {
            // Get salesman name instead of just showing ID
            String salesmanName = SalesmanList.getSalesmanNameById(salesmanID);

            // Create a panel for each salesman with better alignment
            JPanel salesmanItemPanel = new JPanel();
            salesmanItemPanel.setLayout(new BoxLayout(salesmanItemPanel, BoxLayout.X_AXIS));
            salesmanItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            salesmanItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            JLabel salesmanLabel = new JLabel("Salesman: " + salesmanName );
            salesmanLabel.setPreferredSize(new Dimension(150, 25));

            // Add feedback button with consistent size
            JButton feedbackBtn = new JButton("Give Feedback");
            feedbackBtn.setPreferredSize(new Dimension(130, 25));
            feedbackBtn.addActionListener(e -> {
                showSimpleFeedbackDialog(
                        "Salesman: " + salesmanName,
                        CustomerFeedbacks.TYPE_SALESMAN, 
                        salesmanID);
            });

            salesmanItemPanel.add(salesmanLabel);
            salesmanItemPanel.add(Box.createHorizontalStrut(10)); // Add consistent spacing
            salesmanItemPanel.add(feedbackBtn);
            salesmanItemPanel.add(Box.createHorizontalGlue()); // Push components to the left
            salesmanPanel.add(salesmanItemPanel);
            salesmanPanel.add(Box.createVerticalStrut(10)); // Consistent vertical spacing
        }

        // Create cars panel with matching style
        JPanel carPanel = new JPanel();
        carPanel.setLayout(new BoxLayout(carPanel, BoxLayout.Y_AXIS));
        carPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 128), 1), "Cars"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Add purchased cars (labeled as "Purchased") with consistent styling
        if (!purchasedCarIDs.isEmpty()) {
            JLabel purchasedHeader = new JLabel("Purchased Cars:");
            purchasedHeader.setFont(purchasedHeader.getFont().deriveFont(Font.BOLD));
            purchasedHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
            carPanel.add(purchasedHeader);
            carPanel.add(Box.createVerticalStrut(8));

            for (String carID : purchasedCarIDs) {
                JPanel carItemPanel = new JPanel();
                carItemPanel.setLayout(new BoxLayout(carItemPanel, BoxLayout.X_AXIS));
                carItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                carItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

                JLabel carLabel = new JLabel("• Car ID: " + carID + " (Purchased)");
                carLabel.setForeground(new Color(0, 128, 0)); // Green color for purchased
                carLabel.setPreferredSize(new Dimension(200, 25));

                // Add feedback button with consistent size
                JButton feedbackBtn = new JButton("Give Feedback");
                feedbackBtn.setPreferredSize(new Dimension(130, 25));
                feedbackBtn.addActionListener(e -> {
                    showSimpleFeedbackDialog(
                            "Purchased Car: " + carID,
                            CustomerFeedbacks.TYPE_CAR_PURCHASED, 
                            carID);
                });

                carItemPanel.add(carLabel);
                carItemPanel.add(Box.createHorizontalStrut(10)); // Consistent spacing
                carItemPanel.add(feedbackBtn);
                carItemPanel.add(Box.createHorizontalGlue()); // Push components to the left
                carPanel.add(carItemPanel);
                carPanel.add(Box.createVerticalStrut(5));
            }
            carPanel.add(Box.createVerticalStrut(10));
        }

        // Add viewed cars (only booked, not purchased) with matching styling
        if (!onlyBookedCarIDs.isEmpty()) {
            JLabel viewedHeader = new JLabel("Viewed Cars that weren't purchased:");
            viewedHeader.setFont(viewedHeader.getFont().deriveFont(Font.BOLD));
            viewedHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
            carPanel.add(viewedHeader);
            carPanel.add(Box.createVerticalStrut(8));

            for (String carID : onlyBookedCarIDs) {
                JPanel carItemPanel = new JPanel();
                carItemPanel.setLayout(new BoxLayout(carItemPanel, BoxLayout.X_AXIS));
                carItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                carItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

                JLabel carLabel = new JLabel("• Car ID: " + carID + " (Viewed)");
                carLabel.setForeground(new Color(128, 128, 128)); // Gray color for viewed only
                carLabel.setPreferredSize(new Dimension(200, 25));

                // Add feedback button with consistent size
                JButton feedbackBtn = new JButton("Give Feedback");
                feedbackBtn.setPreferredSize(new Dimension(130, 25));
                feedbackBtn.addActionListener(e -> {
                    showSimpleFeedbackDialog(
                            "Viewed Car: " + carID,
                            CustomerFeedbacks.TYPE_CAR_VIEWED, 
                            carID);
                });

                carItemPanel.add(carLabel);
                carItemPanel.add(Box.createHorizontalStrut(10)); // Consistent spacing
                carItemPanel.add(feedbackBtn);
                carItemPanel.add(Box.createHorizontalGlue()); // Push components to the left
                carPanel.add(carItemPanel);
                carPanel.add(Box.createVerticalStrut(5));
            }
        }

        // If no cars at all
        if (purchasedCarIDs.isEmpty() && onlyBookedCarIDs.isEmpty()) {
            JLabel noInteractionLabel = new JLabel("No car interactions found.");
            noInteractionLabel.setForeground(Color.GRAY);
            noInteractionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            carPanel.add(noInteractionLabel);
        }

        // Create a panel to hold both salesman and car panels side by side with better spacing
        JPanel infoPanel = new JPanel(new GridLayout(1, 2, 20, 0)); // Increased horizontal gap

        // Use scroll panes with consistent behavior
        JScrollPane salesmanScrollPane = new JScrollPane(salesmanPanel);
        salesmanScrollPane.setBorder(BorderFactory.createEmptyBorder());
        salesmanScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JScrollPane carScrollPane = new JScrollPane(carPanel);
        carScrollPane.setBorder(BorderFactory.createEmptyBorder());
        carScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        infoPanel.add(salesmanScrollPane);
        infoPanel.add(carScrollPane);

        // Add the info panel to content panel
        contentPanel.add(infoPanel, BorderLayout.CENTER);
        
        // Create a refresh button panel
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("Refresh Data");
        ButtonStyler.stylePrimaryButton(refreshButton);
        refreshButton.setPreferredSize(new Dimension(120, 30));
        // No icon - removed to fix NullPointerException
        refreshButton.addActionListener(e -> refreshFeedbackPage());
        refreshPanel.add(refreshButton);
        
        // Add the refresh panel to the top of the content panel
        contentPanel.add(refreshPanel, BorderLayout.NORTH);

        feedbackPage.add(contentPanel, BorderLayout.CENTER);
    }

    private void refreshFeedbackPage() {
        frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {
            // Remove and recreate the feedback page
            cards.remove(feedbackPage);
            createFeedbackPage();
            cards.add(feedbackPage, "Feedbacks");  
            cardLayout.show(cards, "Feedbacks");   

            // Update navigation button state
            for (int i = 0; i < navButtons.length; i++) {
                if (navButtons[i].getActionCommand().equals("Feedbacks")) {  
                    updateNavButtonsState(i);
                    break;
                }
            }

            // Revalidate and repaint to ensure UI updates
            cards.revalidate();
            cards.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, 
                "Error refreshing feedback data: " + ex.getMessage(), 
                "Refresh Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // For debugging
        } finally {
            // Reset cursor
            frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    private void showSimpleFeedbackDialog(String itemName, String feedbackType, String itemId) {
        // Show simple rating dialog
        String ratingInput = JOptionPane.showInputDialog(
                feedbackPage,
                "Please rate (" + itemName + ") (1-5 stars):",
                "Rating",
                JOptionPane.QUESTION_MESSAGE);
        // Check if user canceled the rating dialog
        if (ratingInput == null || ratingInput.trim().isEmpty()) {
            return;
        }
        // Parse rating
        int rating;
        try {
            rating = Integer.parseInt(ratingInput.trim());
            if (rating < 1 || rating > 5) {
                JOptionPane.showMessageDialog(
                    feedbackPage,
                    "Please enter a rating between 1 and 5.",
                    "Invalid Rating",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                feedbackPage,
                "Please enter a valid number between 1 and 5.",
                "Invalid Rating",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Show review dialog with validation loop
        String review = null;
        while (review == null || review.trim().isEmpty()) {
            review = JOptionPane.showInputDialog(
                    feedbackPage,
                    "Please share your feedback about " + itemName + ":\n(Feedback cannot be empty)",
                    "Feedback",
                    JOptionPane.PLAIN_MESSAGE);
            // If user cancels, exit the method
            if (review == null) {
                return;
            }
            // Check if review is empty or only whitespace
            if (review.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                    feedbackPage,
                    "Feedback cannot be empty. Please enter your feedback.",
                    "Empty Feedback",
                    JOptionPane.WARNING_MESSAGE);
            }
        }


        // Properly escape for CSV: replace quotes with double quotes and wrap in quotes
        String sanitizedReview = "\"" + review.replace("\"", "\"\"") + "\"";

        CustomerFeedbacks feedback = new CustomerFeedbacks(
                customer.getCustomerId(),
                itemId,
                feedbackType,
                rating,
                sanitizedReview
        );

        boolean success = feedback.saveFeedback();
        if (success) {
            JOptionPane.showMessageDialog(
                    feedbackPage,
                    "Thank you for your feedback!",
                    "Feedback Submitted",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(
                    feedbackPage,
                    "There was an error saving your feedback. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
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
    
    private void createFeedbackHistoryPage() {
        feedbackHistoryPanel = createBasicPagePanel("Feedback History");

        // Get the content panel (which is at index 1 in BorderLayout.CENTER)
        JPanel contentPanel = (JPanel) ((BorderLayout) feedbackHistoryPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);

        // Create refresh button without an icon
        JButton refreshButton = new JButton("Refresh");
        ButtonStyler.stylePrimaryButton(refreshButton);

        // Add action listener to refresh button
        refreshButton.addActionListener(e -> {
            refreshFeedbackHistory();
        });
        
        // Page-specific content 
        contentPanel.setLayout(new BorderLayout(10, 10));

        // Create tabs for different feedback types
        JTabbedPane feedbackTabs = new JTabbedPane();
        feedbackTabs.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackTabs.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        
        feedbackTabs.addTab("Salesman Feedback", createFeedbackList(CustomerFeedbacks.TYPE_SALESMAN));
        feedbackTabs.addTab("Car Feedback", createFeedbackList(null));
        

        // Add tabs to the content panel
        contentPanel.add(refreshButton, BorderLayout.NORTH);
        contentPanel.add(feedbackTabs, BorderLayout.CENTER);
    }
    
    private void refreshFeedbackHistory() {
        frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        try {
            // Remove and recreate the entire car history page to refresh both booking and purchase data
            cards.remove(feedbackHistoryPanel);
            createFeedbackHistoryPage();
            cards.add(feedbackHistoryPanel, "FeedbacksHistory");
            cardLayout.show(cards, "FeedbacksHistory");

            for (int i = 0; i < navButtons.length; i++) {
                if (navButtons[i].getActionCommand().equals("FeedbacksHistory")) {
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
    
    private JScrollPane createFeedbackList(String feedbackType) {
        List<CustomerFeedbacks> allFeedbacks = CustomerFeedbacks.getAllFeedbacks();
        List<CustomerFeedbacks> filteredFeedbacks = new ArrayList<>();

        for (CustomerFeedbacks feedback : allFeedbacks) {
            if (feedbackType == null) {
                // For car feedbacks tab, include both car viewed and purchased
                if (feedback.getFeedbackType().equals(CustomerFeedbacks.TYPE_CAR_VIEWED) || 
                    feedback.getFeedbackType().equals(CustomerFeedbacks.TYPE_CAR_PURCHASED)) {
                    filteredFeedbacks.add(feedback);
                }
            } else if (feedback.getFeedbackType().equals(feedbackType)) {
                filteredFeedbacks.add(feedback);
            }
        }

        // Create a panel to hold all feedback items
        JPanel feedbackListPanel = new JPanel();
        feedbackListPanel.setLayout(new BoxLayout(feedbackListPanel, BoxLayout.Y_AXIS));

        // Add each feedback to the panel
        for (CustomerFeedbacks feedback : filteredFeedbacks) {
            JPanel feedbackPanel = createFeedbackPanel(feedback, 
                feedback.getCustomerId().equals(customer.getCustomerId()));
            feedbackListPanel.add(feedbackPanel);
            feedbackListPanel.add(Box.createVerticalStrut(10));
        }

        // Add some padding at the bottom
        feedbackListPanel.add(Box.createVerticalGlue());

        // Create a scroll pane for the feedback list
        JScrollPane scrollPane = new JScrollPane(feedbackListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        return scrollPane;
    }

    private JPanel createFeedbackPanel(CustomerFeedbacks feedback, boolean isCurrentUser) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // User and rating panel
        JPanel topPanel = new JPanel(new BorderLayout());

        // User label (show "YOU" for the current user)
        String userLabel = isCurrentUser ? "YOU" : "Customer " + feedback.getCustomerId();
        JLabel userInfo = new JLabel(userLabel);
        userInfo.setFont(new Font("Arial", Font.BOLD, 14));
        if (isCurrentUser) {
            userInfo.setForeground(new Color(0, 102, 204));
        }
        topPanel.add(userInfo, BorderLayout.WEST);

        // Rating stars
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        for (int i = 1; i <= 5; i++) {
            JLabel star = new JLabel(i <= feedback.getRating() ? "★" : "☆");
            star.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24));
            star.setForeground(i <= feedback.getRating() ? new Color(255, 204, 0) : Color.GRAY);
            ratingPanel.add(star);
        }
        topPanel.add(ratingPanel, BorderLayout.EAST);
        panel.add(topPanel, BorderLayout.NORTH);

        // Feedback content
        JTextArea reviewText = new JTextArea(feedback.getReview());
        reviewText.setWrapStyleWord(true);
        reviewText.setLineWrap(true);
        reviewText.setEditable(false);
        reviewText.setBackground(panel.getBackground());
        panel.add(reviewText, BorderLayout.CENTER);

        // Item info (car or salesman)
        JLabel itemLabel = new JLabel(getItemInfo(feedback));
        itemLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        panel.add(itemLabel, BorderLayout.SOUTH);

        return panel;
    }

    private String getItemInfo(CustomerFeedbacks feedback) {
        if (feedback.getFeedbackType().equals(CustomerFeedbacks.TYPE_SALESMAN)) {
            return "Salesman ID: " + feedback.getItemId();
        } else if (feedback.getFeedbackType().equals(CustomerFeedbacks.TYPE_CAR_VIEWED)) {
            return "Car Viewed - ID: " + feedback.getItemId();
        } else {
            return "Car Purchased - ID: " + feedback.getItemId();
        }
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
        titleLabel.setForeground(new Color(0, 84, 159));
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
    
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            CustomerDataIO.readCustomer();
//            Customer customer = CustomerDataIO.searchName("Yoonadi");
//            //Customer.Customer@1e9b5f31 14cb8731,Yoon,yoon@email.com,1234567A,true
//            if (customer != null) {
//                new CustomerDashboard(customer);
//            }
//            else {
//                System.out.println("error: "+customer);
//            }
//        });
//    }
    
}